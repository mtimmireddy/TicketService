package com.myapp.repository;

import java.util.List;

import com.myapp.model.Theatre;

public interface TheatreRepository {

	Theatre findOne(Integer integer);

	List<Theatre> findAll();

}
