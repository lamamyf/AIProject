package sourceCode;

/* this enum represents the node's monitoring status, as each camera at a node can monitor its parent, itself,
   and its immediate children. */
public enum NodeMonitoringStatus{
	NotMonitored,MonitoredBySelf, MonitoredByParent, MonitoredByChild;
}
