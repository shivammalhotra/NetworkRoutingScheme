/*This is the node structure for the fibonacci heap*/

public class Fnode {
	
	/*Stores the id of the verteces*/
	int ids;
	/*Stroes the reference to previous variable of shortest path*/
	Fnode prev;
	/*Stores the degree of the node*/
	int degree;
	/*Stors refernce to child variable*/
	Fnode child;
	/*Stores the data value of the node*/
	Object data;
	/*Stores reference to the right node*/
	Fnode right;
	/*Stores refernce to the node on left*/
	Fnode left;
	/*Stores the parent of the current node*/
	Fnode parent;
	/*Stores the child cut value*/
	Boolean cc;
	
	/*create the a new node using the data value 
	 * and stores the data value in data variable*/
	public Fnode(Object data) {
		this.data = data;
		this.degree = 0;
	}
	
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public Fnode getChild() {
		return child;
	}
	public void setChild(Fnode child) {
		this.child = child;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Fnode getRight() {
		return right;
	}
	public void setRight(Fnode right) {
		this.right = right;
	}
	public Fnode getLeft() {
		return left;
	}
	public void setLeft(Fnode left) {
		this.left = left;
	}
	public Fnode getParent() {
		return parent;
	}
	public void setParent(Fnode parent) {
		this.parent = parent;
	}
	public Boolean getCc() {
		return cc;
	}
	public void setCc(Boolean cc) {
		this.cc = cc;
	}
	
	
}
