package com.student.logging;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.student.Exceptions.FieldNotFoundException;

public class Loggings {

	boolean missingFields = false;
	String errorMessage;
	private static final Logger LOGGER = Logger.getLogger(Loggings.class.getName()); //getLogger() method of Java System class returns instance of logger

	public boolean logErrors(String[] fileData, int lineNo) throws FieldNotFoundException {

		List<String> list = Arrays.asList(fileData);

		try {
			PropertyConfigurator.configure("./src/log4j.properties");

			list.stream().forEachOrdered(item -> {
				if (item.isBlank() || item.isEmpty() || list.size() < 8) {
					this.missingFields = true;
					throw new FieldNotFoundException();
				}
			});

		} catch (SecurityException e) {
			if (LOGGER.isDebugEnabled())  
			{
			LOGGER.error("", e);
			}
		} catch (FieldNotFoundException ex) {
			if (LOGGER.isDebugEnabled())  //The goal of such “logging guards” is not to prevent log messages from being written.
			{
			errorMessage="Field is missing at line no."+lineNo;
			LOGGER.error(errorMessage,ex);
			}
		}
		return this.missingFields;
	}

}
