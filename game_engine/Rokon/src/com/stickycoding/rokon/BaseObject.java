/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.stickycoding.rokon;

/**
 * Another class borrwed from ReplicaIsland
 * 
 * The core object from which most other objects are derived.  Anything that will be managed by
 * an ObjectManager, and anything that requires an update per frame should be derived from
 * BaseObject.  BaseObject also defines the interface for the object-wide system registry.
 * <p>该类是最基本的类，<b>该系统中所有的类都应该由它派生，它提供了对象的管理，和内存跟踪机制
 */
public abstract class BaseObject extends AllocationGuard {

    public BaseObject() {
        super();//must call
    }

    /**
     * Update this object.
     * @param timeDelta  The duration since the last update (in seconds).
     * @param parent  The parent of this object (may be NULL).
     */
    public void update(float timeDelta, BaseObject parent) {
        // Base class does nothing.
    }


    /**
     * reset object(update and delete)
     */
    public abstract void reset();

}