all: BTrie.class dcLinkList.class FibonacciHeap.class Fnode.class SspGraph.class SspLinkedList.class SspNode.class routing.class
BTrie.class: BTrie.java
	javac -d . -classpath . BTrie.java
dcLinkList.class: dcLinkList.java
	javac -d . -classpath . dcLinkList.java
FibonacciHeap.class: FibonacciHeap.java
	javac -d . -classpath . FibonacciHeap.java
Fnode.class: Fnode.java
	javac -d . -classpath . Fnode.java
SspGraph.class: SspGraph.java
	javac -d . -classpath . SspGraph.java
SspLinkedList.class: SspLinkedList.java
	javac -d . -classpath . SspLinkedList.java
SspNode.class: SspNode.java
	javac -d . -classpath . SspNode.java
routing.class: routing.java
	javac -d . -classpath . routing.java
clean:
	rm -f *.class 
