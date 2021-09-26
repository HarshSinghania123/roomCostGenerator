package com.example.roomCostGenerator.data;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookingData {

	@Id
	private String roomId;
	private String checkIn;
	private String checkOut;
	private int adultCount;
//	private String childAge;
	private int child1Age = 0;
	private int child2Age = 0;
	private int child3Age = 0;
	private int child4Age = 0;
	private int child5Age = 0;
	private int childCount;
	private Date checkInDate;
	private Date checkoutDate;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public int getChild1Age() {
		return child1Age;
	}
	public void setChild1Age(int child1Age) {
		this.child1Age = child1Age;
	}
	public int getChild2Age() {
		return child2Age;
	}
	public void setChild2Age(int child2Age) {
		this.child2Age = child2Age;
	}
	public int getChild3Age() {
		return child3Age;
	}
	public void setChild3Age(int child3Age) {
		this.child3Age = child3Age;
	}
	public int getChild4Age() {
		return child4Age;
	}
	public void setChild4Age(int child4Age) {
		this.child4Age = child4Age;
	}
	public int getChild5Age() {
		return child5Age;
	}
	public void setChild5Age(int child5Age) {
		this.child5Age = child5Age;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	
	@Override
	public String toString() {
		return "BookingData [roomId=" + roomId + ", adultCount="
				+ adultCount + ", child1Age=" + child1Age + ", child2Age=" + child2Age + ", child3Age=" + child3Age
				+ ", child4Age=" + child4Age + ", child5Age=" + child5Age + ", childCount=" + childCount
				+ ", checkInDate=" + checkInDate + ", checkoutDate=" + checkoutDate + "]";
	}
	
}
