package agh.bit.ideafactory.rmi.host;

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
		Submit submit = new DatabaseConnectionUtil().getSubmit(id);
		List<Test> tests = new DatabaseConnectionUtil().getTests(submit);
		try {
			TesterConnectionUtil testerConnection = new TesterConnectionUtil();
			testerConnection.sendZip(submit, tests);
			testerConnection.close();
		} catch (Exception e) {
			logger.error("Exception occured!", e);
		}
		logger.debug("testSubmit method ended");
	}

}
