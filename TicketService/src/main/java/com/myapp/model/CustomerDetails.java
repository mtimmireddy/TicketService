package com.myapp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CustomerDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6862199738987625363L;

	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private Set<SeatsOnHold> seatsOnHold = new HashSet<SeatsOnHold>();

	public CustomerDetails(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<SeatsOnHold> getSeatsOnHold() {
		return seatsOnHold;
	}

	public void setSeatsOnHold(Set<SeatsOnHold> seatsOnHold) {
		this.seatsOnHold = seatsOnHold;
	}

}
