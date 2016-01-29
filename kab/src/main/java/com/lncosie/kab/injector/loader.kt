package com.lncosie.kab.injector

import dalvik.system.DexClassLoader

class loader{
    fun load(){
        val jarFile = "path/to/jarfile.jar"
        val classLoader = DexClassLoader(
                jarFile, "/tmp", null, this.javaClass.getClassLoader());
        val myClass = classLoader.loadClass("MyClass");
    }
}
