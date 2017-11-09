import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Shiha on 10/25/2017.
 */
public class PuzzleGUI extends JPanel{
    public ArrayList<Puzzle> wList = new ArrayList<Puzzle>();
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
        if(curr != getwList().size()) {
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

        }

    }
    public void setwList( ArrayList<Puzzle> wList)
    {
        this.wList = wList;
    }
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
    public static void main(String[] args)
    {
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

        JFrame frame1;
        frame1 = new JFrame( "8 Puzzle Game with w = 2");
        PuzzleGUI game = new PuzzleGUI();
        game.setwList( solution1);
        frame1.setContentPane(game.gamePanel);
        frame1.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible( true);

        JFrame frame2;
        frame2 = new JFrame( "8 Puzzle Game with w = 3");
        PuzzleGUI game2 = new PuzzleGUI();
        game2.setwList( solution2);
        frame2.setContentPane(game2.gamePanel);
        frame2.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setVisible( true);
    }
}
