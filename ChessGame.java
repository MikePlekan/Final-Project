import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
import java.util.Random;
/**
 * This class is a Chess game with rest, color switching, highlighted squared for legal moves, a sidebar with
 * a list of the moves made throughout the game
 *
 * @author Spencer Moon, Michael Plekan
 * @version Spring 2021
 */
public class ChessGame implements Runnable, ActionListener
{
    private final static int SIZE=8;
    private boolean won=false;
    private Square[][] squares;
    private JPanel info,sidebar;
    private JTextArea notation;
    private StringBuilder notes;
    private JButton reset;
    private Board board = new Board();
    private JFrame win;
    private ArrayList<Integer> valid=new ArrayList();
    private ArrayList<Integer> lastvalid=new ArrayList();
    private Point lastClick=new Point();
    private JComboBox color;
    private Random rand = new Random();
    public int red, green, blue, red2, green2, blue2;
    public boolean newColNeeded;
    public boolean mono;

    //Colors and theme options
    public static final String[] colorOptions = {"More Wood","Wood", "Marble", "Orange", "Green", "Purple", "Pink", "Random Mono", "Random Bicolor"};
    Color color1, color2;
    Color validColor = Color.RED;

    /**
     * This contructs a Chess window and game
     */
    public ChessGame(){}

    /**
     * This runs the window, it is where the panels, buttons, etc. are setup
     */
    public void run()
    {
        JFrame frame = new JFrame("CHESS");

        //info
        info=new JPanel(new BorderLayout());
        info.setBackground(Color.BLACK);

        //reset
        reset=new JButton("reset");
        reset.setBackground(Color.WHITE);
        reset.setOpaque(true);
        reset.setBorderPainted(false);
        reset.addActionListener(this);
        info.add(reset,BorderLayout.EAST);

        //Sidebar
        sidebar=new JPanel();
        sidebar.setPreferredSize(new Dimension(100,800));
        notes=new StringBuilder("");
        notation=new JTextArea(notes.toString());
        sidebar.add(notation);
        sidebar.setBackground(Color.WHITE);

        //Color
        color = new JComboBox(colorOptions);
        color.addActionListener(this);
        JLabel colorLabel = new JLabel(" Board:");
        colorLabel.setForeground(Color.WHITE);
        JPanel colorPan = new JPanel(new BorderLayout());
        colorPan.setBackground(Color.BLACK);

        colorPan.add(colorLabel, BorderLayout.WEST);
        colorPan.add(color, BorderLayout.EAST);

        info.add(colorPan, BorderLayout.WEST);

        //Grid
        GridLayout grid = new GridLayout(SIZE, SIZE);

        //Pan
        JPanel pan = new JPanel(grid);
        pan.setPreferredSize(new Dimension(800,800));

        //repainter Tread
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {sleep(20);}
                    catch (InterruptedException e) {}
                    pan.repaint();
                }
            }
        }.start();

        //Creatation of the squares 2d array and setting each square up
        squares = new Square[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
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

            }
        }

        setTheme();

        //FramePanel
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.add(pan,BorderLayout.CENTER);
        framePanel.add(info,BorderLayout.SOUTH);
        framePanel.add(sidebar,BorderLayout.EAST);
        frame.add(framePanel);

        //Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
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
            notes.setLength(0);
            notation.setText(notes.toString());
            return;
        }
        if (e.getSource().equals(color))
        {
            setTheme();
            return;
        }
        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
            {
                if(e.getSource().equals(squares[r][c])){
                    if(valid!=null&&valid.contains((r*8)+c)){
                        board.movePiece((lastClick.x*8)+lastClick.y,(r*8)+c);
                        notes.append(board.moves.peek()+"\n");
                        notation.setText(notes.toString());
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
        if(color.getSelectedIndex()>0){
            squares[r][c].setIcon(null);
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
        else{
            if (r%2!=0)
                if(c%2==0){
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodR.png"));
                    else squares[r][c].setIcon(new ImageIcon("wood.png"));
                }
                else{
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodlightR.png"));
                    else squares[r][c].setIcon(new ImageIcon("woodlight.png"));
                }
            else{
                if(c%2!=0)
                {
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodR.png"));
                    else squares[r][c].setIcon(new ImageIcon("wood.png"));  
                }
                else {
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodlightR.png"));
                    else squares[r][c].setIcon(new ImageIcon("woodlight.png"));
                }
            }
        }
    }

    public void setTheme()
    {
        if(color.getSelectedIndex()==0){}
        
        else if (color.getSelectedIndex() < 7)
        {
            color1 = Theme.FIRST_COLORS[color.getSelectedIndex()-1];
            color2 = Theme.SECOND_COLORS[color.getSelectedIndex()-1];
        }
        else if (color.getSelectedIndex() == 7)
        {
            color1 = Theme.getRandomMono();
            color2 = new Color(color1.getRed()/2, color1.getGreen()/2, color1.getBlue()/2);
        }
        else if (color.getSelectedIndex() == 8)
        {
            Color colors[] = Theme.getRandomBi();
            color1 = colors[0];
            color2 = colors[1];
        }

        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
            {
                setcolor(r, c);
            }
        }

    }

    /**
     * This is the main method, it starts the window
     * @param no input needed
     */
    public static void main(String[] args){javax.swing.SwingUtilities.invokeLater(new ChessGame());}
}