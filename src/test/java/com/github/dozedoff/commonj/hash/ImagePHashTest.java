/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.commonj.hash;

import static org.hamcrest.Matchers.is;
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

public class ImagePHashTest {
	private static Path testImageJPG, testImageSmallJPG;
	private int imageSize = 32;

	private ImagePHash iph;

	@BeforeClass
	public static void setUpClass() throws Exception {
		testImageJPG = findFilePath("testImage.jpg");
		testImageSmallJPG = findFilePath("testImage_small.jpg");
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

		assertThat(getHammingDistance(normal, scaled), is(4));
	}

	@Test
	public void testSourceImageHash() throws Exception {
		long normal = hashImage(testImageJPG);

		assertThat(normal, is(-6261023631344080448L));
	}

	@Test
	public void testScaledSourceImageHash() throws Exception {
		long scaled = hashImage(testImageSmallJPG);

		assertThat(scaled, is(-6261023624918439488L));
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
		bi = ImagePHash.resize(bi, imageSize, imageSize);

		return iph.getLongHashScaledImage(bi);
	}

	private static Path findFilePath(String fileName) throws URISyntaxException {
		return Paths.get(Thread.currentThread().getContextClassLoader().getResource(fileName).toURI());
	}
}
