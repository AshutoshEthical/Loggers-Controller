package com.cg.omts.controller;

import java.io.IOException; 
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Movie;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/MovieDetailsServlet")
public class MovieDetailsController extends HttpServlet{

	final static Logger LOGGER = Logger.getLogger(MovieDetailsController.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		
		IUserService userService = new UserServiceImpl();
		try {
			Movie movie = userService.getMovieDetails(movieId);
			LOGGER.info("Getting movie object");
			request.setAttribute("movie", movie);
			dispatcher = request.getRequestDispatcher("moviedetails.jsp");
			dispatcher.forward(request, response);
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occured");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
