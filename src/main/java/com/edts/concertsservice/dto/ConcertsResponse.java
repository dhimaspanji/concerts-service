package com.edts.concertsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertsResponse {

	private long id;
	private String name;
	private String dateFrom;
	private String dateTo;
	private String timeFrom;
	private String timeTo;
	private String description;
	private String status;
	private int ticket;
	private double pricesTicket;
	private String datePurchaseTicketFrom;
	private String datePurchaseTicketTo;
	private String timePurchaseTicketFrom;
	private String timePurchaseTicketTo;
}
