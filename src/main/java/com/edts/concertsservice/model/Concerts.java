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
@Table(name = "t_concerts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Concerts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date dateFrom;
	private Date dateTo;
	private String timeFrom;
	private String timeTo;
	private String description;
	private String status;
	private int ticket;
	private double pricesTicket;
	private Date datePurchaseTicketFrom;
	private Date datePurchaseTicketTo;
	private String timePurchaseTicketFrom;
	private String timePurchaseTicketTo;
	private Date createdAt;
	private Date updatedAt;
}
