package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class used for sending zipped file to socket
 * @author bgrabski
 *
 */
public class ZipperUtil implements AutoCloseable {
	
	private String host = "localhost";
	private int port = 8080;
	
	private OutputStream outputStream;
	private Socket socket;
	
	public ZipperUtil(String host, int port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
		openSocket();
	}
	
	public ZipperUtil() throws UnknownHostException, IOException {
		openSocket();
	}
	
	public void sendNewEntry(String content) {
		
	}
	
	public void sendNewEntry() {
		
	}
	

	private void openSocket() throws UnknownHostException, IOException {
		socket = new Socket(host,port);
		outputStream = socket.getOutputStream();
	}
	
	
	/**
	 * Cleanup method for performing closure operations on socket and stream
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		outputStream.close();
		socket.close();
	}
	
}
