package com.hao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CollectionMapping implements Serializable{
	
	private int id;
	private String name;
	private Set setValues;
	private List listValues;
	private String[] arrayValues;
	private Map mapValues;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set getSetValues() {
		return setValues;
	}
	public void setSetValues(Set setValues) {
		this.setValues = setValues;
	}
	public List getListValues() {
		return listValues;
	}
	public void setListValues(List listValues) {
		this.listValues = listValues;
	}
	public String[] getArrayValues() {
		return arrayValues;
	}
	public void setArrayValues(String[] arrayValues) {
		this.arrayValues = arrayValues;
	}
	public Map getMapValues() {
		return mapValues;
	}
	public void setMapValues(Map mapValues) {
		this.mapValues = mapValues;
	}
	
	

}
