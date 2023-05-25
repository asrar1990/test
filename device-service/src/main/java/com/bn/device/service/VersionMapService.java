package com.bn.device.service;

import com.bn.device.dto.VersionInfo;
import com.bn.common.exception.BNException;

import java.util.List;
import java.util.Map;

/**
 * @since 3/7/14
 */
public interface VersionMapService {

    Map<String, List<VersionInfo>> getVersionMap() throws BNException;

    /**
     * For a given model name and version, what VersionInfo is supported by the Cloud
     * @return always returns something. If nothing can be determined, returns new VersionInfo("TNK", "1.0", version, 1.0f)
     */
    VersionInfo getSupportVersion(String modelNameIn, String versionIn) throws BNException;
}
