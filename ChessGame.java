import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
import java.util.Random;
/**
 * This class is a game where u try to get the entire board to be black. it has a 
 * cheat button which tells the user the moves they need to do inorder to win. It also 
 * counts the number of clicks
 *
 * @author Spencer Moon and Michael Plekan
 * @version Spring 2021
 */
public class ChessGame implements Runnable, ActionListener
{
    int size;
    boolean won=false;
    JButton[][] squares;
    JPanel info;
    JButton reset;
    Board board = new Board();
    private static String[] file={"BlackQueen.png","WhiteQueen.png"};
    private Image pic;
    JFrame win;
    /**
     * This contructs a lights out method
     * @param size this is the size of the board
     */
    public ChessGame(int size)
    {
        this.size = size;
    }

    /**
     * This runs the window, it is where the panels, buttons, etc. are setup
     */
    public void run()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        info=new JPanel(new BorderLayout());
        info.setBackground(Color.BLACK);

        reset=new JButton("reset");
        reset.setBackground(Color.WHITE);
        reset.setOpaque(true);
        reset.setBorderPainted(false);
        reset.addActionListener(this);
        info.add(reset,BorderLayout.EAST);

        GridLayout grid = new GridLayout(size, size);
        JPanel framePanel = new JPanel(new BorderLayout());
        frame.add(framePanel);
        JPanel pan = new JPanel(grid);
        pan.setPreferredSize(new Dimension(800,800));
        frame.setResizable(false);
        squares = new JButton[size][size];
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                squares[r][c] = new JButton("");
                squares[r][c].setOpaque(true);
                squares[r][c].setBorderPainted(false);
                pan.add(squares[r][c]);
                squares[r][c].addActionListener(this);
                setcolor(r,c);
            }
        }
        framePanel.add(pan,BorderLayout.CENTER);
        framePanel.add(info,BorderLayout.SOUTH);
        setboard();
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This method override the actionPerformed method. This calcutes which lights should turn 
     * on or off
     * 
     * @param e This is an action event from an actionlistener
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(reset)){
            setboard();
            won=false;
            return;
        }
        if (won)
        {
            return;
        }
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                if(e.getSource().equals(squares[r][c])){

                }
            }
        }
    }

    /**
     * this sets the color of each light to the opposite of what it is now
     * 
     * @param r this is the row of the button
     * @param c this is the colum of the button
     */
    private void setcolor(int r,int c){
        if (r%2!=0)
            if(c%2==0)squares[r][c].setBackground(Color.ORANGE);
            else squares[r][c].setBackground(Color.YELLOW);
        else
        if(c%2!=0)squares[r][c].setBackground(Color.ORANGE);
        else squares[r][c].setBackground(Color.YELLOW);
    }

    /**
     * This sets the board black, than sets up board so it is ready for the user. It 
     * also resets the moves array and the number of clicks
     */
    private void setboard(){
        board.board[0] = new Queen(true,0);
        board.board[9]=new Queen(false,0);
        if (won)
        {
            win.dispose();
            won = false;
        }
    }

    /**
     * This is the main method, it calls on a lightsout object inorder to start the window
     * 
     * @param no input needed
     */
    public static void main(String[] args)
    {
        int size = 8;
        javax.swing.SwingUtilities.invokeLater(new ChessGame(size));
    }
}