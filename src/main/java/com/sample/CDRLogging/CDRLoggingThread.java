package com.sample.CDRLogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDRLoggingThread {

	private CDRLoggingThread() {
		// restrict instantiation
	}

	private static final Logger log = LoggerFactory.getLogger(CDRLoggingThread.class);

	public static Logger getLogger() {
		return log;
	}

	public static void write(String info) {
		log.info(info);
	}
}
