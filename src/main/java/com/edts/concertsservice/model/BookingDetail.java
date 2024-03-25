package com.edts.concertsservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_booking_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long bookingId;
	private String ticketCode;
	private String status;
	private double prices;
}
