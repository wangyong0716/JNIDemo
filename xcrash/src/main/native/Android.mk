TOP_PATH := $(call my-dir)

#Build libxcrash
include $(CLEAR_VARS)
LOCAL_PATH := $(TOP_PATH)/libxcrash/jni

LOCAL_MODULE           := test
LOCAL_CFLAGS           := -std=c11 -Weverything -Werror -O0
LOCAL_C_INCLUDES       := $(LOCAL_PATH)
LOCAL_SRC_FILES        := xc_test.c
include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE           := xcrash
LOCAL_CFLAGS           := -std=c11 -Weverything -Werror -fvisibility=hidden
LOCAL_LDLIBS           := -ldl -llog
LOCAL_STATIC_LIBRARIES := test
LOCAL_C_INCLUDES       := $(LOCAL_PATH) $(LOCAL_PATH)/../../common
LOCAL_SRC_FILES        := xc_jni.c      \
                          xc_common.c   \
                          xc_crash.c    \
                          xc_trace.c    \
                          xc_dl.c       \
                          xc_fallback.c \
                          xc_util.c     \
                          $(wildcard $(LOCAL_PATH)/../../common/*.c)
include $(BUILD_SHARED_LIBRARY)


#Build libxcrash_dumper
include $(CLEAR_VARS)
LOCAL_PATH := $(TOP_PATH)/libxcrash_dumper/jni

LOCAL_MODULE           := xcrash_dumper
LOCAL_CFLAGS           := -std=c11 -Weverything -Werror -fvisibility=hidden -fPIE
LOCAL_LDFLAGS          := -pie
LOCAL_LDLIBS           := -ldl -llog
LOCAL_STATIC_LIBRARIES := lzma
LOCAL_C_INCLUDES       := $(LOCAL_PATH) $(LOCAL_PATH)/../../common
LOCAL_SRC_FILES        := $(wildcard $(LOCAL_PATH)/*.c) $(wildcard $(LOCAL_PATH)/../../common/*.c)
include $(BUILD_EXECUTABLE)
include $(LOCAL_PATH)/lzma/Android.mk
