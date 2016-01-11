public class FibonacciHeap {

	Fnode min;
	dcLinkList dll = new dcLinkList();
	Object[] ary;

	public void insert(Fnode node) {
		if (min == null) {
			min = node;
			min.left = min;
			min.right = min;
		} else if (min.left == min) {
			min.left = node;
			min.right = node;
			node.left = min;
			node.right = min;
		} else {
			min.right.left = node;
			node.right = min.right;
			min.right = node;
			node.left = min;
		}
		chkMin(node);
		node.parent = null;
	}

	public Fnode insert(Object data, int ids) {

		if (min == null) {
			min = insertQuick(data, ids);
			return min;
		} else if ((int) min.data > (int) data) {
			min = insertQuick(data, ids);
			return min;
		} else {
			Fnode f1 = insertQuick(data, ids);
			return f1;
		}
	}

	public Fnode insertQuick(Object data, int ids) {
		Fnode f1 = new Fnode(data);
		f1.ids = ids;
		insert(f1);
		return f1;
	}

	public void traverse() {
		if (min == null) {
			return;
		}

		if (min.right == min) {
			System.out.println("Data " + min.data + " & " + "Degree "
					+ min.degree);
		} else {
			System.out.println("Data " + min.data + " & " + "Degree "
					+ min.degree);
			Fnode temp = min.right;
			if (temp.right == min) {
				System.out.println("Data " + temp.data + " & " + "Degree "
						+ temp.degree);
			} else {
				while (temp != min) {
					System.out.println("Data " + temp.data + " & " + "Degree "
							+ temp.degree);
					temp = temp.right;
				}

			}

		}

	}

	public void deleteMin() {
		//System.out.println(min.data);
		if (min.child != null) {
			upgradeChild();
			replaceMin();
			pairWiseCombine();
		} else {
			if (min.right == min) {
				min = null;
			} else {
				replaceMin();
				pairWiseCombine();
			}
		}

	}

	public void replaceMin() {
		Fnode min2 = findMin2();
		removeReference(min);
		min.left = min;
		min.right = min;
		min = min2;

	}

	public Fnode findMin2() {
		Fnode min2 = min.right;
		Fnode temp = min;
		while ((int) temp.right.data != (int) min.data) {

			if ((int) min2.data > (int) temp.right.data) {
				min2 = temp.right;
			}
			temp = temp.right;
		}
		return min2;
	}

	public void upgradeChild() {
		Fnode temp = min.child;
		min.child = null;
		/*
		 * while(temp.right!=null){ Fnode temp2 = temp.right;
		 * removeReference(temp); insert(temp); temp = temp2; temp }
		 */

		while (temp != null) {
			if (temp.right != temp) {
				Fnode temp2 = temp.right;
				removeReference(temp);
				insert(temp);
				temp = temp2;
			} else {
				insert(temp);
				temp.parent = null;
				temp = null;
			}
		}

	}

	public void pairWiseCombine() {

		ary = new Object[100000];
		if (min.right == min) { // case with only 1 node
			return;
		} else {

			ary[min.getDegree()] = min;
			Fnode temp = min.right;
			if (temp.degree == 1000) {

			}
			if (temp.right == min) { // case with only 2 nodes in F heap
				if (min.getDegree() == temp.getDegree()) {
					pwcReadjust2(min, temp);
				} else {
					return;
				}

			} else { // rest of cases
				Fnode store = min.left;
				while (temp != store) {
					Fnode holder = temp.right;
					pwcReadjust(temp);
					temp = holder;
				}
				pwcReadjust(store);

			}
			// traverse();
		}
	}

	public void pwcReadjust(Fnode temp) { // takes the node and compares if
											// another with same degree exists
		if (ary[temp.getDegree()] == null) { // and if it exists it checks the
												// smaller and calls
												// pwcReadjust(smaller,larger)
			ary[temp.getDegree()] = temp;

		} else {
			Fnode temp2 = (Fnode) ary[temp.getDegree()];
			if ((int) temp2.data < (int) temp.data) {
				pwcReadjust2(temp2, temp);

			} else {
				pwcReadjust2(temp, temp2);
			}

		}

	}

	public void pwcReadjust2(Fnode node1, Fnode node2) { // removes the larger
															// node from linked
															// list, make new
															// child and parents
		if (node2 == min) {
			replaceMin();
		}

		removeReference(node2);
		if (node1.child != null) {
			node2.right = node1.child.right;
			node2.left = node1.child;
			if (node1.child.right == node1.child) {
				node1.child.left = node2;
			} else {
				node1.child.right.left = node2;
			}

			node1.child.right = node2;
		} else {
			node2.right = node2;
			node2.left = node2;
			node1.child = node2;
		}
		node2.parent = node1;
		ary[node1.degree] = null;
		node1.degree++;
		node2.cc = false;
		pwcReadjust(node1);
	}

	/*
	 * public void pwcReadjust2(Fnode node1, Fnode node2){ //removes the larger
	 * node from linked list, make new child and parents removeReference(node2);
	 * if(node1.child != null){ node2.right = node1.child; node2.left =
	 * node1.child.left; node1.child.left.right = node2; }else{ node2.right =
	 * node2; node2.left = node2; } node1.child = node2; node2.parent = node1;
	 * ary[node1.degree] =null; node1.degree++; node2.cc = false;
	 * pwcReadjust(node1); }
	 */
	public void removeReference(Fnode node2) { // Removes a node from linked
												// list
		node2.left.right = node2.right;
		node2.right.left = node2.left;
	}

	public void decreaseKey(Fnode node, int newVal) {
		if (node == min) {
			node.data = newVal;
			return;
		}

		/*
		 * if((int)node.data==(int)min.data){ node.data = newVal; return; }
		 */
		if (node.parent == null) {
			if (newVal < (int) node.data) {
				node.data = newVal;
				if ((int) min.data > (int) node.data) {
					min = node;
				}
			}
		}
		/*else if(newVal < (int)node.data && newVal>=(int)node.parent.data){
			node.data = newVal;
			
		}else */
		if (newVal < (int) node.data) {
			if (node.right == node) {
				node.data = newVal;
				if (node.parent.child == node) {
					node.parent.child = null;
				}
				recurringOps(node);
				/*
				 * node.parent = null; insert(node);
				 */
			} else {

				if (node.parent.child == node) {
					node.data = newVal;
					node.parent.child = node.right;
					removeReference(node);
					recurringOps(node);
				} else {
					node.data = newVal;
					removeReference(node);
					recurringOps(node);
				}
			}
		}
	}

	public void recurringOps(Fnode node) {
		// removeReference(node);
		insert(node);
		if (node.parent != null) {
			if (node.parent.cc != null) {
				cascadinCut(node.parent);
			}
		}
		chkMin(node);
		if (node.parent != null) {
			node.parent.degree--;
		}
		node.parent = null;
	}

	private void chkMin(Fnode node) {
		if ((int) node.data < (int) min.data) {
			min = node;
		}

	}

	private void cascadinCut(Fnode node) {
		if (node.cc == true) {
			recurringOps(node);

		} else {
			node.cc = true;
		}

	}

	public boolean checkCircular() {
		if (min == null)
			return true;
		Fnode temp = min.right;
		while (true) {
			if (temp == min)
				return true;
			temp = temp.right;
		}

	}
}
