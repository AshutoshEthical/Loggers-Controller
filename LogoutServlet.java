package com.cg.omts.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = Logger.getLogger(LogoutServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		  HttpSession session = request.getSession(false);
	      //session.setAttribute("some_token",)
	     //session.setAttribute("name", null);
	     session.removeAttribute("name");
	     //session.removeAttribute("some_token");
	     session.invalidate();
	     request.setAttribute("logout", "Successfully logged out");
	     RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
	     rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		 HttpSession session = request.getSession(false);
	      //session.setAttribute("some_token",)
	     //session.setAttribute("name", null);
	     session.removeAttribute("username");
	     //session.removeAttribute("some_token");
	     session.invalidate();
	     request.setAttribute("logout", "Successfully logged out");
	     RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
	     rd.forward(request, response);
	}

}
