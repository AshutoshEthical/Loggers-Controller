package com.cg.omts.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cg.omts.dto.Booking;
import com.cg.omts.dto.Transaction;
import com.cg.omts.exceptions.OMTSException;
import com.cg.omts.service.IAdminService;
import com.cg.omts.service.IUserService;
import com.cg.omts.service.UserServiceImpl;

@WebServlet("/paymentController")
public class PaymentController extends HttpServlet{
	final static Logger LOGGER = Logger.getLogger(PaymentController.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub.
		PrintWriter out = resp.getWriter();
		RequestDispatcher dispatcher = null;
		int accountNo=Integer.parseInt(req.getParameter("acc"));
		int cvv =Integer.parseInt(req.getParameter("cvv"));
		String password=req.getParameter("pass");
		int ticketId = Integer.parseInt(req.getParameter("ticketId"));
		int theatreId = Integer.parseInt(req.getParameter("theatreId"));
		int screenId = Integer.parseInt(req.getParameter("screenId"));
		int showId = Integer.parseInt(req.getParameter("showId"));
		int movieId = Integer.parseInt(req.getParameter("movieId"));
		int totalCost = Integer.parseInt(req.getParameter("totalPrice"));
		IUserService user = new UserServiceImpl();
		int currentBalance = 0;
		String movieName = "", theatreName = "", screenName = "", showName = "";
		try {
			
			Boolean flag= user.validatePayment(accountNo, cvv, password);
			
			if(flag==true) {
				LOGGER.info("Validated credentials");
				//int ticketId = 1;
				int userId = 1;
				//int totalCost = 1000; 
				int transactionId = GenerateTransactionID.getTransactionId();
				int bookingId = GenerateTicketID.getBookingId();
				LOGGER.info("Transaction id generated");
				System.out.println("Transaction Id generated id: "+transactionId);
				Transaction transaction = new Transaction(transactionId, accountNo, totalCost);
				System.out.println("Transaction obj in paymentprocess: " + transaction);
				currentBalance = user.getCurrentBalance(transaction);
				int isTransact= user.addTransaction(transaction, ticketId);
				int isdeducted = user.makePayment(accountNo, currentBalance, totalCost);
				LOGGER.info("Payment made");
				System.out.println("is Transaction done: "+ isTransact);
				//add transaction (get tran id) -- completed
				//Booking id=customerid+theatreid+movieid+showid;
				//int theatreId =1, movieId = 1, showId = 1; 
				
				/*
				 * DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new
				 * Date(); System.out.println(df.format(dateobj));
				 */
				
				
				
				Date todayDate = new Date(System.currentTimeMillis());
				System.out.println("Today's date : "+ todayDate);
				//call addbooking
				
				Booking booking = new Booking(bookingId, todayDate);
				int isBooked = user.addBooking(booking, ticketId, transactionId);
				
				//setSeatstatus to booked.
				
				//setTicketstatus to booked.
				if(isBooked > 0) {
					user.setTicketStatus(ticketId, "BOOKED");
				//user.setSeatStatus(seatId, "BOOKED");
				
					List<Integer> seatList =  user.getSeatsByTicket(ticketId);
					int seatListLength = seatList.size();
					
					for(Integer seatId: seatList) {
						user.setSeatStatus(seatId, "BOOKED");
		
					}
					movieName = user.getMovieDetails(movieId).getMovieName();
					theatreName = user.getTheatreName(theatreId);
					screenName = user.getScreenName(screenId);
					showName = user.getShowName(showId);
					req.setAttribute("ticketId", ticketId);
					req.setAttribute("booking", booking);
					req.setAttribute("transaction", transaction);
					req.setAttribute("movieName", movieName);
					req.setAttribute("theatreName", theatreName);
					req.setAttribute("screenName", screenName);
					req.setAttribute("showName", showName);
					req.setAttribute("message", "payment successful");
					dispatcher = req.getRequestDispatcher("bookingConfirmation.jsp");
					
					dispatcher.forward(req, resp);
//					HttpSession session = req.getSession();
//					session.setAttribute("booking", booking);
//					session.setAttribute("transaction", transaction);
//					resp.sendRedirect("bookingConfirmation.jsp");
				}
			}else
				LOGGER.info("Invalid Credentials");
				//out.println("Invalid Credentials");
				req.setAttribute("message", "Invalid Credentials");
				req.setAttribute("totalPrice", totalCost);
				req.setAttribute("ticketId", ticketId);
				
				req.setAttribute("movieName", movieName);
				req.setAttribute("theatreName", theatreName);
				req.setAttribute("screenName", screenName);
				req.setAttribute("showName", showName);
				dispatcher = req.getRequestDispatcher("payment.jsp");
				dispatcher.forward(req, resp);
			
		} catch (OMTSException e) {
			LOGGER.warn("Exception occured");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
