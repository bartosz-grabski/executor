package agh.bit.ideafactory.rmi.test.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

import agh.bit.ideafactory.rmi.utils.ZipUtil;

public class ZipUtilTest {
	
	private ZipUtil zipUtil;
	
	private byte[] testArray;
	
	@Before
	public void setUp() {
		zipUtil = new ZipUtil();
	}
	
	@Test
	public void shouldProperlyWriteStringToZipStream() throws Exception {
		
		byte[] buffer = new byte[2048];
		String stringToWrite = "someString";
		String entryName = "entry";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		zipUtil.openZipStream(outputStream);
		zipUtil.writeString(stringToWrite, entryName);
		zipUtil.close();
		outputStream.close();
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		ZipInputStream zis = new ZipInputStream(inputStream);
		ByteArrayOutputStream stringStream = new ByteArrayOutputStream();
		
		ZipEntry entry = zis.getNextEntry();
		assertEquals(entry.getName(), entryName);
		int read = 0;
		while ((read = zis.read(buffer)) != -1) {
			stringStream.write(buffer);
		}
		
		zis.close();
		inputStream.close();
		
		assertEquals(stringStream.toString().trim(),stringToWrite);
	}
	
	@Test
	public void shouldProperlyWriteByteArrayToZipStream() throws Exception {

		int bufferSize = 128;
		String entryName = "entry";
		givenTestArray(bufferSize);
		byte[] buffer = new byte[bufferSize];
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		zipUtil.openZipStream(outputStream);
		zipUtil.writeByteArray(testArray, entryName);
		zipUtil.close();
		outputStream.close();
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		ZipInputStream zis = new ZipInputStream(inputStream);
		
		ZipEntry entry = zis.getNextEntry();
		assertEquals(entry.getName(), entryName);
		int read = 0;
		zis.read(buffer);
		zis.close();
		inputStream.close();
		
		assert(Arrays.equals(buffer, testArray));		
		
	}
	
	
	private void givenTestArray(int bufferSize) {
		byte[] testArray = new byte[bufferSize];
		for (int i = 0; i < bufferSize; i++) {
			testArray[i] = (byte) (i % 128);
		}
		this.testArray = testArray;
	}
}
