package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Movie;
import com.cg.omts.dto.Theatre;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/DisplayMoviesToUser")
public class DisplayMoviesToUserServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	final static Logger LOGGER = Logger.getLogger(DisplayMoviesToUserServlet.class);
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		IUserService userService = new UserServiceImpl();
		System.out.println("Inside doGet");
		try {
			String city = request.getParameter("city");
			request.setAttribute("city", city);
			if(city.equals("All Cities")) {
				LOGGER.info("Selected all cities option");
				dispatcher = request.getRequestDispatcher("userhome.jsp");
				dispatcher.include(request, response);
			} else {
				LOGGER.info("Selected a city from above cities");
				List<Integer> movieIdList = new ArrayList<Integer>();
				List<Integer> theatreIdList = userService.getTheatresByCity(city);
				movieIdList = userService.getMoviesByTheatre(theatreIdList);
				List<Movie> movieList = new ArrayList<Movie>();
				Set<Integer> movieIdSet = new HashSet<Integer>();
				Set<Movie> movieSet = new HashSet<Movie>();
				for(Integer movieId : movieIdList) {
					movieIdSet.add(movieId);
				}
				movieIdList = new ArrayList<Integer>();
				for(Integer movieId: movieIdSet) {
					movieIdList.add(movieId);
				}
				movieList = userService.getMoviesById(movieIdList);
				for(Movie movie : movieList) {
					movieSet.add(movie);
				}
				//HttpSession session = request.getSession();
				//request.setAttribute("movie", movieList);
				request.setAttribute("movie", movieSet);
			
				dispatcher = request.getRequestDispatcher("movieoncity.jsp");
				dispatcher.include(request, response);
			}
		} catch(OMTSException e) {
			e.printStackTrace();
			LOGGER.warn("Exception occured");
		}
	}
}
