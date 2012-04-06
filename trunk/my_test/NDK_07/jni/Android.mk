
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog


LOCAL_MODULE    := NDK_07
LOCAL_SRC_FILES := \
TransmissionPerson.c	\
TransmissionString.c

include $(BUILD_SHARED_LIBRARY)