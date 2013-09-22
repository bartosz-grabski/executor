package agh.bit.ideafactory.rmi.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;

@Component
public class TesterDeliverer {
	
	@Autowired
	private ZipUtil zipUtil;
	
	@Autowired
	private JSONConverter jsonConverter;
	
	@Autowired
	private SocketFactory factory;
	
	public void deliver(Submit submit, List<Test> tests) {
		//TODO
	}

}
