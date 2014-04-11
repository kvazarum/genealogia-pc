/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;




/**
 *
 * @author kvazar
 */
public class Data {
    
    public Data()
    {
        try
        {
            Settings settings = new Settings();
            serverName = settings.getUrl() + "iface/iface.php";
        }
        catch(ParserConfigurationException | SAXException ex)
        {
            
        }        
    }
    
    private Document doc;
    
    private static String serverName;
    
    /*
    /   Возвращает ХМЛ-дерево, содержащее данные человека
    /
    */
    public Document getHumanData(String id)
    {        
        return getData("human", id);
    }
    
    public Document getFamilyData(String id)
    {   
        return getData("family", id);
    }
   
   public Document getSearchResult(String searchString)
   {
        String[] split = searchString.split(" ");
        searchString = "";
        for (int i=0; i<split.length; i++)
        {
            if (i<split.length - 1)
            {
                searchString += split[i] + ":";
            }
            else
            {
                searchString += split[i];
            }
        }  
        
        return getData("search", searchString);
   }
   
/**
 * Получение детей без одного известного родителя
 * @param id идентификатор человека
 * @return XML-документ
 */   
   public Document getChildrenWithoutFamily(String id)
   {        
        return getData("childrenOrf", id);   
   }
   
   
   private Document getData(String type, String id)
   {
        try
        {
            URL url = new URL(serverName + "?data=" + id + "&type=" + type);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = fact.newDocumentBuilder();
            
            if (reader.readLine().compareTo("Unknown") != 0)
            {
                InputSource is = new InputSource(reader);
                doc = db.parse(is);
            }
            else
            {
                doc = null;
            }

        }
        catch (IOException | ParserConfigurationException | SAXException e)
        {
            System.out.println("Ошибка класса Data: " + e);
        }
        
        return doc;   
   }
}
