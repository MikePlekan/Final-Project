import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
/**
 * Write a description of class PawnPromotion here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PawnPromotion extends Thread implements ActionListener
{
    int current;
    JFrame promoteWin;
    Board board;
    JButton buttons[] = new JButton[4];
    boolean done=false;
    public PawnPromotion(int current,Board board){
        this.current=current;
        this.board=board;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (int c = 0; c < 4; c++)
        {
            if(e.getSource().equals(buttons[c])){
                if(c==0){
                    board.promoteTo="Q";
                }
                else if(c==1){
                    board.promoteTo="R";
                }
                else if(c==2){
                    board.promoteTo="N";
                }
                else if(c==3){
                    board.promoteTo="B";
                }
                if(promoteWin!=null)promoteWin.dispose();
                this.stop();
            }
        }
    }

    @Override
    public void run() {
        if(promoteWin!=null)promoteWin.dispose();
        promoteWin=new JFrame("Pawn Promotion");
        GridLayout Pawngrid = new GridLayout(1, 4);
        JPanel panel = new JPanel(Pawngrid);
        boolean color=board.board[current].color;
        String pics[]=new String[4];
        if(!color){
            pics[0]="BlackQueen.png";
            pics[1]="BlackRook.png";
            pics[2]="BlackKnight.png";
            pics[3]="BlackBishop.png";
        }
        else {
            pics[0]="WhiteQueen.png";
            pics[1]="WhiteRook.png";
            pics[2]="WhiteKnight.png";
            pics[3]="WhiteBishop.png";
        }

        for (int c = 0; c < 4; c++)
        {
            buttons[c]=new JButton();
            buttons[c].setOpaque(true);
            buttons[c].setBorderPainted(false);
            panel.add(buttons[c]);
            buttons[c].addActionListener(this);
            buttons[c].setIcon(new ImageIcon(pics[c]));

        }
        if(board.promoteTo=="Q"){
            buttons[0].setBackground(Color.RED);      
        }
        else if(board.promoteTo=="R"){
            buttons[1].setBackground(Color.RED); 
        }
        else if(board.promoteTo=="N"){
            buttons[2].setBackground(Color.RED); 
        }
        else if(board.promoteTo=="B"){
            buttons[3].setBackground(Color.RED); 
        }
        promoteWin.add(panel);
        promoteWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        promoteWin.setPreferredSize(new Dimension(450,150));
        promoteWin.pack();
        promoteWin.setVisible(true);
    }
}