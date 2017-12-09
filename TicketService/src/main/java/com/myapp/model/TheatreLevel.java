package com.myapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum TheatreLevel {

	ORCHESTRA("Orchestra", 1),
	MAIN("Main", 2),
	BALCONY1("Balcony 1", 3),
	BALCONY2("Balcony 2", 4);

	private final Integer id;
	private final String name;

	private TheatreLevel(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean equalsName(String otherName){
		return (otherName == null) ? false : name.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return name;
	}

	/**
	 * Find all level between minLevel and maxLevel
	 * @param minLevel lower level which means better level seat
	 * @param maxLevel higher level which means cheaper level seat
	 * @return list of venueLevel enum
	 */
	public static List<TheatreLevel> getLevels(Optional<Integer> minLevel, Optional<Integer> maxLevel) {
		List<TheatreLevel> levels = new ArrayList<TheatreLevel>();
		Integer min = minLevel.isPresent() ? minLevel.get() : 1;
		Integer max = maxLevel.isPresent() ? maxLevel.get() : 4;

		if(max < min) {
			min = max;
		}

		for(TheatreLevel theatre : TheatreLevel.values()) {
			if( min <= theatre.getId() && theatre.getId() <= max) {
				levels.add(theatre);
			}
		}
		return levels;
	}

}


