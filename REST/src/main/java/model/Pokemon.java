package model;

import java.util.ArrayList;

public class Pokemon {
	private Integer id;
	private String num;
	private String name;
	private ArrayList<String> type;
	private ArrayList<NextEvolutions> next_evolution;
	private ArrayList<PrevEvolutions> prev_evolution;
	
	
	public Pokemon() {
		
	}
	public Pokemon(Integer id, String num, String name, ArrayList<String> type, ArrayList<NextEvolutions> next_evolution,
			ArrayList<PrevEvolutions> prev_evolution) {
		this.id = id;
		this.num = num;
		this.name = name;
		this.type = type;
		this.next_evolution = next_evolution;
		this.prev_evolution = prev_evolution;
	}

	public Pokemon(String num) {
		this.setNum(num);
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getType() {
		return type;
	}
	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	public ArrayList<NextEvolutions> getNext_evolution() {
		return next_evolution;
	}

	public void setNext_evolution(ArrayList<NextEvolutions> next_evolution) {
		this.next_evolution = next_evolution;
	}
	public ArrayList<PrevEvolutions> getPrev_evolution() {
		return prev_evolution;
	}

	public void setPrev_evolution(ArrayList<PrevEvolutions> prev_evolution) {
		this.prev_evolution = prev_evolution;
	}
	
}
