import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.min;

public class PuzzleTreeAStar {

    private Puzzle root;
    private Puzzle goal;

    private ArrayList <Puzzle> leafs;

    // --------------CONSTRUCTORS---------------
    PuzzleTreeAStar (){
        this.root = new Puzzle();
        this.root.puzzleGenerator();
        this.root.setParent(null);
        leafs = new ArrayList<Puzzle>();
        leafs.add(root);
    }

    PuzzleTreeAStar (Puzzle root){
        this.root = root;
    }

    // -----------------SETTERS-------------------
    public void setRoot (Puzzle node){ this.root = node; }

    // -----------------GETTERS-------------------
    public Puzzle getRoot () { return this.root; }

    // ---------CALCULATING METHODS---------------
    public void generateTree (){

        generateTreeHelper(root);
    }

    public void addLeaf (Puzzle p){
        if (leafs == null){
            leafs = new ArrayList<Puzzle>();
            leafs.add(p);
            //System.out.print(leafs.get(0).getCostPath() + "\n\n\n");
            return;
        }

        boolean added = false;

        for (int i = 0; i < leafs.size(); i++) {
            //System.out.print(leafs.get(i).getCostPath()+ " ");
            if (leafs.get(i).getCostPath() >= p.getCostPath()) {
                leafs.add(i, p);
                added = true;
                return;
            }

        }
        if (!added) {
            leafs.add(p);
            //System.out.print(p.getCostPath());
        }
        //System.out.print("\n\n\n");
    }

    public void generateTreeHelper (Puzzle node){
        leafs.remove(node);

        node.generateNextMoves();

        for (int i=0; i < node.getChildNum(); i ++) {
            addLeaf (node.getChild(i));
            if ( node.getChild(i).getHeuristic() == 0){
                goal = node.getChild(i);
                return;
            }
        }

        //printNode(node);
        //printList(node.getChildren());

        //System.out.println( "\n---------------------------------------------------------\n\n\n");

        if (goal == null)
            generateTreeHelper(leafs.get(0));
    }


    public ArrayList<Puzzle> solution (){
        ArrayList<Puzzle> result = new ArrayList<Puzzle>();

        result.add(goal);

        Puzzle parent = goal.getParent();

        while (parent != null) {
            result.add(0, parent);
            parent = parent.getParent();
        }

        return result;
    }

    public int getMoveCount (){
        if (goal == null)
            return 0;
        return goal.getHeight()+1;
    }

    // -----------------PRINT-------------------


    public void printNode (Puzzle node){
        if (node == null)
            return;
        System.out.print(node.toString());
        System.out.print( "CostPath: " + node.getCostPath() + "\n-------------------------\n");

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
        // generating 2 trees with same initial state
        Puzzle puzzle = new Puzzle();
        puzzle.puzzleGenerator();

        System.out.println("NOW SOLVE THIS PUZZLE:\n" + puzzle.toString() + "\n");

        PuzzleTreeAStar tree = new PuzzleTreeAStar();

        tree.getRoot().copy(puzzle);

        tree.generateTree();


        // solving a tree and printing its solution path and number of moves
        ArrayList<Puzzle> solution = tree.solution();
        int moveCount = tree.getMoveCount();

        System.out.print ("\n\nHere is the solution if size " + moveCount + "\n");

        for (int i=0; i < moveCount; i ++){
            System.out.print (solution.get(i).toString() + "\n" + "\n");
        }

    }

}