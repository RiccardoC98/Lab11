package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event> {

	public enum EventType {
		F_IN,
		F_OUT
	}
	
	private EventType type;
	private LocalDate day;
	private double flow;
	public Event(EventType type, LocalDate day, double flow) {
		super();
		this.type = type;
		this.day = day;
		this.flow = flow;
	}
	public EventType getType() {
		return type;
	}
	public LocalDate getDay() {
		return day;
	}
	public double getFlow() {
		return flow;
	}
	@Override
	public int compareTo(Event other) {
		return this.day.compareTo(other.day);
	}
	
	
	
}
