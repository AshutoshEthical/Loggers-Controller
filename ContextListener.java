package com.cg.omts.controller;


import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.events.Event;


@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();
		String log4jConfigFile = context.getInitParameter("log4j-config-location");
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		PropertyConfigurator.configure(fullPath);
	}
}
