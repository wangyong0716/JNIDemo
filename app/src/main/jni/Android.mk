
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jnidemo

LOCAL_SRC_FILES := Hello.c

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)