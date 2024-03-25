package com.edts.concertsservice.model;

import java.util.Date;

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
@Table(name = "t_booking")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long concertsId;
	private int qty;
	private double totalPrices;
	private Date datePurchase;
	private Date createdAt;
	private Date updatedAt;
}
