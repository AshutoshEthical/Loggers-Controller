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

import com.cg.omts.dto.Screen;
import com.cg.omts.dto.Seat;
import com.cg.omts.dto.Show;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/BookingShowController")
public class BookingShowController extends HttpServlet{

	 final static Logger LOGGER = Logger.getLogger(BookingShowController.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = null;
		IUserService userService = new UserServiceImpl();
		Seat seat = null;
		try {
			int theatreId = Integer.parseInt(request.getParameter("theatreId"));
			int noOfSeats = Integer.parseInt(request.getParameter("noOfSeats"));
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			int screenId = Integer.parseInt(request.getParameter("screenId"));
			int showId = Integer.parseInt(request.getParameter("showId"));
			String showName = userService.getShowName(showId);
			if(theatreId != -1 && screenId != -1) {
				
				seat = userService.getSeatPrice(screenId);
				LOGGER.info("Getting seat object");
			}
			double totalPrice = noOfSeats * seat.getSeatPrice();
			String screenName = userService.getScreenName(screenId);
			int seatsAvailable = userService.getSeatsAvailable(screenId);
			Screen screen = userService.getScreen(screenId);
			int totalSeatsAvailable = (screen.getRows()*screen.getColumns())-seatsAvailable;
			System.out.println("Seats available =======" + totalSeatsAvailable);
			request.setAttribute("theatreId", theatreId);
;			request.setAttribute("screenId", screenId);
			request.setAttribute("movieId", movieId);
			request.setAttribute("showId", showId);
			request.setAttribute("screenName", screenName);
			request.setAttribute("price", seat.getSeatPrice());
			request.setAttribute("noOfSeats", noOfSeats);
			request.setAttribute("totalPrice", totalPrice);
			request.setAttribute("showName", showName);
			request.setAttribute("totalSeatsAvailable", totalSeatsAvailable);
			dispatcher = request.getRequestDispatcher("booking.jsp");
			dispatcher.forward(request, response);
		}catch(OMTSException e) {
			LOGGER.warn("Exception occurred");
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
