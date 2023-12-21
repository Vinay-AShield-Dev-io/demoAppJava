package com.sample.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	private Log() {

	}
	private static final Logger log = LoggerFactory.getLogger(Log.class);

	public static Logger getLogger() {
		return log;
	}

	public synchronized static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public static void write(Object info) {
		log.info(info.toString());
	}

	public static void errorlog(Object errorMsg) {
		log.error(errorMsg.toString());
	}

}