package agh.bit.ideafactory.rmi.host;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import agh.bit.ideafactory.rmi.common.TesterService;
import agh.bit.ideafactory.rmi.model.Submit;
import agh.bit.ideafactory.rmi.model.Test;
import agh.bit.ideafactory.utils.DatabaseConnectionUtil;
import agh.bit.ideafactory.utils.TesterConnectionUtil;

/**
 * RMI service - host implementation
 * 
 * @author bgrabski
 * 
 */
public class RMIHostService implements TesterService {

	private static final Logger logger = LoggerFactory.getLogger(RMIHostService.class);
	
	@Override
	public void testSubmit(int id) {
		logger.debug("testSubmit("+id+") invoked");
		DatabaseConnectionUtil connection = new DatabaseConnectionUtil();
		Submit submit = connection.getSubmit(id);
		List<Test> tests = connection.getTests(submit);
		try (TesterConnectionUtil testerConnection = new TesterConnectionUtil();) {
			testerConnection.openConnection();
			testerConnection.sendZip(submit, tests);
		} catch (Exception e) {
			logger.error("Exception occured!", e);
		}
		logger.debug("testSubmit method ended");
	}

}
