package com.myapp.repository;

import com.myapp.model.CustomerDetails;


public interface CustomerDetailsRepository {
	
	CustomerDetails findByEmail(String email);

	CustomerDetails save(CustomerDetails customerDetails);

}
