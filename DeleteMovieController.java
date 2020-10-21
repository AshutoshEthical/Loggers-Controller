package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Movie;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;

@WebServlet("/DeleteMovieServlet")
public class DeleteMovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger LOGGER = Logger.getLogger(DeleteMovieController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAdminService adminService = new AdminServiceImpl();
		
		ArrayList<Movie> displayDetails;
		try {
			displayDetails = adminService.getMovieDetailsToDelete();
			HttpSession session = request.getSession();
			session.setAttribute("displayDetails", displayDetails);
			request.getRequestDispatcher("deleteMovies.jsp").forward(request, response);
		} catch (OMTSException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		IAdminService adminService = new AdminServiceImpl();
		String message;
		try {
			boolean isMIdExists = adminService.isMovieIdExists(movieId);
			if(isMIdExists) {
				int rowsDeleted = adminService.deleteMovie(movieId);
				if(rowsDeleted > 0) {
					LOGGER.info("Movie deleted successfully");
					message = "Successfully deleted record with movie ID: "+movieId;
					request.setAttribute("message", message);
					request.getRequestDispatcher("adminHomePage.jsp").forward(request, response);
				} else { 
					LOGGER.info("Failed to delete movie");
					message = "Failed to delete the record with movie ID: "+movieId;
					request.setAttribute("message", message);
					request.getRequestDispatcher("adminHomePage.jsp").forward(request, response);
				}
			} else {
				LOGGER.info("Invalid movie iD");
				message = "Enter a valid movie ID";
				request.setAttribute("message", message);
				request.getRequestDispatcher("adminHomePage.jsp").forward(request, response);
			}
		} catch (OMTSException e1) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occured");
			e1.printStackTrace();
		}
				
	}

}
