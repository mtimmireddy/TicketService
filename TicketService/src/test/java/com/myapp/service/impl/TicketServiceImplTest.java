package com.myapp.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.errorhandling.CustomerValidationException;
import com.myapp.errorhandling.SeatHoldNotFoundException;
import com.myapp.model.SeatsOnHold;
import com.myapp.service.TicketService;

public class TicketServiceImplTest {
	
	@Autowired
	private TicketService ticketService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNumSeatsAvailable() {
		int numSeatsAvail = ticketService.numSeatsAvailable(Optional.of(1));
		assertTrue(0 <= numSeatsAvail);
	}

	/**
	 * Test with mocked repositories
	 * mocked customer is ready for the email tracy@gmail.com
	 */
	@Test
	public void testFindAndHoldSeats() {
		SeatsOnHold seatsOnHold = ticketService.findAndHoldSeats(900, Optional.of(1), Optional.of(3), "tracy@gmail.com");
		assertNotNull(seatsOnHold);
		
		assertFalse(seatsOnHold.getSeatsReserved().isEmpty());
	}

	/**
	 * Test with giving empty level for min and max both and 
	 * passing new email which is not in customer list
	 */
	@Test
	public void testFindAndHoldSeatsWithNolevels() {
		SeatsOnHold seatsOnHold = ticketService.findAndHoldSeats(1000, Optional.empty(), Optional.empty(), "courtney@gmail.com");
		assertNotNull(seatsOnHold);
		assertFalse(seatsOnHold.getSeatsReserved().isEmpty());
	}

	/**
	 * Test for checking reserveSeats
	 * Mocked repositories has customer for email homer@simpson.com and SeatHoldId = 11
	 */
	@Test
	public void testReserveSeats() {
		String confirmCode = ticketService.reserveSeats(11, "jenny@gmail.com");
		assertFalse(confirmCode.isEmpty());
	}

	/**
	 * Test with passing SeatHoldId which is not existing on database
	 * This is test for simulating the case the SeatHold is expired and deleted already
	 */
	@Test(expected=SeatHoldNotFoundException.class)
	public void testReserveSeatsWithExpiredSeatHoldId() {
		ticketService.reserveSeats(5, "mike@gmail.com");
	}

	/**
	 * This test is for the case where seatHoldId is gone from database
	 * most likely seatHold is already expired and deleted to free hold seats
	 */
	@Test(expected=SeatHoldNotFoundException.class)
	public void testReserveSeatsWithNonExistingSeatHoldId() {
		ticketService.reserveSeats(9, "mike@gmail.com");
	}

	/**
	 * This test is for the case where given wrong email address for the seatHold
	 * It should throw exception for email verification failure
	 */
	@Test(expected=CustomerValidationException.class)
	public void testReserveSeatsWithInvalidEmail() {
		ticketService.reserveSeats(11, "harry@gmail.com");
	}

}
