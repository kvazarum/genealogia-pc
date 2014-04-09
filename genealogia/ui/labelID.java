/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia.ui;

import java.awt.Cursor;
import javax.swing.JLabel;

/**
 *
 * @author kvazar
 */
public class labelID extends JLabel{
    
    private int id;
    
    public void setID(int id)
    {
        this.id = id;
    }
    
    public int getID()
    {
        return this.id;
    }
    
    public void setHandCursor()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setWaitCursor()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
}
