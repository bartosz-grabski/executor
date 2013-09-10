package agh.bit.ideafactory.utils;

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

	@Override
	public void close() throws Exception {
		// TODO TesterConnection closing
	}
		
	public void sendZip(Submit submit, List<Test> tests) {
		// TODO sendZip
	}

}
