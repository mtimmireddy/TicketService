package com.myapp.repository;

import com.myapp.model.SeatsOnHold;
import com.myapp.model.Theatre;

import java.util.List;

import com.myapp.model.ReservedSeats;

public interface ReservedSeatsRepository {
	
	List<ReservedSeats> findBySeatHold(SeatsOnHold seatsOnhold);
	List<ReservedSeats> findByVenue(Theatre theatre);
}
