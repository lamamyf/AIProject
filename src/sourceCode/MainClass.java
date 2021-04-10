package sourceCode;

import java.io.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    System.out.println("Start program");
    System.out.println("__________________________________________________________________________________________");
    System.out.println("Welcome,");
    System.out.println("You can give us the bank's floor map as an input in the BinaryTree.txt file and \r\n" + 
    		"our program will return the mininmum number of 📷 (s) and their locations using DFS!");
    System.out.println("__________________________________________________________________________________________");
    
    BinaryTree BTtree = new BinaryTree();
    //-------------Manual: only for testing the tree:--------------------------------------
    String [] nodes = {"0","0","0",null,"0",null,"0","0","0"};
    //-------------Manual: only for testing the tree:-------------------------------------
    
    //-----------Read BT from file:-------------------------------------------------------
//    String[] nodes = readBinaryTreeFromFile();
    //-----------Read BT from file:-------------------------------------------------------
    
    if(nodes.length != 0) {
    BTtree.generateTree(nodes);
    BTtree.search();
    BinaryTreePrinter treePrinter = new BinaryTreePrinter(node -> ("" + node.value), node -> node.leftChild, node -> node.rightChild);
    System.out.println("● The binary tree representing the floor map: ");
    treePrinter.printTree(BTtree.root);
    System.out.println("__________________________________________________________________________________________");
    System.out.println("● At least "+BTtree.numberOfCameras+"  📷 (s) are needed to monitor all nodes of the tree. The \r\n" + 
    		"above image shows one of the valid configurations of 📷   placement.");
    System.out.println("__________________________________________________________________________________________");
    System.out.println("● The performance of the search algorithm is: ");
    System.out.println("   ● Time complexity for DFS is O(Number of nodes visited) = O("+ BTtree.numberOfNodes+").");
    BTtree.findBTMaxDepth();
    System.out.println("__________________________________________________________________________________________");
	System.out.println("Program reached the end, Thank you!");

    }
	}
	
	public static String[] readBinaryTreeFromFile() {
		
		String[] emptyArray = {};
		
		try {
				
		File BTFile = new File("BinaryTree.txt");
		BTFile.createNewFile();
		FileReader fileReader = new FileReader (BTFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
			
		//If BinaryTree.txt is empty, the program ends with a message.
		if (BTFile.length() == 0){
			System.out.println("BinaryTree.txt file is empty. Please enter a valid bank floor map.");
			System.out.println("Program end.");
			System.exit(1);
		}
			
		String bankFloorMap = bufferedReader.readLine();
		System.out.println("The bank's floor map is: "+bankFloorMap);
		
		String[] validatedBFM = validateBankFloorMap(bankFloorMap);
			
		if (validatedBFM.length == 0){
			System.out.println("Program end.");
			System.exit(1);
		}
			
		bufferedReader.close();
		fileReader.close();
		
		return validatedBFM;
			
		}
		catch(FileNotFoundException e) {
				
			System.out.println("BinaryTree.txt file was not found.");
			System.out.println("Program end.");
			System.exit(1);
		}
		catch(Exception e) {
				
		System.out.println("An exception was found, find its information below: ");
		System.out.println(e.getMessage());
		System.out.println("Program end.");
		System.exit(1);}
		
		return emptyArray;
	}
		
	public static String[] validateBankFloorMap (String bankFloorMap) {
		//Note: This method can be updated later.

		String [] emptyArray = {};
		
		//Validate if bankFloorMap has the format [x,y,0]:
		String pattern = "(^\\[[[\\,\\[a-zA-Z0-9]\\,]*\\]$)";
		if (!(bankFloorMap.matches(pattern))) {
			System.out.println("Bank floor map's value must follow a format. Here is an example for 2 values: [first,second] where first (root of BT) must equal 0 and second can be 0 or null.");
			return emptyArray;
		}
		
		//Remove [ and ] from bankFloorMap:
		bankFloorMap = bankFloorMap.substring(1,bankFloorMap.length()-1);

		String [] bankFloorMapArray = bankFloorMap.split(",");

		//Validate bankFloorMap's length:
		if ((bankFloorMap.length() == 0) || (bankFloorMapArray.length == 0) || (bankFloorMapArray.length > 1000)) {
			System.out.println("Bank floor map's length must be in the range 1-1000.");
			return emptyArray;
		}
		
		//Ensure that first index (root) != null:
		if (bankFloorMapArray[0].equals("null")){
			System.out.println("The first value (root of BT) of bank floor map can not be null.");
		    return emptyArray;

		}
		
		//Ensure that the array only has 0 or null as values:
		for(int i = 0; i<bankFloorMapArray.length; i++) {
			System.out.println(bankFloorMapArray[i]+ " for."+bankFloorMapArray.length);

			if (!(bankFloorMapArray[i].equals("0"))){
					
				if (!(bankFloorMapArray[i].equals("null"))){
					
                    if(bankFloorMapArray[i].equals(""))
    					System.out.println(bankFloorMapArray[i]+ "An empty value was found which is considered invalid. Only 0 and null are considered valid values for a bank floor map.");
         
                    else
					    System.out.println(bankFloorMapArray[i]+ " value was found which is considered invalid. Only 0 and null are considered valid values for a bank floor map.");
				    return emptyArray;
				   }
			}
			
			//If it is a valid value, and that value is "null" as a string, then make it point to null.
			if(bankFloorMapArray[i].equals("null"))
				bankFloorMapArray[i] = null;
			
		}
		
		//If input is valid, return bankFloorMap as a string array:
		return bankFloorMapArray;

		}
}