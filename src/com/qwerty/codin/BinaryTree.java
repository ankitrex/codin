package com.qwerty.codin;

import java.util.List;
import java.util.Vector;

public class BinaryTree {

	private Node root;

	private Node addRecursive(Node node, Integer score) {

		if (node == null) {

			return new Node(score);
		}

		if (score < node.score) {
			node.left = addRecursive(node.left, score);
		}
		else if (score > node.score) {
			node.right = addRecursive(node.right, score);
		}

		return node;
	}

	private Node deleteRecursive(Node node, Integer value) {

		if (node == null) {
			return null;
		}

		if (node.score == value) {

			if (node.left == null & node.right == null) {
				return null;
			}

			if (node.left == null) {
				return node.right;
			}

			if (node.right == null) {
				return node.left;
			}

			Integer smallest = getSmallestRecursive(node.right);
			node.score = smallest;
			deleteRecursive(node.right, smallest);

			return node;
		}

		if (value < node.score) {
			node.left = deleteRecursive(node.left, value);
			return node;
		}

		node.right = deleteRecursive(node.right, value);

		return node;
	}

	private Integer getSizeRecursive(Node node) {

		if (node == null) {

			return 0;
		}

		return 1 + getSizeRecursive(node.left) + getSizeRecursive(node.right);
	}

	private Boolean containsNodeRecursive(Node node, Integer score) {

		if (node == null) {

			return false;
		}

		if (node.score == score) {

			return true;
		}

		return node.score < score ? containsNodeRecursive(node.left, score) : containsNodeRecursive(node.right, score);
	}

	private Integer getSmallestRecursive(Node node) {

		if (node.left == null) {

			return node.score;
		}

		return getSmallestRecursive(node.left);
	}

	private Integer getLargestRecursive(Node node) {

		if (node.right == null) {

			return node.score;
		}

		return getLargestRecursive(node.right);
	}

	private String recursiveInorder(Node node) {

		if (node == null) {
			return "";
		}

		return recursiveInorder(node.left) + node.score + " " + recursiveInorder(node.right);
	}

	private String recursivePreorder(Node node) {

		if (node == null) {
			return "";
		}

		return node.score + " " + recursivePreorder(node.left) + recursivePreorder(node.right);
	}

	private String recursivePostorder(Node node) {

		if (node == null) {
			return "";
		}

		return recursivePostorder(node.left) + recursivePostorder(node.right) + node.score + " ";
	}

	private Boolean recursiveBalanceCheck(Node node) {

		if (node == null) {
			return true;
		}

		Integer leftH = recursiveHeight(node.left);
		Integer rightH = recursiveHeight(node.right);

		if (Math.abs(leftH - rightH) <= 1 && recursiveBalanceCheck(node.left) && recursiveBalanceCheck(node.right)) {
			return true;
		}

		return false;
	}

	private Integer recursiveHeight(Node node) {

		if (node == null) {
			return 0;
		}

		return 1 + Math.max(recursiveHeight(node.left), recursiveHeight(node.right));
	}

	private Vector<Integer> getTreeInorder(Node node) {

		if (node == null) {
			return new Vector<>();
		}

		Vector<Integer> subList = new Vector<>();

		subList.addAll(getTreeInorder(node.left));
		subList.add(node.score);
		subList.addAll(getTreeInorder(node.right));

		return subList;
	}

	private Node createBalancedTree(List<Integer> sortedElems) {

		Integer size = sortedElems.size();

		if (size == 0) {
			return null;
		}

		Integer middleElem = sortedElems.get(size / 2);

		Node node = new Node(middleElem);
		node.left = createBalancedTree(sortedElems.subList(0, size / 2));
		node.right = createBalancedTree(sortedElems.subList(size / 2 + 1, size));

		return node;
	}

	public void add(Integer score) {

		root = addRecursive(root, score);
	}

	public void delete(Integer value) {

		root = deleteRecursive(root, value);
	}

	public Boolean isEmpty() {

		return root == null;
	}

	public Integer getSize() {

		return getSizeRecursive(root);
	}

	public Boolean containsNode(Integer score) {

		return containsNodeRecursive(root, score);
	}

	public Integer findSmallestElement() {

		return getSmallestRecursive(root);
	}

	public Integer findLargestElement() {

		return getLargestRecursive(root);
	}

	public String traverseInorder() {

		return recursiveInorder(root);
	}

	public String traversePreorder() {

		return recursivePreorder(root);
	}

	public String traversePostorder() {

		return recursivePostorder(root);
	}

	public Boolean isBalanced() {

		return recursiveBalanceCheck(root);
	}

	public void balance() {

		List<Integer> sortedElems = getTreeInorder(root);

		root = createBalancedTree(sortedElems);
	}

}
