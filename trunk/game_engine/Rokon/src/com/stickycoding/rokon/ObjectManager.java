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
 * Another class borrowed from ReplicaIsland
 * 
 * ObjectManagers are "group nodes" in the game graph.  They contain child objects, and updating
 * an object manager invokes update on its children.  ObjectManagers themselves are derived from
 * BaseObject, so they may be strung together into a hierarchy of objects.  ObjectManager may
 * be specialized to implement special types of traversals (e.g. PhasedObjectManager sorts its
 * children).
 * 对象管理器，里面有三个数组(正使用的，待增加的，待删除的)对象，然后提供更新，重置对象等方法
 */

public class ObjectManager extends BaseObject {
	
    protected static final int DEFAULT_ARRAY_SIZE = 256;

    private FixedSizeArray<BaseObject> mObjects;
    private FixedSizeArray<BaseObject> mPendingAdditions;//待增加的数组
    private FixedSizeArray<BaseObject> mPendingRemovals;//待删除的数组

    public ObjectManager(int arraySize) {
        super();
        mObjects = new FixedSizeArray<BaseObject>(arraySize);
        mPendingAdditions = new FixedSizeArray<BaseObject>(arraySize);
        mPendingRemovals = new FixedSizeArray<BaseObject>(arraySize);
    }

    @Override
    public void reset() {
        commitUpdates();
        final int count = mObjects.getCount();
        for (int i = 0; i < count; i++) {
            BaseObject object = mObjects.get(i);
            object.reset();
        }
    }

    /**
     * 实现的功能就是将{@code mPendingAdditions}数组的元素添加到{@code mObjects}
     * 然后将{@code mPendingRemovals}数组中元素从{@code mObjects}中移除
     */
    public void commitUpdates() {
    	//将待增加的添加到mObjects数组
        final int additionCount = mPendingAdditions.getCount();
        if (additionCount > 0) {
            final Object[] additionsArray = mPendingAdditions.getArray();
            for (int i = 0; i < additionCount; i++) {
                BaseObject object = (BaseObject)additionsArray[i];
                mObjects.add(object);
            }
            mPendingAdditions.clear();
        }

        //将待移除的从mObjects数组移除
        final int removalCount = mPendingRemovals.getCount();
        if (removalCount > 0) {
            final Object[] removalsArray = mPendingRemovals.getArray();

            for (int i = 0; i < removalCount; i++) {
                BaseObject object = (BaseObject)removalsArray[i];
                mObjects.remove(object, true);
            }
            mPendingRemovals.clear();
        }
    }

    @Override
    public void update(float timeDelta, BaseObject parent) {
        commitUpdates();
        final int count = mObjects.getCount();
        if (count > 0) {
            final Object[] objectArray = mObjects.getArray();
            for (int i = 0; i < count; i++) {
                BaseObject object = (BaseObject)objectArray[i];
                object.update(timeDelta, this);
            }
        }
    }

    public final FixedSizeArray<BaseObject> getObjects() {
        return mObjects;
    }

    public final int getCount() {
        return mObjects.getCount();
    }

    /** Returns the count after the next commitUpdates() is called. */
    public final int getConcreteCount() {
        return mObjects.getCount() + mPendingAdditions.getCount() - mPendingRemovals.getCount();
    }

    public final BaseObject get(int index) {
        return mObjects.get(index);
    }

    /**
     * add to {@link #mPendingAdditions}, invoke {@link #commitUpdates()} will add element to {@link #mObjects}
     * @param object
     */
    public void add(BaseObject object) {
        mPendingAdditions.add(object);
    }

    /**
     * remove from {@link #mPendingRemovals} <p>
     * <b>Notice: </b>not remove from {@link #mObjects}
     * @param object
     */
    public void remove(BaseObject object) {
        mPendingRemovals.add(object);
    }

    /**
     * first, add {@link #mObjects} all elements to {@link mPendingRemovals}; 
     * second, clear {@link mPendingRemovals} arrays.
     */
    public void removeAll() {
        final int count = mObjects.getCount();
        final Object[] objectArray = mObjects.getArray();
        for (int i = 0; i < count; i++) {
            mPendingRemovals.add((BaseObject)objectArray[i]);
        }
        mPendingAdditions.clear();
    }

    /**
     * Finds a child object by its type.  Note that this may invoke the class loader and therefore
     * may be slow.
     * @param classObject The class type to search for (e.g. BaseObject.class).
     * @return
     */
    public <T> T findByClass(Class<T> classObject) {
        T object = null;
        final int count = mObjects.getCount();
        for (int i = 0; i < count; i++) {
            BaseObject currentObject = mObjects.get(i);
            if (currentObject.getClass() == classObject) {
                object = classObject.cast(currentObject);
                break;
            }
        }
        return object;
    }

    protected FixedSizeArray<BaseObject> getPendingObjects() {
        return mPendingAdditions;
    }

}