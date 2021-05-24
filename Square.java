import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
/**
 * This class is used to store a bit of extra data for each square of the board
 *
 * @author Spencer Moon, Michael Plekan, Luke Jennings, and Nathan Poirier
 * @version Spring 2021
 */
public class Square extends JButton
{
    public int row;
    public int col;
    public Image pic;
    /**
     * This constructor is used for squares with a piece on them
     */
    public Square(int row, int col,Image pic)
    {
        super();
        this.col = col;
        this.row = row;
        this.pic=pic;
    }
    /**
     * This constructor is used for squares without a piece on them
     */
    public Square(int row, int col)
    {
        super();
        this.col = col;
        this.row = row;
    }
}
