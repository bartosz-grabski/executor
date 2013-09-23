package agh.bit.ideafactory.rmi.utils;

import java.io.StringWriter;
import java.util.List;

import net.sf.json.util.JSONBuilder;

import org.springframework.stereotype.Component;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;

/**
 * Class for managing JSON conversions
 * 
 * @author bgrabski
 * 
 */
@Component
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
	public String convertToInfoJSONString(Submit submit, List<Test> tests) {
		StringWriter writer = new StringWriter();
		JSONBuilder builder = new JSONBuilder(writer);
		builder.object()
			.key("id").value(submit.getId())
			.key("language").value(submit.getLanguageEnum().getName())
			.key("tests").array();
			for (Test test : tests) {
				builder.object()
					.key("id").value(test.getId())
					.key("memory").value(Props.getDefMemory())
					.key("time").value(Props.getDefTime())
				.endObject();
			}
			builder.endArray();
			builder.endObject();
		return writer.toString();
	}

}
