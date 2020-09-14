package com.page.support;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * @description: 属性增强
 * @author: AlbertXe
 * @create: 2020-09-09 19:42
 */
@Data
public class PropertiesHelper {
    private Properties p;
    private int mode = NEVER;

    private static final int NEVER = 0;
    private static final int FALLBACK = 1;
    private static final int OVERRIDE = 2;

    public PropertiesHelper(Properties p) {
        this.p = p;
    }

    public String getProperty(String key) {
        String val = null;
        if (mode == OVERRIDE) {
            val = getSysProperty(key);
        }
        if (val == null) {
            val = p.getProperty(key);
        }
        if (val == null && mode == FALLBACK) {
            val = getSysProperty(key);
        }
        return val;
    }

    private String getSysProperty(String key) {
        String value = System.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = System.getenv(key);
        }
        return value;
    }

    public String getRequiredString(String key) {
        String val = getProperty(key);
        if (StringUtils.isBlank(val)) {
            throw new IllegalStateException("value is null");
        }
        return val;
    }


    public boolean getBoolean(String key, Boolean aFalse) {
        String val = getProperty(key);
        if (StringUtils.isBlank(val)) {
            return aFalse;
        }
        return Boolean.parseBoolean(val);

    }

    public int getInt(String key, int a) {
        String val = getProperty(key);
        if (StringUtils.isBlank(val)) {
            return a;
        }
        return Integer.parseInt(val);
    }
}
