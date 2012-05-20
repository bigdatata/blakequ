package com.stickycoding.rokon;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This was taken from ReplicaIsland(a game), another useful class to ease our troubles,
 * No good for end-user<p>
 * 
 * AllocationGuard is a utility class for tracking down memory leaks.  It implements a 
 * "checkpoint" memory scheme.  After the static sGuardActive flag has been set, any further
 * allocation of AllocationGuard or its derivatives(衍生,派生) will cause an error log entry.  Note
 * that AllocationGuard requires all of its derivatives to call super() in their constructor. 
 * <p>内存分配检测工具--用于跟踪内存泄露
 * 
 * <p><b>注：继承该类的函数都需调用super()函数，以确定内存跟踪是否正在进行</b>
 * @see com.stickycoding.rokon.BaseObject
 * @see com.stickycoding.rokon.FixedSizeArray
 */

public class AllocationGuard {
    protected static boolean sGuardActive = false;
    protected AllocationGuard() {
        if (sGuardActive) {
            // An allocation has occurred while the guard is active!  Report it.
            Debug.warning("AllocGuard", "An allocation of type " + this.getClass().getName() + " occurred while the AllocGuard is active.");
        }
    }
}
