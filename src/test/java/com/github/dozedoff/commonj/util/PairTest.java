/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.commonj.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("FCBL_FIELD_COULD_BE_LOCAL")
public class PairTest {
	private Pair<String, String> pair;

	private static final String testStringA = "A";
	private static final String testStringB = "B";
	private static final String testStringC = "C";

	@Before
	public void setUp() throws Exception {
		pair = new Pair<String, String>(testStringA, testStringB);
	}

	@Test
	public void testGetLeft() {
		assertThat(pair.getLeft(), is(testStringA));
	}

	@Test
	public void testSetLeft() {
		pair.setLeft(testStringC);
		assertThat(pair.getLeft(), is(testStringC));
	}

	@Test
	public void testGetRight() {
		assertThat(pair.getRight(), is(testStringB));
	}

	@Test
	public void testSetRight() {
		pair.setRight(testStringC);
		assertThat(pair.getRight(), is(testStringC));
	}

	@Test
	public void testEqualsNull() {
		assertFalse(pair.equals(null));
	}

	@Test
	public void testEqualsWrongType() {
		assertFalse(pair.equals(new Integer(5)));
	}

	@Test
	public void testEquals() {
		Pair<String, String> other = new Pair<String, String>("A", "B");
		assertThat(pair.equals(other), is(true));
	}

	@Test
	public void testNotEquals() {
		Pair<String, String> other = new Pair<String, String>(testStringB, testStringA);
		assertFalse(pair.equals(other));
	}

	@Test
	public void testNotEquals2() {
		Pair<String, String> other = new Pair<String, String>(testStringA, testStringC);
		assertFalse(pair.equals(other));
	}

	@Test
	public void testNotEqualsWrongType() {
		Pair<Integer, Integer> other = new Pair<Integer, Integer>(1, 2);
		assertFalse(pair.equals(other));
	}
}
