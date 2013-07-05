/*  Copyright (C) 2012  Nicholas Wright

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.commonj.image;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JLabel;

import org.junit.BeforeClass;
import org.junit.Test;

import sun.awt.image.ImageFormatException;

@SuppressWarnings("restriction")
public class SubsamplingImageLoaderTest {
	private static Path imagePath, textpath;

	@BeforeClass
	public static void setUp() throws Exception {
		new SubsamplingImageLoader(); // Used to get 100% coverage for static only classes
		imagePath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("test.jpg").toURI());
		textpath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("testdata.txt").toURI());
	}

	@Test
	public void testLoadImageNoSubSample() throws Exception {
		Dimension dim = new Dimension(100, 100);
		JLabel label = SubsamplingImageLoader.loadAsLabel(imagePath, dim);

		assertNotNull(label.getIcon());
		assertThat(label.getIcon().getIconHeight(), is(60));
		assertThat(label.getIcon().getIconWidth(), is(50));
	}

	@Test
	public void testLoadImageSubsample() throws Exception {
		Dimension dim = new Dimension(6, 6);
		JLabel label = SubsamplingImageLoader.loadAsLabel(imagePath, dim);

		assertNotNull(label.getIcon());
		assertThat(label.getIcon().getIconHeight(), is(6));
		assertThat(label.getIcon().getIconWidth(), is(5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoadImageNoSubSampleZeroDim() throws Exception {
		Dimension dim = new Dimension();
		SubsamplingImageLoader.loadAsLabel(imagePath, dim);
	}

	@Test(expected = ImageFormatException.class)
	public void testLoadNotAnImage() throws Exception {
		Dimension dim = new Dimension(100, 100);
		SubsamplingImageLoader.loadAsLabel(textpath, dim);
	}
}
