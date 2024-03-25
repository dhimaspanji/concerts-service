package com.edts.concertsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edts.concertsservice.model.Concerts;

import jakarta.transaction.Transactional;

public interface ConcertsRepository extends JpaRepository<Concerts, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE t_concerts SET ticket = :ticket WHERE id = :id",
			nativeQuery = true)
	int updateTicketConcerts(@Param("id") long id, @Param("ticket") int ticket);
	
	@Query(value = "SELECT * FROM t_concerts WHERE name LIKE CONCAT('%',:keyword,'%') AND status = 'available' AND ticket > 0",
			nativeQuery = true)
	List<Concerts> findConcertsAvailable(@Param("keyword") String keyword);
}
