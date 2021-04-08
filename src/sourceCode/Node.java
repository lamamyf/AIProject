package sourceCode;

class Node {
	
	//Attributes:
    String value; //Initially 0, but can have camera as a value.
    Node leftChild;
    Node rightChild;

    Node() {
        this.value = "0";
        leftChild = null;
        rightChild = null;
    }
}
