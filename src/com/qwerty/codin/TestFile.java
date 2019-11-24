package com.qwerty.codin;

public class TestFile {

	public static void main(String[] args) {

		BinaryTree bTree = new BinaryTree();
		bTree.add(100);
		bTree.add(80);
		bTree.add(15);
		bTree.add(13);
		bTree.add(40);
		bTree.add(120);
		bTree.add(110);
		bTree.add(105);
		bTree.add(112);
		bTree.add(130);
		bTree.add(150);
		bTree.add(125);
		bTree.add(126);
		bTree.add(107);

		System.out.println(bTree.isEmpty());
		System.out.println(bTree.getSize());
		System.out.println(bTree.containsNode(100));
		System.out.println(bTree.containsNode(111));

		System.out.println();

		System.out.println(bTree.findSmallestElement());
		System.out.println(bTree.findLargestElement());

		System.out.println();

		System.out.println(bTree.traverseInorder());
		System.out.println(bTree.traversePreorder());
		System.out.println(bTree.traversePostorder());

		// System.out.println();

		// bTree.delete(100);
		// System.out.println(bTree.traverseInorder());
		// System.out.println(bTree.traversePreorder());
		// System.out.println(bTree.traversePostorder());

		System.out.println();

		System.out.println(bTree.isBalanced());
		bTree.balance();
		System.out.println(bTree.isBalanced());
	}
}
