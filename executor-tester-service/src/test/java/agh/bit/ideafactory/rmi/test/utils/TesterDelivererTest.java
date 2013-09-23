package agh.bit.ideafactory.rmi.test.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.utils.JSONConverter;
import agh.bit.ideafactory.rmi.utils.Props;
import agh.bit.ideafactory.rmi.utils.SocketFactory;
import agh.bit.ideafactory.rmi.utils.TesterDeliverer;
import agh.bit.ideafactory.rmi.utils.ZipUtil;

public class TesterDelivererTest {

	@Mock
	private JSONConverter jsonConverter;
	
	@Mock
	private ZipUtil zipUtil;
	
	@Mock
	private SocketFactory socketFactory;
	
	@InjectMocks
	private TesterDeliverer testerDeliverer;
	
	private String host;
	private int port;
	private Submit submit;
	private List<agh.bit.ideafactory.model.Test> tests;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void shouldProperlyCreateConnectionAndDelegateToZipUtil() throws UnknownHostException, IOException {
		Long submitId = 1L;
		givenHost(Props.getHostProperty());
		givenPort(Props.getPortProperty());
		givenSubmit(submitId);
		givenTests(new Long[] { 1L, 2L });
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String jsonInfoString = "";
		
		Socket socket = mock(Socket.class);	//Using of mock is justified here - need to check whether getOutputStream() was called
		when(socketFactory.getConnection(host, port)).thenReturn(socket);
		when(socket.getOutputStream()).thenReturn(outputStream);
		when(jsonConverter.convertToInfoJSONString(submit, tests)).thenReturn(jsonInfoString);
		
		testerDeliverer.deliver(submit, tests);
		
		verify(socketFactory).getConnection(host, port);
		verify(socket).getOutputStream();
		verify(zipUtil).openZipStream(outputStream);
		verify(zipUtil).writeByteArray(submit.getContent(), "code");
		verify(jsonConverter).convertToInfoJSONString(submit, tests);
		verify(zipUtil).writeString(jsonInfoString, "info");
		
		for (agh.bit.ideafactory.model.Test test : tests) {
			verify(zipUtil).writeString(test.getInput(), "tests/"+test.getId()+"/in");
			verify(zipUtil).writeString(test.getOutput(), "tests/"+test.getId()+"/out");
		}
	}
	
	
	private void givenHost(String host) {
		this.host = host;
	}
	
	private void givenPort(int port) {
		this.port = port;
	}
	
	private void givenSubmit(Long id) {
		Submit submit = new Submit();
		submit.setId(id);
		submit.setContent(new byte[] { 1, 2, 3 });
		this.submit = submit;
	}
	
	private void givenTests(Long[] ids) {
		List<agh.bit.ideafactory.model.Test> tests = new ArrayList<>();
		for (Long id : ids) {
			agh.bit.ideafactory.model.Test test = new agh.bit.ideafactory.model.Test();
			test.setInput("Some input"+id);
			test.setOutput("Some output"+id);
			test.setId(id);
			tests.add(test);
		}
		
		this.tests = tests;
	}
}
