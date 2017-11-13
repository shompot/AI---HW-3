import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Shiha on 10/25/2017.
 */
public class PuzzleGUI extends JPanel{
    public ArrayList<Puzzle> wList = new ArrayList<Puzzle>();
    public ArrayList<Integer> costPaths = new ArrayList<Integer>();
    public ArrayList<Integer> heuristics = new ArrayList<Integer>();
    public ArrayList<Integer> heights = new ArrayList<Integer>();
    public int curr = 0;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton nextButton;
    public JPanel gamePanel;
    private JLabel costPathLabel;

    public PuzzleGUI() {
        nextButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                updateMatrix();
                curr = curr + 1;
            }
        });
    }
    public void updateMatrix()
    {

        if(curr < getwList().size()) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp.add(getwList().get(curr).getPuzzleArr()[i][j]);
                }
            }
            for (int i = 0; i < 9; i++) {
                if (temp.get(i) != -1) {
                    (getButtons().get(i)).setText("" + temp.get(i));
                } else {
                    getButtons().get(i).setText(" ");
                }
            }
            //costPathLabel.setText(  "f(n) = g(n) + h(n) \n"
            //        + costPaths.get(curr) + " = " + heuristics.get(curr) + " + " + heights.get(curr));

        }

    }
    public void setwList( ArrayList<Puzzle> wList)
    {
        this.wList = wList;
    }
    public void setCostPaths (ArrayList<Integer> costPaths) { this.costPaths = costPaths; }
    public void setHeuristics (ArrayList<Integer> heuristics) { this.heuristics = heuristics; }
    public void setHeights (ArrayList<Integer> heights) { this.heights = heights; }
    public ArrayList<Puzzle> getwList()
    {
        return this.wList;
    }


    public ArrayList<JButton> getButtons()
    {
        ArrayList<JButton> buttonList= new ArrayList<JButton>();
        buttonList.add( a1Button);
        buttonList.add( a2Button);
        buttonList.add( a3Button);
        buttonList.add( a4Button);
        buttonList.add( a5Button);
        buttonList.add( a6Button);
        buttonList.add( a7Button);
        buttonList.add( a8Button);
        buttonList.add( a9Button);
        return buttonList;
    }
    public JButton getNextButton()
    {
        return this.nextButton;
    }
    //public Puzzle[] getPuzzleList()
    //{
    //    return this.puzzleList;
    //}
    //public void setPuzzleList( Puzzle[] puzzleList)
    //{
    //    this.puzzleList = puzzleList;
    //}
    public static void main(String[] args) {


        // generating 2 trees with same initial state
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

        // solving a tree and printing its solution path and number of moves
        ArrayList<Puzzle> solution = treeAStar.solution();
        JFrame frame;
        frame = new JFrame("8 Puzzle Game with A*");
        PuzzleGUI game = new PuzzleGUI();
        game.setwList(solution);
        game.setCostPaths(treeAStar.getCostPaths());
        game.setHeuristics(treeAStar.getHeuristics());
        game.setHeights(treeAStar.getHeights());
        frame.setContentPane(game.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
