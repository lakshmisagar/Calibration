package com.asu.seatr.opetest.models;

public class class_question implements java.io.Serializable{

	private int id;
	private int question_id;
	private int category_id;
	private int order;
	private int for_credit;
	private int mc_enabled;
	private int input_enabled;
	private int guided_enabled;
	private int user_id;
	
	public class_question() {
    }
    public class_question(int id,int question_id,int category_id, int order, int  for_credit,
    		int  mc_enabled,int  input_enabled,int guided_enabled,int user_id) {
        this.id = id;
        this.question_id = question_id;
        this.category_id = category_id;
        this.order = order;
        this.for_credit = for_credit;
        this.mc_enabled = mc_enabled;
        this.input_enabled = input_enabled;
        this.guided_enabled = guided_enabled;
        this.user_id = user_id;
    }
    public class_question(int question_id,int category_id, int order, int  for_credit,
    		int  mc_enabled,int  input_enabled,int guided_enabled,int user_id) {
        this.question_id = question_id;
        this.category_id = category_id;
        this.order = order;
        this.for_credit = for_credit;
        this.mc_enabled = mc_enabled;
        this.input_enabled = input_enabled;
        this.guided_enabled = guided_enabled;
        this.user_id = user_id;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getFor_credit() {
		return for_credit;
	}
	public void setFor_credit(int for_credit) {
		this.for_credit = for_credit;
	}
	public int getMc_enabled() {
		return mc_enabled;
	}
	public void setMc_enabled(int mc_enabled) {
		this.mc_enabled = mc_enabled;
	}
	public int getInput_enabled() {
		return input_enabled;
	}
	public void setInput_enabled(int input_enabled) {
		this.input_enabled = input_enabled;
	}
	public int getGuided_enabled() {
		return guided_enabled;
	}
	public void setGuided_enabled(int guided_enabled) {
		this.guided_enabled = guided_enabled;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
