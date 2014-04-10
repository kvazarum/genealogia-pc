

package genealogia;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import genealogia.ui.mainForm;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Гапчич И.Б.
 */
public class Main {
    
    private String path = Settings.getPath() + "pics/";
    Settings settings;
    
/**
 * Установка пути к фото
 * @param path 
 */    
    public void setPath(String path)
    {
        this.path = path;
    }
    
/**
 * Получение пути к фото
 * @param path 
 */       
    public String getPath()
    {
        return this.path;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        setSettings();
        displayMainForm();   
    }
    
/**    
 * Показ основной формы
 */
    private static void displayMainForm()
    {
        mainForm form = new mainForm();
        Settings sets = setSettings();
        String id = sets.getDefaultHuman();
        if (id == null)
        {
            id = "2";
            sets.setDefaultHuman(id);
        }        
        form.setHuman(id);
        form.history.addToHistory(form.getRelative());
        form.setForm();
        form.pack();
        form.setSize(800, 600);
        form.setVisible(true);
    }
    
    private static Settings setSettings()
    {
        Settings result = null;
        try
        {
            Settings setts = new Settings();
            result = setts;
        }
        catch (SAXException | ParserConfigurationException ex)
        {
            System.out.println("Some problems with XML-file");
        }  
        return result;
    }
    
}
