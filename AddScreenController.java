package com.cg.omts.controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Screen;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;

@WebServlet("/AddScreenServlet")
public class AddScreenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      final static Logger LOGGER = Logger.getLogger(AddScreenController.class);
      
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int screenId = Integer.parseInt(request.getParameter("screenId"));
		String screenName = request.getParameter("screenName");
		int screenRows = Integer.parseInt(request.getParameter("screenRows"));
		int screenColumns = Integer.parseInt(request.getParameter("screenColumns"));
		int seatPrice = Integer.parseInt(request.getParameter("seatPrice"));
		IAdminService adminService = new AdminServiceImpl();
		System.out.println("In AddScreenController screenId: "+ screenId);
		try {
			if(!adminService.isScreenIdExists(screenId)) {
				Screen screen = new Screen(screenId, screenName, screenRows, screenColumns);
				LOGGER.info("In AddScreenController");
				System.out.println("In AddScreenController screen: "+ screen);
				ServletContext context=getServletContext();
				
				request.setAttribute("seatPrice", seatPrice);
				context.setAttribute("screen", screen);
				request.getRequestDispatcher("getTheatreScreenDetails.jsp").forward(request, response);
			} else {
				LOGGER.info("Screen already exists");
				String message = "Screen with ID: "+screenId + " already exists!!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("addScreen.jsp").forward(request, response);
			}
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occurred");
			
			
			e.printStackTrace();
		}
		
	}

}
