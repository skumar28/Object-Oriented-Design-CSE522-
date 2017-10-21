
/**
 * This class is designed to work for both Tree and DupTree with: insert, delete,
 * min and max operations.
 * Added getter/setter for count and decrementCount
 * methods in AbsTree to handle the operation in both type of Tree.
 * 
 * @author Sandeep
 *
 */
class BST_Part2 {

	public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(25);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(25);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);

		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(25);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(25);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
	}
}

/**
 * This AbsTree class implemented to handle both Tree/DupTree operations
 * 
 * @author Sandeep
 *
 */
abstract class AbsTree {

	public AbsTree(int n) {
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
			count_duplicates();
		else if (value < n)
			if (right == null)
				right = add_node(n);
			else
				right.insert(n);
		else if (left == null)
			left = add_node(n);
		else
			left.insert(n);
	}

	/**
	 * delete a node from BST
	 * 1. If count is greater than 1 then decrement the count and return
	 * 2. if count is equal to 1 then delte the node
	 * @param n
	 */
	public void delete(int n) { // assume > 1 nodes in tree
		AbsTree t = find(n);
		// From snippet part1

		if (t == null) {
			// print out error message and return
			System.out.println("Value to be deleted not present in Tree: " + n);
			return;
		}

		// check the count value if it is greater than 1 then just decrement the
		// counter.
		if (t.getCount() > 1) {
			t.decreaseCount();
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

		// adapt Part 1 solution here
	}

	protected void case1(AbsTree t, AbsTree root) {
		if (t == root) {
			System.out.println("Only root node can not be deleted");
			return;
		}

		if (root.left == t) {
			root.left = null;
			return;
		}
		if (root.right == t) {
			root.right = null;
			return;
		}

		if (root.value > t.value)
			case1(t, root.left);
		else
			case1(t, root.right);
	}

	protected void case2(AbsTree t, AbsTree root) {

		AbsTree temp;
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

	protected void case3(AbsTree t, String side) {

		// which == "left" or which == "right"
		AbsTree tempNode;
		AbsTree root = this;

		if (root == t && t.right != null) {
			tempNode = t.right.min();

			if (tempNode.left == null && tempNode.right == null) {
				case1(tempNode, t);
				t.value = tempNode.value;
				t.setCount(tempNode.getCount());
				return;
			} else {
				case2(tempNode, t);
				t.value = tempNode.value;
				t.setCount(tempNode.getCount());
				return;
			}
		}
		// if left subtree is not null
		if (root == t && t.left != null) {
			tempNode = t.left.max();

			if (tempNode.left == null && tempNode.right == null) {
				case1(tempNode, t);
				t.value = tempNode.value;
				t.setCount(tempNode.getCount());
				return;
			} else {
				case2(tempNode, t);
				t.value = tempNode.value;
				t.setCount(tempNode.getCount());
				return;
			}
		}

		if (root.value > t.value)
			root.left.case3(t, "left");
		else
			root.right.case3(t, "right");

	}

	private AbsTree find(int n) {
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

	public AbsTree min() {

		if (this.left == null)
			return this;

		return this.left.min();
	}

	public AbsTree max() {
		if (this.right == null) {
			return this;
		}
		return this.right.max();
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;

	// Protected Abstract Methods

	protected abstract AbsTree add_node(int n);

	protected abstract void count_duplicates();

	// additional protected abstract methods, as needed
	protected abstract int getCount();

	protected abstract void setCount(int count);

	protected abstract void decreaseCount();
}

/**
 * Tree with no duplicate nodes
 * @author Sandeep
 *
 */
class Tree extends AbsTree {

	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}

	// return 1 for each node.
	protected int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	protected void decreaseCount() {
		;
	}

	@Override
	protected void setCount(int count) {
		;
	}

	// additional protected methods, as needed
}

/**
 * Tree consisting duplicate node as well
 * @author Sandeep
 *
 */
class DupTree extends AbsTree {

	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected int count;
	
	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	// additional protected methods, as needed

	protected int getCount() {
		return count;
	}

	protected void decreaseCount() {
		count--;
	}

	protected void setCount(int c) {
		count = c;
	}
}