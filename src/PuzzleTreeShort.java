import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.min;

public class PuzzleTreeShort {

    private Puzzle root;
    private Puzzle[] currentNodes;
    private int w = 2;
    private int nodeNum = 0;
    private int leafNum = 0;

    // --------------CONSTRUCTORS---------------
    PuzzleTreeShort ( int w){
        this.root = new Puzzle();
        this.root.puzzleGenerator();
        this.root.setParent(null);
        this.w = w;
        this.currentNodes = new Puzzle[w];
        nodeNum = 1;
        leafNum = 1;
    }

    PuzzleTreeShort (Puzzle root){
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

}
