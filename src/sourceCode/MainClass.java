package sourceCode;

import java.io.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    System.out.println("Start program");
    System.out.println("________________________________________________________");
    readBinaryTreeFromFile();
    
	}
	
	public static void readBinaryTreeFromFile() {
		
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