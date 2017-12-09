package com.myapp.model;

public final class SeatHoldTime {

	
	private final Long seatHoldExpireTime;	// default

	public SeatHoldTime(Long seatHoldExpireTime) {
		this.seatHoldExpireTime = seatHoldExpireTime;
	}

	public final Long getSeatHoldExpireTime() {
		return seatHoldExpireTime;
	}

}
