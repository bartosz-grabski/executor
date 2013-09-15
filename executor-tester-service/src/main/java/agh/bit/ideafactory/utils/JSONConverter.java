package agh.bit.ideafactory.utils;

import java.io.StringWriter;
import java.util.List;

import net.sf.json.util.JSONBuilder;
import agh.bit.ideafactory.rmi.model.Submit;
import agh.bit.ideafactory.rmi.model.Test;

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
	 * @param submit - submit to fetch info from (id, language)
	 * @param tests - tests to fetch info from (ids, boundaries)
	 * @return String representation of created JSON
	 */
	public static String convertToInfoJSONString(Submit submit, List<Test> tests) {
		StringWriter writer = new StringWriter();
		JSONBuilder builder = new JSONBuilder(writer);
		builder.object()
			.key("submit")
			.object()
				.key("id").value(submit.getSubmitId())
				.key("language").value(submit.getLanguage())
				.key("tests").array();
		for (Test t : tests) {
			builder.object();
			builder.key("id").value(t.getId());
			//TO BE CHANGED LATER TO NON-DEFAULT VALUES
			builder.key("memory").value(Props.getIntProperty("agh.bit.ideafactory.defmem"));
			builder.key("time").value(Props.getIntProperty("agh.bit.ideafactory.deftime"));
			builder.endObject();
		}
		builder.endArray();
		builder.endObject();
		builder.endObject();
		return writer.toString();
	}

}
