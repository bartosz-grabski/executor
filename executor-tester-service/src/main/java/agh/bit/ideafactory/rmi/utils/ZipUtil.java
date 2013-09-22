package agh.bit.ideafactory.rmi.utils;

import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Component;

@Component
public class ZipUtil implements AutoCloseable {
	
	private ZipOutputStream zipStream;
	
	public void openZipStream(OutputStream outputStream) {
		//TODO
	}
	
	public void writeString(String content, String entryName) {
		//TODO
	}
	
	public void writeByteArray(byte[] content, String entryName) {
		//TODO
	}

	@Override
	public void close() throws Exception {
		//TODO
	}
	}
