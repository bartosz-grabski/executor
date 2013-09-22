package agh.bit.ideafactory.rmi.test.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.utils.JSONConverter;
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
		givenHost("localhost");
		givenPort(8080);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		Socket socket = mock(Socket.class);	//Using of mock is justified here - need to check whether getOutputStream() was called
		when(socketFactory.getConnection(host, port)).thenReturn(socket);
		when(socket.getOutputStream()).thenReturn(outputStream);
		
		verify(socketFactory).getConnection(host, port);
		verify(socket).getOutputStream();
	}
	
	
	private void givenHost(String host) {
		this.host = host;
	}
	
	private void givenPort(int port) {
		this.port = port;
	}
	
	private void givenSubmit() {
		
	}
	
	private void givenTests() {
		
	}
}
