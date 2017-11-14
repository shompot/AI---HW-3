import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.min;

public class PuzzleTreeAStar {

    private Puzzle root;
    private Puzzle goal;

    private ArrayList <Puzzle> leafs;
    private ArrayList<Puzzle> result;

    // --------------CONSTRUCTORS---------------
    PuzzleTreeAStar (){
        this.root = new Puzzle();
        this.root.puzzleGenerator();
        this.root.setParent(null);
        leafs = new ArrayList<Puzzle>();
    }

    PuzzleTreeAStar (Puzzle root){
        this.root = root;
        leafs = new ArrayList<Puzzle>();
    }

    // -----------------SETTERS-------------------
    public void setRoot (Puzzle node){ this.root = node; }

    // -----------------GETTERS-------------------
    public Puzzle getRoot () { return this.root; }

    // ---------CALCULATING METHODS---------------
    public void generateTree (){

        addLeaf(this.root);

        //System.out.println("\nGenerate tree for root " + this.root.toString());

        while (this.goal==null) {

        //for (int i=0; i<20000; i++){
            //System.out.println("\nGenerate tree in loop " + i + " for  " + this.popQueue());
            generateTreeHelper();
        }
        //System.out.println("\nEND");
    }

    public void generateTreeHelper (){

        if (leafs == null) {

            //System.out.println("\n----------Just return cuz leafs is null------------\n");
            return;
        }

        if (leafs.size()==0) {

            //System.out.println("\n----------Just return cuz leafs is empty------------\n");
            return;
        }

        if ( isSolved()) {

            //System.out.println("\n----------Just return cuz it is solved------------\n");
            return;
        }

        //System.out.println ("\n---------I am inside the helper----------\n");

        Puzzle node = popQueue();


        //System.out.println("\nInside generate tree helper with node " +  node.toString());

        node.generateNextMoves();

        //System.out.println ("Children generated are: ");

        Puzzle[] children = new Puzzle[node.getChildNum()];
        children = node.getChildren();

        /*for (int i=0; i < node.getChildNum(); i ++){
            System.out.println (children[i].toString() + "CostPath: " + children[i].getCostPath()+ "\tHeuristics: " + children[i].getHeuristic() + "\n");
        }
*/

        for (int i=0; i < node.getChildNum(); i ++) {
            addLeaf (children[i]);
            if ( children[i].getHeuristic() == 0){
                goal = children[i];
                return;
            }
        }

        //System.out.println("\nLeafs after generate tree helper is " + leafsToString() + "\n");
        leafs.remove(node);
    }

    public void addLeaf (Puzzle p){

        if (leafs == null){
            leafs = new ArrayList<Puzzle>();
            //System.out.println ("Leafs is created");
        }

        if (leafs.isEmpty()){
            leafs.add(0, p);
            //System.out.println ("Add:\n" + p.toString() + " with cost path " + p.getCostPath() + " to i=" + 0 + "\n\nSo now leafs is:\n" + leafsToString() + "\n\n\n");

            return;
        }

        int i = 0;
        int tCostPath = p.getCostPath();
        while ( (i < leafs.size()) && (leafs.get(i).getCostPath() < tCostPath) ) {
            //if (p.isEqual(leafs.get(i)) && p.getHeight()>= leafs.get(i).getHeight()) {
            //    return;
            //}
            i++;
        }
        leafs.add(i, p);
        //System.out.println ("Add:\n" + p.toString() + " with cost path " + p.getCostPath() + " to i=" + i + "\n\nSo now leafs is:\n" + leafsToString() + "\n\n\n");

    }

    public Puzzle popQueue (){
        //System.out.println ("Trying to popQueue from leafs: " + leafsToString());
        if (leafs == null || leafs.isEmpty())
            return null;
        if (!leafs.contains(leafs.get(0)))
            return null;
        Puzzle p = leafs.get(0);
        return p;
    }

    public ArrayList<Puzzle> solution (){
        result = new ArrayList<Puzzle>();

        result.add(goal);

        Puzzle parent = goal.getParent();

        while (parent != null) {
            result.add(0, parent);
            parent = parent.getParent();
        }

        return result;
    }

    public String leafsToString (){
        String s = "";

        for (int i=0; i < leafs.size(); i++)
            s += leafs.get(i).toString() + "CostPath: " + leafs.get(i).getCostPath() + "\tHeuristic: " + leafs.get(i).getHeuristic() + "\n\n";
        return s;
    }

    public int getMoveCount (){
        if (goal == null)
            return 0;
        return goal.getHeight()+1;
    }

    public ArrayList<Integer> getCostPaths (){
        ArrayList<Integer> costPaths = new ArrayList<Integer> ();
        if (result == null)
            return null;
        for (int i = 0; i < result.size(); i++){
            costPaths.add(result.get(i).getCostPath());
        }
        return costPaths;
    }

    public ArrayList<Integer> getHeuristics (){
        ArrayList<Integer> heuristics = new ArrayList<Integer> ();
        if (result == null)
            return null;
        for (int i = 0; i < result.size(); i++){
            heuristics.add(result.get(i).getHeuristic());
        }
        return heuristics;
    }

    public ArrayList<Integer> getHeights (){
        ArrayList<Integer> heights = new ArrayList<Integer> ();
        if (result == null)
            return null;
        for (int i = 0; i < result.size(); i++){
            heights.add(result.get(i).getHeight());
        }
        return heights;
    }

    public boolean isSolved (){
        if (goal == null)
            return false;
        return true;
    }

    // -----------------PRINT-------------------


    public void printNode (Puzzle node){
        if (node == null)
            return;
        System.out.print(node.toString());
        System.out.print( "CostPath: " + node.getCostPath() + "\tHeuristic: " + node.getHeuristic() +"\n-------------------------\n");

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
       /* // generating 2 trees with same initial state
        Puzzle puzzle = new Puzzle();
        puzzle.puzzleGenerator();
        System.out.println("NOW SOLVE THIS PUZZLE:\n" + puzzle.toString() + "\n");
        PuzzleTreeAStar treeAStar = new PuzzleTreeAStar();
        treeAStar.getRoot().copy(puzzle);
        treeAStar.generateTree();
        PuzzleTreeShort treeShort = new PuzzleTreeShort(5);

        // Now run the program for 100 puzzles

        System.out.println("\n==================================================\n\n");
        System.out.println("NOW SOLVE FOR 100 DISTINCT PUZZLES:\n\n");
*/
        for (int i = 0; i < 100; i ++ ){

            // initialize trees
            Puzzle tempPuzzle = new Puzzle();
            tempPuzzle.puzzleGenerator();

            PuzzleTreeShort tempTreeShort = new PuzzleTreeShort(tempPuzzle,3);
            PuzzleTreeAStar tempTreeAStar = new PuzzleTreeAStar(tempPuzzle);

            System.out.println("Solved the following puzzle number " + (i+1) + ":\n\n" + tempPuzzle.toString());

            // get current time in milliseconds
            long time1 = System.currentTimeMillis() % 1000;

            // solve puzzle with Shortest Path Algorithm
            tempTreeShort.generateTree();

            // calculate time taken to solve the puzzle by Shortest Path algorithm
            time1 = System.currentTimeMillis() % 1000 - time1;

            // get move count
            int moveCount1 = tempTreeShort.solution().size();


            // get current time in milliseconds
            long time2 = System.currentTimeMillis() % 1000;

            // solve puzzle with A-Star Algorithm
            tempTreeAStar.generateTree();

            // calculate time taken to solvse puzzle by A-Start algorithm
            time1 = System.currentTimeMillis() % 1000 - time1;

            // get move count
            int moveCount2 = tempTreeAStar.solution().size();

            // print result


            System.out.println("\t\t\t\tSHORTEST PATH:\t\tA-STAR:\n");
            System.out.println("Time (millisec)\t\t" + time1 + "\t\t\t\t"+ time2 + "\n");
            System.out.println("Moves\t\t\t\t" + moveCount1 + "\t\t\t\t"+ moveCount2 + "\n");
            System.out.println("\n==================================================\n\n");

            tempTreeAStar.generateTree();

        }

        //PuzzleTreeAStar tempTreeAStar = new PuzzleTreeAStar();
        /*for (int i = 0; i < 10; i++){
            Puzzle tempPuzzle = new Puzzle();
            tempPuzzle.puzzleGenerator();

            PuzzleTreeAStar tempTreeAStar = new PuzzleTreeAStar(tempPuzzle);

            tempTreeAStar.generateTree();

            ArrayList <Puzzle> solution = tempTreeAStar.solution();

            for (int j = 0; j<solution.size(); j ++){
                System.out.println(solution.get(j).toString());
            }
        }
*/
    }

}