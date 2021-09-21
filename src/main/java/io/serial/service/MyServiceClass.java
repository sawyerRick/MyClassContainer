package io.serial.service;

import io.serial.plugin.MyPluginClass;

public class MyServiceClass {

    static {
        System.out.println("MyServiceClass was loaded by classloader:" + MyServiceClass.class.getClassLoader());

    }

    public void process() {
        System.out.println("MyServiceClass is processing...");
        MyPluginClass pluginClass = new MyPluginClass();
        pluginClass.process();
    }
}
