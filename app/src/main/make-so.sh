#!/bin/bash

#clean
ndk-build -C ./jni clean
#build
ndk-build -C ./jni
#rm old so
rm -rf ./jnilibs/armeabi-v7a
rm -rf ./jnilibs/arm64-v8a
rm -rf ./jnilibs/x86
#mkdir
mkdir -p ./jnilibs/armeabi-v7a
mkdir -p ./jnilibs/arm64-v8a
mkdir -p ./jnilibs/x86
#mv new so
cp -f ./libs/armeabi-v7a/libjnidemo.so ./jnilibs/armeabi-v7a/libjnidemo.so
cp -f ./libs/arm64-v8a/libjnidemo.so   ./jnilibs/arm64-v8a/libjnidemo.so
cp -f ./libs/x86/libjnidemo.so         ./jnilibs/x86/libjnidemo.so
#rm temp files
rm -rf ./libs
rm -rf ./obj
