package agh.bit.ideafactory.rmi.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

/**
 * Utility class for obtaining new Socket connection. Useful in unit testing
 * 
 * @author bgrabski
 * 
 */
@Component
public class SocketFactory {

	public Socket getConnection(String host, int port) throws UnknownHostException, IOException {
		return new Socket(host, port);
	}

}
