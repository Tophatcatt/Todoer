package com.tophat.todo.database;

public class Task {
    private int id;
    private Integer urgency;
    private String title;
    private String description;
    public Task(String title, String desc, Integer urgency, int id){
        this.urgency=urgency;
        this.title=title;
        this.description=desc;
        this.id=id;
    }
    public String getTitle() {
    	return this.title;
    }
    public String getDescription() {
    	return this.description;
    }
    public Integer getUrgency() {
    	return this.urgency;
    }
    public Integer getId() {
    	return this.id;
    }
}
