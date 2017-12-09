package com.myapp.service.impl;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myapp.model.CustomerDetails;
import com.myapp.model.ReservedSeats;
import com.myapp.model.SeatHoldTime;
import com.myapp.model.SeatsOnHold;
import com.myapp.model.Theatre;
import com.myapp.repository.CustomerDetailsRepository;
import com.myapp.repository.ReservedSeatsRepository;
import com.myapp.repository.SeatHoldRepository;
import com.myapp.repository.TheatreRepository;
import com.myapp.service.TicketService;

@Configuration
public class ServiceImplTestConfig {
	
	@Bean
	public SeatHoldTime seatHoldTime() {
		return new SeatHoldTime(Long.valueOf("120"));
	}

	@Bean
	public TheatreRepository theatreRepository() {
		TheatreRepository repository = mock(TheatreRepository.class);
		when(repository.findOne(1)).thenReturn(new Theatre(1, "Orchestra", BigDecimal.valueOf(100.00d), 25, 50));
		when(repository.findOne(2)).thenReturn(new Theatre(2, "Main", BigDecimal.valueOf(75.00d), 20, 100));
		when(repository.findOne(3)).thenReturn(new Theatre(3, "Balcony 1", BigDecimal.valueOf(50.00d), 15, 100));
		when(repository.findOne(4)).thenReturn(new Theatre(4, "Balcony 2", BigDecimal.valueOf(40.00d), 15, 100));
		return repository;
	}

	@Bean
	public CustomerDetailsRepository customerRepository() {
		CustomerDetailsRepository repository = mock(CustomerDetailsRepository.class);
		when(repository.findByEmail("tracy@gmail.com")).thenReturn(new CustomerDetails("tracy@gmail.com"));
		when(repository.save(any(CustomerDetails.class))).thenReturn(new CustomerDetails("sam@gmail.com"));
		return repository;
	}

	@Bean
	public SeatHoldRepository seatHoldRepository() {
		LocalDateTime now = LocalDateTime.now();
		Instant nowInstant = now.atZone(ZoneId.systemDefault()).toInstant();
		LocalDateTime expired = now.minusSeconds(SEAT_HOLD_EXPIRATION_TIME_IN_SECONDS + 100L);
		Instant expiredInstant = expired.atZone(ZoneId.systemDefault()).toInstant();

		SeatHoldRepository repository = mock(SeatHoldRepository.class);
		SeatsOnHold seatsOnHold = new SeatsOnHold();
		seatsOnHold.setId(11);
		seatsOnHold.setCustomerDetails(new CustomerDetails("jenny@gmail.com"));
		seatsOnHold.getSeatsReserved().add(new ReservedSeats());
		seatsOnHold.setHoldTime(Date.from(nowInstant));

		SeatsOnHold expiredSeatHold = new SeatsOnHold();
		expiredSeatHold.setId(5);
		expiredSeatHold.setCustomerDetails(new CustomerDetails("sandy@gmail.com"));
		expiredSeatHold.setHoldTime(Date.from(expiredInstant));

		when(repository.save(any(SeatsOnHold.class))).thenReturn(seatsOnHold);
		when(repository.findOne(11)).thenReturn(seatsOnHold);
		when(repository.findOne(5)).thenReturn(expiredSeatHold);

		List<SeatsOnHold> expiredSeatHolds = new ArrayList<SeatsOnHold>();
		expiredSeatHolds.add(expiredSeatHold);
		when(repository.findByHoldTimeBefore(any(Date.class))).thenReturn(expiredSeatHolds);
		return repository;
	}

	@Bean
	public ReservedSeatsRepository seatOrderRepository() {
		ReservedSeatsRepository repository = mock(ReservedSeatsRepository.class);
		List<ReservedSeats> seatsReserved = new ArrayList<ReservedSeats>();
		ReservedSeats seatsReserved1 = new ReservedSeats(new SeatsOnHold(), new Theatre(), 1000);
		ReservedSeats seatsReserved2 = new ReservedSeats(new SeatsOnHold(), new Theatre(), 200);
		seatsReserved.add(seatsReserved1);
		seatsReserved.add(seatsReserved2);
		when(repository.findByVenue(any(Theatre.class))).thenReturn(seatsReserved);
		return repository;
	}

	@Bean
	public TicketService ticketService() {
		return new TicketServiceImpl();
	}

}
