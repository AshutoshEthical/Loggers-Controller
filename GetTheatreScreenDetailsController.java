package com.cg.omts.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Movie;
import com.cg.omts.dto.Screen;
import com.cg.omts.dto.Theatre;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;

@WebServlet("/GetTheatreScreenDetailsController")
public class GetTheatreScreenDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	final static Logger LOGGER = Logger.getLogger(GetTheatreScreenDetailsController.class);
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theatreCity = request.getParameter("theatreCity");
		IAdminService adminService = new AdminServiceImpl();
		
		ArrayList<Theatre> getTheatres = null;
		try {
			request.setAttribute("theatreCity", theatreCity);
					
			getTheatres = adminService.getTheatreDetails(theatreCity);
			LOGGER.info("List of theatres generated);
			request.setAttribute("theatreDetails", getTheatres);
			
			int seatPrice = Integer.parseInt((String)request.getParameter("seatPrice"));
			LOGGER.info("Seat price generated");
			ServletContext context=getServletContext();    
			Screen screen = (Screen) context.getAttribute("screen");
			context.setAttribute("screen",screen);
			LOGGER.info("Screen details generated);
			request.setAttribute("seatPrice", seatPrice);
//			System.out.println("In get theatre screen controller\nscreen: "+screen+"\nseatPrice: "+seatPrice);
			request.getRequestDispatcher("selectTheatreId.jsp").forward(request, response);
		} catch (OMTSException e) {
		LOGGER.warn("Exception occured");
			e.printStackTrace();
	}
			
	}

}
