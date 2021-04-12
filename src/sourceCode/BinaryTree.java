package sourceCode;

import java.util.*;
public class BinaryTree {
	
    /* Binary Tree Attributes: */
    Node root;
    int numberOfCameras;
    int branchingFactor = 2;  /* equals 2 since it is a binary tree: */
    int numberOfNodes;

    
    public BinaryTree() {
    	root = null; /* the root is initially initialized to null */
    }
    
    /*Sets the node value: either with 'c', which means the node has a camera, or '0'
      and sets the node monitoring status: which is either NotMonitored,MonitoredBySelf, MonitoredByParent, MonitoredByChild */
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
    
    /* Constructs the binary tree based on a string array (the input) */
    public void buildBinaryTree(String [] input) {
    	Queue<Node> parents = new LinkedList<Node>(); /* A FIFO queue to hold the parent nodes*/

    	Node rootNode = new Node(); /* creating the root node */
    	root = rootNode; /* saving the reference of the root node by assigning it to the root attribute */
    	parents.add(rootNode); /* Adding the root to the parents queue, to be able to add children to it later on */
    	numberOfNodes++;
    	
    	/* traversing the input array, starting from the second index -where i = 1-
    	   at each iteration, the loop processes two indexes at time*/
    	for(int i = 1 ; i<input.length; i += 2) {
    		Node current = parents.poll(); /* retrieves and removes the head from the parents queue */
    		
    		/* the value at index i always represents the value of the left child, 
    		   if it's not equal to null, then we'll create a left child for the current (parent) node*/
    		if (input[i] != null){
    			Node leftChild = new Node(); /* creates a node */
    			current.leftChild = leftChild; /* sets the created node as a left child for the current (parent) node*/
    			parents.add(leftChild); /* adds the child to the parents queue, to be able to add children to it later on -if it has any-*/
    			numberOfNodes++; /* increment the number of nodes*/
    		} 
    		
    		/* the value at index (i + 1), always represents the value of the right child,
    		   if it's actually a valid index and it's not equal to null, we'll create a right child for the current (parent) node*/
    		if (i + 1 < input.length && input[i + 1] != null) {
    			Node rightChild = new Node();
    			current.rightChild = rightChild;
    			parents.add(rightChild);
    			numberOfNodes++;
    		}
    	
    	}
    }
    
    public void installSurveillanceCameras() {
    	installSurveillanceCameras(root);
    }
    
    private void installSurveillanceCameras(Node current) {
    	/*there are no more nodes along the current path, you move backwards on the same path to find nodes to traverse. */
    	if(current == null)
    		return;
    	
    	installSurveillanceCameras(current.leftChild);
    	installSurveillanceCameras(current.rightChild);
    	
    	boolean isFullSubTree = current.leftChild != null && current.rightChild != null;
    	
    	if (current.isMonitred()) {
    		if (isFullSubTree) {
    			boolean unmonitredChild = false;
    			if (current.leftChild.hasCamera() && (current.leftChild.isLeaf() || !current.leftChild.areChildernMonitredByParent())){
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				unmonitredChild = true;
    			}
    			
    			if (current.rightChild.hasCamera() && (current.rightChild.isLeaf() || !current.rightChild.areChildernMonitredByParent())){
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				unmonitredChild = true;
    			}
    			
    			if (unmonitredChild) {
    				setNodeValue(current,"c", NodeMonitoringStatus.MonitoredBySelf);
    				return;
    			}
    			
    		}
    		
    		//I have one child only /*changed not sure!*/
    		if (!isFullSubTree) {
    			
    			if(current.leftChild != null && current.leftChild.hasCamera() && 
    					(current.leftChild.isLeaf() || !current.leftChild.areChildernMonitredByParent() )) {
    				
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    				return;
    				
    			}else if (current.rightChild != null && current.rightChild.hasCamera() && 
    					(current.rightChild.isLeaf() || !current.rightChild.areChildernMonitredByParent()) ) {
    				
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    				return;
    			}
    			
    			
    		}
    		
    		return;
    	}
    	
    	/* put camera initially, will optimize it later!*/
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