import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Threads objects used for piece animations in the ChessGame class
 *
 * @author Spencer Moon, Michael Plekan, Luke Jennings, and Nathan Poirier
 * @version Spring 2021
 */
public class PieceThread extends Thread 
{
    // instance variables - replace the example below with your own
    private int start,end;
    private final int DELAY_TIME=200; 
    private Board board;
    Square[][] squares;
    int Crow,Ccol,Prow,Pcol,Erow,Ecol;
    /**
     * Constructor for objects of class PieceThread. This thread is 
     * used for piece movement animation
     * @param board this is the board object
     * @param squares this is the 2d array that makes the visual board
     * @param start,end the postions where the moves starts and ends
     */
    public PieceThread(Board board,Square[][] squares, int start,int end)
    {
        Crow=start/8;
        Erow=end/8;
        Ccol=start%8;
        Ecol=end%8;
        this.start=start;
        this.end=end;
        this.squares=squares;
        this.board=board;
    }
    /**
     * This makes the animation run, then re-syncs the visual board with the logical board,
     * then stops the tread
     */
    @Override
    public void run() {
        Prow=Crow;
        Pcol=Ccol;
        while(Crow != Erow || Ccol != Ecol){
            try {sleep(DELAY_TIME);}
            catch (InterruptedException e) {}
            if(Crow>Erow)Crow-=1;
            else if(Crow<Erow)Crow+=1;
            if(Ccol>Ecol)Ccol-=1;
            else if(Ccol<Ecol)Ccol+=1;
            if(squares[Crow][Ccol].pic==null){
                squares[Crow][Ccol].pic=board.board[end].pic;
                squares[Prow][Pcol].pic=null;
                Prow=Crow;
                Pcol=Ccol;
            }
        }
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
        this.stop();
    }
}

