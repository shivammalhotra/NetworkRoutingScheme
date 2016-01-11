import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/*this object creates the graph using the input file
 * it creates an adjacency list of the vertices 
 * where vertices are the index of the arraylist
 * and the corresondng edges and thier weights are stored 
 * in sspnode and are reference is stored in ssplinkedlist */
public class SspGraph {
	int verteces; // number of vertices in the file

	int edges;// number of edges in the file

	ArrayList<SspLinkedList> aryll;// this arraylist stores the refernce to head
									// node of linked list of particular
									// vertices
	FibonacciHeap fheap; // This heap is used in shortest path calculation
	Fnode[] visited; // array which stores the nodes already visited
	Fnode[] unvisited; // array which stores the nodes not visited yet

	// creates a graph using a number of vertices
	public SspGraph(int v) {
		aryll = new ArrayList();
		for (int i = 0; i < v; i++) {
			SspLinkedList sll = new SspLinkedList();
			aryll.add(i, sll);
		}
	}

	/*
	 * creates a graph using adjacency list notation using the given file name
	 */
	public SspGraph(String fName) {
		try {

			Scanner in = new Scanner(new FileReader(fName));
			verteces = in.nextInt();
			aryll = new ArrayList();
			for (int i = 0; i < verteces; i++) {
				SspLinkedList sll = new SspLinkedList();
				aryll.add(i, sll);
			}

			edges = in.nextInt();
			/*
			 * reads the next 3 integer values from the file and stores them as
			 * vertex, edge and weight respectively
			 */
			while (in.hasNext()) {
				int v = in.nextInt();
				int e = in.nextInt();
				int w = in.nextInt();
				// System.out.println(v+" "+e+" "+w );
				SspLinkedList sll = aryll.get(v);
				sll.add(e, w);
				sll = aryll.get(e);
				sll.add(v, w);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// returns the list of vertices in the graph
	public int v() {
		return aryll.size();
	}

	// adds an edge to the existeing vertex
	public void addEdge(int v, int e, int w) {
		SspLinkedList sll = aryll.get(v);
		sll.add(e, w);
	}

	public SspLinkedList adj(int v) {
		SspLinkedList sll = aryll.get(v);

		return sll;
	}

	public ArrayList<SspLinkedList> printIt() {
		return aryll;
	}

	/*
	 * Used to mark the next node of a particular node in shortest path it uses
	 * visited array to calculate the next node
	 */
	public int getNextNode(int i, int j) {
		Fnode temp = visited[j];
		while (temp != null) {
			if (temp.prev == visited[i])
				return temp.ids;
			temp = temp.prev;
		}
		return -1;
	}

	/* It rint path of shortest node from node to destination */
	public void printPath(Fnode fNode) {
		if (fNode == null) {
			return;
		}
		printPath(fNode.prev);
		System.out.print(fNode.ids + " ");

	}

	/*
	 * calculates the shortest path from the source to destination it first
	 * calls the shortestpath setup method which calculates the shortest path
	 * from source node to all possible nodes
	 */
	public void shortestPath(int source, int destination) {
		shortestPathSetup(source);
		Fnode temp = visited[destination];
		printPath(temp);
		/*
		 * String ssp; while (temp.prev != null) {
		 * System.out.print(temp.ids+" "); temp = temp.prev; }
		 * System.out.print(temp.ids+" ");
		 */
	}

	/*
	 * This method is called from the shortest path method which calculates the
	 * shortest path from source node to all possible nodes
	 */

	public void shortestPathSetup(int source) {
		fheap = new FibonacciHeap();
		visited = new Fnode[verteces];
		unvisited = new Fnode[verteces];
		int i;
		double inf = Double.POSITIVE_INFINITY;
		int inf2 = Integer.MAX_VALUE;

		/* Inserts given vertices into fibonacci heap */
		for (i = 0; i <= verteces - 1; i++) {
			if (i == source) {
				Fnode f1 = fheap.insert(0, i);
				unvisited[i] = f1;
			} else {
				Fnode f2 = fheap.insert(inf2, i);
				unvisited[i] = f2;
				// System.out.println(i);
			}
		}
		/*this loop will run as long as the all the elements 
		 * are popped from the fibonnaci heap*/
		while (fheap.min != null) {
			int ver1 = fheap.min.ids;
			// System.out.println(fheap.min.ids);
			if (fheap.min.ids == 3361) {
				System.out.println("");
			}
			fheap.deleteMin();
			SspLinkedList ll = aryll.get(ver1);

			SspNode temp = ll.head;
			while (temp != null) {

				int ver2 = temp.edge;
				if (visited[ver2] == null) {

					int newVal = temp.weight + (int) unvisited[ver1].data;
					/*Checks if weight in node is currently less than the 
					 * new value which is the sum of the weight from source
					 *  to destination and weight of the source node*/
					if (((int) unvisited[ver2].data > newVal)) {
						fheap.decreaseKey(unvisited[ver2], newVal);
						unvisited[ver2].prev = unvisited[ver1];

					}

				}
				temp = temp.id;
			}
			visited[ver1] = unvisited[ver1];
			unvisited[ver1] = null;

			// System.out.println(visited[ver1].data);

		}

	}

	public boolean isVisited(int i) {
		if (visited[i] != null) {
			return true;
		} else {
			return false;
		}

	}

	public int getMinIndex(Fnode min) {
		for (int i = 0; i <= verteces; i++) {
			if (unvisited[i] == min) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * public getDestIndex(Fnode Dest){ for(int i=0;i<=verteces;i++){
	 * if(visited[i].data == Dest){ return i; } } return -1; }
	 */
}
