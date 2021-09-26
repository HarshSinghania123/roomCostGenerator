package com.example.roomCostGenerator.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoomDetails {

	public RoomDetails() {
		super();
	}

	@Id
	@GeneratedValue
	private int id;
	private String roomId;
	private int maxAdults;
	private int maxChildren;
	private int maxChildAge;
	private Date rateValidFrom;
	private Date rateValidTo;
	private float pricePerDay;
	private float extraAdultCharges;
	private float extraChildCharges;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public int getMaxAdults() {
		return maxAdults;
	}
	public void setMaxAdults(int maxAdults) {
		this.maxAdults = maxAdults;
	}
	public int getMaxChildren() {
		return maxChildren;
	}
	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}
	public int getMaxChildAge() {
		return maxChildAge;
	}
	public void setMaxChildAge(int maxChildAge) {
		this.maxChildAge = maxChildAge;
	}
	public Date getRateValidFrom() {
		return rateValidFrom;
	}
	public void setRateValidFrom(Date rateValidFrom) {
		this.rateValidFrom = rateValidFrom;
	}
	public Date getRateValidTo() {
		return rateValidTo;
	}
	public void setRateValidTo(Date rateValidTo) {
		this.rateValidTo = rateValidTo;
	}
	public float getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(float pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public float getExtraAdultCharges() {
		return extraAdultCharges;
	}
	public void setExtraAdultCharges(float extraAdultCharges) {
		this.extraAdultCharges = extraAdultCharges;
	}
	public float getExtraChildCharges() {
		return extraChildCharges;
	}
	public void setExtraChildCharges(float extraChildCharges) {
		this.extraChildCharges = extraChildCharges;
	}
	
	public RoomDetails(String roomId, int maxAdults, int maxChildren, int maxChildAge, Date rateValidFrom,
			Date rateValidTo, float pricePerDay, float extraAdultCharges, float extraChildCharges) {
		super();
		this.roomId = roomId;
		this.maxAdults = maxAdults;
		this.maxChildren = maxChildren;
		this.maxChildAge = maxChildAge;
		this.rateValidFrom = rateValidFrom;
		this.rateValidTo = rateValidTo;
		this.pricePerDay = pricePerDay;
		this.extraAdultCharges = extraAdultCharges;
		this.extraChildCharges = extraChildCharges;
	}
	
	@Override
	public String toString() {
		return "RoomDetails [id=" + id + ", roomId=" + roomId + ", maxAdults=" + maxAdults + ", maxChildren="
				+ maxChildren + ", maxChildAge=" + maxChildAge + ", rateValidFrom=" + rateValidFrom + ", rateValidTo="
				+ rateValidTo + ", pricePerDay=" + pricePerDay + ", extraAdultCharges=" + extraAdultCharges
				+ ", extraChildCharges=" + extraChildCharges + "]";
	}
	
}
