package com.fireflyingup.deerlet.boot.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author: Fire Flying
 * @create: 2023-07-21 15:28
 **/

public class DeerletBootClassLoader extends URLClassLoader {

    public DeerletBootClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader().getParent());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }
        if (name != null && (name.startsWith("java.") || name.startsWith("sun."))) {
            return super.loadClass(name, resolve);
        }
        try {
            Class<?> aClass = findClass(name);
            if (resolve) {
                resolveClass(aClass);
            }
            return aClass;
        } catch (Exception e) {
        }
        return super.loadClass(name, resolve);
    }
}
