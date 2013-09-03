package agh.bit.ideafactory.test.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import agh.bit.ideafactory.utils.ZipperUtil;

public class ZipperUtilTest {

	private String host;
	private int port;

	private static final int SOCKET_PORT = 8080;
	private static ServerSocket socket;

	@BeforeClass
	public static void setUp() throws IOException {
		socket = new ServerSocket(SOCKET_PORT);
	}
	
	@Test
	public void shouldConnectToDefault() throws UnknownHostException, IOException {
		ZipperUtil zipperUtil = new ZipperUtil();
		zipperUtil.connectToSocket();
		zipperUtil.close();
	}

	@Test(expected = UnknownHostException.class)
	public void shouldThrowUnknownHostException() throws UnknownHostException, IOException {
		givenHost("unknown");
		givenPort(8080);
		ZipperUtil zipperUtil = new ZipperUtil(host, port);
		zipperUtil.connectToSocket();
		zipperUtil.close();
	}

	@Test
	public void shouldConnectToExplicit() throws UnknownHostException, IOException {
		givenHost("127.0.0.1");
		givenPort(8080);
		ZipperUtil zipperUtil = new ZipperUtil();
		zipperUtil.connectToSocket();
		zipperUtil.close();
	}

	@Test
	public void shouldOpenZipStream() throws UnknownHostException, IOException {
		ZipperUtil zipperUtil = new ZipperUtil();
		zipperUtil.connectToSocket();
		zipperUtil.openZipStream();
		zipperUtil.close();
	}

	private void givenHost(String host) {
		this.host = host;
	}

	private void givenPort(int port) {
		this.port = port;
	}

}
