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

	public static boolean dataInserted = false;
	@Autowired
	private RoomDetailsRepo roomDetailRepo;

	public float generateCost(BookingData bookingDetails) {
		float totalCost = 0;
		//		BookingData bookingDetails = null;
		try {
			bookingDetails = parseInput(bookingDetails);
		} catch (ParseException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Adding Data in repo");
		if(!dataInserted)
			addData();
		//		viewAll();
		List<RoomDetails> availRoomList = getRoomData(bookingDetails.getRoomId());
		if(availRoomList.isEmpty()) {
			return -1;
		}

		totalCost = calculateCost(bookingDetails, availRoomList);

		return (totalCost == 0)? -1 : totalCost;
	}

	public BookingData parseInput(BookingData bookingDetails) throws ParseException, JSONException {
		int childCount = 0;

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date checkInDate = df.parse(bookingDetails.getCheckIn());
		Date checkOutDate = df.parse(bookingDetails.getCheckOut());
		if(bookingDetails.getChild1Age() > 0) {
			childCount++;
		}
		if(bookingDetails.getChild2Age() > 0) {
			childCount++;
		}
		if(bookingDetails.getChild3Age() > 0) {
			childCount++;
		}
		if(bookingDetails.getChild4Age() > 0) {
			childCount++;
		}
		if(bookingDetails.getChild5Age() > 0) {
			childCount++;
		}

		bookingDetails.setCheckInDate(checkInDate);
		bookingDetails.setCheckoutDate(checkOutDate);
		bookingDetails.setChildCount(childCount);
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
		int childAges[] = new int[5];

		long inDate = inpData.getCheckInDate().getTime();
		long outDate = inpData.getCheckoutDate().getTime();
		System.out.println("Calculate room Cost ");
		for(RoomDetails roomDetails : roomList) {
			if(outDate < inDate) {
				System.out.println("Invalid Date");
				exit = true;
				continue;
			}
			if(inDate < roomDetails.getRateValidFrom().getTime() || outDate < roomDetails.getRateValidFrom().getTime()) {
				System.out.println("Booking unavailable");
				exit = true;
				continue;
			}
			if(inDate > roomDetails.getRateValidTo().getTime() || outDate > roomDetails.getRateValidTo().getTime()) {
				System.out.println("Booking unavailable");
				exit = true;
				continue;
			}

			stay = ((outDate - inDate) / (1000*60*60*24));
			System.out.println("Nights to stay "+stay);

			if(outDate == inDate)
				stay = 1;
			int adultCount = inpData.getAdultCount();
			int childCount = inpData.getChildCount();
			childAges[0] = inpData.getChild1Age();
			childAges[1] = inpData.getChild2Age();
			childAges[2] = inpData.getChild3Age();
			childAges[3] = inpData.getChild4Age();
			childAges[4] = inpData.getChild5Age();

			for(int age : childAges) {
				if(age > roomDetails.getMaxChildAge() && age > 0) {
					adultCount++;
					childCount--;
				}
			}
			System.out.println("ChildCount " + childCount + " AdultCount " + adultCount);
			if(childCount > roomDetails.getMaxChildren()) {
				System.out.println(childCount + " Children are not allowed");
				exit = true;
				continue;
			}
			if(adultCount > roomDetails.getMaxAdults()) {
				System.out.println(adultCount + " Adults are not allowed");
				exit = true;
				continue;
			}
			baseCharge += roomDetails.getPricePerDay() * stay;
			//			System.out.println("basecharge " + baseCharge);
			if(adultCount > 2) {
				inpData.setAdultCount((adultCount - 2));
				extraAdultCharge = roomDetails.getExtraAdultCharges() * (adultCount-2) * stay;
				//				System.out.println("Extra adult charge " + extraAdultCharge);
			}

			if(childCount > 0) {
				extraChildCharge = roomDetails.getExtraChildCharges() * childCount * stay;
				//				System.out.println("Child charge "+extraChildCharge);
			}
			totalCost = baseCharge + extraAdultCharge + extraChildCharge;
			//			System.out.println("Total cost " + totalCost);
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
		dataInserted = true;
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
