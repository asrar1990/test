package com.bn.device.service.impl;

import com.bn.device.dto.DevicePlatform;
import com.bn.device.dto.VersionInfo;
import com.bn.device.service.DevicePlatformService;
import com.bn.device.service.IMasterDataUtility;
import com.bn.device.service.VersionMapService;
import com.bn.common.dto.util.VersionUtil;
import com.bn.common.exception.BNException;
import com.bn.common.service.ConfigService;
import com.bn.common.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
public class VersionMapServiceImpl implements VersionMapService {

    private static final String SUPPORTED_VERSION_MAP = "supportedVersionMap";
    private static final String VERSION_MAP = "versionMap";
    private static final int MULTIPLIER = 100000;

    @Autowired
    @Qualifier("configServiceImpl")
    private ConfigService configService;

    @Autowired
    @Qualifier("masterDataUtility")
    private IMasterDataUtility masterData;

    @Autowired
    @Qualifier("devicePlatformServiceImpl")
    private DevicePlatformService devicePlatformService;

    /**
     * versionMap stores key-value pairs of modelId and all the client versions for it
     */
    private Map<String, List<VersionInfo>> versionMap = new HashMap<>();

    /**
     * supportedVersionMap stores key-value pairs of modelId + clientVersion and versionInfo for it
     */
    private Map<String, VersionInfo> supportedVersionMap = new HashMap<>();

    @PostConstruct 
    public void init() {
        try {
            log.info("Loading version map into local memory...");
            loadVersionMapAndSupportedVersionMap();
        }
        catch(final Exception ex) {
            log.error("VERSION MAP ERROR: Error loading version map", ex);
        }
    }

    public void loadVersionMapAndSupportedVersionMap() throws BNException {
        final Map<String, List<VersionInfo>> tempVersionMap = loadDBVersionMap();

        if (tempVersionMap.isEmpty()) {
            log.error("VERSION MAP ERROR: Could not load version map...version map is empty");
        }
        else {
            // if the version map returned is not empty, then replace versionMap with tempVersionMap
            log.info("Version map is not empty...swapping version map");
            versionMap = tempVersionMap;
            log.info(String.format("Version map loaded successfully. Version map size= %d", versionMap.size()));

            final Map<String, VersionInfo> tempSupportedVersionMap = loadSupportedVersionMap();

            if (tempSupportedVersionMap.isEmpty()) {
                log.error("VERSION MAP ERROR: Could not load supported version map...supported version map is empty");
            }
            else {
                // if the supported version map returned is not empty, then replace supportedVersionMap with tempSupportedVersionMap
                supportedVersionMap = tempSupportedVersionMap;
                log.info(String.format("Supported version map loaded successfully. Supported version map size=" + supportedVersionMap.size()));
            }
        }
    }

    /**
     * Gets version map from memcache when memcacheEnabled is true or loads version map from bncloud DB version_map table
     * @return mapping of modelId and list of client version info
     */
    @Override
    public Map<String, List<VersionInfo>> getVersionMap() throws BNException {
        return loadVersionMap();
    }

    /**
     * For a given model name and version, what VersionInfo is supported by the Cloud.
     * Results could be cached from previous call.
     * @return always returns something. If nothing can be determined, returns new VersionInfo("TNK", "1.0", version, 1.0f)
     */
    public VersionInfo getSupportVersion(String modelNameIn, String versionIn) throws BNException {
        String version = versionIn;
        String modelName = modelNameIn;
        if(StringUtils.isBlank(version) && StringUtils.isNotBlank(modelName)) {
            version = "1.0";
        }

        if(StringUtils.isNotBlank(version) && Validator.isFloat(version)) {
            version = new Float(version).toString();
        }
        VersionInfo vInfo = null;
        if(StringUtils.isNotBlank(modelName)) {
            modelName = modelName.trim();
            final String tver = trimDevOSVersion(version);
            final String c = modelName + tver;
            final Map<String, VersionInfo> vMap = getSupportedVersionMap();
            //retrieve the supported version
            vInfo = vMap.get(c);
            
            if (vInfo == null) {
                log.debug(String.format("VERSION MAP WARNING: VersionInfo is not found in supportedVersionMap...looking up in versionMap for modelName=%s version=%s", modelName, version));

                // exact match for modelId and client version is not found in supportedVersionMap
                // lookup modelId in versionMap, if exists return a close match to the client version
                vInfo = getVersion(modelName, tver);

                if (vInfo == null) {
                    // version info is not found for the given modelName
                    log.warn(String.format("VERSION MAP WARNING: VersionInfo is not found in versionMap for ModelName=%s version=%s", modelName, version));
                }
            }
        }
        if(vInfo == null) {
            vInfo = new VersionInfo("TNK", "1.0", version, 1.0f);
        }

        return vInfo;
    }

    /**
     * For a given version and license type, determine if the version supports the license type
     * @param vInfo - contains a list of license supported (comma separated)
     * @param type - license type
     * @return
     */
    public static boolean isLicenseSupported(VersionInfo vInfo, int type) {
        if(vInfo != null && StringUtils.isNotBlank(vInfo.getLicenseType())) {
            final String[] tArray = vInfo.getLicenseType().split(",");
            if(tArray.length > 0) {
                for(String aTArray : tArray) {
                    if(StringUtils.isNotBlank(aTArray) && Validator.isInteger(aTArray)) {
                        final int v = new Integer(aTArray);
                        if(v == type) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return never null. Either cached copy or if memcacheEnabled is false, return pre loaded supportedVersionMap
     */
    private Map<String, VersionInfo> getSupportedVersionMap() {
        return supportedVersionMap;
    }

    /*
     * If version is 1.2.3 trim it to 1.2.  Only retain major and minor of a version number
     */
    private static String trimDevOSVersion(String version) {
        if(StringUtils.isNotBlank(version)) {
            final String[] cArry = version.split("\\.");
            if(cArry.length > 2) {
                final int l = cArry[0].length();
                return version.substring(0, version.indexOf('.', l + 1));
            }
        }

        return version;
    }

    /**
     * Find the VersionInfo that best matches the model name and version
     * @param modelName - encore, gossamer
     * @param version - 1.2
     * @return could be null
     */
    private VersionInfo getVersion(String modelName, String version) throws BNException {
        final Map<String, List<VersionInfo>> versionMap = getVersionMap();
        if(versionMap == null) {
            return null;
        }
        final List<VersionInfo> svList = versionMap.get(modelName);
        if(svList == null) {
            return null;
        }
        VersionInfo vInfo = null;
        //from the list of supported value for this model, what is the minimum version
        String v = "0.0";
        for(VersionInfo i : svList) {
            if(VersionUtil.isOneGreaterAndEqual(version, i.getDeviceReportedVersion()) && VersionUtil.isOneGreater(version, v)) {
                final double sv = new Double(i.getDeviceReportedVersion()) * MULTIPLIER;
                final double d = sv / MULTIPLIER;
                v = Double.toString(d);
                vInfo = i;
            }
        }
        return vInfo;
    }

    /**
     * Only called if cached copy could not be found
     */
    private HashMap<String, List<VersionInfo>> loadVersionMap() throws BNException {
        final List<VersionInfo> cloudDBVersionList = masterData.getVersionInfoList();
        final HashMap<String, List<VersionInfo>> cVersionMap = new HashMap<String, List<VersionInfo>>(cloudDBVersionList.size());
        final HashMap<String, Integer> devIdProductMap = loadBackendSupportedVersionMap();
        for(VersionInfo vInfo : cloudDBVersionList) {
            //find the device id
            final String key = vInfo.getClientDescription() + vInfo.getClientVersion();
            if(devIdProductMap.containsKey(key)) {
                final int deviceID = devIdProductMap.get(key);
                vInfo.setProductDeviceId(deviceID);

                List<VersionInfo> svList = null;
                if(cVersionMap.containsKey(vInfo.getClientModel())) {
                    svList = cVersionMap.get(vInfo.getClientModel());
                    boolean exists = false;
                    for(int j = 0; j < svList.size(); j++) {
                        final VersionInfo i = svList.get(j);
                        //if the version matches, replace it
                        if(i.getDeviceReportedVersion().equals(vInfo.getDeviceReportedVersion())) {
                            exists = true;
                            svList.remove(j);
                            svList.add(j, vInfo);
                        }
                    }

                    if(!exists) {
                        svList.add(vInfo);
                    }
                }
                else {
                    svList = new ArrayList<VersionInfo>();
                    svList.add(vInfo);
                }

                if(svList != null) {
                    cVersionMap.put(vInfo.getClientModel(), svList);
                }
            }
        }
        return cVersionMap;
    }

    /**
     * This method is called when memcache for version map is disabled
     * gets version info including device id from version map table in bncloud
     * creates a map by grouping all versions for a modelId
     */
    private Map<String, List<VersionInfo>> loadDBVersionMap() throws BNException {
        final List<VersionInfo> cloudDBVersionList = masterData.getVersionInfoList();
        final Map<String, List<VersionInfo>> cVersionMap = new HashMap<>(cloudDBVersionList.size());

        for(final VersionInfo vInfo : cloudDBVersionList) {
            if (cVersionMap.containsKey(vInfo.getClientModel())) {
                // client model already exists in the map
                // add vInfo to the existing list
                cVersionMap.get(vInfo.getClientModel()).add(vInfo);
            }
            else {
                final List<VersionInfo> svList = new ArrayList<>();
                svList.add(vInfo);
                cVersionMap.put(vInfo.getClientModel(), svList);
            }
        }

        return cVersionMap;
    }

    /**
     * Retrieve model name + version and device id from backend system. Only called if version map is not cached.
     */
    private HashMap<String, Integer> loadBackendSupportedVersionMap() throws BNException {
        final HashMap<String, Integer> devIdProductMap = new HashMap<String, Integer>();
        final Map<Integer, DevicePlatform> deviceMap = devicePlatformService.getDevicePlatformsMap();
        if(deviceMap == null || deviceMap.isEmpty()) {
            log.error("loadBackendSupportedVersionMap:deviceMap is empty");
        }
        else {
            for(Map.Entry<Integer, DevicePlatform> integerDevicePlatformEntry : deviceMap.entrySet()) {
                final DevicePlatform dp = integerDevicePlatformEntry.getValue();
                final String model = dp.getModel().trim().toLowerCase();
                final String mV = dp.getVersion();
                devIdProductMap.put(model + StringUtils.defaultString(mV, "1.0"), integerDevicePlatformEntry.getKey());
            }
        }
        return devIdProductMap;
    }

    /**
     * loads supported version map from versionMap
     * supported version map is key-value pair of clientModel + clientVersion and versionInfo for it
     */
    private Map<String, VersionInfo> loadSupportedVersionMap() {
        final Map<String, VersionInfo> tempSupportedVersionMap = new HashMap<>();

        for (final Map.Entry<String, List<VersionInfo>> entry : versionMap.entrySet()) {
            final String modelName = entry.getKey().trim();
            final List<VersionInfo> versions = entry.getValue();

            for (final VersionInfo versionInfo : versions) {
                final String tver = trimDevOSVersion(versionInfo.getDeviceReportedVersion());
                tempSupportedVersionMap.put(modelName+tver, versionInfo);
            }
        }

        return tempSupportedVersionMap;
    }

    public boolean isAppSupportAudioBooks(int deviceId) {
        final Map<Integer, DevicePlatform> deviceMap = devicePlatformService.getDevicePlatformsMap();
        DevicePlatform devicePlatform = deviceMap.get(deviceId);
        if(devicePlatform != null) {
            return compareVersion(devicePlatform.getVersion(), "6.0") >= 0;
        }
        return true;
    }

    /**
     * To compare the two version strings.
     *
     * @param appVersion     - a string of numerals separated by decimal points.
     * @param supportVersion - a string of numerals separated by decimal points.
     * @return The result is 1 if appVersion is greater than supportVersion.
     * The result is -1 if supportVersion is greater than appVersion.
     * The result is zero if the versions are equal.
     */
    public static int compareVersion(String appVersion, String supportVersion) {
        String[] appVersionArr = appVersion.replaceFirst("[^0-9.]", "").split("\\.");
        String[] supportVersionArr = supportVersion.replaceFirst("[^0-9.]", "").split("\\.");
        Long appVersionNumber, supportVersionNumber;
        int compareValue;
        int length = Math.max(appVersionArr.length, supportVersionArr.length);
        for (int i = 0; i < length; i++) {
            if (i < appVersionArr.length && i < supportVersionArr.length) {
                if (appVersionArr[i].length() > supportVersionArr[i].length()) return 1;
                else if (appVersionArr[i].length() < supportVersionArr[i].length()) return -1;
            }
            appVersionNumber = i < appVersionArr.length ? Long.parseLong(appVersionArr[i]) : 0;
            supportVersionNumber = i < supportVersionArr.length ? Long.parseLong(supportVersionArr[i]) : 0;
            compareValue = appVersionNumber.compareTo(supportVersionNumber);
            if (compareValue != 0) return compareValue;
        }
        return 0;
    }


    public static String getShopSupportedVersion(String appVersion, Collection<String> versionList) throws BNException {
        String v = "0.0";
        if (StringUtils.isNotBlank(appVersion) && null != versionList && !versionList.isEmpty()) {
            Collections.sort((List) versionList);
            for (String supportedVersion : versionList) {
                if (VersionUtil.isOneGreaterAndEqual(appVersion, supportedVersion) && VersionUtil.isOneGreater(appVersion, v)) {
                    final double sv = new Double(supportedVersion) * MULTIPLIER;
                    final double d = sv / MULTIPLIER;
                    v = Double.toString(d);
                }
            }
        } else {
            log.info("getShopSupportedVersion . appVersion (and/or) versionList is null or empty. appVersion: " + appVersion);
        }
        return v.equals("0.0") ? null : v;
    }
}
