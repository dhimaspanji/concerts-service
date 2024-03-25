package com.edts.concertsservice.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.edts.concertsservice.common.CommonLibrary;
import com.edts.concertsservice.common.StatusEnum;
import com.edts.concertsservice.dto.BookingRequest;
import com.edts.concertsservice.model.Booking;
import com.edts.concertsservice.model.BookingDetail;
import com.edts.concertsservice.model.Concerts;
import com.edts.concertsservice.repository.BookingDetailRepository;
import com.edts.concertsservice.repository.BookingRepository;
import com.edts.concertsservice.repository.ConcertsRepository;
import com.edts.concertsservice.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

	private final CommonLibrary commonLibrary;
	private final WebClient.Builder webClientBuilder;
	
	private final ConcertsRepository concertsRepository;
	private final BookingRepository bookingRepository;
	private final BookingDetailRepository bookingDetailRepository;
	
	public int createdBooking(List<BookingRequest> bookingRequests) throws Exception {
		if (bookingRequests.size() > 0) {
			for (BookingRequest bookingRequest : bookingRequests) {				
				Optional<Concerts> concerts = concertsRepository.findById(bookingRequest.getConcertsId());
				
				if (concerts.isPresent()) {
					String dateStrFrom = commonLibrary.dateToString(concerts.get().getDatePurchaseTicketFrom()) + " " + subtractionTime(concerts.get().getTimePurchaseTicketFrom()); // min 1
					String dateStrTo = commonLibrary.dateToString(concerts.get().getDatePurchaseTicketTo()) + " " + additionTime(concerts.get().getTimePurchaseTicketTo()); // plus 1
					
					int isCompare = compareDate(dateStrFrom, dateStrTo);
					
					if (isCompare > 0) {
						if (concerts.get().getTicket() > 0) {
							int qty = bookingRequest.getQty();
							double prices = concerts.get().getPricesTicket();
							double totalPrices = prices * qty;
							
							Booking booking = new Booking();
							booking.setConcertsId(bookingRequest.getConcertsId());
							booking.setQty(bookingRequest.getQty());
							booking.setTotalPrices(totalPrices);
							booking.setDatePurchase(new Date());
							booking.setCreatedAt(new Date());
							
							bookingRepository.save(booking);
							log.info("Booking {} is saved", booking.getId());
							
							for (int i = 0; i < bookingRequest.getQty(); i++) {				
								BookingDetail bookingDetail = new BookingDetail();
								bookingDetail.setBookingId(booking.getId());
								bookingDetail.setTicketCode(generateBookingCode());
								bookingDetail.setStatus(StatusEnum.AVAILABLE.getLabel());
								bookingDetail.setPrices(prices);
								
								bookingDetailRepository.save(bookingDetail);
								log.info("Booking detail {} is saved", bookingDetail.getId());
							}
							
							webClientBuilder.build().post()
							.uri("http://localhost:8080/api/concerts/updated-ticket?id=" + concerts.get().getId() + "&ticket=" + qty)
							.retrieve()
							.bodyToMono(Response.class)
							.block();
						} else {
							return 3;
						}
					} else {
						return 2;
					}
				}
			}
			
			return 1;
		}
		
		return 0;
	}
	
	public int bookingScanTicketCode(String ticketCode) {
		Optional<BookingDetail> bookingDetail = bookingDetailRepository.findBookingDetailByTicketCode(ticketCode);
		
		if (bookingDetail.isPresent()) {
			if (bookingDetail.get().getStatus().equals(StatusEnum.AVAILABLE.getClass())) {				
				String status = StatusEnum.ALREADY_USED.getLabel();
				
				int isUpdateStatus = bookingDetailRepository.updateStatusBookingDetail(bookingDetail.get().getId(), status);
				
				if (isUpdateStatus > 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 2;
			}
		}
		
		return 0;
	}
	
	private String subtractionTime(String time) {
		String[] timeArr = time.split(":");
		
		int minuteInt = Integer.parseInt(timeArr[1]) - 1;
		
		String hour = timeArr[0];
		String minute = String.valueOf(minuteInt);
		
		return hour + ":" + minute;
	}
	
	private String additionTime(String time) {
		String[] timeArr = time.split(":");
		
		int minuteInt = Integer.parseInt(timeArr[1]) + 1;
		
		String hour = timeArr[0];
		String minute = String.valueOf(minuteInt);
		
		return hour + ":" + minute;
	}
	
	private int compareDate(String dateStrFrom, String dateStrTo ) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = sdf.parse(sdf.format(new Date()));
		Date date2 = sdf.parse(dateStrFrom);
		Date date3 = sdf.parse(dateStrTo);
		
		if (date1.compareTo(date2) > 0 && date1.compareTo(date3) < 0) {
			return 1;
		}
		
		return 0;
	}
	
	private String generateBookingCode() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		String month = String.valueOf(calendar.get(Calendar.MONTH));
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		
		String hour = String.valueOf(calendar.get(Calendar.HOUR));
		String minute = String.valueOf(calendar.get(Calendar.MINUTE));
		String second = String.valueOf(calendar.get(Calendar.SECOND));
		
		String randomCode = RandomStringUtils.randomAlphanumeric(10);
		
		String str = year + month + day + hour + minute + second + randomCode;
		
		return commonLibrary.mixString(str);
	}
}
