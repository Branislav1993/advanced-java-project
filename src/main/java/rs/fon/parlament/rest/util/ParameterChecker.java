package rs.fon.parlament.rest.util;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rs.fon.parlament.rest.exceptions.AppException;
import rs.fon.parlament.util.ResourceBundleUtil;
import rs.fon.parlament.util.exceptions.KeyNotFoundInBundleException;

public class ParameterChecker {

	private static final Logger logger = LogManager.getLogger(ParameterChecker.class);

	public static int check(int value, int defaultVal) {
		return value >= 1 ? value : defaultVal;
	}

	public static String check(String value, String defaultVal, String[] possibleValues) {
		if (value == null) {
			return defaultVal;
		}

		boolean valid = false;

		for (String val : possibleValues) {
			if (val.equalsIgnoreCase(value)) {
				valid = true;
				break;
			}
		}

		return valid ? value : defaultVal;
	}

	public static String check(String value, String defaultVal) {

		if (value == null) {
			return defaultVal;
		}

		if (!DateRegex.ValidateDate(value)) {
			try {
				throw new AppException(Status.BAD_REQUEST,
						ResourceBundleUtil.getMessage("date.invalid_date_format", value));
			} catch (KeyNotFoundInBundleException e) {
				logger.error(e);
			}
		}

		return value;
	}
}
