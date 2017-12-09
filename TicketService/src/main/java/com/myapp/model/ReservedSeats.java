package com.myapp.model;

public class ReservedSeats {

	private Integer id;
	private SeatsOnHold seatsOnHold;
	private Theatre theatre;
	private Integer numberOfSeats;
	
	
	public ReservedSeats() {}

	public ReservedSeats(SeatsOnHold seatsOnHold, Theatre theatre, Integer numberOfSeats) {
		this.seatsOnHold = seatsOnHold;
		this.theatre = theatre;
		this.numberOfSeats = numberOfSeats;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public SeatsOnHold getSeatsOnHold() {
		return seatsOnHold;
	}

	public void setSeatsOnHold(SeatsOnHold seatsOnHold) {
		this.seatsOnHold = seatsOnHold;
	}

}
