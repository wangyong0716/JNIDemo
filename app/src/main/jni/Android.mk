
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jnidemo

LOCAL_SRC_FILES := Hello.c

include $(BUILD_SHARED_LIBRARY)