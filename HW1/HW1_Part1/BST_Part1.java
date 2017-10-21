// CSE 522: Homework 1, Part 1
/**
 * This class is designed to work on BST: insert, delete, find, min and max
 * operations
 * 
 * @author Sandeep
 *
 */
class BST_Part1 {
	public static void main(String args[]) {
		Tree tr;
		tr = new Tree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(25);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(125);
		tr.delete(150);
		tr.delete(100);
		tr.delete(50);
		tr.delete(75);
		tr.delete(25);
		tr.delete(90);
	}
}

/**
 * Defines one node of a binary search tree
 * 
 * @author Sandeep
 *
 */
class Tree {

	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}
	/**
	 * insert node in BST
	 * @param n
	 */
	public void insert(int n) {
		if (value == n)
			return;
		if (value < n)
			if (right == null)
				right = new Tree(n);
			else
				right.insert(n);
		else if (left == null)
			left = new Tree(n);
		else
			left.insert(n);
	}

	/**
	 * This recursive method will return the minimum value in the tree.
	 * 
	 * @return
	 */
	public Tree min() {
		// returns the Tree node with the minimum value
		// you should write the code
		Tree leftNode = this.left;
		if (leftNode == null)
			return this;

		else {
			return leftNode.min();
		}
	}

	/**
	 * This method will return the maximum value in the tree
	 * 
	 * @return
	 */
	public Tree max() {
		// returns the Tree node with the maximum value
		// you should write the code
		Tree rightNode = this.right;
		if (rightNode == null)
			return this;
		else {
			return rightNode.max();
		}
	}

	/**
	 * find a given value in BST
	 * 
	 * @param n
	 * @return
	 */
	public Tree find(int n) {
		// returns the Tree node with value n
		// returns null if n is not present in the tree
		// you should write the code
		if (this != null && this.value == n)
			return this;

		if (this.value > n && this.left != null)
			return this.left.find(n);

		if (this.value < n && this.right != null) {
			return this.right.find(n);
		}

		return null;
	}

	/**
	 * delete a given value from BST
	 * 
	 * @param n
	 */
	public void delete(int n) {
		Tree t = find(n);
		if (t == null) {
			// print out error message and return
			System.out.println("Value to be deleted not present in Tree: " + n);
			return;
		}
		if (t.left == null && t.right == null) {
			// do case1 and return
			case1(t, this);
			return;
		}
		if (t.left == null || t.right == null) {
			if (t != this) {
				// do case2 and return
				case2(t, this);
				return;
			} else {
				// do case3 and return
				// if it's a root node and having left or right subtree
				case3(t, null);
				return;
			}
		}
		// do case3 and return
		case3(t, null);
	}

	private void case1(Tree t, Tree root) {

		if (root != null && root.left == t) {
			root.left = null;
			return;
		}
		if (root != null && root.right == t) {
			root.right = null;
			return;
		}

		if (t == root) {
			System.out.println("Only root node can not be deleted");
			return;
		}

		if (root.value > t.value)
			case1(t, root.left);
		else
			case1(t, root.right);
	}

	private void case2(Tree t, Tree root) {
		Tree temp;
		// If node to be deleted is in left
		if (root.left == t) {
			if (t.left == null) {
				temp = t.right;
				root.left = temp;
				t.right = null;
				return;
			} else {
				temp = t.left;
				root.left = temp;
				t.left = null;
				return;
			}
		}
		// if node to be deleted is in right
		if (root.right == t) {
			if (t.left == null) {
				temp = t.right;
				root.right = temp;
				t.right = null;
				return;
			} else {
				temp = t.left;
				root.right = temp;
				t.left = null;
				return;
			}
		}

		if (root.value > t.value)
			case2(t, root.left);
		else
			case2(t, root.right);
	}

	private void case3(Tree t, String which_side) {
		// which == "left" or which == "right"
		Tree root = this;
		Tree tempNode;
		// if right subtree is not null
		if (root == t && t.right != null) {
			//get the minimum value in right subtree
			tempNode = t.right.min();

			if (tempNode.left == null && tempNode.right == null) {
				case1(tempNode, t);
				t.value = tempNode.value;
				return;
			} else {
				case2(tempNode, t);
				t.value = tempNode.value;
				return;
			}
		}
		// if left subtree is not null
		if (root == t && t.left != null) {
			// get the maximum value in left subtree
			tempNode = t.left.max();

			if (tempNode.left == null && tempNode.right == null) {
				case1(tempNode, t);
				t.value = tempNode.value;
				return;
			} else {
				case2(tempNode, t);
				t.value = tempNode.value;
				return;
			}
		}

		if (root.value > t.value) {
			root.left.case3(t, "left");
		} else {
			root.right.case3(t, "right");
		}
	}

	protected int value;
	protected Tree left;
	protected Tree right;
}
