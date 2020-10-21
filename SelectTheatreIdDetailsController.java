package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dao.AdminDaoImpl;
import com.cg.omts.dto.Movie;
import com.cg.omts.dto.Screen;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;


@WebServlet("/SelectTheatreIdDetails")
public class SelectTheatreIdDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger LOGGER = Logger.getLogger(MovieDetailsController.class);

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAdminService adminService = new AdminServiceImpl();
		String theatreCity = request.getParameter("theatreCity");
		int theatreId = Integer.parseInt(request.getParameter("theatreId"));
		LOGGER.info("Theatre ID in SelectTheatreScreenIdDetails Controller");
		System.out.println("Theatre ID in SelectTheatreScreenIdDetails Controller" + theatreId);
		Boolean isAdded = false, isPriceAdded = false;
		
		ServletContext context=getServletContext();
		Screen screen = (Screen)context.getAttribute("screen");
		int seatPrice = Integer.parseInt(request.getParameter("seatPrice"));
		
		PrintWriter out = response.getWriter();
		String message;
		try {
			if(adminService.isTheatreIdExists(theatreId) && adminService.checkTheatreIdInCity(theatreId, theatreCity)) {
				isAdded = adminService.addScreen(screen, theatreId);
				LOGGER.info("screenid and theatreid are added to admin");
				int screenId = screen.getScreenId();
				System.out.println("Screen Id : "+screenId+"\nSeat price = "+seatPrice);
				isPriceAdded = adminService.addScreenSeatPrice(screenId, seatPrice);
				if(isAdded && isPriceAdded) {
					LOGGER.info("Screen details successfully added");
					message = "Successfully added screen details with ID: "+ screen.getScreenId();
					request.setAttribute("message", message);
					request.getRequestDispatcher("addScreen.jsp").forward(request, response);
				} else {
					LOGGER.info("FAiled to add screen details");
					message = "Failed to add Screen details with ID: "+screen.getScreenId();
					request.setAttribute("message", message);
					request.getRequestDispatcher("addScreen.jsp").forward(request, response);
				}
			} else {
				LOGGER.info("Valid theatreid not added");
				message = "Enter valid theatre ID";
				request.setAttribute("message", message);
				request.getRequestDispatcher("addScreen.jsp").forward(request, response);
			}
		} catch (OMTSException e) {
			LOGGER.warn("Exception occured");
			System.out.println("Couldn't add the details\n" + e);
			
		}
	}

}
