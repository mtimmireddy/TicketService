package com.myapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SeatsOnHold implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5651167038542500166L;

	private Integer id;
	/** timestamp when seatHold was made */
	private Date holdTime;
	private CustomerDetails customerDetails;
	private String confirmationCode;
	private Date reservationTime;
	private Set<ReservedSeats> seatsReserved = new HashSet<ReservedSeats>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Date holdTime) {
		this.holdTime = holdTime;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}

	public Set<ReservedSeats> getSeatsReserved() {
		return seatsReserved;
	}

	public void setSeatsReserved(Set<ReservedSeats> seatsReserved) {
		this.seatsReserved = seatsReserved;
	}

}
