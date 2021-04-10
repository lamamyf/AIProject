package sourceCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BinaryTreePrinter {
    
	//Function is an interface that takes two parameters <inputType, resultType>	
    private Function<Node, String> nodeValue;//takes the Node and return its String value
    private Function<Node, Node> leftNodes;//takes the Node and return its left Node
    private Function<Node, Node> rightNodes;//takes the Node and return its left Node

    public int minHorizontalSpace = 4;//minimum horizontal space between any 2 nodes

    //BinaryTreePrinter constructor
    public BinaryTreePrinter(Function<Node, String> nodeValue, Function<Node, Node> leftNodes, Function<Node, Node> rightNodes) {
        this.nodeValue = nodeValue;
        this.leftNodes = leftNodes;
        this.rightNodes = rightNodes;
    }

    public void printTree(Node rootNode) {
        List<TreeLine> treeLines = treeLinesBuilder(rootNode);//called to build the tree lines from the root node
        printTreeLines(treeLines);//only to print the lines
    }

    private List<TreeLine> treeLinesBuilder(Node rootNode) {
        if (rootNode == null) return Collections.emptyList();//to avoid null pointer exception
        else {
        	//method apply applies the function on its argument 
            String rootValue = nodeValue.apply(rootNode);//first build the lines for the root node
            List<TreeLine> leftTreeLines = treeLinesBuilder(leftNodes.apply(rootNode));//build the lines for the left nodes recursively 
            List<TreeLine> rightTreeLines = treeLinesBuilder(rightNodes.apply(rootNode));//build the lines for the right nodes recursively

            //get the size of the left and right lines
            int leftLinesSize = leftTreeLines.size();
            int rightLinesSize = rightTreeLines.size();
            
            //get the maximum and minimum size for the left and right lines
            int minLinesSize = Math.min(leftLinesSize, rightLinesSize);
            int maxLinesSize = Math.max(leftLinesSize, rightLinesSize);
            int maxRootSpacing = 0;
            
            //loop on the minimum lines size to set the maximum root spacing
            for (int i = 0; i < minLinesSize; i++) {
                int spacing = leftTreeLines.get(i).rightOffset - rightTreeLines.get(i).leftOffset;
                if (spacing > maxRootSpacing) 
                	maxRootSpacing = spacing;
            }
            
            //set the root spacing to be the number of spacing between the two subtrees
            int rootSpacing = maxRootSpacing + minHorizontalSpace;
            if (rootSpacing % 2 == 0) 
            	rootSpacing++;
           
            List<TreeLine> allTreeLines = new ArrayList<>();

            String renderedRootValue = rootValue;
            
            //add the root and its two branches that leads to its two subtrees
            allTreeLines.add(new TreeLine(rootValue, -(renderedRootValue.length() - 1) / 2, renderedRootValue.length() / 2));

            //used to set the adjustment for the left and right subtrees
            int leftTreeAdjustment = 0;
            int rightTreeAdjustment = 0;
            
            //if we only have a right subtree
            if (leftTreeLines.isEmpty()) {
                if (!rightTreeLines.isEmpty()) {
                            allTreeLines.add(new TreeLine("\u2514\u2510", 0, 1));
                            rightTreeAdjustment = 1;
                }
            
            //if we only have a left subtree
            } else if (rightTreeLines.isEmpty()) {
                        allTreeLines.add(new TreeLine("\u250C\u2518", -1, 0));
                        leftTreeAdjustment = -1; 
                        
            //if we have both right and left subtree
            } else {         
                    int adjust = (rootSpacing / 2) + 1;
                    String horizontal = String.join("", Collections.nCopies(rootSpacing / 2, "\u2500"));
                    String branch = "\u250C" + horizontal + "\u2534" + horizontal + "\u2510";
                    allTreeLines.add(new TreeLine(branch, -adjust, adjust));
                    rightTreeAdjustment = adjust;
                    leftTreeAdjustment = -adjust;
            }
            
            //loop to add the lines of the subtrees with the calculated spaces and the adjusted offsets
            for (int i = 0; i < maxLinesSize; i++) {
                TreeLine leftLine, rightLine;
                if (i >= leftTreeLines.size()) {
                    rightLine = rightTreeLines.get(i);
                    rightLine.leftOffset += rightTreeAdjustment;
                    rightLine.rightOffset += rightTreeAdjustment;
                    allTreeLines.add(rightLine);
                } else if (i >= rightTreeLines.size()) {
                    leftLine = leftTreeLines.get(i);
                    leftLine.leftOffset += leftTreeAdjustment;
                    leftLine.rightOffset += leftTreeAdjustment;
                    allTreeLines.add(leftLine);
                } else {
                    leftLine = leftTreeLines.get(i);
                    rightLine = rightTreeLines.get(i);
                    int adjustedRootSpacing = (rootSpacing == 1 ? 1 : rootSpacing);
                    TreeLine combined = new TreeLine(leftLine.line + spaces(adjustedRootSpacing - leftLine.rightOffset + rightLine.leftOffset) + rightLine.line,
                            leftLine.leftOffset + leftTreeAdjustment, rightLine.rightOffset + rightTreeAdjustment);
                    allTreeLines.add(combined);
                }
            }
            return allTreeLines;
        }
    }
    
    private void printTreeLines(List<TreeLine> treeLines) {
        if (treeLines.size() > 0) {
        	//Set the min left offset and max right offset for the tree lines
            int minLeftOffset = minLeftOffset(treeLines);
            int maxRightOffset = maxRightOffset(treeLines);
            for (TreeLine treeLine : treeLines) {
                int leftSpaces = -(minLeftOffset - treeLine.leftOffset);
                int rightSpaces = maxRightOffset - treeLine.rightOffset;
                System.out.println(spaces(leftSpaces) + treeLine.line + spaces(rightSpaces));
            }
        }
    }

    //to get the min left and max right offsets for the tree lines
    private static int minLeftOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(left -> left.leftOffset).min().orElse(0);
    }
    private static int maxRightOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(right -> right.rightOffset).max().orElse(0);
    }

    //to set a number of spaces to the tree lines
    private static String spaces(int numberOfSpaces) {
        return String.join("", Collections.nCopies(numberOfSpaces, " "));
    }

    //private class for the tree lines
    private static class TreeLine {
        String line;
        int leftOffset;
        int rightOffset;

        //TreeLine class constructor
        TreeLine(String line, int leftOffset, int rightOffset) {
            this.line = line;
            this.leftOffset = leftOffset;
            this.rightOffset = rightOffset;
        }
    }
}
