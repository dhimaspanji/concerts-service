package com.edts.concertsservice.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

	private long id;
	private long concertsId;
	private int qty;
	private double totalPrices;
	private Date datePurchase;
	private String timePurchase;
	private List<BookingDetailResponse> bookingDetail;
}
