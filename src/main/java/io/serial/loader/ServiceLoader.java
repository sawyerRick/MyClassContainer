package io.serial.loader;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class ServiceLoader extends ClassLoader {
    /**
     * 用于获取classpath的class的binary
     */
    private final ClassPool pool = ClassPool.getDefault();;

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 由JVM来查找此classloader是否已经load该class
        Class<?> c = this.findLoadedClass(name);
        // 加载JVM基础类
        if (c == null) {
            c = this.loadClassByDelegation(name);
        }

        // 从 exportedClassMap 加载
        if (c == null) {
            c = PluginLoader.exportedClassMap.get(name);
//            System.out.println("export map:" + c.getClassLoader());
        }

        // 自己加载
        if (c == null) {
            c = this.findClass(name);
        }

        // 父类加载
        if (c == null) {
            c = this.delegateToParent(name);
        }

//        if (resolve) {
//            this.resolveClass(c);
//        }
        if (c != null) {
            System.out.printf("class %s was loaded by %s\n", name, c.getClassLoader());
        }

        return c;
    }

    private Class<?> loadClassByDelegation(String name) throws ClassNotFoundException {
        Class<?> c = null;
        if ((name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("jdk.internal.") || name.startsWith("sun.") || name.startsWith("com.sun.") || name.startsWith("org.w3c.") || name.startsWith("org.xml."))) {

            c = this.delegateToParent(name);
        }

        return c;
    }

    protected Class<?> delegateToParent(String classname) throws ClassNotFoundException {
        ClassLoader cl = this.getParent();
        return cl != null ? cl.loadClass(classname) : this.findSystemClass(classname);
    }


    /* Finds a specified class.
     * The bytecode for that class can be modified.
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        System.out.println("find class by name:" + name);
        try {
            CtClass cc = pool.get(name);
            // modify the CtClass object here
            byte[] b = cc.toBytecode();
            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }
}
