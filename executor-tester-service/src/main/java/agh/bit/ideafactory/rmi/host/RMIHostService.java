package agh.bit.ideafactory.rmi.host;

import java.io.IOException;

import agh.bit.ideafactory.rmi.common.TesterService;
import agh.bit.ideafactory.utils.DatabaseConnectionUtil;

/**
 * RMI service - host implementation
 * 
 * @author bgrabski
 * 
 */
public class RMIHostService implements TesterService {

	@Override
	public void testSubmit(int id) {
		System.out.println("ELO");
		try {
			DatabaseConnectionUtil.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
