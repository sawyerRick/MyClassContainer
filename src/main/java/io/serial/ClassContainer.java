package io.serial;

import io.serial.loader.PluginLoader;
import io.serial.loader.ServiceLoader;

public class ClassContainer {

    /**
     * A classloader demo
     * 1. 验证ClassLoader的传递性
     * 2. 实现一个最简单的类隔离容器Demo
     */
    public static void main(String[] args) throws Throwable {
        // load and export plugin
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.loadClass("io.serial.plugin.MyPluginClass");

        System.out.println("=========");

        ServiceLoader serviceLoader = new ServiceLoader();
        Class<?> service = serviceLoader.loadClass("io.serial.service.MyServiceClass");
        service.getDeclaredMethod("process").invoke(service.newInstance());
  }
}
