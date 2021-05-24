import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
import java.util.Random;
/**
 * This class is a Chess game with reset, color switching, highlighted squared for legal moves, a sidebar with
 * a list of the moves made throughout the game
 *
 * @author Spencer Moon, Michael Plekan, Luke Jennings, and Nathan Poirier
 * @version Spring 2021
 */
public class ChessGame implements Runnable, ActionListener
{
    private final static int SIZE=8;
    private Square[][] squares;
    private JPanel info,sidebar,pan,buttonPanel;
    private JTextArea notation;
    private JScrollPane scroll;
    private StringBuilder notes;
    private JButton reset,pawnPro;
    protected Board board = new Board();
    private JFrame win;
    private ArrayList<Integer> valid=new ArrayList();
    private ArrayList<Integer> lastvalid=new ArrayList();
    private Point lastClick=new Point();
    private boolean wKingNormal = true,bKingNormal = true;

    //pawn promotion
    int current=0;

    //Colors and theme options
    private JComboBox color;
    public static final String[] colorOptions = {"Wood","Flat Wood", "Marble", "Orange", "Green", "Purple", "Pink", "Random Mono", "Random Bicolor"};
    Color color1, color2;
    Color validColor = Color.RED;

    /**
     * This contructs a Chess window and game
     */
    public ChessGame(){}

    /**
     * This runs the window, it is where the panels, buttons, etc. are setup and customized
     */
    public void run()
    {
        JFrame frame = new JFrame("PLEKANCHESS");
        frame.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        //info
        info=new JPanel(new BorderLayout());
        info.setBackground(Color.BLACK);

        buttonPanel=new JPanel(new BorderLayout());
        //reset
        reset=new JButton("reset");
        reset.setBackground(Color.WHITE);
        reset.setOpaque(true);
        reset.setBorderPainted(false);
        reset.addActionListener(this);
        buttonPanel.add(reset,BorderLayout.EAST);
        info.add(buttonPanel,BorderLayout.EAST);
        
        //reset
        pawnPro=new JButton("Promotion");
        pawnPro.setBackground(Color.WHITE);
        pawnPro.setOpaque(true);
        pawnPro.setBorderPainted(false);
        pawnPro.addActionListener(this);
        buttonPanel.add(pawnPro,BorderLayout.WEST);

        //Sidebar
        sidebar=new JPanel();
        sidebar.setPreferredSize(new Dimension(180,800));
        notes=new StringBuilder("");
        notation=new JTextArea(28,16);
        notation.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        scroll = new JScrollPane (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.add(notation);
        scroll.setViewportView(notation);
        scroll.setPreferredSize(new Dimension(175,650));
        sidebar.add(scroll);
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
        pan = new JPanel(grid);
        pan.setPreferredSize(new Dimension(800,800));

        //repainter Tread
        new Thread() {
            
            /**
             * Repaints the panel once every 20 ms.
             */
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
                if(board.board[r*8+c]!=null){
                    squares[r][c] = new Square(r,c,board.board[r*8+c].getPic()){
                        
                        /**
                         * Paints an image.
                         * 
                         * @param g A Graphics object
                         */
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            if(this.pic!=null)g.drawImage(this.pic,10,10,null);
                        }
                    };
                }
                else{
                    squares[r][c] = new Square(r,c){
                        /**
                         * Paints an image.
                         * 
                         * @param g A Graphics object
                         */
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            if(this.pic!=null)g.drawImage(this.pic,10,10,null);
                        }
                    };
                }
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
     * This method override the actionPerformed method. This gets which peice the player
     * wants to move and where they want to move it to, then uses the board object to check
     * validity. It also checks for reset, promotion and theme change.
     * 
     * @param e This is an action event from an actionlistener
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(pawnPro)){
            PawnPromotion pawn=new PawnPromotion(current,board);
            pawn.start();
        }
        if (e.getSource().equals(reset)){
            board=new Board();
            for (int R = 0; R < 8; R++)
            {
                for (int C = 0; C < 8; C++)
                {
                    if(board.board[(R*8)+C]!=null)squares[R][C].pic=board.board[(R*8)+C].pic;
                    else{
                        squares[R][C].pic=null;
                    }
                }
            }
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
                        current=(r*8)+c;
                        PieceThread piece=new PieceThread(board,squares,(lastClick.x*8)+lastClick.y,(r*8)+c);
                        board.movePiece((lastClick.x*8)+lastClick.y,(r*8)+c);
                        piece.start();
                        //check to see if won
                        if(board.gameEnded){
                            this.showWinner();
                        }
                        //notation
                        if(board.playerToMove){
                            notes.append(board.moves.size() / 2 + 1 + ". ");
                        }
                        if(board.moves.peek() != null){
                            notes.append(board.moves.peek());
                        }
                        if(!board.playerToMove){
                            notes.append("\n");
                        } else {
                            notes.append("   ");
                        }
                        notation.setText(notes.toString());

                        lastvalid=(ArrayList<Integer>)valid.clone();
                        valid.clear();
                        for (int i:lastvalid)
                        {
                            setcolor(i/8,i%8);
                        }
                        //trying to change image during check
                        if(board.blackKing.checked == true){
                            board.blackKing.setImg(true,true);
                            bKingNormal = false;
                        }
                        else if(!bKingNormal){
                            board.blackKing.setImg(false,true);
                            bKingNormal = true;
                        }
                        if(board.whiteKing.checked == true){
                            board.whiteKing.setImg(true,false);
                            wKingNormal = false;
                        }
                        else if(!wKingNormal){
                            board.whiteKing.setImg(false,false);
                            wKingNormal = true;
                        }
                    }
                    else{
                        lastvalid=(ArrayList<Integer>)valid.clone();

                        if(board.board[(r*8)+c]!=null && board.board[(r*8)+c].color == board.playerToMove){

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
                        lastClick.x=r;
                        lastClick.y=c;

                    }
                }
            }

        }

    }

    /**
     * this sets the color of each square depending on the theme
     * 
     * @param r this is the row of the button
     * @param c this is the colum of the button
     */
    private void setcolor(int r,int c){
        if(color.getSelectedIndex()>0){
            squares[r][c].setIcon(null);
            if(valid.contains((r*8)+c))
            {
                //squares[r][c].setBackground(validColor);
                if (r%2!=0){
                    if(c%2==0)squares[r][c].setBackground(Theme.VALID_DARK);
                    else squares[r][c].setBackground(Theme.VALID_LIGHT);}
                else{
                    if(c%2!=0)squares[r][c].setBackground(Theme.VALID_DARK);
                    else squares[r][c].setBackground(Theme.VALID_LIGHT);}
            }
            else{
                if (r%2!=0){
                    if(c%2==0)squares[r][c].setBackground(color2);
                    else squares[r][c].setBackground(color1);}
                else{
                    if(c%2!=0)squares[r][c].setBackground(color2);
                    else squares[r][c].setBackground(color1);}
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
                if(c%2!=0){
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodR.png"));
                    else squares[r][c].setIcon(new ImageIcon("Wood.png"));  
                }
                else{
                    if(valid.contains((r*8)+c))squares[r][c].setIcon(new ImageIcon("woodlightR.png"));
                    else squares[r][c].setIcon(new ImageIcon("woodlight.png"));
                }
            }
        }
    }

    /**
     * Sets the color of the board based on what option is currently
     * selected in the drop down menu.
     */
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
     * This creates a pop up for the winner of the game
     */
    private void showWinner(){
        if(board.winner==false){
            if(win!=null)win.dispose();
            win=new JFrame("WINNER");
            JPanel panel = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Font f = new Font("Comic Sans MS",2,16);
                        g.setFont(f);
                        g.setColor(Color.white);
                        g.fillRect(this.getWidth()/4,this.getHeight()/4, 
                            this.getWidth()/2, this.getHeight()/2);
                        g.setColor(Color.black);
                        String s = "WHITE WINS";
                        g.drawString(s,this.getWidth()/2 - g.getFontMetrics().stringWidth(s)/2,
                            this.getHeight()/2 - g.getFontMetrics().getAscent()/2);
                    }
                };
            win.add(panel);
            win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win.setPreferredSize(new Dimension(400,400));
            win.pack();
            win.setVisible(true);
        }
        else if(board.winner==true){
            if(win!=null)win.dispose();
            win=new JFrame("WINNER");
            JPanel panel = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Font f = new Font("Comic Sans MS",2,16);
                        g.setFont(f);
                        g.setColor(Color.black);
                        g.fillRect(this.getWidth()/4,this.getHeight()/4, 
                            this.getWidth()/2, this.getHeight()/2);
                        g.setColor(Color.white);
                        String s = "BLACK WINS";
                        g.drawString(s,this.getWidth()/2 - g.getFontMetrics().stringWidth(s)/2,
                            this.getHeight()/2 - g.getFontMetrics().getAscent()/2);
                    }
                };
            panel.setPreferredSize(new Dimension(400,400));
            win.add(panel);
            win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win.setPreferredSize(new Dimension(400,400));
            win.pack();
            win.setVisible(true);
        }
        else{
            if(win!=null)win.dispose();
            win=new JFrame("DRAW");
            JPanel panel = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Font f = new Font("Comic Sans MS",2,16);
                        g.setFont(f);
                        g.setColor(Color.gray);
                        g.fillRect(this.getWidth()/4,this.getHeight()/4, 
                            this.getWidth()/2, this.getHeight()/2);
                        g.setColor(Color.black);
                        String s = "STALEMATE";
                        g.drawString(s,this.getWidth()/2 - g.getFontMetrics().stringWidth(s)/2,
                            this.getHeight()/2 - g.getFontMetrics().getAscent()/2);
                    }
                };
            win.add(panel);
            win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win.setPreferredSize(new Dimension(400,400));
            win.pack();
            win.setVisible(true);
        }
    }

    /**
     * This is the main method, it starts the window
     * @param no input needed
     */
    public static void main(String[] args){javax.swing.SwingUtilities.invokeLater(new ChessGame());}
}