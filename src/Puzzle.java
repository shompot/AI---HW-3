import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by Shiha on 10/24/2017.
 */
public class Puzzle
{
    private int x;
    private int y;
    private int[][] puzzle;

    private Puzzle parent;
    private Puzzle[] children;

    // --------------CONSTRUCTORS---------------
    Puzzle()
    {
        this.puzzle = new int[3][3];
        for( int i = 0; i < 3; i++)
        {
            for( int j = 0; j < 3; j++)
            {
                this.puzzle[i][j] = -1;
            }
        }

        this.x = -1;
        this.y = -1;

        initializeChildren();
        this.parent = null;
    }
    Puzzle(int [][] array, int x, int y){
        this.puzzle = array;
        this.x = x;
        this.y = y;
        this.parent = null;
        initializeChildren();
    }
    Puzzle(int [][] array, int x, int y, Puzzle parent){
        this.puzzle = array;
        this.x = x;
        this.y = y;
        this.parent = parent;
        initializeChildren();
    }

    // -----------------SETTERS-------------------
    public void setPuzzleArr( int[][] newPuzzle)
    {
        this.puzzle = newPuzzle;
    }
    public void setTile( int i, int j, int t)
    {
        this.puzzle[i][j] = t;
    }
    public void setParent (Puzzle parent){ this.parent = parent; }

    public void setChildren (Puzzle[] children) {this.children = children; }
    public void addChild (Puzzle child){
        if (getChildNum() < 4) {
            int i = 0;
            while (this.children[i] != null) i++;

            this.children[i] = child;
            this.children[i].setParent(this);
        }
    }
    public void initializeChildren (){
        this.children = new Puzzle[4];
        for (int i = 0; i < 4; i ++){
            this.children[i] = null;
        }
    }
    // -----------------GETTERS-------------------
    public int[][] getPuzzleArr()
    {
        return this.puzzle;
    }
    public int getTile (int i, int j)
    {
        return this.puzzle[i][j];
    }
    public Puzzle getParent () { return this.parent; }

    public Puzzle[] getChildren() { return this.children;}
    public Puzzle getChild (int i) {
        if ( i < getChildNum() )
            return this.children[i];

        return null;
    }
    public int getChildNum (){
        if (this.children==null)
            return 0;
        int i = 0;
        while (i < 4 && this.children[i] != null) i++;
        return i;
    }
    public int getX (){ return this.x; }
    public int getY (){ return this.y; }

    // ---------CALCULATING METHODS---------------
    public boolean makeGoal()
    {
        //Create Goal Puzzle
        this.puzzle[0][0] = 1;
        this.puzzle[0][1] = 2;
        this.puzzle[0][2] = 3;
        this.puzzle[1][0] = 4;
        this.puzzle[1][1] = 5;
        this.puzzle[1][2] = 6;
        this.puzzle[2][0] = 7;
        this.puzzle[2][1] = 8;
        this.puzzle[2][2] = -1; //-1 indicates empty box
        this.x = 2;
        this.y = 2;
        return true;
        //Puzzle looks like this:
        // [1][2][3]
        // [4][5][6]
        // [7][8][-1]
    }

    public void puzzleGenerator(){
        //Puzzle myPuzzle = new Puzzle();
        if( this.makeGoal()) {
            Random randomGenerator = new Random();
            int lastMove = -1;
            for (int i = 0; i < 50; i++) {
                int randomInt = randomGenerator.nextInt(4);
                int temp = 0;
                //0 means up, 1 means down, 2 means right, 3 means left
                if (randomInt == 0) //go up
                {
                    if (lastMove == 1 ) {
                        i--;
                    } else if (!(this.goUp())) {
                        i--;
                    }
                } else if (randomInt == 1) //go down
                {
                    if (lastMove == 0) {
                        i--;
                    } else if (!this.goDown()) {
                        i--;
                    }
                } else if (randomInt == 2)  //go right
                {
                    if (lastMove == 3) {
                        i--;
                    } else if (!this.goRight()) {
                        i--;
                    }
                } else if (randomInt == 3) //go left
                {
                    if (lastMove == 2) {
                        i--;
                    } else if (!this.goLeft()) {
                        i--;
                    }
                } else {
                    System.out.println("It can't go there!");
                }
                if( (lastMove + randomInt) != 1 && (lastMove + randomInt) != 5)
                {
                    Puzzle newPuzzle = new Puzzle();
                    newPuzzle.copy( this);
                    //stepsToGoal.add(newPuzzle);
                }
                lastMove = randomInt;
                //System.out.println( "i:" + i + "\n" + stepsToGoal.get(i).toString());
            }
        }
        else
        {
            System.out.println( "Puzzle does not start from goal state. Puzzle cannot be guaranteed to be solved if" +
                    " created.");
        }
        //return stepsToGoal;
    }


    public int getHeuristic (){
        if (this == null){
            return 1000;
        }
        int n = 0;

        for ( int i = 0; i < 3; i ++)
            for (int j = 0; j < 3; j ++)
                n += getTileDistance(i,j);

        return n;
    }

    public int getTileDistance (int i, int j){
        int tile = this.puzzle[i][j];

        if (tile == -1)
            return 0;

        int rowDifference = abs (i-(tile-1)/3);
        int columnDifference = abs (j-(tile-1)%3);

        return rowDifference+columnDifference;
    }

    public void generateNextMoves (){

        Puzzle up = new Puzzle();
        up.copy(this);
        up.setParent(this);
        if (up.goUp() && isLoopFree(up)){
            this.addChild(up);

            //System.out.println( "\nChild Up Created\n");
        }

        Puzzle down = new Puzzle();
        down.copy(this);
        down.setParent(this);
        if (down.goDown() && isLoopFree(down)){
            this.addChild(down);
            //System.out.println( "\nChild Down Created\n");
        }

        Puzzle left = new Puzzle();
        left.copy(this);
        left.setParent(this);
        if (left.goLeft() && isLoopFree(left)){
            this.addChild(left);
            //System.out.println( "\nChild Left Created\n");
        }
        Puzzle right = new Puzzle();
        right.copy(this);
        right.setParent(this);
        if (right.goRight() && isLoopFree(right)){
            this.addChild(right);
            //System.out.println( "\nChild Right Created\n");
        }
    }

    public void copy (Puzzle p) {
        this.x = p.getX();
        this.y = p.getY();
        for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j ++)
                this.puzzle[i][j] = p.getTile(i,j);
        }
    }

    public boolean isEqual (Puzzle p) {
        for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j ++)
                if (this.puzzle[i][j] != p.getTile(i,j)){
                    return false;
                }
        }
        return true;
    }

    public boolean isLoopFree (Puzzle node){
        Puzzle t = node.getParent();
        //System.out.print("\nChecking isLoopFree node " + node.toString() + " and it's parent is " + node.getParent().toString());
        while ( t != null ){
            //System.out.print("\nwith node " + t.toString());
            if (node.isEqual(t)) {
                //System.out.print("\nEqual, so it is with loops\n");
                return false;
            }
            //System.out.print("\nNot Equal\n");
            t = t.getParent();
            //System.out.print("\nt's parent is " + t.toString());
        }
        //System.out.print("\nLoop Free\n");
        return true;
    }
    // -----------------MOVES-------------------
    public boolean goUp()
    {
        if( y == 0)
        {
            return false;
        }
        int temp = this.puzzle[y - 1][x];
        this.puzzle[y - 1][x] = -1;
        this.puzzle[y][x] = temp;
        y = y - 1;

        return true;
    }
    public boolean goDown()
    {
        if( y == 2)
        {
            return false;
        }
        int temp = this.puzzle[y + 1][x];
        this.puzzle[y + 1][x] = -1;
        this.puzzle[y][x] = temp;
        y = y + 1;

        return true;
    }
    public boolean goRight()
    {
        if( x == 2)
        {
            return false;
        }
        int temp = this.puzzle[y][x + 1];
        this.puzzle[y][x + 1] = -1;
        this.puzzle[y][x] = temp;
        x = x + 1;

        return true;
    }
    public boolean goLeft()
    {
        if( x == 0)
        {
            return false;
        }
        int temp = this.puzzle[y][x - 1];
        this.puzzle[y][x - 1] = -1;
        this.puzzle[y][x] = temp;
        x = x - 1;

        return true;
    }

    // -----------------PRINT-------------------
    public String toString()
    {
        if (this == null)
            return "";

        String s;
        s = "";
        for (int i = 0; i < 3; i++)
        {
            for( int j = 0; j < 3; j++)
            {
                s = s +  "[" + this.puzzle[i][j] + "]" ;
            }
            s = s + "\n";
        }
        //s = s + "\n";
        return s;
    }

    // -----------------MAIN-------------------

    //for testing purposes
    //will provide listings of program
   /* public static void main( String[] args)

    {
        Puzzle goalPuzzle;
        ArrayList<Puzzle> list = new ArrayList<Puzzle>();
        goalPuzzle = new Puzzle();
        goalPuzzle.makeGoal();

        System.out.println( "Goal state:\n" + goalPuzzle.toString());
        Puzzle[] puzzleList = new Puzzle[10];
        for( int i = 0; i < 10; i++)
        {
            if( i == 2)
            {
                puzzleList[i] = new Puzzle();
                list = puzzleList[i].puzzleGenerator();
            }
            else
            {
                puzzleList[i] = new Puzzle();
                puzzleList[i].puzzleGenerator();
            }
            //System.out.println( "Puzzle " + (i + 1) + ":\n" + puzzleList[i].toString());
        }
        for( int i = 0; i < list.size(); i++)
        {
            System.out.println( i + ":\n" + list.get(i).toString());
        }

    }
    */
}