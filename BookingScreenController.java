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
import com.cg.omts.dto.Show;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/BookingScreenController")
public class BookingScreenController extends HttpServlet {
	 final static Logger LOGGER = Logger.getLogger(BookingScreenController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = null;
		IUserService userService = new UserServiceImpl();
		List<Show> showList = null;
		try {
			int theatreId = Integer.parseInt(request.getParameter("theatreId"));
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			int screenId = Integer.parseInt(request.getParameter("screenId"));
			List<Screen> screenList = null;
			if(theatreId != -1 && screenId != -1) {
				LOGGER.info("Getting list of screen and shows");
				screenList = new ArrayList<Screen>();
				screenList = userService.getScreenByTheatreId(theatreId);
				showList = new ArrayList<Show>();
				showList = userService.getShowsByMovieAndTheatre(screenId, theatreId, movieId);
			}
			
			String screenName = userService.getScreenName(screenId);
			request.setAttribute("screenList", screenList);
			request.setAttribute("theatreId", theatreId);
			request.setAttribute("screenId", screenId);
			request.setAttribute("movieId", movieId);
			request.setAttribute("showList", showList);
			request.setAttribute("screenName", screenName);
			dispatcher = request.getRequestDispatcher("booking.jsp");
			dispatcher.forward(request, response);
		}catch(OMTSException e) {
			LOGGER.warn("Exception Occurred");
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
