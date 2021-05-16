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
    int r,c;
    final static int SIZE=8;
    boolean won=false;
    Square[][] squares;
    JPanel info;
    JButton reset;
    Board board = new Board();
    JFrame win;
    ArrayList<Integer> valid=new ArrayList();
    ArrayList<Integer> lastvalid=new ArrayList();
    Point lastClick=new Point();
    JComboBox color;
    public static final String[] colorOptions = {"Wood", "Marble", "Orange", "Green", "Purple", "Pink", "Random"};
            /**
             * This contructs a lights out method
             * @param SIZE this is the SIZE of the board
             */
    public ChessGame(){}

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

        color = new JComboBox(colorOptions);
        color.addActionListener(this);
        info.add(color, BorderLayout.CENTER);

        GridLayout grid = new GridLayout(SIZE, SIZE);
        JPanel framePanel = new JPanel(new BorderLayout());
        frame.add(framePanel);
        JPanel pan = new JPanel(grid);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(20);
                    }
                    catch (InterruptedException e) {
                    }
                    pan.repaint();
                }
            }
        }.start();
        pan.setPreferredSize(new Dimension(800,800));
        frame.setResizable(false);
        squares = new Square[SIZE][SIZE];
        for (r = 0; r < SIZE; r++)
        {
            for (c = 0; c < SIZE; c++)
            {
                squares[r][c] = new Square(r,c){
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if(board.board[row*8+col]!=null)g.drawImage(board.board[row*8+col].getPic(),10,10,null);
                    }
                };
                squares[r][c].setOpaque(true);
                squares[r][c].setBorderPainted(false);
                pan.add(squares[r][c]);
                squares[r][c].addActionListener(this);
                setcolor(r,c);

            }
        }
        framePanel.add(pan,BorderLayout.CENTER);
        framePanel.add(info,BorderLayout.SOUTH);
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
            board=new Board();
            return;
        }
        if (e.getSource().equals(color))
        {
            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {
                    setcolor(i, j);
                }
            }
            return;
        }
        for (r = 0; r < SIZE; r++)
        {
            for (c = 0; c < SIZE; c++)
            {
                if(e.getSource().equals(squares[r][c])){
                    if(valid!=null&&valid.contains((r*8)+c)){
                        board.movePiece((lastClick.x*8)+lastClick.y,(r*8)+c);
                        lastvalid=(ArrayList<Integer>)valid.clone();
                        valid.clear();
                        for (int i:lastvalid)
                        {
                            setcolor(i/8,i%8);
                        }
                    }
                    else{
                        lastvalid=(ArrayList<Integer>)valid.clone();
                        if(board.board[(r*8)+c]!=null){
                            valid=board.board[(r*8)+c].legalMoves(board);
                            for (int i:lastvalid)
                            {
                                setcolor(i/8,i%8);
                            }
                            for (int i:valid)
                            {
                                setcolor(i/8,i%8);
                            }

                        }
                        else{

                            valid.clear();
                            for (int i:lastvalid)
                            {
                                setcolor(i/8,i%8);
                            }

                        }
                    }
                    lastClick.x=r;
                    lastClick.y=c;

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
        
        Color color1;
        Color color2;
        Color validColor = Color.RED;
        if (color.getSelectedIndex() == 0)
        {
            color1 = new Color(255, 228, 179);
            color2 = new Color(158, 107, 71);
        }
        else if (color.getSelectedIndex() == 1)
        {
            color1 = Color.WHITE;
            color2 = new Color(100, 100, 100);
        }
        else if (color.getSelectedIndex() == 2)
        {
            color1 = Color.YELLOW;
            color2 = Color.ORANGE;
        }
        else if (color.getSelectedIndex() == 3)
        {
            color1 = new Color(255, 249, 209);
            color2 = new Color(52, 173, 82);
            //validColor = new Color(52, 86, 41);
        }
        else if (color.getSelectedIndex() == 4)
        {
            color1 = new Color(247, 204, 255);
            color2 = new Color(141, 56, 201);
        }
        else if (color.getSelectedIndex() == 5)
        {
            color1 = Color.WHITE;
            color2 = new Color(255, 184, 230);
        }
        else if (color.getSelectedIndex() == 6)
        {
            color1 = Color.YELLOW;
            color2 = Color.ORANGE;
        }
        else
        {
            color1 = Color.YELLOW;
            color2 = Color.ORANGE;
        }
        
        if(valid.contains((r*8)+c))squares[r][c].setBackground(validColor);
        else{
            if (r%2!=0)
                if(c%2==0)squares[r][c].setBackground(color2);
                else squares[r][c].setBackground(color1);
            else
            if(c%2!=0)squares[r][c].setBackground(color2);
            else squares[r][c].setBackground(color1);
        }
    }

    /**
     * This is the main method, it calls on a lightsout object inorder to start the window
     * @param no input needed
     */
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new ChessGame());

    }
}