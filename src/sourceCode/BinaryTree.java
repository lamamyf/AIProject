package sourceCode;

import java.util.*;
public class BinaryTree {
    //BT Attributes:
    Node root;
    int numberOfCameras;
    
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
    
    public void search() {
    	search(root);
    	System.out.println(numberOfCameras);
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
    			if (current.leftChild.hasCamera() && current.leftChild.isLeaf()){
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				unmonitredChild = true;
    			}
    			
    			if (current.rightChild.hasCamera() && current.rightChild.isLeaf()){
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
    			
    			if(current.leftChild != null && current.leftChild.hasCamera() && current.leftChild.isLeaf()) {
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    				return;
    				
    			}else if (current.rightChild != null && current.rightChild.hasCamera() && current.rightChild.isLeaf()) {
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    				return;
    			}
    			
    			
    		}
    		
    		if(!current.isLeaf()) {
    			
    			boolean unmonitredChild = false;
    		
    			if(current.leftChild != null && current.leftChild.hasCamera() && 
    					!current.leftChild.isLeaf() && !current.leftChild.areChildernMonitredByParent()) {
    				setNodeValue(current.leftChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				unmonitredChild = true;
    			}
    			
    			if(current.rightChild != null && current.rightChild.hasCamera() && 
    					!current.rightChild.isLeaf() && !current.rightChild.areChildernMonitredByParent()) {
    				setNodeValue(current.rightChild,"0", NodeMonitoringStatus.MonitoredByParent);
    				unmonitredChild = true;
    			}
    			
    			if(unmonitredChild)
    				setNodeValue(current,"c", NodeMonitoringStatus.MonitoredBySelf);
    			
    		}
    		
    		return;
    	}
    	
    	if (current.isLeaf()){
    		setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    		return;
    	}
    	
    	setNodeValue(current, "c", NodeMonitoringStatus.MonitoredBySelf);
    }
}