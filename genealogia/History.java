/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import genealogia.enums.Direction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

/**
 *
 * @author kvazar
 */
public class History {
    
    public History(){
        this.init();
    }    
/**
 * Список id просмотренных людей
 */    
    private ArrayList<Relative> historyList = new ArrayList<Relative>();
    
/**
 * Максимальное количество записей id
 */
    private static final int MAX_HISTORY_LENGHT = 5;
/**
 * Текущая позиция в истории просмотров
 */
    private int position = -1; 
    
    private final String fileName = ".history.xml";
    
    Document doc;

    public void setDoc(Document doc) {
        this.doc = doc;
    }


    public String getFileName() {
        return fileName;
    }
    
/**
 * Append instance of human in list of visited people
 * @param human instance of human 
 */
    public void addToHistoryList(Relative human)
    {
        if (historyList.size() >= MAX_HISTORY_LENGHT)
        {
            this.historyList.remove(0);
        }
        this.historyList.add(human);
    }

/**
 * Removes elements from the specified position to end of list
 */    
    public void removeHighPosition()
    {
        if (this.getPosition() != this.getSize() - 1)
        {
            for (int i = this.getPosition() + 1; i < this.getSize(); i++)
            {
                this.historyList.remove(this.getSize() - 1);
            }
        }    
    }
    
/**
 * Change current position in the history list
 */    
    public void changePosition()
    {
        this.position = historyList.size() - 1;
    }
    
/**
 * Get size of the history list
 * @return 
 */    
    public int getSize()
    {
        return this.historyList.size();
    }
    
/**
 * return maximum count of record in the history list
 * @return maximum count of record in the history list
 */    
    public int getMax()
    {
        return MAX_HISTORY_LENGHT;
    }
    
    public Relative get(int id)
    {
        if (id >= 0 && id <= MAX_HISTORY_LENGHT)
        {
            return historyList.get(id);
        }
        else
        {
            return null;
        }
    }
    
    public Relative getCurrentHuman()
    {
        Relative result;
        if (this.position < historyList.size())
        {
            result = historyList.get(this.position);
        }
        else
        {
            result = null;
        }
        return result;
    }
    
    public void remove(int index)
    {
        this.historyList.remove(index);
    }
    
    public int changePosition(Direction direction)
    {
        if (direction == Direction.Back)
        {
            if (this.position > 0)
            {
                setPosition(getPosition() - 1);
            }
        }
        else
        {
            if (this.position < MAX_HISTORY_LENGHT)
            {
                setPosition(getPosition() + 1);
            }
        }
        return this.position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public void saveToFile()
    {
        File historyFile = this.getFile();
        try
        {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(this.doc), new StreamResult(new FileOutputStream(historyFile)));    
        }
        catch(FileNotFoundException |TransformerException ex)
        {
            System.out.println("Error saving file. - " + ex);
        }        
        
    }
    
/**
 * Return File of history
 * @return File
 */    
    public File getFile()
    {
        File homeDir = new File(System.getProperty("user.home")); // домашняя папка
        File programDir = new File(homeDir, Settings.getAppHomeDir()); // папка программы
        
        if (!programDir.exists())
        {
            programDir.mkdir();
        }        
        
        File historyFile = new File(programDir, this.getFileName());
        
        return historyFile;
    }    
    
/**    
 * Инициализация, начальное заполнение истории
 */
    private void init(){
    
        File _historyFile = this.getFile();

        if (!_historyFile.exists()){
            try {
                //создаем документ
                _historyFile.createNewFile();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder build = factory.newDocumentBuilder();
                Document document = build.newDocument();
                
                Element rootElement = document.createElement("history");
                document.appendChild(rootElement);
                Element _element = document.createElement("position");
                _element.setTextContent(String.valueOf(0));
                rootElement.appendChild(_element);
                this.setDoc(document);
                this.saveToFile();  //Записываем файл
                
                
            } 
            catch (IOException e) 
            {
                System.out.println(e);
            } 
            catch (ParserConfigurationException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        else
        {
            try 
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder build = factory.newDocumentBuilder();
                Document document = build.parse(_historyFile);
                if (document != null)
                {
                    this.setDoc(document);
                    restoreFromXML();                
                }
            } 
            catch (IOException | ParserConfigurationException | SAXException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }        
    } 
    
/**
 * Сохранение текущего состояния истории в XML-файл
 */    
    public void saveToXML()
    {
        Node _rootNode = doc.getElementsByTagName("history").item(0); //root node
        
//  Очищаем предыдущую историю        
        NodeList _nods = doc.getElementsByTagName("element");
        int _count = _nods.getLength(); // count of nodes
        while (_nods.getLength() >0 ) 
        {
            try
            {
                Node node = _nods.item(0);
                node.getParentNode().removeChild(node);
            }
            catch (NullPointerException ex)
            {
                System.out.println("Ошибка при удалении нода. "+ ex);
            }
        }

        for (Relative human : this.historyList) 
        {
            Element element = doc.createElement("element");
            element.setTextContent(human.getID());
            _rootNode.appendChild(element);
        }
        Node element = doc.getElementsByTagName("position").item(0);
        element.setTextContent(String.valueOf(this.getPosition()));
    }
    
    private void restoreFromXML()
    {
        Node _rootNode = doc.getElementsByTagName("history").item(0); //root node
        NodeList _nods = doc.getElementsByTagName("element");
        int _count = _nods.getLength(); // count of nodes
        for (int i = 0; i < _count; i++) 
        {
            Relative human = new Relative(_nods.item(i).getTextContent());
            this.addToHistoryList(human);
        }
        Node element = doc.getElementsByTagName("position").item(0);
        this.setPosition(Integer.parseInt(element.getTextContent()));
    }
}
