/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author kvazar
 */
public class Settings {

    public Settings() throws SAXException, ParserConfigurationException{
        this.init();
    }
    
    private static final String PATH = "http://gapchich.ru/";
    private static final String APP_HOME_DIR = "Genealogia";
    
    private String url;
    private String fileName = ".settings.xml";
    private String password;
    private String userName;
    private String defaultUser = null;
    private String defaultHuman = null;
    
    Document doc;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getPath() {
        return PATH;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultHuman() {
        return defaultHuman;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

/**
 * Устанавливает ID человека, чьи данные будут открываться по-умолчанию при старте приложения
 * @param defaultHuman 
 */    
    public void setDefaultHuman(String defaultHuman) {
        this.defaultHuman = defaultHuman;
        this.setNodeValue("defaultHuman", defaultHuman);
    }

/**
 * Устанавливает ID пользователя, чьи данные будут подставляться по-умолчанию при старте приложения
 * @param defaultUser 
 */    
    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
        this.setNodeValue("defaultUser", defaultUser);
    }
    
    private void setNodeValue(String nodeName, String nodeValue)
    {
        Node dh = doc.getElementsByTagName(nodeName).item(0);
        dh.setTextContent(nodeValue);
        this.saveToFile();
    }
    
        
    
/**    
 * Инициализация, начальное заполнение переменных
 */
    private void init(){
    
        File settingsFile = this.getFile();

        if (!settingsFile.exists()){
            try {
                settingsFile.createNewFile();
                //создаем документ
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder build = factory.newDocumentBuilder();
                Document document = build.newDocument();
                
                Element rootElement = document.createElement("settings");
                document.appendChild(rootElement);
                
                Element path = document.createElement("path");
                path.setTextContent(Settings.getPath());
                rootElement.appendChild(path);
                
                Element user = document.createElement("user");
                user.setTextContent("anonymous");
                rootElement.appendChild(user);
                
                Element password = document.createElement("password");
                //password.setTextContent("unknown");
                rootElement.appendChild(password); 
                
                Element defaultUser = document.createElement("defaultUser");                
                rootElement.appendChild(defaultUser);
                
                Element defaultHuman = document.createElement("defaultHuman");                
                rootElement.appendChild(defaultHuman);
                
                this.setDoc(document);
                
                //Записываем файл
                this.saveToFile();
                
                
            } 
            catch (IOException e) 
            {
                System.out.println(e);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
            try 
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder build = factory.newDocumentBuilder();
                Document document = build.parse(settingsFile);
                this.setDoc(document);
                if (this.getUrl() == null)
                {
                    this.setUrl(this.getPath());
                }
            } catch (IOException | ParserConfigurationException | SAXException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }        
    }
    
/**
 * Сохранение данных в файл
 */    
    public void saveToFile() 
    {
        File settingsFile = this.getFile();        
        try
        {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(this.doc), new StreamResult(new FileOutputStream(settingsFile)));    
        }
        catch(FileNotFoundException |TransformerException ex)
        {
            
        }
    }
    
/**
 * Return File of settings
 * @return File
 */    
    public File getFile()
    {
        File homeDir = new File(System.getProperty("user.home")); // домашняя папка
        File programDir = new File(homeDir, APP_HOME_DIR); // папка программы
        
        if (!programDir.exists())
        {
            programDir.mkdir();
        }        
        
        File settingsFile = new File(programDir, this.getFileName());
        
        return settingsFile;
    }
    
}
