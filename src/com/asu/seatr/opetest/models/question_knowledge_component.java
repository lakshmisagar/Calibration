package com.asu.seatr.opetest.models;

public class question_knowledge_component {
	
	private int question_id;
	private int knowledge_component_id;
	
	public question_knowledge_component() {
    }
    public question_knowledge_component(int question_id,int knowledge_component_id) {
        this.question_id = question_id;
        this.knowledge_component_id = knowledge_component_id;
    }
    public question_knowledge_component(int knowledge_component_id) {
        this.knowledge_component_id = knowledge_component_id;
    }
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getKnowledge_component_id() {
		return knowledge_component_id;
	}
	public void setKnowledge_component_id(int knowledge_component_id) {
		this.knowledge_component_id = knowledge_component_id;
	}
    
    
}
