package com.myapp.service;


import java.util.Optional;

import com.myapp.model.SeatsOnHold;

public interface TicketService {
	
	 int numSeatsAvailable(Optional<Integer> theatreLevel);
	 
	 
	 SeatsOnHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,String customerEmail);
	 
	 String reserveSeats(int seatHoldId, String customerEmail);

	

}
