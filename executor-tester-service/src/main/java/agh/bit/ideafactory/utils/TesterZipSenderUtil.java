package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class used for sending zipped file to socket
 * 
 * @author bgrabski
 * 
 */
public class TesterZipSenderUtil implements AutoCloseable {

	private String host = "localhost";
	private int port = 8080;

	private OutputStream outputStream;
	private Socket socket;
	private ZipOutputStream zipOutputStream;

	public TesterZipSenderUtil(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public TesterZipSenderUtil() { }

	public void writeString(String content, String entryName) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		byte[] b = content.getBytes("UTF-8");
		zipOutputStream.write(b);
		zipOutputStream.closeEntry();
	}

	public void writeBlob(byte[] blob, String entryName) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		zipOutputStream.write(blob);
		zipOutputStream.closeEntry();
	}
	
	public void writeJSON(String json, String entryName) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		byte[] jsonBytes = json.getBytes("UTF-8");
		zipOutputStream.write(jsonBytes);
		zipOutputStream.closeEntry();
	}

	public void connectToSocket() throws UnknownHostException, IOException {
		socket = new Socket(host, port);

	}

	public void openZipStream() throws IOException {
		outputStream = socket.getOutputStream();
		zipOutputStream = new ZipOutputStream(outputStream);
	}

	/**
	 * Cleanup method for performing closure operations on socket and stream
	 * 
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		if (outputStream != null) outputStream.close();
		if (socket != null) socket.close();
	}
	
	/**
	 * Setters for testing
	 */
	
	

}
