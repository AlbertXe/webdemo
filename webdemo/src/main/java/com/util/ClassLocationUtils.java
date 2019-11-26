package com.util;

import com.mysql.cj.util.TestUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * 查找class的 具体来源
 *
 * @author AlbertXe
 * @date 2019-11-07 13:59
 */
public class ClassLocationUtils {
    public static void main(String[] args) {
        String where = where(TestUtils.class);
        System.out.println(where);
    }

    public static String where(final Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("null input: cls");
        }
        URL result = null;
        final String clazzAsResource = clazz.getName().replace('.', '/').concat(".class");
        final ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        if (protectionDomain != null) {
            final CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource != null) result = codeSource.getLocation();
            if (result != null) {
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar") || result.toExternalForm().endsWith(".zip")) {
                            result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clazzAsResource));
                        } else if (new File(result.getFile()).isDirectory()) {
                            result = new URL(result, clazzAsResource);
                        }
                    } catch (MalformedURLException ignore) {

                    }
                }
            }
        }
        if (result == null) {
            final ClassLoader clsLoader = clazz.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clazzAsResource) : ClassLoader.getSystemResource(clazzAsResource);
        }
        return result.toString();
    }
}