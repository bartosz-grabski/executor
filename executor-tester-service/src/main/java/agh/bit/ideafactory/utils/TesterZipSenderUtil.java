package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Utility class used for sending zipped objects to socket
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

	/**
	 * Writes a string as content with a given entry name to zip stream
	 * @param content String to be written as content
	 * @param entryName Zip entry (file) name
	 * @throws IOException
	 */
	public void writeString(String content, String entryName) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		byte[] b = content.getBytes("UTF-8");
		zipOutputStream.write(b);
		zipOutputStream.closeEntry();
	}

	/**
	 * Writes a byte array (blob) as content with a given entry name to zip stream
	 * @param blob
	 * @param entryName
	 * @throws IOException
	 */
	public void writeBlob(byte[] blob, String entryName) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		zipOutputStream.write(blob);
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
