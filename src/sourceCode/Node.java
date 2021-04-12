package sourceCode;

class Node {
	
	/*Node Attributes:*/
    String value; /*Initially 0, but can have camera as a value.*/
    Node leftChild;
    Node rightChild;
    NodeMonitoringStatus status; /* represents the node monitoring status, 
    which is either NotMonitored, MonitoredBySelf, MonitoredByParent, MonitoredByChild */

    public Node() {
        this.value = "0";
        leftChild = null;
        rightChild = null;
        status = NodeMonitoringStatus.NotMonitored; /* Initially set the NodeMonitoringStatus to NotMonitored  */
    }
    
    /* returns true if node is monitored, otherwise returns false*/
    public boolean isMonitred() {
    	
    	if (status == NodeMonitoringStatus.MonitoredByChild || status == NodeMonitoringStatus.MonitoredByParent
    			|| status == NodeMonitoringStatus.MonitoredBySelf)
    		
    	return true;
    	
    	/*if the node's status currently not monitored, but one or both of it's children have a camera
    	  then update the node monitoring status and return true*/
    	if ((leftChild != null && leftChild.hasCamera()) ||
				(rightChild != null && rightChild.hasCamera())) {
    		
    		status =  NodeMonitoringStatus.MonitoredByChild;
    		return true;
    	}
    	
    	return false;
    }
    
    /* returns true if node is a camera, otherwise returns false */
    public boolean hasCamera() {
    	return value.equals("c");
    }
    
    /* returns true if node is a leaf, i.e doesn't have children. otherwise returns false*/
    public boolean isLeaf() {
    	return leftChild == null && rightChild == null;
    }
    
    /* returns true if one or both of the node's children are monitored by parent (by the current node itself), otherwise returns false */
    public boolean areChildrenMonitredByParent() {
    	return (rightChild != null && rightChild.status == NodeMonitoringStatus.MonitoredByParent) ||
    			(leftChild != null && leftChild.status == NodeMonitoringStatus.MonitoredByParent);
    }
}
