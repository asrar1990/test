package com.bn.common.dto.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class ResourceUtil {

    /**
     * Prevent object use
     */
    private ResourceUtil() {
    }

    public static Properties loadJdbcStatements(String... jdbcFile) throws IOException {
        return loadProperty(jdbcFile);
    }

    public static Properties loadProperty(String... resource) throws IOException {
        final Properties properties = new Properties();
        loadProperty(properties, resource);
        return properties;
    }

    public static void loadProperty(Properties property, String... resource) throws IOException {
        for (String res: resource) {
            final InputStream is = ResourceUtil.class.getResourceAsStream(res);
            try {
                if (is == null) {
                    final String message = "Could not find resource=" + res;
                    log.error(message);
                    throw new NullPointerException(message);
                }
                property.load(is);
            } catch (IOException ex) {
                log.error("Error in loading the resource stream for jdbc Operations, resource=" + res, ex);
                throw ex;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        log.error("Error in closing the resource stream for jdbc Operations, resource" + res, ex);
                    }
                }
            }
        }
    }

    /**
     * Load properties and convert any IOExceptions to an IllegalStateException
     * @param file relative location of properties file
     * @return properties object loaded from file system
     */
    public static Properties loadPropertiesThrowRunnable(String file) {
        try {
            return loadJdbcStatements(file);
        }
        catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
