package com.asu.seatr.opetest.models;

public class knowledge_component implements java.io.Serializable{
	
	private int id;
	private String name;
	private byte[] description;
	private int importance;

	
	public knowledge_component() {
    }
    public knowledge_component(int id,String name,byte[] description , int importance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.importance = importance;
    }
    public knowledge_component(String name,byte[] description , int importance) {
        this.name = name;
        this.description = description;
        this.importance = importance;
    }
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
	public byte[] getDescription() {
		return description;
	}
	public void setDescription(byte[] description) {
		this.description = description;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	
}
