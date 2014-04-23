

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
    
    private String path = Settings.getPATH() + "pics/";
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
        showMainForm();   
    }
    
/**    
 * Показ основной формы
 */
    private static void showMainForm()
    {
        mainForm form = new mainForm(); 
        form.init();
        Settings settings = setSettings();
        String id = settings.getDefaultHuman();
        if (id == null)
        {
            id = "2";
            settings.setDefaultHuman(id);
        }
        History history = form.history;
        if (history.getSize() > 0 && history.getPosition() < history.getSize())
        {
            form.setHuman(history.get(history.getPosition()));
        }
        else
        {
            form.setHuman(id);
            form.history.addToHistoryList(form.getRelative());
        }
        form.setForm();
        form.pack();
        form.setSize(800, 600);
        form.setHistoryButtons();
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
