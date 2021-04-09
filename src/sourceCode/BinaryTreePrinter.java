package sourceCode;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BinaryTreePrinter {

    private Function<Node, String> nodeValue;
    private Function<Node, Node> leftNodes;
    private Function<Node, Node> rightNodes;

    public PrintStream outStream = System.out;
    public int minHorizontalSpace = 4;//minimum horizontal space between any 2 nodes

    //BinaryTreePrinter constructor
    public BinaryTreePrinter(Function<Node, String> nodeValue, Function<Node, Node> leftNodes, Function<Node, Node> rightNodes) {
        this.nodeValue = nodeValue;
        this.leftNodes = leftNodes;
        this.rightNodes = rightNodes;
    }

    public void printTree(Node rootNode) {
        List<TreeLine> treeLines = treeLinesBuilder(rootNode);
        printTreeLines(treeLines);
    }

    private List<TreeLine> treeLinesBuilder(Node rootNode) {
        if (rootNode == null) return Collections.emptyList();
        else {
            String rootLabel = nodeValue.apply(rootNode);
            List<TreeLine> leftTreeLines = treeLinesBuilder(leftNodes.apply(rootNode));
            List<TreeLine> rightTreeLines = treeLinesBuilder(rightNodes.apply(rootNode));

            int leftLinesCount = leftTreeLines.size();
            int rightLinesCount = rightTreeLines.size();
            int minCount = Math.min(leftLinesCount, rightLinesCount);
            int maxCount = Math.max(leftLinesCount, rightLinesCount);
            int maxRootSpacing = 0;
            for (int i = 0; i < minCount; i++) {
                int spacing = leftTreeLines.get(i).rightOffset - rightTreeLines.get(i).leftOffset;
                if (spacing > maxRootSpacing) maxRootSpacing = spacing;
            }
            int rootSpacing = maxRootSpacing + minHorizontalSpace;
            if (rootSpacing % 2 == 0) rootSpacing++;
           
            List<TreeLine> allTreeLines = new ArrayList<>();

            String renderedRootLabel = rootLabel.replaceAll("\\e\\[[\\d;]*[^\\d;]", "");

            allTreeLines.add(new TreeLine(rootLabel, -(renderedRootLabel.length() - 1) / 2, renderedRootLabel.length() / 2));

            int leftTreeAdjust = 0;
            int rightTreeAdjust = 0;

            if (leftTreeLines.isEmpty()) {
                if (!rightTreeLines.isEmpty()) {
                            allTreeLines.add(new TreeLine("\u2514\u2510", 0, 1));
                            rightTreeAdjust = 1;
                }
            } else if (rightTreeLines.isEmpty()) {
                        allTreeLines.add(new TreeLine("\u250C\u2518", -1, 0));
                        leftTreeAdjust = -1;             
            } else {         
                    int adjust = (rootSpacing / 2) + 1;
                    String horizontal = String.join("", Collections.nCopies(rootSpacing / 2, "\u2500"));
                    String branch = "\u250C" + horizontal + "\u2534" + horizontal + "\u2510";
                    allTreeLines.add(new TreeLine(branch, -adjust, adjust));
                    rightTreeAdjust = adjust;
                    leftTreeAdjust = -adjust;
            }

            for (int i = 0; i < maxCount; i++) {
                TreeLine leftLine, rightLine;
                if (i >= leftTreeLines.size()) {
                    rightLine = rightTreeLines.get(i);
                    rightLine.leftOffset += rightTreeAdjust;
                    rightLine.rightOffset += rightTreeAdjust;
                    allTreeLines.add(rightLine);
                } else if (i >= rightTreeLines.size()) {
                    leftLine = leftTreeLines.get(i);
                    leftLine.leftOffset += leftTreeAdjust;
                    leftLine.rightOffset += leftTreeAdjust;
                    allTreeLines.add(leftLine);
                } else {
                    leftLine = leftTreeLines.get(i);
                    rightLine = rightTreeLines.get(i);
                    int adjustedRootSpacing = (rootSpacing == 1 ? 1 : rootSpacing);
                    TreeLine combined = new TreeLine(leftLine.line + spaces(adjustedRootSpacing - leftLine.rightOffset + rightLine.leftOffset) + rightLine.line,
                            leftLine.leftOffset + leftTreeAdjust, rightLine.rightOffset + rightTreeAdjust);
                    allTreeLines.add(combined);
                }
            }
            return allTreeLines;
        }
    }
    
    private void printTreeLines(List<TreeLine> treeLines) {
        if (treeLines.size() > 0) {
            int minLeftOffset = minLeftOffset(treeLines);
            int maxRightOffset = maxRightOffset(treeLines);
            for (TreeLine treeLine : treeLines) {
                int leftSpaces = -(minLeftOffset - treeLine.leftOffset);
                int rightSpaces = maxRightOffset - treeLine.rightOffset;
                outStream.println(spaces(leftSpaces) + treeLine.line + spaces(rightSpaces));
            }
        }
    }

    private static int minLeftOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(l -> l.leftOffset).min().orElse(0);
    }

    private static int maxRightOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(l -> l.rightOffset).max().orElse(0);
    }

    private static String spaces(int n) {
        return String.join("", Collections.nCopies(n, " "));
    }

    private static class TreeLine {
        String line;
        int leftOffset;
        int rightOffset;

        TreeLine(String line, int leftOffset, int rightOffset) {
            this.line = line;
            this.leftOffset = leftOffset;
            this.rightOffset = rightOffset;
        }
    }
}
