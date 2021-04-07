package sourceCode;

public class BinaryTree {
    //BT Attributes:
    Node root;

    //Method called to ctreate a BT:
    private BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();
        return bt;
    }
    
    //Method to add Node with a value: 
    private Node addRecursive(Node current, String value) {
        if (current == null) {
            return new Node(value);
        }
    //This code is modified. To be later discussed:
        if (current.leftChild == null) {
            current.leftChild = addRecursive(current.leftChild, value);
        } else if (current.rightChild == null) {
            current.rightChild = addRecursive(current.rightChild, value);
        } else {
            return current;
        }

        return current;
    }
    
  //Method to add Node with a value: 
    public void add(String value) {
        root = addRecursive(root, value);
    }
    //Method to traverse using DFS (in-order):
    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.leftChild);
            System.out.print(" " + node.value);
            traverseInOrder(node.rightChild);
        }
    }
    
}