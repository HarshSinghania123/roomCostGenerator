package com.example.roomCostGenerator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomCostGenerator.data.BookingData;
import com.example.roomCostGenerator.data.RoomDetails;
import com.example.roomCostGenerator.data.RoomDetailsRepo;

@Service
public class CostGeneratorHelper {

	@Autowired
	private RoomDetailsRepo roomDetailRepo;

	public float generateCost(JSONObject inpJson) {
		float totalCost = 0;
		BookingData bookingDetails = null;
		try {
			bookingDetails = parseInput(inpJson);
		} catch (ParseException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Adding Data in repo");
//		addData();
		viewAll();
		List<RoomDetails> availRoomList = getRoomData(bookingDetails.getRoomId());
		if(availRoomList.isEmpty()) {
			return -1;
		}

		totalCost = calculateCost(bookingDetails, availRoomList);

		return (totalCost == 0)? -1 : totalCost;
	}

	public BookingData parseInput(JSONObject obj) throws ParseException, JSONException {
		BookingData bookingDetails = new BookingData();
		int iterator = 1;
		String childAge = "";
		String childCount = "";
//		long inDate = null, outDate = null;

		bookingDetails.setRoomId(obj.getString("RoomId"));
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date checkInDate = df.parse(obj.getString("CheckIn"));
		Date checkOutDate = df.parse(obj.getString("CheckOut"));
		if(obj.has("Child"+(iterator)+"Age")) {
		do {
			childCount = "Child"+iterator+"Age";
			childAge += obj.getInt(childCount) + ",";
		}while(obj.has("Child"+(++iterator)+"Age"));
		
		childAge = childAge.substring(0, childAge.length()-1);
		}

		bookingDetails.setCheckIn(checkInDate);
		bookingDetails.setCheckOut(checkOutDate);
		bookingDetails.setAdultCount(obj.getInt("AdultCount"));
		bookingDetails.setChildCount(iterator-1);
				System.out.println(childAge);
		bookingDetails.setChildAge(childAge);
		System.out.println(bookingDetails.toString());

		return bookingDetails;
	}

	public List<RoomDetails> getRoomData(String roomId) {
		List<RoomDetails> roomDetails = new ArrayList<RoomDetails>();
		roomDetailRepo.getByRoomId(roomId).forEach(roomDetails::add);

		System.out.println("Get Available rooms " + roomDetails.size());
		return roomDetails;
	}

	public float calculateCost(BookingData inpData, List<RoomDetails> roomList) {
		float totalCost = 0;
		float baseCharge = 0;
		float extraAdultCharge = 0;
		float extraChildCharge = 0;
		long stay = 0;
		boolean exit = false;

		//		need to find date diff btwn checkin and checkout
		long inDate = inpData.getCheckIn().getTime();
		long outDate = inpData.getCheckOut().getTime();
		System.out.println("Calculate room Cost ");
		for(RoomDetails roomDetails : roomList) {
			System.out.println(roomDetails.toString());
			if(outDate < inDate) {
				System.out.println("Invalid Date1");
				exit = true;
				continue;
			}
			if(inDate < roomDetails.getRateValidFrom().getTime() || outDate < roomDetails.getRateValidFrom().getTime()) {
				System.out.println("Invalid Date2");
				exit = true;
				continue;
			}
			if(inDate > roomDetails.getRateValidTo().getTime() || outDate > roomDetails.getRateValidTo().getTime()) {
				System.out.println("Invalid Date3");
				exit = true;
				continue;
			}

			stay = ((outDate - inDate) / (1000*60*60*24));
			System.out.println("Nights to stay "+stay);

			if(outDate == inDate)
				stay = 1;
			int adultCount = inpData.getAdultCount();
			int childCount = inpData.getChildCount();
			if(!inpData.getChildAge().equals("")) {
				String childAges[] = inpData.getChildAge().split(",");

				for(String age : childAges) {
					if(Integer.parseInt(age) > roomDetails.getMaxChildAge()) {
						adultCount++;
						childCount--;
					}
				}
			} else {
				childCount=0;
			}
			System.out.println("ChildCount " + childCount + " AdultCount " + adultCount);
			if(childCount > roomDetails.getMaxChildren()) {
				System.out.println("invalid childCount");
				exit = true;
				continue;
			}
			if(adultCount > roomDetails.getMaxAdults()) {
				System.out.println("invalid adult count");
				exit = true;
				continue;
			}
			baseCharge += roomDetails.getPricePerDay() * stay;
			System.out.println("basecharge " + baseCharge);
			if(adultCount > 2) {
				inpData.setAdultCount((adultCount - 2));
				extraAdultCharge = roomDetails.getExtraAdultCharges() * (adultCount-2) * stay;
				System.out.println("Extra adult charge " + extraAdultCharge);
			}

			if(childCount > 0) {
				extraChildCharge = roomDetails.getExtraChildCharges() * childCount;
				System.out.println("child charge "+extraChildCharge);
			}
			totalCost = baseCharge + extraAdultCharge + extraChildCharge;
			System.out.println("total cost " + totalCost);
			if(!exit)
				return totalCost;
		}
		

		return totalCost;
	}
	
	public void addData() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		List<RoomDetails> roomList = null;
		try {
			roomList = new ArrayList<RoomDetails>(Arrays.asList(
					new RoomDetails("R123", 2, 2, 12, sdf.parse("01-SEP-2021"), sdf.parse("31-DEC-2021"), 1000, 500, 200),
					new RoomDetails("R123", 2, 2, 12, sdf.parse("01-FEB-2022"), sdf.parse("31-DEC-2022"), 2000, 700, 300),
					new RoomDetails("R124", 3, 2, 12, sdf.parse("01-SEP-2021"), sdf.parse("31-DEC-2021"), 2000, 700, 400),
					new RoomDetails("R124", 3, 2, 12, sdf.parse("01-FEB-2022"), sdf.parse("31-DEC-2022"), 3000, 900, 600)
					));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		roomDetailRepo.saveAll(roomList);
	}
	
	public void viewAll() {
		System.out.println("view all rooms");
		List<RoomDetails> roomList = new ArrayList<RoomDetails>();
		
		roomDetailRepo.findAll().forEach(roomList::add);
		
		for(RoomDetails rooms: roomList) {
			System.out.println(rooms.toString());
		}
	}
}
