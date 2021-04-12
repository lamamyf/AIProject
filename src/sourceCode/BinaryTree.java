package sourceCode;

import java.util.*;
public class BinaryTree {
	
    /* Binary Tree Attributes: */
    Node root;
    int numberOfCameras;
    int branchingFactor = 2;  /* equals 2 since it is a binary tree: */
    int numberOfNodes;

    
    public BinaryTree() {
    	root = null; /* the root is initially initialized to null. */
    }
    
    /*Sets the node value: either to 'c', which means the node has a camera, or to '0'
      and sets the node monitoring status: which is either NotMonitored, MonitoredBySelf, MonitoredByParent, MonitoredByChild. */
    public void setNodeValue(Node node, String value, NodeMonitoringStatus status){
    	node.status = status;
    	
    	if (value.equals("c")) {
    		node.value = "c";
    		numberOfCameras++;
    	}else {
    		node.value = "0";
    		numberOfCameras--;
    	}
    		
    }
    
    
    /* Constructs the binary tree based on a string array (the input). */
    public void buildBinaryTree(String [] input) {
    	Queue<Node> parents = new LinkedList<Node>(); /* A FIFO queue to hold the parent nodes. */

    	Node rootNode = new Node(); /* creating the root node. */
    	root = rootNode; /* saving the reference of the root node by assigning it to the root attribute. */
    	parents.add(rootNode); /* Adding the root to the parents queue, to be able to add children to it later on. */
    	numberOfNodes++;
    	
    	/* traversing the input array, starting from the second index -where i = 1-
    	   at each iteration, the loop processes two indexes at time. */
    	for(int i = 1 ; i<input.length; i += 2) {
    		Node current = parents.poll(); /* retrieves and removes the head from the parents queue. */
    		
    		/* the value at index i always represents the value of the left child, 
    		   if it's not equal to null, then we'll create a left child for the current (parent) node. */
    		if (input[i] != null){
    			Node leftChild = new Node(); /* creates a node */
    			current.leftChild = leftChild; /* sets the created node as a left child for the current (parent) node*/
    			parents.add(leftChild); /* adds the child to the parents queue, to be able to add children to it later on -if it has any-. */
    			numberOfNodes++; /* increment the number of nodes*/
    		} 
    		
    		/* the value at index (i + 1), always represents the value of the right child,
    		   if it's actually a valid index and it's not equal to null, we'll create a right child for the current (parent) node. */
    		if (i + 1 < input.length && input[i + 1] != null) {
    			Node rightChild = new Node();
    			current.rightChild = rightChild;
    			parents.add(rightChild);
    			numberOfNodes++;
    		}
    	
    	}
    }
    
    
    /* public method that calls the recursive method to install the surveillance cameras. */
    public void installSurveillanceCameras() {
    	installSurveillanceCameras(root);
    }
    
    
    /* Recursive method that traverses the tree using Post-order traversal technique, which visits the left subtree first, then the right subtree
       then the root node. */
    private void installSurveillanceCameras(Node current) {
    	
    	/*The base case, if there're no more nodes return, i.e backtrack to the last last visited node
    	  and complete its execution. */
    	if(current == null)
    		return;
    	
    	installSurveillanceCameras(current.leftChild); /* recursively calls the method and passes the left child. In this manner, the method keeps going  
    													  deep in the tree as much as possible until it reaches the end (base case), then it backtracks */
        installSurveillanceCameras(current.rightChild);/* to the last visited node and completes its execution through calling the method again and passing 
    													  its right child. */
    	 												 
    	
    	
    	boolean isFullSubTree = current.leftChild != null && current.rightChild != null; /* evaluated to true if the node has both left and right children. */
    	
    	/* if the node is monitored, either by: self, child or parent: */ 
    	if (current.isMonitored()) {
    		if (isFullSubTree) {
    			boolean unmonitredChild = false;
    			
    			/* if the left child has a camera and it doesn't have children or its children aren't monitored by it, i.e aren't depending on it */
    			if (current.leftChild.hasCamera() && (current.leftChild.isLeaf() || !current.leftChild.areChildrenMonitredByParent())){
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent); /* optimize the current state through setting the left child's value to zero */
    				unmonitredChild = true;														/*  and putting the camera into the parent node.*/
    			}
    			
    			/* if the right child has a camera and it doesn't have children or its children aren't monitored by it, i.e aren't depending on it*/
    			if (current.rightChild.hasCamera() && (current.rightChild.isLeaf() || !current.rightChild.areChildrenMonitredByParent())){
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent); /* optimize the current state through setting the child's value to zero */
    				unmonitredChild = true;														 /*  and putting the camera into the parent node.*/
    			}
    			
    			if (unmonitredChild) {
    				setNodeValue(current,"c", NodeMonitoringStatus.MonitoredBySelf); /* putting the camera into the parent. */
    				return;
    			}
    			
    		}
    		
    		/* if node has only one child or it doesn't have any (not interested in this case):*/
    		if (!isFullSubTree) {
    			
    			/* if the node has only a left child: if the left child has a camera and it doesn't have children or its children aren't monitored by it, i.e aren't depending on it*/
    			if(current.leftChild != null && current.leftChild.hasCamera() && 
    					(current.leftChild.isLeaf() || !current.leftChild.areChildrenMonitredByParent() )) {
    				
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent); /* optimize the current state through setting the child's value to zero */
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);			/*  and putting the camera into the parent node.*/
    				return;
    				
    			/* if the node has only a right child: if the right child has a camera and it doesn't have children or its children aren't monitored by it, i.e aren't depending on it*/		
    			}else if (current.rightChild != null && current.rightChild.hasCamera() && 
    					(current.rightChild.isLeaf() || !current.rightChild.areChildrenMonitredByParent()) ) {
    				
    				
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent); /* optimize the current state through setting the child's value to zero */
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);		     /*  and putting the camera into the parent node.*/
    				return;
    			}
    			
    			
    		}
    		
    		return;
    	}
    	
    	/* This means that the current node isn't monitored. So: */
    	/* put a camera into it initially, then we'll optimize it later! */
    	setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    	
    }
    
    
    /* Compute and print the space complexity for DFS which equals O(bm): */
    public void findSpaceComplexity(){
    	int BTMaxDepth = findBTMaxDepth(root);
        System.out.println("   - Space complexity for DFS is O(branching factor * maximum length of any path) = O("+ this.branchingFactor +" * "+ BTMaxDepth +") = O("+ (this.branchingFactor*BTMaxDepth)+").");

    }
    
    
    
   
    /* Find (m) which can be defined as the maximum length of any path in the state space = BT max depth: */
    private int findBTMaxDepth(Node current)
    {
        if (current == null)
            return 0;
        else
        {
            /* Find right and left subtrees depth: */
            int leftSubtreeDepth = findBTMaxDepth(current.leftChild);
            int rightSubtreeDepth = findBTMaxDepth(current.rightChild);
  
            /* Return the max depth of the two subtrees: */
            if (leftSubtreeDepth > rightSubtreeDepth)
                return (leftSubtreeDepth + 1);
             else
                return (rightSubtreeDepth + 1);
        }
    }

    
    
}