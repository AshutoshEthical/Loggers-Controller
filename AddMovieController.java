package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.InputStream;
import java.sql.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Movie;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;


@WebServlet("/AddMovieServlet")

public class AddMovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(AddMovieController.class);   
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String movieName = request.getParameter("movieName");
		String movieGenre = request.getParameter("movieGenre");
		String movieDirector = request.getParameter("movieDirector");
		int movieLength = Integer.parseInt(request.getParameter("movieLength"));
		String movieLanguage = request.getParameter("movieLanguage");
		String releaseDate = request.getParameter("movieReleaseDate");
	 
		Date movieReleaseDate = Date.valueOf(releaseDate);
		IAdminService adminService = new AdminServiceImpl();
		PrintWriter out = response.getWriter();
		String message = " ";
		try {
			if(adminService.isMovieIdExists(movieId)){
				LOGGER.info("Movie already exists.");
				message = "Movie with ID: "+movieId+" already exists!!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("addMovie.jsp").forward(request, response);
			} else {
				LOGGER.info("Adding new movie");
				Movie movie = new Movie(movieId, movieName, movieGenre, movieDirector, movieLength, movieLanguage, movieReleaseDate);
				
				boolean isMovieAdded = false;
				try {
					isMovieAdded = adminService.addMovie(movie);
					if(isMovieAdded) {
						LOGGER.info("New Movie added");
						message = "Movie details with ID : "+ movie.getMovieId()+ " is successfully added";
						request.setAttribute("message", message);
						request.getRequestDispatcher("addMovie.jsp").forward(request, response);
					}
					else {
						LOGGER.warn("Failed to add movie");
						message = "Failed to add movie details with ID : "+ movie.getMovieId();
						request.setAttribute("message", message);
						request.getRequestDispatcher("addMovie.jsp").forward(request, response);
					}
				} catch (OMTSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (OMTSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
				
	
	}

}
