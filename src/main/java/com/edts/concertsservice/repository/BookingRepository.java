package com.edts.concertsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edts.concertsservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
