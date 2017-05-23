package com.asu.seatr.opeclass25models;

public class student_response {

	private int id;
	private int user_id;
	private int correct;
	private int class_question_id;
	private int  duration;
	private int  explanation_duration;
	private String  date;
	private String question_type;
	private String response_type;
	private String source;
	private int[] data;
	
	public student_response() {
    }
    public student_response(int id,int user_id,int correct, int class_question_id, int  duration,
    		int  explanation_duration,String  date,String question_type,String response_type,
    		String source,int[] data ) {
        this.id = id;
        this.user_id = user_id;
        this.correct = correct;
        this.class_question_id = class_question_id;
        this.duration = duration;
        this.explanation_duration = explanation_duration;
        this.date = date;
        this.question_type = question_type;
        this.response_type = response_type;
        this.source = source;
        this.data = data;
    }
    public student_response(int user_id,int correct, int class_question_id, int  duration,
    		int  explanation_duration,String  date,String question_type,String response_type,
    		String source,int[] data ) {
    	this.user_id = user_id;
        this.correct = correct;
        this.class_question_id = class_question_id;
        this.duration = duration;
        this.explanation_duration = explanation_duration;
        this.date = date;
        this.question_type = question_type;
        this.response_type = response_type;
        this.source = source;
        this.data = data;
    }
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getClass_question_id() {
		return class_question_id;
	}
	public void setClass_question_id(int class_question_id) {
		this.class_question_id = class_question_id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getExplanation_duration() {
		return explanation_duration;
	}
	public void setExplanation_duration(int explanation_duration) {
		this.explanation_duration = explanation_duration;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getQuestion_type() {
		return question_type;
	}
	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
}
