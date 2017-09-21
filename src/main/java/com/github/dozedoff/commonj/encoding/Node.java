/*
 * The MIT License (MIT)
 * Copyright (c) 2017 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */
package com.github.dozedoff.commonj.encoding;

public class Node {
	Node leftChild;
	Node rightChild;
	Node parent;

	double probability = -1.0;
	char symbol;

	boolean coding;

	public Node(Node leftChild, Node rightChild, Node parent) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parent = parent;
	}

	public boolean isLeaf() {
		return (leftChild == null && rightChild == null);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isJunction() {
		return ((!isLeaf()) && (!isRoot()));
	}
}
