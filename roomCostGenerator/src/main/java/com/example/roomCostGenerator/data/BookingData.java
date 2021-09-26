package com.example.roomCostGenerator.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookingData {

	@Id
	private String roomId;
	private Date checkIn;
	private Date checkOut;
	private int adultCount;
	private String childAge;
	private int childCount;
	
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public String getChildAge() {
		return childAge;
	}
	public void setChildAge(String childAge) {
		this.childAge = childAge;
	}
	
	@Override
	public String toString() {
		return "BookingData [roomId=" + roomId + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", adultCount="
				+ adultCount + ", childAge=" + childAge + ", childCount=" + childCount + "]";
	}
	
}
