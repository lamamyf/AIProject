package sourceCode;

import java.util.*;
public class BinaryTree {
    //BT Attributes:
    Node root;
    int numberOfCameras;
    int branchingFactor = 2;
    int numberOfNodes;

    
    public BinaryTree() {
    	root = null;
    }
    
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
    
    public void generateTree(String [] input) {
    	Queue<Node> parents = new LinkedList<Node>();

    	Node rootNode = new Node(); 
    	root = rootNode;
    	parents.add(rootNode);
    	numberOfNodes++;
    	
    	for(int i = 1 ; i<input.length; i += 2) {
    		Node current = parents.poll();
    		
    		if (input[i] != null){
    			Node leftChild = new Node();
    			current.leftChild = leftChild;
    			parents.add(leftChild);
    			numberOfNodes++;
    		}
    		
    		if (i + 1 < input.length && input[i + 1] != null) {
    			Node rightChild = new Node();
    			current.rightChild = rightChild;
    			parents.add(rightChild);
    			numberOfNodes++;
    		}
    	
    	}
    }
    
    public void search() {
    	search(root);
    }
    
    private void search(Node current) {
    	if(current == null)
    		return;
    	
    	search(current.leftChild);
    	search(current.rightChild);
    	
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
    
    //Find maximum length of any path in the state space = BT max depth:
    public void findBTMaxDepth(){
    	int BTMaxDepth = findBTMaxDepth(root);
        System.out.println("   - Space complexity for DFS is O(branching factor * maximum length of any path) = O(2 * "+ BTMaxDepth +") = O("+ (this.branchingFactor*BTMaxDepth)+").");

    }
    
    
    private int findBTMaxDepth(Node current)
    {
        if (current == null)
            return 0;
        else
        {
            //Find right and left subtrees depth:
            int leftSubtreeDepth = findBTMaxDepth(current.leftChild);
            int rightSubtreeDepth = findBTMaxDepth(current.rightChild);
  
            //Return the max of the two subtrees:
            if (leftSubtreeDepth > rightSubtreeDepth)
                return (leftSubtreeDepth + 1);
             else
                return (rightSubtreeDepth + 1);
        }
    }

    
    
}