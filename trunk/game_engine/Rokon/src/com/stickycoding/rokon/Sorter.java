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
import java.util.Comparator;

/**
 * Sorter.java
 * This is another class borrowed from the ReplicaIsland project
 * 自定义的排序抽象类
 */
public abstract class Sorter<Type> {
	
	/**
	 * 排序
	 * @param array
	 * @param count
	 * @param comparator
	 */
    protected abstract void sort(Type[] array, int count, Comparator<Type> comparator);
}