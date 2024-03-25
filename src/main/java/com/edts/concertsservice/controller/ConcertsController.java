package com.edts.concertsservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edts.concertsservice.common.CommonLibrary;
import com.edts.concertsservice.dto.ConcertsRequest;
import com.edts.concertsservice.dto.ConcertsResponse;
import com.edts.concertsservice.response.Response;
import com.edts.concertsservice.service.ConcertsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
@Slf4j
public class ConcertsController {

	private final ConcertsService concertsService;
	
	@PostMapping("/created")
	public Response createdConcerts(@RequestBody ConcertsRequest concertsRequest) {
		Response resp = new Response();
		
		try {
			int isCreated = concertsService.createdConcerts(concertsRequest);
			
			if (isCreated == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
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
	
	@GetMapping("/get-all")
	public Response getAllConcerts() {
		Response resp = new Response();
		
		try {
			List<ConcertsResponse> data = concertsService.getAllConcerts();
			
			if (data.size() > 0) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
				resp.setData(data);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@GetMapping("/get-concerts-available")
	public Response getConcertsAvailable(@RequestParam("keyword") String keyword) {
		Response resp = new Response();
		
		try {
			List<ConcertsResponse> data = concertsService.getConcertsAvailable(keyword);
			
			if (data.size() > 0) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
				resp.setData(data);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@PostMapping("/updated/{id}")
	public Response updatedConcerts(@PathVariable("id") long id, @RequestBody ConcertsRequest concertsRequest) {
		Response resp = new Response();
		
		try {
			int isUpdated = concertsService.updatedConcerts(id, concertsRequest);
			
			if (isUpdated == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isUpdated == 2) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@PostMapping("/updated-ticket")
	public Response updatedTicketConcerts(@RequestParam("id") long id, @RequestParam int ticket) {
		Response resp = new Response();
		
		try {
			int isUpdated = concertsService.updatedTicketConcerts(id, ticket);
			
			if (isUpdated == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isUpdated == 2) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage("Number of tickets exceeds ticket quantity");
			} else if (isUpdated == 3) {
				resp.setCode(CommonLibrary.FAILED_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@DeleteMapping("/deleted/{id}")
	public Response deletedConcerts(@PathVariable("id") long id) {
		Response resp = new Response();
		
		try {
			int isDeleted = concertsService.deletedConcerts(id);
			
			if (isDeleted == 1) {
				resp.setCode(CommonLibrary.SUCCESS_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
}
