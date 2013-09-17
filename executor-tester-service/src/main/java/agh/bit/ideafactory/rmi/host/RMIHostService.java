package agh.bit.ideafactory.rmi.host;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import agh.bit.ideafactory.rmi.common.TesterService;

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
		
	}

}
