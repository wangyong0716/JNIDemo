package com.example.jnidemo;

public class JniUtil {
  static {
    System.loadLibrary("jnidemo"); // 名字必须和build.gradle中的moduleName一致
  }

  public static native String sayHello();
  public static native String testNativeCrash();
  public static native String testNativeAnr();

  public static void callJavaMethod(String str) {
    System.out.println("callJavaMethod -> " + str);
  }

  public static String getStringFromJava(String str) {
    System.out.println("getStringFromJava -> " + str);
    return "<Java Str>";
  }
}
