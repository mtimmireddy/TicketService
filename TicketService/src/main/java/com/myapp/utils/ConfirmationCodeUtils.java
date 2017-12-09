package com.myapp.utils;

import java.util.UUID;

public class ConfirmationCodeUtils {
	
	public static final String CODE_GENERATOR_SEED_PREFIX = "TicketService";
	public static final String CODE_DELIMITER = ":";
	
	public static String generateCode(int seatHoldId, String customerEmail) {
		
		String name = String.join(CODE_DELIMITER, CODE_GENERATOR_SEED_PREFIX, customerEmail, String.valueOf(seatHoldId));
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		return uuid.toString();
	}

}
