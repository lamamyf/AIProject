package sourceCode;

class Node {
	
	//Attributes:
    String value; //Initially 0, but can have camera as a value.
    Node leftChild;
    Node rightChild;
    NodeMonitoringStatus status;

    public Node() {
        this.value = "0";
        leftChild = null;
        rightChild = null;
        status = NodeMonitoringStatus.NotMonitored;
    }
    
    public boolean isMonitred() {
    	
    	if (status == NodeMonitoringStatus.MonitoredByChild || status == NodeMonitoringStatus.MonitoredByParent
    			|| status == NodeMonitoringStatus.MonitoredBySelf)
    		
    	return true;
    	
    	if ((leftChild != null && leftChild.hasCamera()) ||
				(rightChild != null && rightChild.hasCamera())) {
    		
    		status =  NodeMonitoringStatus.MonitoredByChild;
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean hasCamera() {
    	return value.equals("c");
    }
    
    public boolean isLeaf() {
    	return leftChild == null && rightChild == null;
    }
    
    public boolean areChildernMonitredByParent() {
    	return (rightChild != null && rightChild.status == NodeMonitoringStatus.MonitoredByParent) ||
    			(leftChild != null && leftChild.status == NodeMonitoringStatus.MonitoredByParent);
    }
}
