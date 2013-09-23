package agh.bit.ideafactory.rmi.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Component;

@Component
public class ZipUtil implements AutoCloseable {
	
	private ZipOutputStream zipStream;
	
	public void openZipStream(OutputStream outputStream) {
		zipStream = new ZipOutputStream(outputStream);
	}
	
	public void writeString(String content, String entryName) throws IOException {
		zipStream.putNextEntry(new ZipEntry(entryName));
		zipStream.write(content.getBytes());
		zipStream.closeEntry();
	}
	
	public void writeByteArray(byte[] content, String entryName) throws IOException {
		zipStream.putNextEntry(new ZipEntry(entryName));
		zipStream.write(content);
		zipStream.closeEntry();
	}

	@Override
	public void close() throws Exception {
		zipStream.close();
	}
}