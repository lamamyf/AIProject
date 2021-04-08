package sourceCode;

import java.util.*;
public class BinaryTree {
    //BT Attributes:
    Node root;

    public BinaryTree() {
    	root = null;
    }

    public void generateTree(String [] input) {
    	Queue<Node> parents = new LinkedList<Node>();

    	Node rootNode = new Node(); 
    	root = rootNode;
    	parents.add(rootNode);
    	
    	for(int i = 1 ; i<input.length; i += 2) {
    		Node current = parents.poll();
    		
    		if (input[i] != null){
    			Node leftChild = new Node();
    			current.leftChild = leftChild;
    			parents.add(leftChild);
    		}
    		
    		if (i + 1 < input.length && input[i + 1] != null) {
    			Node rightChild = new Node();
    			current.rightChild = rightChild;
    			parents.add(rightChild);
    		}
    	
    	}
    }
}