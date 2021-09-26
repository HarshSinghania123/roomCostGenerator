package com.example.roomCostGenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomCostGenerator.data.BookingData;

@RestController
public class CostGeneController {

	@Autowired
	private CostGeneratorHelper helper;
		
	@RequestMapping(method = RequestMethod.POST, value = "/getRoomCost",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public float getRoomCost(@RequestBody BookingData request ){
		return helper.generateCost(request);
	}
}
