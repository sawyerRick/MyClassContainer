package io.serial.plugin;

public class MyPluginClass {

    static {
        System.out.println("MyPluginClass was loaded by classloader:" + MyPluginClass.class.getClassLoader());
    }

    public void process() {

        System.out.println("MyPluginClass is processing...");
    }
}
