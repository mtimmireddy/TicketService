package com.myapp.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.myapp.errorhandling.CustomerValidationException;
import com.myapp.errorhandling.SeatHoldNotFoundException;
import com.myapp.model.CustomerDetails;
import com.myapp.model.ReservedSeats;
import com.myapp.model.SeatHoldTime;
import com.myapp.model.SeatsOnHold;
import com.myapp.model.Theatre;
import com.myapp.model.TheatreLevel;
import com.myapp.repository.CustomerDetailsRepository;
import com.myapp.repository.ReservedSeatsRepository;
import com.myapp.repository.SeatHoldRepository;
import com.myapp.repository.TheatreRepository;
import com.myapp.service.TicketService;
import com.myapp.utils.ConfirmationCodeUtils;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    @Autowired
	private TheatreRepository theatreRepository;
    
    @Autowired
	private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
	private SeatHoldRepository seatHoldRepository;
    
    @Autowired
	private ReservedSeatsRepository reservedSeatsRepository;
    
    @Autowired
	private SeatHoldTime seatHoldTime;
	
	
    @Override
	public int numSeatsAvailable(Optional<Integer> theatreLevel) {
		deleteExpiredSeatsOnHold();

		if (theatreLevel.isPresent()) {
			Theatre theatre = theatreRepository.findOne(theatreLevel.get());
			if (null != theatre) {
				return getAvailableSeatsInVenueLevel(theatre);
			}
		} else {
			// If no Level is given, search total available seat through whole levels
			List<Theatre> theatres = theatreRepository.findAll();
			return theatres.stream().mapToInt(theatre -> getAvailableSeatsInVenueLevel(theatre)).sum();
		}
		return 0;
	}
	
	private int getAvailableSeatsInVenueLevel(Theatre theatre){
		List<ReservedSeats> seatsReserved = reservedSeatsRepository.findByVenue(theatre);
		int numberOfSeatTaken = reservedSeats.getNumberOfSeats();
		int totalNumberOfSeat = theatre.getNumberOfRow() * theatre.getSeatsInRow();
		return totalNumberOfSeat - numberOfSeatTaken;
		
	}

	private void deleteExpiredSeatsOnHold() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expired = now.minusSeconds(seatHoldTime.getSeatHoldExpireTime());
		Instant expiredInstant = expired.atZone(ZoneId.systemDefault()).toInstant();

		// Clean up expired holds
		List<SeatsOnHold> expiredHolds = seatHoldRepository.findByHoldTimeBefore(Date.from(expiredInstant));
		if(!expiredHolds.isEmpty()) {
			seatHoldRepository.delete(expiredHolds);
		}

	}
	
    @Override
	public SeatsOnHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail){
    	 
    		if(StringUtils.isEmpty(customerEmail)) {
    			logger.warn("Got empty email, so ignore hold request");
    			throw new CustomerValidationException();
    		}

    		// Retreive venue and customer info
    		List<TheatreLevel> theatreLevels = TheatreLevel.getLevels(minLevel, maxLevel);
    		CustomerDetails customerDetails = customerDetailsRepository.findByEmail(customerEmail);
    		if(null == customerDetails) {
    			logger.info(String.join(": ", "create new customer by email", customerEmail));
    			customerDetails = customerDetailsRepository.save(new CustomerDetails(customerEmail));
    		} else {
    			logger.info(String.join(": ", "found existing customer by email", customerEmail));
    		}
    		SeatsOnHold seatsOnHold = new SeatsOnHold();
    		seatsOnHold.setCustomerDetails(customerDetails);

    		// Find seats through venue levels
    		int numSeatsToHold = numSeats;
    		for(TheatreLevel theatreLevel: theatreLevels) {
    			if(0 < numSeatsToHold) {
    				Theatre theatre = theatreRepository.findOne(theatreLevel.getId());
    				int numSeatsAvail = numSeatsAvailable(Optional.of(theatreLevel.getId()));
    				if( 0 < numSeatsAvail ) {
    					if(numSeatsAvail >= numSeatsToHold) {	// more seat available
    						seatsOnHold.getSeatsReserved().add( new ReservedSeats(seatsOnHold, theatre, numSeatsToHold) );
    						numSeatsToHold = 0;
    						break;
    					} else {							// not enough seat available in this level
    						seatsOnHold.getSeatsReserved().add( new ReservedSeats(seatsOnHold, theatre, numSeatsAvail) );
    						numSeatsToHold = numSeatsToHold - numSeatsAvail;
    					}
    				}
    			}
    		}

    		// persist the hold
    		if( seatsOnHold.getSeatsReserved().isEmpty() ) {
    			logger.warn("fail to hold any seat in levels for customer email: ".concat(customerEmail));
    		} else {
    			seatsOnHold.setHoldTime(new Date());
    			seatsOnHold = seatHoldRepository.save(seatsOnHold);
    			String message = new StringBuilder("hold ")
    					.append(numSeats - numSeatsToHold)
    					.append(" seats for email: ")
    					.append(customerEmail)
    					.toString();
    			logger.info(message);
    		}
    		return seatsOnHold;
    	 
    		
	}
    
    @Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		
    	LocalDateTime now = LocalDateTime.now();
		LocalDateTime expired = now.minusSeconds(seatHoldTime.getSeatHoldExpireTime());
		Instant expiredInstant = expired.atZone(ZoneId.systemDefault()).toInstant();

		// find SeatHold
		SeatsOnHold seatsOnHold = seatHoldRepository.findOne(seatHoldId);
		if(null == seatsOnHold) {
			String errorMessage = String.join(": ", "fail on reservation, no SeatHold found. it probably already expired, seatHoldId", String.valueOf(seatHoldId));
			logger.error(errorMessage);
			throw new SeatHoldNotFoundException(errorMessage);
		}

		// verify customer info first
		CustomerDetails customerDetails = seatsOnHold.getCustomerDetails();
		if(null == customerDetails || (!customerDetails.getEmail().equals(customerEmail))) {
			StringBuilder errorMessage = new StringBuilder("Customer Email Validation on SeatHold fail, seatHoldId: ")
					.append(seatHoldId)
					.append(", customerEmail: ")
					.append(customerEmail);
			logger.error(errorMessage.toString());
			throw new CustomerValidationException(errorMessage.toString());
		}

		// verify seatHold and reservation status
		if( StringUtils.isEmpty(seatsOnHold.getConfirmationCode()) ) {
			if(seatsOnHold.getHoldTime().before(Date.from(expiredInstant))) {
				String errorMessage = String.join(": ", "fail on reservation, the SeatHold is expired, seatHoldId", String.valueOf(seatHoldId));
				logger.error(errorMessage);
				throw new SeatHoldNotFoundException(errorMessage);
			}
		} else {
			String message = new StringBuilder("The seatHold is already reservated, seatHoldId: ")
					.append(seatHoldId)
					.append(", customerEmail: ")
					.append(customerEmail)
					.toString();
			logger.warn(message);
			return seatsOnHold.getConfirmationCode();
		}

		// generate confirmation code and check in the hold as reservation
		String code = ConfirmationCodeUtils.generateCode(seatHoldId, customerEmail);
		seatsOnHold.setConfirmationCode(code);
		seatsOnHold.setReservationTime(new Date());
		seatHoldRepository.save(seatsOnHold);
		String message = new StringBuilder("Reserved Seat for email: ")
				.append(customerEmail)
				.append(", seatHoldId: ")
				.append(seatHoldId)
				.append(", confirmationCode: ")
				.append(code)
				.toString();
		logger.info(message);
		return code;
	}


}
