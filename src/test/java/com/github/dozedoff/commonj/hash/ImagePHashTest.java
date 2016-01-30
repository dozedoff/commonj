/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.commonj.hash;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.dozedoff.commonj.util.ImageUtil;

@SuppressWarnings("deprecation")
public class ImagePHashTest {
	private static Path testImageJPG, testImageSmallJPG, testImagePNG, testImageGIF, testImageBMP, testImagePNGtr, testImageGIFtr;
	
	private static final long TEST_IMAGE_HASH = -7271519559717445615L;
	private static final int MAX_HAMMING_DIFF = 4;
	
	private int imageSize = 32;

	private ImagePHash iph;

	@BeforeClass
	public static void setUpClass() throws Exception {
		testImageJPG = findFilePath("testImage.jpg");
		testImagePNG = findFilePath("testImage.png");
		testImageGIF = findFilePath("testImage.gif");
		testImageBMP = findFilePath("testImage.bmp");

		testImageSmallJPG = findFilePath("testImage_small.jpg");

		testImagePNGtr = findFilePath("testImage-with-transparency.png");
		testImageGIFtr = findFilePath("testImage-with-transparency.gif");
	}

	@Before
	public void setUp() throws Exception {
		iph = new ImagePHash();
	}

	@Test
	public void testgetLongHashCompareScaledandUnscaled() throws Exception {
		long normal = hashWithNoScale();
		long scaled = hashWithScale();

		assertThat(scaled, is(normal));
	}

	@Test
	public void testCompareScaledSourceImage() throws Exception {
		long normal = hashImage(testImageJPG);
		long scaled = hashImage(testImageSmallJPG);

		assertThat(getHammingDistance(normal, scaled), is(MAX_HAMMING_DIFF));
	}

	@Test
	public void testSourceImageHash() throws Exception {
		long normal = hashImage(testImageJPG);

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testSourceImageHashPNG() throws Exception {
		long normal = hashImage(testImagePNG);

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testSourceImageHashGIF() throws Exception {
		long normal = hashImage(testImageGIF);

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testSourceImageHashBMP() throws Exception {
		long normal = hashImage(testImageBMP);

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testSourceImageHashPNGtr() throws Exception {
		long normal = hashImage(testImagePNGtr);

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testSourceImageHashGIFtr() throws Exception {
		long normal = hashImage(testImageGIFtr);

		assertThat(getHammingDistance(normal, TEST_IMAGE_HASH), lessThanOrEqualTo(MAX_HAMMING_DIFF));
	}

	@Test
	public void testScaledSourceImageHash() throws Exception {
		long scaled = hashImage(testImageSmallJPG);

		assertThat(getHammingDistance(scaled, TEST_IMAGE_HASH), lessThanOrEqualTo(MAX_HAMMING_DIFF));
	}

	@Test
	public void testGetStringHash() throws Exception {
		String hash = iph.getStringHash(Files.newInputStream(testImageJPG));

		assertThat(hash, is("1100110110001011001011111011111001001000110111100101000000001000"));
	}

	@Test
	public void testGetLongHashBufferedImage() throws Exception {
		long normal = iph.getLongHash(ImageIO.read(Files.newInputStream(testImageJPG)));

		assertThat(normal, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testGetLongHashScaledImage() throws Exception {
		BufferedImage bi = ImageIO.read(testImageJPG.toFile());
		bi = ImageUtil.resizeImage(bi, imageSize, imageSize);

		long hash = iph.getLongHash(bi);

		assertThat(hash, is(TEST_IMAGE_HASH));
	}

	@Test
	public void testGetLongHashOpenCV() throws Exception {
		long hash = iph.getLongHash(testImageJPG);

		assertThat(hash, is(-7276023159344816079L));
	}

	private int getHammingDistance(long a, long b) {
		long xor = a ^ b;
		int distance = Long.bitCount(xor);
		return distance;
	}

	private long hashImage(Path path) throws IOException, Exception {
		return iph.getLongHash(new BufferedInputStream(Files.newInputStream(path)));
	}

	private long hashWithNoScale() throws Exception {
		return hashImage(testImageJPG);
	}

	private long hashWithScale() throws Exception {
		BufferedImage bi = ImageIO.read(testImageJPG.toFile());
		bi = ImageUtil.resizeImage(bi, imageSize, imageSize);

		return iph.getLongHashScaledImage(bi);
	}

	private static Path findFilePath(String fileName) throws URISyntaxException {
		return Paths.get(Thread.currentThread().getContextClassLoader().getResource(fileName).toURI());
	}
}
