package com.myapp.model;

public class TicketServiceModel {

	public int totalSeats;
	public int seatsAvailable;
	public int seatsOnHold;
	public int seatsReserved;

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public int getSeatsOnHold() {
		return seatsOnHold;
	}

	public void setSeatsOnHold(int seatsOnHold) {
		this.seatsOnHold = seatsOnHold;
	}

	public int getSeatsReserved() {
		return seatsReserved;
	}

	public void setSeatsReserved(int seatsReserved) {
		this.seatsReserved = seatsReserved;
	}

}
