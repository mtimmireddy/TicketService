package com.myapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Theatre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 795044123578402164L;

	private Integer levelId;
	private String levelName;
	private BigDecimal price;
	private Integer numberOfRow;
	private Integer seatsInRow;
	private Set<ReservedSeats> seatsReserved = new HashSet<ReservedSeats>();
	
	public Theatre() {}

	public Theatre(Integer levelId, String levelName, BigDecimal price, Integer numberOfRow, Integer seatsInRow) {
		this.levelId = levelId;
		this.levelName = levelName;
		this.price = price;
		this.numberOfRow = numberOfRow;
		this.seatsInRow = seatsInRow;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNumberOfRow() {
		return numberOfRow;
	}

	public void setNumberOfRow(Integer numberOfRow) {
		this.numberOfRow = numberOfRow;
	}

	public Integer getSeatsInRow() {
		return seatsInRow;
	}

	public void setSeatsInRow(Integer seatsInRow) {
		this.seatsInRow = seatsInRow;
	}

	public Set<ReservedSeats> getSeatsReserved() {
		return seatsReserved;
	}

	public void setSeatsReserved(Set<ReservedSeats> seatsReserved) {
		this.seatsReserved = seatsReserved;
	}

}
