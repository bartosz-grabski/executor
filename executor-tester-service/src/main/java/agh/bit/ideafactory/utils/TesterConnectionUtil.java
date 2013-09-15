package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.util.List;

import agh.bit.ideafactory.rmi.model.Submit;
import agh.bit.ideafactory.rmi.model.Test;

/**
 * Class for managing connection with tester
 * @author bgrabski
 *
 */
// TODO default and passing to constructor
public class TesterConnectionUtil implements AutoCloseable {

	private TesterZipSenderUtil zipSender;
	
	public TesterConnectionUtil() {
		zipSender = new TesterZipSenderUtil();
	}
	
	public TesterConnectionUtil(String host, int port)  {
		zipSender = new TesterZipSenderUtil(host, port);
	}
	
	@Override
	public void close() throws Exception {
		zipSender.close();
	}
	
	public void openConnection() throws IOException {
		zipSender.openZipStream();
	}
		
	public void sendZip(Submit submit, List<Test> tests) throws IOException {
		zipSender.writeBlob(submit.getContent(), "code");
		zipSender.writeJSON(JSONConverter.convertToInfoJSONString(submit, tests), "info");
		for (Test t : tests) {
			zipSender.writeBlob(t.getInput(), "tests/"+t.getId()+"/in");
			zipSender.writeBlob(t.getOutput(), "tests/"+t.getId()+"/out");
		}
	}

}
