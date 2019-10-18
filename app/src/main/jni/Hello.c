//
// Created by wangyong on 2019-10-16.
//
#include "com_example_jnidemo_JniUtil.h"
#include <string.h>
#include <jni.h>
#include <stdio.h>


jobject callJavaMethod(JNIEnv *env, jobject thiz)
{
      jclass clazz = (*env)->FindClass(env, "com/example/jnidemo/JniUtil");
      if(clazz == NULL)
      {
            printf("find class JniUtil error!");
            return "";
      }
      jmethodID id = (*env)->GetStaticMethodID(env, clazz, "getStringFromJava", "(Ljava/lang/String;)Ljava/lang/String;");
      if(id == NULL)
      {
            printf("find method getStringFromJava error!");
            return "";
      }
      jstring msg = (*env)->NewStringUTF(env, "-> msg send by callJavaMethod in Hello.c.");
      jobject result;
      result = (*env)->CallStaticObjectMethod(env, clazz, id, msg);
      printf("get java result");
      return result;
}

void callJavaVoidMethod(JNIEnv *env, jobject thiz)
{
      jclass clazz = (*env)->FindClass(env, "com/example/jnidemo/JniUtil");
      if(clazz == NULL)
      {
            printf("find class JniUtil error!");
            return;
      }
      jmethodID id = (*env)->GetStaticMethodID(env, clazz, "callJavaMethod", "(Ljava/lang/String;)V");
      if(id == NULL)
      {
            printf("find method callJavaMethod error!");
            return;
      }
      jstring msg = (*env)->NewStringUTF(env, "-> msg send by callJavaVoidMethod in Hello.c.");
      (*env)->CallStaticVoidMethod(env, clazz, id, msg);
}

void callJavaVoidMethodWithMsg(JNIEnv *env, jobject thiz, jstring msg)
{
      jclass clazz = (*env)->FindClass(env, "com/example/jnidemo/JniUtil");
      if(clazz == NULL)
      {
            printf("find class JniUtil error!");
            return;
      }
      jmethodID id = (*env)->GetStaticMethodID(env, clazz, "callJavaMethod", "(Ljava/lang/String;)V");
      if(id == NULL)
      {
            printf("find method callJavaMethod error!");
            return;
      }
      (*env)->CallStaticVoidMethod(env, clazz, id, msg);
}

JNIEXPORT jstring JNICALL Java_com_example_jnidemo_JniUtil_sayHello(JNIEnv *env, jclass thiz) {
  callJavaVoidMethod(env, thiz);
  jstring res = callJavaMethod(env, thiz);
  callJavaVoidMethodWithMsg(env, thiz, res);
  jint ri = callJavaMethod(env, thiz);
  ri += 1;
  callJavaVoidMethodWithMsg(env, thiz, ri);
  return (*env) -> NewStringUTF(env, "hello world");
}