package model;

import java.util.List;

public class Projeto {
	
	public String id, title, description;
	public List<Tasks> tasks;
	
	public Projeto(String title, String description, List<Tasks> tasks) {
		this.title = title;
		this.description = description;
		this.tasks = tasks;
	}

}
