package com.cg.omts.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Theatre;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;

/**
 * Servlet implementation class AddTheatreServlet
 */
@WebServlet("/AddTheatreController")
public class AddTheatreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 final static Logger LOGGER = Logger.getLogger(AddTheatreController.class);
	    
    
    public AddTheatreController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rowsInserted = 0;
		String theatreName = request.getParameter("theatreName");
		int theatreId = Integer.parseInt(request.getParameter("theatreId"));
		String theatreCity = request.getParameter("theatreCity");
		String managerName = request.getParameter("managerName");
		String managerContact = request.getParameter("managerContact");
		Theatre theatre = new Theatre();
		theatre.setTheatreName(theatreName);
		theatre.setTheatreId(theatreId);
		theatre.setTheatreCity(theatreCity);
		theatre.setManagerName(managerName);
		theatre.setManagerContact(managerContact);
		IAdminService adminService = new AdminServiceImpl();
		try {
			
			rowsInserted = adminService.addTheatre(theatre);
		} catch (OMTSException e) {
			LOGGER.warn("Exception occured");
			e.printStackTrace();
		}
		if(rowsInserted!=0) {
			LOGGER.info("Theatre inserted");
			request.setAttribute("message", "Successfully registered Theatre with theatreId :"+theatreId);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("displayTheatres.jsp");
			requestDispatcher.forward(request, response);
		}else {
			LOGGER.info("Couldn't insert theatre");
			request.setAttribute("message", "Could not insert the Threatre, the theatreId :"+theatreId+" is already existing");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("addTheatre.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
