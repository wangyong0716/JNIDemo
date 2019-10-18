#!/bin/bash

mkdir -p ../xcrash/libs//armeabi
mkdir -p ../xcrash/libs//armeabi-v7a
mkdir -p ../xcrash/libs//arm64-v8a
mkdir -p ../xcrash/libs//x86
mkdir -p ../xcrash/libs//x86_64

cp -f ./libxcrash/libs/armeabi/libxcrash.so     ../xcrash/libs/armeabi/libxcrash.so
cp -f ./libxcrash/libs/armeabi-v7a/libxcrash.so ../xcrash/libs/armeabi-v7a/libxcrash.so
cp -f ./libxcrash/libs/arm64-v8a/libxcrash.so   ../xcrash/libs/arm64-v8a/libxcrash.so
cp -f ./libxcrash/libs/x86/libxcrash.so         ../xcrash/libs/x86/libxcrash.so
cp -f ./libxcrash/libs/x86_64/libxcrash.so      ../xcrash/libs/x86_64/libxcrash.so

cp -f ./libxcrash_dumper/libs/armeabi/xcrash_dumper     ../xcrash/libs/armeabi/libxcrash_dumper.so
cp -f ./libxcrash_dumper/libs/armeabi-v7a/xcrash_dumper ../xcrash/libs/armeabi-v7a/libxcrash_dumper.so
cp -f ./libxcrash_dumper/libs/arm64-v8a/xcrash_dumper   ../xcrash/libs/arm64-v8a/libxcrash_dumper.so
cp -f ./libxcrash_dumper/libs/x86/xcrash_dumper         ../xcrash/libs/x86/libxcrash_dumper.so
cp -f ./libxcrash_dumper/libs/x86_64/xcrash_dumper      ../xcrash/libs/x86_64/libxcrash_dumper.so
