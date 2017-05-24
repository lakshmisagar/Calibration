package com.asu.seatr.opetest.models;

public class Knowledge_component {
	
	private int id;
	private String name;
	private int importance;
	private String description;
	
	public Knowledge_component() {
    }
    public Knowledge_component(int id,String name, int importance, String description) {
        this.id = id;
        this.name = name;
        this.importance = importance;
        this.description = description;
    }
    public Knowledge_component(String name, int importance, String description) {
        this.name = name;
        this.importance = importance;
        this.description = description;
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
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
}
