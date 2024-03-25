package com.edts.concertsservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edts.concertsservice.model.BookingDetail;

import jakarta.transaction.Transactional;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {

	@Query(value = "SELECT * FROM t_booking_detail WHERE ticket_code = :ticketCode",
			nativeQuery = true)
	Optional<BookingDetail> findBookingDetailByTicketCode(@Param("ticketCode") String ticketCode);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE t_booking_detail SET status = :status WHERE id = :id",
			nativeQuery = true)
	int updateStatusBookingDetail(@Param("id") long id, @Param("status") String status);
}
