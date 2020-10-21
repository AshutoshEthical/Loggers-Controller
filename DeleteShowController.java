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

import com.cg.omts.dto.Show;
import com.cg.omts.dto.Theatre;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.AdminServiceImpl;
import com.cg.omts.service.IAdminService;


/**
 * Servlet implementation class DeleteShowController
 */
@WebServlet("/DeleteShowServlet")
public class DeleteShowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger LOGGER = Logger.getLogger(DeleteShowController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteShowController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String showName = request.getParameter("showNameSearch");
		
		IAdminService adminService = new AdminServiceImpl();
		try {
			List<Show> searchShowList = adminService.getShowByName(showName);
			LOGGER.info("Getting show list");
			if(searchShowList.size()==0) {
				LOGGER.info("Show name not found");
				request.setAttribute("errorMessage","The Show Name does not exist");
			}
			request.setAttribute("searchShowList", searchShowList);
			RequestDispatcher rd = request.getRequestDispatcher("./deleteShows.jsp");
			rd.forward(request, response);
			
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Exception occured");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		int showId = Integer.parseInt(request.getParameter("showId"));
		IAdminService adminDao = new AdminServiceImpl();
		try {
			adminDao.deleteShow(showId);
		} catch (OMTSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("./deleteShows.jsp?message=Successfully Deleted");
	}

}
