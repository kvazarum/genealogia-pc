/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia.ui;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author kvazar
 */
public class AvatarLabel extends JLabel
{
    public boolean setAvatar(String path)
    {
        boolean result = true;
        try
        {
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            ImageIcon ii = new ImageIcon(image);
            this.setIcon(new ImageIcon(ii.getImage().getScaledInstance(30, 30, ii.getImage().SCALE_DEFAULT)));
            this.setText("");
        }
        catch(Exception e)
        {
            result = false;
        }        
        return result;
    }    
}
