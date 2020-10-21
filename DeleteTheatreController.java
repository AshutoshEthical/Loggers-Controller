package com.cg.omts.controller;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/DeleteTheatreServlet")
public class DeleteTheatreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger LOGGER = Logger.getLogger(DeleteTheatreController.class);

    public DeleteTheatreController() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theatreName = request.getParameter("theatreName");
		
		IAdminService adminService = new AdminServiceImpl();
		try {
			List<Theatre> searchTheatreList = adminService.getTheatreByName(theatreName);
			if(searchTheatreList.size()==0) {
				LOGGER.info("Theatre name does not exist");
				request.setAttribute("errorMessage","The Theatre Name does not exist");
			}
			request.setAttribute("searchTheatreList", searchTheatreList);
			RequestDispatcher rd = request.getRequestDispatcher("./deleteTheatre.jsp");
			rd.forward(request, response);
			
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		int rowsDeleted=0;
		int theatreId = Integer.parseInt(request.getParameter("theatreId"));
		IAdminService adminService = new AdminServiceImpl();
		try {
			rowsDeleted = adminService.deleteTheatre(theatreId);
			LOGGER.info("Theatre is deleted");
			response.sendRedirect("./deleteTheatre.jsp?message=Successfully Deleted");
		} catch (OMTSException e) {
			LOGGER.warn("Exception occured");
			e.printStackTrace();
		}
		
		
	}

}
