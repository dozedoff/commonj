package file;

import static org.junit.Assert.*;

import helper.DataGenerator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryFileReaderTest {
	private static byte testData[];
	private static File testFile;
	
	private BinaryFileReader bfr;
	
	@BeforeClass
	public static void before() throws IOException{
		createTestFile(5120); // 5 kb
	}
	
	@Before
	public void setUp() throws Exception {
		bfr = new BinaryFileReader();
	}

	@Test
	public void testBufferToSmall() throws IOException {
		bfr = new BinaryFileReader(5);
		assertArrayEquals(testData, bfr.get(testFile));
	}
	
	@Test
	public void testDefaultBuffer() throws IOException {
		assertArrayEquals(testData, bfr.get(testFile));
	}

	@Test
	public void testGetString() throws Exception {
		assertArrayEquals(testData, bfr.get(testFile.toString()));
	}

	@Test
	public void testReUse() throws IOException {
		assertArrayEquals(testData, bfr.get(testFile));
		
		createTestFile(512);
		
		assertArrayEquals(testData, bfr.get(testFile));
	}

	private static File createTestFile(int fileSize) throws IOException {
		testFile = Files.createTempFile("BFRtestfile", null).toFile();
		testFile.createNewFile();
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(testFile));
		testData = DataGenerator.generateRandomByteArray(fileSize);
		bos.write(testData, 0, testData.length);
		bos.close();
		
		return testFile;
	}
}
