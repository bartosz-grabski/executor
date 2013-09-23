package agh.bit.ideafactory.rmi.host;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.rmi.common.TesterService;
import agh.bit.ideafactory.rmi.services.SubmitService;
import agh.bit.ideafactory.rmi.services.TestService;
import agh.bit.ideafactory.rmi.utils.TesterDeliverer;

/**
 * RMI service - host implementation
 * 
 * @author bgrabski
 * 
 */
public class RMIHostService implements TesterService {

	private static final Logger logger = LoggerFactory.getLogger(RMIHostService.class);
	
	@Autowired 
	private SubmitService submitService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TesterDeliverer testerDeliverer;
	
	@Override
	public void testSubmit(Long id) {
		Submit submit = submitService.getSubmitById(id);
		List<Test> tests = testService.getTestsBySubmit(submit);
			try {
				testerDeliverer.deliver(submit, tests);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
	}

}
