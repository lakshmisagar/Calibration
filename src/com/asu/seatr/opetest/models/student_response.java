package com.asu.seatr.opetest.models;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;


public class student_response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8970977115131474109L;
	private int id;
	@ManyToOne
	private int user_id;
	private int correct;
	@ManyToOne
	private int class_question_id;
	private int  duration;
	private int  explanation_duration;
	@ManyToOne
	private Date  date;
	private String question_type;
	private String response_type;
	@ManyToOne
	private String source;
	private Blob[] data;
	
	public student_response() {
    }
    public student_response(int id,int user_id,int correct, int class_question_id, int  duration,
    		int  explanation_duration,Date  date,String question_type,String response_type,
    		String source,Blob[] data ) {
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
    		int  explanation_duration,Date  date,String question_type,String response_type,
    		String source,Blob[] data ) {
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
	public Blob[] getData() {
		return data;
	}
	public void setData(Blob[] data) {
		this.data = data;
	}
}
