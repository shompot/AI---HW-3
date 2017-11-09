import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.min;

public class PuzzleTree {

    private Puzzle root;
    private Puzzle[] currentNodes;
    private int w = 2;
    private int nodeNum = 0;
    private int leafNum = 0;

    // --------------CONSTRUCTORS---------------
    PuzzleTree ( int w){
        this.root = new Puzzle();
        this.root.puzzleGenerator();
        this.root.setParent(null);
        this.w = w;
        this.currentNodes = new Puzzle[w];
        nodeNum = 1;
        leafNum = 1;
    }

    PuzzleTree (Puzzle root){
        this.root = root;
        nodeNum = 1 + root.getChildNum();
        leafNum = root.getChildNum();
    }

    // -----------------SETTERS-------------------
    public void setRoot (Puzzle node){ this.root = node; }
    public void setWidth (int w) { this.w = w; }

    // -----------------GETTERS-------------------
    public Puzzle getRoot () { return this.root; }
    public int getWidth () { return w; }
    public int getNodeNum (){ return nodeNum; }
    public int getLeafNum () { return leafNum; }

    // ---------CALCULATING METHODS---------------
    public void generateTree (){

        root.generateNextMoves();
        //printNode(root);

        nodeNum += root.getChildNum();
        leafNum = root.getChildNum();

        Puzzle[] children = new Puzzle[root.getChildNum()];

        for (int i=0; i < root.getChildNum(); i ++)
            children[i] = root.getChild(i);

        currentNodes = findWBestNodes(children);

       // printList(currentNodes);

        while (!hasReachedGoal()) {
            generateTreeHelper(currentNodes);
        }
    }

    public Puzzle[] findWBestNodes (Puzzle[] children){


        int [] heuristics = new int [children.length];

        for (int i = 0; i < heuristics.length; i ++)
            heuristics [i] = children[i].getHeuristic();

        int [] sortedHeuristics = sort (heuristics);

        Puzzle [] result = new Puzzle[min(this.w, heuristics.length)];
        for (int i=0; i < min(this.w, heuristics.length); i ++){
            result[i] = children[sortedHeuristics[i]];
        }

        return result;
    }

    public void generateTreeHelper (Puzzle [] nodes){

        int childNum = 0;
        for (int i=0; i < nodes.length; i ++){
            nodes[i].generateNextMoves();
            //printNode(nodes[i]);
            childNum += nodes[i].getChildNum();
            leafNum --;
        }
        Puzzle [] children = new Puzzle[childNum];
        int k = 0;
        for (int i=0; i < nodes.length; i ++){
            for (int j=0; j < nodes[i].getChildNum(); j ++) {
                children[k] = nodes[i].getChild(j);
                k++;
            }
        }

        nodeNum += k;
        leafNum += k;
        currentNodes = findWBestNodes(children);
       // printList(currentNodes);
    }

    public boolean hasReachedGoal (){

        int [] heuristics = new int [currentNodes.length];

        for (int i = 0; i < currentNodes.length; i ++) {
            heuristics[i] = currentNodes[i].getHeuristic();
            if (heuristics[i] == 0)
                return true;
        }

        return false;
    }

    public  int [] sort (int[] arr) {
        int [] result = new int [arr.length];

        for (int i=0; i < arr.length; i ++)
            result[i] = i;

        for (int i=0; i < arr.length - 1; i ++){
            for (int j = i+1; j < arr.length; j ++){
                if (arr[result[i]] > arr[result[j]]){
                    int t = result[i];
                    result[i] = result[j];
                    result[j] = t;
                }
            }
        }

        return result;
    }

    public ArrayList<Puzzle> solution (){
        ArrayList<Puzzle> result = new ArrayList<Puzzle>();

        for (int i=0; i < currentNodes.length; i ++){
            if (currentNodes[i].getHeuristic() == 0){
                result.add(currentNodes[i]);
                break;
            }
        }
        Puzzle t = result.get(0).getParent();
        while (t != null){
            result.add(0, t);
            t = t.getParent();
        }

        return result;
    }

    // -----------------PRINT-------------------


    public void printNode (Puzzle node){
        if (node == null)
            return;
        System.out.print(node.toString());
        System.out.print( "Heuristic: " + node.getHeuristic() + "\n-------------------------\n");
        for (int i=0; i < node.getChildNum(); i ++) {
            System.out.print(node.getChild(i).toString());
            System.out.print( "Heuristic: " + node.getChild(i).getHeuristic() + "\n-------------------------\n");
        }
    }

    public void printList (Puzzle[] nodes){
        if (nodes == null)
            return;
        System.out.print( "================================\nSelected the following:\n");
        for (int i=0; i < nodes.length; i ++) {
            System.out.print(nodes[i].toString());
            System.out.print( "\nHeuristic: " + nodes[i].getHeuristic() + "\n");
        }
        System.out.print( "================================\nDone listing selected ones\n");
    }
    public void printTree (){

    }

    public void printTreeHelper ( Puzzle node){

    }

    // -----------------MAIN-------------------

    public static void main( String[] args)
    {
        /*
        PuzzleTree tree = new PuzzleTree(3);
        tree.generateTree();
        //tree.print(tree.root);

        ArrayList<Puzzle> path = tree.solution();

        System.out.print ("\n\nHere is the Solution if size: " + path.size()+ "\n");

        for (int i =0; i < path.size(); i ++){
            System.out.print (path.get(i).toString() + "\n");
        }
        */


        // Generating and printing the goal state
        Puzzle goalPuzzle;
        goalPuzzle = new Puzzle();
        goalPuzzle.makeGoal();

        System.out.println( "Goal state:\n" + goalPuzzle.toString());

        // Generating and printing 10 distinct solvable puzzles
        Puzzle[] puzzleList = new Puzzle[10];

        for( int i = 0; i < 10; i++)
        {
            boolean isUnique = true;

            puzzleList[i] = new Puzzle();
            puzzleList[i].puzzleGenerator();

            // check for uniqueness
            for (int j = 0; j < i; j ++){
                if(puzzleList[i].isEqual(puzzleList[j])) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                System.out.println("Puzzle " + (i + 1) + ":\n" + puzzleList[i].toString());
            }
            else
                i--;
        }

        // generating 2 trees with same initial state
        Puzzle puzzle = new Puzzle();
        puzzle.puzzleGenerator();

        System.out.println("NOW SOLVE THIS PUZZLE:\n" + puzzle.toString() + "\n");

        PuzzleTree treeForWidth2 = new PuzzleTree(2);
        PuzzleTree treeForWidth3 = new PuzzleTree(3);

        treeForWidth2.getRoot().copy(puzzle);
        treeForWidth3.getRoot().copy(puzzle);

        treeForWidth2.generateTree();
        treeForWidth3.generateTree();

        // solving 2 trees and printing their solution path and number of moves
        ArrayList<Puzzle> solution1 = treeForWidth2.solution();
        int moveCount1 = solution1.size();
        ArrayList<Puzzle> solution2 = treeForWidth3.solution();
        int moveCount2 = solution2.size();

        System.out.print ("\n\nHere is the solution if size " + moveCount1+ " using w = 2\n");

        for (int i=0; i < moveCount1; i ++){
            System.out.print (solution1.get(i).toString() + "\n");
        }

        System.out.print ("\n\nHere is the solution if size " + moveCount2+ " using w = 3\n");

        for (int i=0; i < moveCount2; i ++){
            System.out.print (solution2.get(i).toString() + "\n");
        }

        // Now run the program for 10 puzzles

        System.out.println("\n==================================================\n\n");
        System.out.println("NOW SOLVE FOR 10 DISTINCT PUZZLES:\n\n");

        for (int i = 0; i < 10; i ++ ){
            PuzzleTree tempTreeWidth2 = new PuzzleTree(2);
            PuzzleTree tempTreeWidth3 = new PuzzleTree(3);

            tempTreeWidth2.getRoot().copy(puzzleList[i]);
            tempTreeWidth3.getRoot().copy(puzzleList[i]);

            tempTreeWidth2.generateTree();
            tempTreeWidth3.generateTree();

            // solving 2 trees and saving for each: number of moves and number of closedNodes
            int tMoveCount1 = tempTreeWidth2.solution().size();
            int tMoveCount2 = tempTreeWidth3.solution().size();

            int closedNodes1 = tempTreeWidth2.getNodeNum() - tempTreeWidth2.getLeafNum();
            int closedNodes2 = tempTreeWidth3.getNodeNum() - tempTreeWidth3.getLeafNum();

            // print result

            System.out.println("Solved the following puzzle number " + (i+1) + ":\n\n" + puzzleList[i].toString());
            System.out.println("\t\t\t\tWIDTH = 2:\t\tWIDTH = 3:\nClosed Nodes\t" + closedNodes1 + "\t\t\t\t"+ closedNodes2 + "\n");
            System.out.println("Moves\t\t\t" + tMoveCount1 + "\t\t\t\t"+ tMoveCount2 + "\n");
            System.out.println("\n==================================================\n\n");

        }

        // Now run the program for 100 puzzles

        System.out.println("\n==================================================\n\n");
        System.out.println("NOW SOLVE FOR 100 DISTINCT PUZZLES:\n\n");

        for (int i = 0; i < 100; i ++ ){
            Puzzle tempPuzzle = new Puzzle();
            tempPuzzle.puzzleGenerator();

            PuzzleTree tempTreeWidth2 = new PuzzleTree(2);
            PuzzleTree tempTreeWidth3 = new PuzzleTree(3);

            tempTreeWidth2.getRoot().copy(tempPuzzle);
            tempTreeWidth3.getRoot().copy(tempPuzzle);

            tempTreeWidth2.generateTree();
            tempTreeWidth3.generateTree();

            // solving 2 trees and saving for each: number of moves and number of closedNodes
            int tMoveCount1 = tempTreeWidth2.solution().size();
            int tMoveCount2 = tempTreeWidth3.solution().size();

            int closedNodes1 = tempTreeWidth2.getNodeNum() - tempTreeWidth2.getLeafNum();
            int closedNodes2 = tempTreeWidth3.getNodeNum() - tempTreeWidth3.getLeafNum();

            // print result

            System.out.println("Solved the following puzzle number " + (i+1) + ":\n\n" + tempPuzzle.toString());
            System.out.println("\t\t\t\tWIDTH = 2:\t\tWIDTH = 3:\nClosed Nodes\t" + closedNodes1 + "\t\t\t\t"+ closedNodes2 + "\n");
            System.out.println("Moves\t\t\t" + tMoveCount1 + "\t\t\t\t"+ tMoveCount2 + "\n");
            System.out.println("\n==================================================\n\n");

        }

    }

}
