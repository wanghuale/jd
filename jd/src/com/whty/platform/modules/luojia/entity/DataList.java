package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataList implements Serializable{
	private String error;
	private Map<String,String> attrbute;
	private List<Flight> flights = new ArrayList();	
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Map<String, String> getAttrbute() {
		return attrbute;
	}
	public void setAttrbute(Map<String, String> attrbute) {
		this.attrbute = attrbute;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	public void setFlights(Flight flight) {
		this.flights.add(flight);
	}
}
