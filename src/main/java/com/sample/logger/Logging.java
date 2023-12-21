package com.sample.logger;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Logging {

	private Timestamp startTime;
	private Timestamp endTime;
	private StringBuilder string_builder;

	public Logging() {
		string_builder = new StringBuilder();
	}

	public void record_time(String component_name, String method_name, String stage) {
		if (stage == "Start") {
			startTime = new Timestamp(System.currentTimeMillis());
			string_builder.append(startTime + "\t" + component_name + method_name + "INFO" + "\n");
		}
		if (stage == "End") {
			endTime = new Timestamp(System.currentTimeMillis());
			long method_duration = endTime.getTime() - startTime.getTime();
			string_builder.append(endTime + "\t" + component_name + method_name + "INFO" + "\t"
					+ time_difference(method_duration) + "\n");
		}
	}

	public void setInfoMessage(String component_name, String method_name, String log_message) {
		// if infor settong is true, then log
		string_builder.append(new Timestamp(System.currentTimeMillis()) + "\t" + component_name + method_name
				+ "INFO" + "\t" + log_message);
	}

	public void setWarningMessage(String component_name, String method_name, String log_message) {
		string_builder.append(new Timestamp(System.currentTimeMillis()) + "\t" + component_name + method_name
				+ "Warning" + "\t" + log_message);
	}

	public void setErrorMessage(String component_name, String method_name, String log_message) {
		string_builder.append(new Timestamp(System.currentTimeMillis()) + "\t" + component_name + method_name
				+ "ERROR" + "\t" + log_message);
	}

	public void setDebugMessage(String component_name, String method_name, String log_message) {
		string_builder.append(new Timestamp(System.currentTimeMillis()) + "\t" + component_name + method_name
				+"DEBUG" + "\t" + log_message);
	}

	public void setCDRMessage() {
	}

	public synchronized void start() {
		Log.getLogger().info(string_builder.toString());
		startTime = endTime = null;
		string_builder = null;
	}

	private static String time_difference(long diff) {
		String difference = "";
		try {
			// in milliseconds
			long diffMiliSeconds = diff % 1000;
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diffDays != 0)
				difference = difference + (diffDays + " days, ");
			if (diffHours != 0)
				difference = difference + (diffHours + " hours, ");
			if (diffMinutes != 0)
				System.out.print(diffMinutes + " minutes, ");
			if (diffSeconds != 0)
				difference = difference + (diffSeconds + " seconds, ");
			if (diffMiliSeconds != 0)
				difference = difference + (diffMiliSeconds + " milliseconds.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (difference.isEmpty())
			difference = "0";
		return difference;
	}
}