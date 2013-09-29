package agh.bit.ideafactory.rmi.host;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.rmi.common.TesterService;
import agh.bit.ideafactory.rmi.services.SubmitService;
import agh.bit.ideafactory.rmi.services.TestService;
import agh.bit.ideafactory.rmi.utils.TesterDeliverer;

/**
 * RMI service - host implementation. It provides an implementation for RMI service Methods from TesterService can be invoked remotely.
 * 
 * @author bgrabski
 * 
 */
public class RMIHostService implements TesterService {

	@Autowired
	private SubmitService submitService;

	@Autowired
	private TestService testService;

	@Autowired
	private TesterDeliverer testerDeliverer;

	/**
	 * Delivers Submit with given id to tester
	 * 
	 * @param id - submit id to be delivered to tester
	 *           
	 */
	@Override
	public void testSubmit(Long id) {
		Submit submit = submitService.getSubmitById(id);
		List<Test> tests = testService.getTestsBySubmit(submit);
		try {
			testerDeliverer.deliver(submit, tests);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
