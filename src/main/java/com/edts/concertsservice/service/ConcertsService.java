package com.edts.concertsservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edts.concertsservice.common.CommonLibrary;
import com.edts.concertsservice.dto.ConcertsRequest;
import com.edts.concertsservice.dto.ConcertsResponse;
import com.edts.concertsservice.model.Concerts;
import com.edts.concertsservice.repository.ConcertsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertsService {

	private final CommonLibrary commonLibrary;
	private final ConcertsRepository concertsRepository;
	
	public int createdConcerts(ConcertsRequest concertsRequest) {		
		Concerts concerts = new Concerts();
		concerts.setName(concertsRequest.getName());
		concerts.setDateFrom(commonLibrary.stringToDate(concertsRequest.getDateFrom()));
		concerts.setDateTo(commonLibrary.stringToDate(concertsRequest.getDateTo()));
		concerts.setTimeFrom(concertsRequest.getTimeFrom());
		concerts.setTimeTo(concertsRequest.getTimeTo());
		concerts.setDescription(concertsRequest.getDescription());
		concerts.setStatus(concertsRequest.getStatus());
		concerts.setTicket(concertsRequest.getTicket());
		concerts.setPricesTicket(concertsRequest.getPricesTicket());
		concerts.setDatePurchaseTicketFrom(commonLibrary.stringToDate(concertsRequest.getDatePurchaseTicketFrom()));
		concerts.setDatePurchaseTicketTo(commonLibrary.stringToDate(concertsRequest.getDatePurchaseTicketTo()));
		concerts.setTimePurchaseTicketFrom(concertsRequest.getTimePurchaseTicketFrom());
		concerts.setTimePurchaseTicketTo(concertsRequest.getTimePurchaseTicketTo());
		concerts.setCreatedAt(new Date());
		
		concertsRepository.save(concerts);
		log.info("Concerts {} is saved", concerts.getId());
		
		Optional<Concerts> data = concertsRepository.findById(concerts.getId());
		
		if (data.isPresent()) {
			return 1;
		}
		
		return 0;
	}
	
	public List<ConcertsResponse> getAllConcerts() {
		List<Concerts> concerts = concertsRepository.findAll();
		
		return concerts.stream().map(this::mapToConcertsResponse).toList();
	}
	
	public List<ConcertsResponse> getConcertsAvailable(String keyword) {
		List<Concerts> concerts = concertsRepository.findConcertsAvailable(keyword);
		
		return concerts.stream().map(this::mapToConcertsResponse).toList();
	}
	
	public int updatedConcerts(long id, ConcertsRequest concertsRequest) {
		Optional<Concerts> dataConcerts = concertsRepository.findById(id);
		
		if (dataConcerts.isPresent()) {
			Concerts concerts = new Concerts();
			concerts.setId(id);
			concerts.setName(concertsRequest.getName());
			concerts.setDateFrom(commonLibrary.stringToDate(concertsRequest.getDateFrom()));
			concerts.setDateTo(commonLibrary.stringToDate(concertsRequest.getDateTo()));
			concerts.setTimeFrom(concertsRequest.getTimeFrom());
			concerts.setTimeTo(concertsRequest.getTimeTo());
			concerts.setDescription(concertsRequest.getDescription());
			concerts.setStatus(concertsRequest.getStatus());
			concerts.setTicket(concertsRequest.getTicket());
			concerts.setPricesTicket(concertsRequest.getPricesTicket());
			concerts.setDatePurchaseTicketFrom(commonLibrary.stringToDate(concertsRequest.getDatePurchaseTicketFrom()));
			concerts.setDatePurchaseTicketTo(commonLibrary.stringToDate(concertsRequest.getDatePurchaseTicketTo()));
			concerts.setTimePurchaseTicketFrom(concertsRequest.getTimePurchaseTicketFrom());
			concerts.setTimePurchaseTicketTo(concertsRequest.getTimePurchaseTicketTo());
			concerts.setCreatedAt(dataConcerts.get().getCreatedAt());
			concerts.setUpdatedAt(new Date());
			
			concertsRepository.save(concerts);
			log.info("Concerts {} is updated", concerts.getId());
			
			Optional<Concerts> data = concertsRepository.findById(concerts.getId());
			
			if (data.isPresent()) {
				return 1;
			}
			
			return 2;
		}
		
		return 0;
	}
	
	public int updatedTicketConcerts(long id, int ticket) {
		Optional<Concerts> concerts = concertsRepository.findById(id);
		
		if (concerts.isPresent()) {
			int ticketAvailable = concerts.get().getTicket();
			
			if (ticketAvailable > 0 && ticketAvailable > ticket) {
				int totalTicket = ticketAvailable - ticket;
				
				int updateTicketConcerts = concertsRepository.updateTicketConcerts(id, totalTicket);
				
				if (updateTicketConcerts > 0) {
					return 1;
				} else {
					return 3;
				}
			} else {
				return 2;
			}
		}
		
		return 0;
	}
	
	public int deletedConcerts(long id) {
		Optional<Concerts> concerts = concertsRepository.findById(id);
		
		if (concerts.isPresent()) {
			concertsRepository.deleteById(id);
			log.info("Concerts {} is deleted", id);
			
			return 1;
		}
		
		return 0;
	}
	
	private ConcertsResponse mapToConcertsResponse(Concerts concerts) {
		return ConcertsResponse.builder()
				.id(concerts.getId())
				.name(concerts.getName())
				.dateFrom(commonLibrary.dateToString(concerts.getDateFrom()))
				.dateTo(commonLibrary.dateToString(concerts.getDateTo()))
				.timeFrom(concerts.getTimeFrom())
				.timeTo(concerts.getTimeTo())
				.description(concerts.getDescription())
				.status(concerts.getStatus())
				.ticket(concerts.getTicket())
				.pricesTicket(concerts.getPricesTicket())
				.datePurchaseTicketFrom(commonLibrary.dateToString(concerts.getDatePurchaseTicketFrom()))
				.datePurchaseTicketTo(commonLibrary.dateToString(concerts.getDatePurchaseTicketTo()))
				.timePurchaseTicketFrom(concerts.getTimePurchaseTicketFrom())
				.timePurchaseTicketTo(concerts.getTimePurchaseTicketTo())
				.build();
	}
}
