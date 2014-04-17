/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abstracts;

import java.io.File;
import org.w3c.dom.Document;

/**
 *
 * @author ingvar
 */
public abstract class AFile {
/**
 * XML document
 */    
    Document doc;
    
/**
 * Name of the file
 */    
    private String fileName;
    
/**
 * Path to site, where stores data
 */    
    private static final String PATH = "http://gapchich.ru/";
    
/**
 * Name of directory, where stores local files
 */    
    private static final String APP_HOME_DIR = "Genealogia";    
    
/**
 * Returns XML document
 * @return 
 */    
    public Document getDoc() {
        return this.doc;
    }    

/**
 * Sets XML document
 * @param doc 
 */    
    public void setDoc(Document doc) {
        this.doc = doc;
    }

/**
 * Returns name of the file
 * @return 
 */    
    public String getFileName() {
        return fileName;
    }

/**
 * Sets name of the file
 * @param fileName 
 */    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

/**
 * Returns File
 * @return File
 */    
    public File getFile()
    {
        File homeDir = new File(System.getProperty("user.home")); // домашняя папка
        File programDir = new File(homeDir, getAppHomeDir()); // папка программы
        File settingsFile = null;
        if (!programDir.exists())
        {
            programDir.mkdir();
        }        
        
        try
        {
            settingsFile = new File(programDir, this.getFileName());
        }
        catch(NullPointerException ex)
        {
            System.out.println(ex);
        }
        
        return settingsFile;
    }

/**
 * Returns path to site gapchich.ru
 * @return String path to site gapchich.ru
 */    
    public static String getPATH() {
        return PATH;
    }
    
/**
 * Returns path to home directory
 * @return String path to home directory
 */    
    public static String getAppHomeDir()
    {
        return APP_HOME_DIR;
    }    
    
}
