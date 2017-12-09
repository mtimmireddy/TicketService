package com.myapp.repository;

import java.util.Date;
import java.util.List;

import com.myapp.model.CustomerDetails;
import com.myapp.model.SeatsOnHold;


public interface SeatHoldRepository {
	
	List<SeatsOnHold> findByHoldTimeBefore(Date date);
	List<SeatsOnHold> findByCustomer(CustomerDetails customerDetails);
	void delete(List<SeatsOnHold> expiredHolds);
	SeatsOnHold save(SeatsOnHold seatsOnHold);
	SeatsOnHold findOne(int seatHoldId);

}
