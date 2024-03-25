package com.edts.concertsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailResponse {

	private long id;
	private long bookingId;
	private String ticketCode;
	private String status;
	private double prices;
}
