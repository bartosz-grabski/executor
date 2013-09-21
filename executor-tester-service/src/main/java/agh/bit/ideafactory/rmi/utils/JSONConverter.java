package agh.bit.ideafactory.rmi.utils;

import java.util.List;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;

/**
 * Class for managing JSON conversions
 * 
 * @author bgrabski
 * 
 */
public class JSONConverter {

	/**
	 * <pre>
	 * Converts to JSON Info String
	 * 
	 * Sample JSON String:
	 * {
	 *     "submit": {
	 *         "id": 13579,
	 *         "language": "c",
	 *         "tests": [
	 *             {"id": 1, "memory" : 10000, "time": 1500},
	 *             {"id": 2, "memory" : 15000, "time": 2000},
	 *             {"id": 80085, "memory" : 200000, "time": 100000}
	 *         ]
	 *     }
	 * }
	 * </pre>
	 * 
	 * @param submit
	 *            - submit to fetch info from (id, language)
	 * @param tests
	 *            - tests to fetch info from (ids, boundaries)
	 * @return String representation of created JSON
	 */
	public static String convertToInfoJSONString(Submit submit, List<Test> tests) {
		return null;
	}

}
