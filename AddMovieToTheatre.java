package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;

@WebServlet("/AddMovieToTheatre")
public class AddMovieToTheatre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(AddMovieToTheatre.class);
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String theatreCity = request.getParameter("theatreCity");
		
		int theatreId = Integer.parseInt(request.getParameter("theatreId"));
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		IAdminService adminService = new AdminServiceImpl();
		boolean movieIdExists = false, theatreIdExists = false, theatreInSameCity = false;
		String message = "";
		
		try {
			movieIdExists = adminService.isMovieIdExists(movieId);
			theatreIdExists = adminService.isTheatreIdExists(theatreId);
			theatreInSameCity = adminService.checkTheatreIdInCity(theatreId, theatreCity);
		
			if(movieIdExists && theatreIdExists && theatreInSameCity ) {
				if(! adminService.checkIdTheatreMovieAlreadyExists(theatreId, movieId)) {
					
						boolean isAdded = false;
						try {
							isAdded = adminService.addMovieToTheatre(movieId, theatreId);
							if(isAdded) {
								LOGGER.info("Movie added successfully");
								message = "Successfully added movie with ID: "+ movieId + " to the theatre with ID: " + theatreId;
								request.setAttribute("message", message);
								request.getRequestDispatcher("getTheatreDetails.jsp").forward(request, response);
							} else {
								LOGGER.info("Failed to create movie");
								message = "Failed to add movie with ID: "+ movieId + " to the theatre with ID: " + theatreId;
								request.setAttribute("message", message);
								request.getRequestDispatcher("getTheatreDetails.jsp").forward(request, response);
							}
						} catch (OMTSException e) {
							LOGGER.warn("Exception occurred");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} else {
					LOGGER.info("Movie already exists");
					message = "Movie with ID: "+ movieId + " already exists in Theatre with ID: "+ theatreId;
					request.setAttribute("message", message);
					request.getRequestDispatcher("getTheatreDetails.jsp").forward(request, response);
				}
			} else {
				LOGGER.info("Entered Invalid thetre ID and Movie ID");
				message = "Enter valid Theate ID and Movie ID";
				request.setAttribute("message", message);
				request.getRequestDispatcher("getTheatreDetails.jsp").forward(request, response);
			}
		} catch (OMTSException e1) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occurred");			
			e1.printStackTrace();
		}
		
	}

}
