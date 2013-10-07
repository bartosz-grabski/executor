package agh.bit.ideafactory.rmi.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;

/**
 * Utility class for delivering submit with corresponding tests to tester
 * @author bgrabski
 *
 */
@Component
public class TesterDeliverer {
	
	@Autowired
	private ZipUtil zipUtil;
	
	@Autowired
	private JSONConverter jsonConverter;
	
	@Autowired
	private SocketFactory factory;
	
	public void deliver(Submit submit, List<Test> tests) throws Exception {
		
		Socket socket = factory.getConnection(Props.getHostProperty(), Props.getPortProperty());
		zipUtil.openZipStream(socket.getOutputStream());
		
		String jsonInfoString = jsonConverter.convertToInfoJSONString(submit, tests);
		
		zipUtil.writeByteArray(submit.getContent(), "code");
		zipUtil.writeString(jsonInfoString, "info");
		zipUtil.close();
		
		for (Test test : tests) {
			zipUtil.writeString(test.getInput(), "tests/"+test.getId()+"/in");
			zipUtil.writeString(test.getOutput(), "tests/"+test.getId()+"/out");
		}
		
	}

}
