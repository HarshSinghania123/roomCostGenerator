package com.example.roomCostGenerator.service;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomCostGenerator.data.BookingData;
import com.fasterxml.jackson.core.JsonParser;

@RestController
public class CostGeneController {

	@Autowired
	private CostGeneratorHelper helper;
	
	@RequestMapping(method = RequestMethod.POST, value = "/getRoomCost")
	public float getRoomCost(HttpServletRequest request ){
		BufferedReader reader = null;
		StringBuffer sBuf = new StringBuffer();
		JSONObject roomDetails = null;
		String line = null;
		try{
			reader = request.getReader();
			 while((line = reader.readLine()) != null) {
				 sBuf.append(line);
			 }
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			roomDetails = new JSONObject(sBuf.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return helper.generateCost(roomDetails);
	}
}
