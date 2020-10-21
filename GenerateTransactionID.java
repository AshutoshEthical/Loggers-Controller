package com.cg.omts.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cg.omts.dao.AdminDaoImpl;
import com.cg.omts.exceptions.OMTSException;

public class GenerateTransactionID {

	final static Logger LOGGER = Logger.getLogger(GenerateTransactionID.class);
	public static int startTransaction = 1000;
	
	static public int getTransactionId() {
		try {
			boolean isTransactionNull = AdminDaoImpl.checkTransaction();
			if(! isTransactionNull) {
				LOGGER.info("First transaction");
				startTransaction = 1000;
				System.out.println("From generate transaction id class:" + isTransactionNull);
			} else {
				startTransaction = AdminDaoImpl.getMaxTransactionId() + 1;
			}
			
		} catch (SQLException | OMTSException e) {
			LOGGER.warn("Exception occured");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startTransaction;
		
	}

}
