package sourceCode;

/* Importing Java's input/output in order to handle files: */
import java.io.*;

public class MainClass {

	
	public static void main(String[] args) {

	System.out.println("Start program");
    System.out.println("__________________________________________________________________________________________");
    System.out.println("Welcome,");
    System.out.println("You can give us the bank's floor map as an input in the BinaryTree.txt file and \r\n" + 
    		"our program will return the mininmum number of camera(s) and their locations using DFS!");
    System.out.println("__________________________________________________________________________________________");
    
    /* Create BinaryTree object: */
    BinaryTree BTtree = new BinaryTree();
    
    /* Read binary tree's nodes from BinaryTree.txt file: */
    String[] nodes = readBinaryTreeFromFile();
    
    /* Enter this block if file was read correctly and the value read from it is valid: */
    if(nodes.length != 0) {
    	
    /* Build the binary tree: */
    BTtree.buildBinaryTree(nodes);
    
    /* Start search: */
    BTtree.installSurveillanceCameras();

    /* Print the solution: */
    /* Print the binary tree representing the bank's floor map: */
    BinaryTreePrinter treePrinter = new BinaryTreePrinter(node -> ("" + node.value), node -> node.leftChild, node -> node.rightChild);
    System.out.println("- The binary tree representing the floor map: ");
    treePrinter.printTree(BTtree.root);
    System.out.println("__________________________________________________________________________________________");
    
    /* Print the minimum number of cameras: */
    System.out.println("- At least "+BTtree.numberOfCameras+"  camera(s) are needed to monitor all nodes of the tree. The \r\n" + 
    		"above image shows one of the valid configurations of cameras' placement.");
    System.out.println("__________________________________________________________________________________________");
    
    /* Print the performance of the search algorithm: */
    System.out.println("- The performance of the search algorithm is: ");
    
    /* Print the time complexity: */
    BTtree.findTimeComplexity();    
    
    /* Call findSpaceComplexity() to compute and print the space complexity: */
    BTtree.findSpaceComplexity();
    
    System.out.println("__________________________________________________________________________________________");
	System.out.println("Program reached the end, Thank you!");

    }
	}
	
	
	
	public static String[] readBinaryTreeFromFile() {
		
	    /* emptyArray (with length 0) will be returned in case of an error: */
		String[] emptyArray = {};
		
		try {
			
		/* Create the BinaryTree.txt file, FileReader and BufferedReader: */
		File BTFile = new File("BinaryTree.txt");
		BTFile.createNewFile();
		FileReader fileReader = new FileReader (BTFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
			
		/* If BinaryTree.txt file is empty, an error message will be printed and the program ends. */
		if (BTFile.length() == 0){
			System.out.println("BinaryTree.txt file is empty. Please enter a valid bank floor map.");
		    System.out.println("__________________________________________________________________________________________");
			System.out.println("Program end.");
			System.exit(1);
		}
		
		/* If BinaryTree.txt file is not empty, read it: */
		String bankFloorMap = bufferedReader.readLine();
		System.out.println("The bank's floor map is: "+bankFloorMap);
		
		/* Validate the bank's floor map that was read from the BinaryTree.txt file: */
		String[] validatedBFM = validateBankFloorMap(bankFloorMap);
			
		/* If validateBankFloorMap(bankFloorMap) returns an array with length 0, then the value read from the file is invalid and program ends: */
		if (validatedBFM.length == 0){
		    System.out.println("__________________________________________________________________________________________");
			System.out.println("Program end.");
			System.exit(1);
		}
			
		bufferedReader.close();
		fileReader.close();
		
		/* If validateBankFloorMap(bankFloorMap) returns an array with length > 0, then the value read from the file is valid and returned as an array of strings: */
		return validatedBFM;
			
		}
		catch(FileNotFoundException e) {
				
			System.out.println("BinaryTree.txt file was not found.");
		    System.out.println("__________________________________________________________________________________________");
			System.out.println("Program end.");
			System.exit(1);
		}
		catch(Exception e) {
				
		System.out.println("An exception was found, find its information below: ");
		System.out.println(e.getMessage());
	    System.out.println("__________________________________________________________________________________________");
		System.out.println("Program end.");
		System.exit(1);}
		
	    /* emptyArray (with length 0) is returned in case of an error: */
		return emptyArray;
	}
		
	
	
	public static String[] validateBankFloorMap (String bankFloorMap) {
		
	    /* emptyArray (with length 0) will be returned in case of an error: */
		String [] emptyArray = {};
		
	    /* Trim white spaces from bankFloorMap: */
		bankFloorMap = bankFloorMap.trim();
		
		/* Validate if bankFloorMap has correct format. If not, then print an error message and return emptyArray (with length 0): */
		String pattern = "(^\\[[[\\,\\[a-zA-Z0-9]\\,]*\\]$)";
		if (!(bankFloorMap.matches(pattern))) {
			System.out.println("Bank floor map's value must follow a format. Here is an example for 2 values: [first,second] where first (root of BT) must equal 0 and second can be 0 or null.");
			return emptyArray;
		}
		
	    /* Remove [ and ] from bankFloorMap: */
		bankFloorMap = bankFloorMap.substring(1,bankFloorMap.length()-1);

	    /* Convert bankFloorMap to an array of strings: */
		String [] bankFloorMapArray = bankFloorMap.split(",");

	    /* Validate the length. If not valid, then print an error message and return emptyArray (with length 0): */
		if ((bankFloorMap.length() == 0) || (bankFloorMapArray.length == 0) || (bankFloorMapArray.length > 1000)) {
			System.out.println("Bank floor map's length must be in the range 1-1000.");
			return emptyArray;
		}
		
	    /* Validate that bankFloorMapArray's first index (root) != null. Else, print an error message and return emptyArray (with length 0): */
		if (bankFloorMapArray[0].equals("null")){
			System.out.println("The first value (root of BT) of bank floor map can not be null.");
		    return emptyArray;

		}
		
	    /* Validate that bankFloorMapArray only has 0 or null as values. If it is not valid print an error message and return emptyArray (with length 0): */
		for(int i = 0; i<bankFloorMapArray.length; i++) {

			if (!(bankFloorMapArray[i].equals("0"))){
					
				if (!(bankFloorMapArray[i].equals("null"))){
					
                    if(bankFloorMapArray[i].equals(""))
    					System.out.println(bankFloorMapArray[i]+ "An empty value was found which is considered invalid. Only 0 and null are considered valid values for a bank floor map.");
         
                    else
					    System.out.println(bankFloorMapArray[i]+ " value was found which is considered invalid. Only 0 and null are considered valid values for a bank floor map.");
				    return emptyArray;
				   }
			}
			
			/* If bankFloorMapArray[i] is a valid value, and that value is "null" as a string, then make it point to null: */
			if(bankFloorMapArray[i].equals("null"))
				bankFloorMapArray[i] = null;
			
		}
		
		/*If the value read from file is valid, return bankFloorMapArray: */
		return bankFloorMapArray;

		}
	
	
}