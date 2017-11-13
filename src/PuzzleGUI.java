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
            //costPathLabel.setText(  "f(n) = g(n) + h(n) \n"
            //        + costPaths.get(curr) + " = " + heuristics.get(curr) + " + " + heights.get(curr));

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
    public static void main(String[] args) {

<<<<<<< HEAD
=======

        // generating 2 trees with same initial state
        Puzzle puzzle = new Puzzle();
        puzzle.puzzleGenerator();
        System.out.println("NOW SOLVE THIS PUZZLE:\n" + puzzle.toString() + "\n");
        PuzzleTreeAStar treeAStar = new PuzzleTreeAStar();
        treeAStar.getRoot().copy(puzzle);
        treeAStar.generateTree();
        PuzzleTreeShort treeShort = new PuzzleTreeShort(5);



        // solving a tree and printing its solution path and number of moves
        ArrayList<Puzzle> solution = treeAStar.solution();
        JFrame frame;
        frame = new JFrame("8 Puzzle Game with A*");
        PuzzleGUI game = new PuzzleGUI();
        game.setwList(solution);
        frame.setContentPane(game.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
>>>>>>> parent of 91e5e65... Merge branch 'developBranch' of https://github.com/shompot/AI---HW-3 into developBranch
    }
}
