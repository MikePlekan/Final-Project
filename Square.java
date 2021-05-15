import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
/**
 * Write a description of class Square here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Square extends JButton
{
    public int row;
    public int col;
    
    public Square(int row, int col)
    {
        super("");
        this.col = col;
        this.row = row;
    }
}
