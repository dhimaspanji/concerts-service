package com.edts.concertsservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edts.concertsservice.common.CommonLibrary;
import com.edts.concertsservice.dto.BookingRequest;
import com.edts.concertsservice.response.Response;
import com.edts.concertsservice.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

	private final BookingService bookingService;
	
	@PostMapping("/created")
	public Response createdBooking(@RequestBody List<BookingRequest> bookingRequests) {
		Response resp = new Response();
		
		try {
			int isCreated = bookingService.createdBooking(bookingRequests);
			
			if (isCreated == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isCreated == 2) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Purchase time is closed or not yet available");
			} else if (isCreated == 3) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Concerts tickets have been sold out");
			} else {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@PostMapping("/scan-ticket-code")
	public Response bookingScanTicketCode(@RequestParam("ticketCode") String ticketCode) {
		Response resp = new Response();
		
		try {
			int isUpdateStatus = bookingService.bookingScanTicketCode(ticketCode);
			
			if (isUpdateStatus == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isUpdateStatus == 2) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Ticket already in use");
			} else {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
}
