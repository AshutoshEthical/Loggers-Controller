package com.cg.omts.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Booking;
import com.cg.omts.dto.Movie;
import com.cg.omts.dto.Ticket;
import com.cg.omts.dto.Transaction;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/ViewBookingController")
public class ViewBookingController extends HttpServlet{
	public static Logger LOGGER = Logger.getLogger(ViewBookingController.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = null;
		String message = (String) req.getAttribute("message");
		int userId = 1;//after implementing sessions, get this value from session
		IUserService userService = new UserServiceImpl();
		List<Integer> ticketIdList = new ArrayList<Integer>();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<Transaction> transactionList = new ArrayList<Transaction>();
		List<Booking> bookingList = new ArrayList<Booking>();
		List<String> theatreNameList = new ArrayList<String>();
		List<Integer> theatreIdList = new ArrayList<Integer>();
		List<Movie> moviesList = new ArrayList<Movie>();
		List<String> movieNameList = new ArrayList<String>();
		List<String> showNameList = new ArrayList<String>();
		List<String> screenNameList = new ArrayList<String>();
		List<Integer> movieIdList = new ArrayList<Integer>();
		try {
			
			ticketIdList = userService.getTicketIdsByUser(userId);
			LOGGER.info("Getting ticketid list by userid");
			ticketList = userService.getTicketByIDS(ticketIdList);
			for(Ticket ticket : ticketList) {
				theatreIdList.add(ticket.getTheatreId());
				
			}
			transactionList = userService.getTransactionByTicket(ticketIdList);
			LOGGER.info("getting transaction id list by ticketid list");
			bookingList = userService.getBookingByUser(transactionList);
			LOGGER.info("getting booking id list by transaction list");
			theatreNameList = userService.getTheatreNames(theatreIdList);
			LOGGER.info("getting names of theatre from theatre id list");
			movieIdList = userService.getMoviesByTheatre(theatreIdList);
			LOGGER.info("getting movieid list by theatre id list");
			moviesList = userService.getMoviesById(movieIdList);
			LOGGER.info("getting movies list by movieid list");
			for(Movie movie : moviesList) {
				movieNameList.add(movie.getMovieName());
			}
			showNameList = userService.getShowNamesByTheatre(theatreIdList);
			LOGGER.info("getting show names list by theatreid list");
			screenNameList = userService.getScreenNameByTheatre(theatreIdList);
			LOGGER.info("getting screen name list by theatreid list");
			System.out.println(ticketList);
			System.out.println(transactionList);
			System.out.println(bookingList);
			req.setAttribute("movieNameList", movieNameList);
			req.setAttribute("ticketList", ticketList);
			req.setAttribute("bookingList", bookingList);
			req.setAttribute("transactionList", transactionList);
			req.setAttribute("theatreNameList", theatreNameList);
			req.setAttribute("showNameList", showNameList);
			req.setAttribute("screenNameList", screenNameList);
			LOGGER.info("All the details successfully set");
			System.out.println("VIIIIIIIIEWWWWWWWWWWW BOKKKKKKKKKIIIIIIIIIINNNNNNNNNG"+message);
			req.setAttribute("message", message);
			dispatcher = req.getRequestDispatcher("userbookings.jsp");

			dispatcher.forward(req, resp);
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occured");
			e.printStackTrace();
		}
		
		
	}
}
