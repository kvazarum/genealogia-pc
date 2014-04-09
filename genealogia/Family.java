/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import abstracts.AFamily;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Гапчич И.Б.
 */
public class Family extends AFamily{
    
    private String id;
    private String comments;
    
    private ArrayList<String> children = new ArrayList<String>();

/**
 * @return the id
 */
    public String getId() 
    {
        return id;
    }

/**
 * @param id the id to set
 */
    public void setID(String id) 
    {
        this.id = id;
    }

/**
 * @return the comments
 */
    public String getDescription() 
    {
        return comments;
    }

/**
 * @param comments the comments to set
 */
    public void setDescription(String comments)
    {
        this.comments = comments;
    }
    
/**
 *  Установка значений семьи
 * @param id 
 */    
    public void setFamily(String id)
    {
       Data data = new Data();
        
        Document doc = data.getFamilyData(id);
        
        NodeList nodelist;
        
        if (doc != null) 
        {
            this.setID(id);
            
            nodelist = doc.getChildNodes();

            Node root = nodelist.item(0); // получаем рутовый нод

            Node family = root.getChildNodes().item(0);   //получаем нод семьи   
            
            for (int i = 0; i < family.getChildNodes().getLength(); i++)
            {
                String nodeName = family.getChildNodes().item(i).getNodeName();
                
                if (nodeName.equals("father")) 
                {
                    this.setFather(family.getChildNodes().item(i).getTextContent());
                } 
                if (nodeName.equals("mother")) 
                {
                    this.setMother(family.getChildNodes().item(i).getTextContent());
                }                
                if (nodeName.equals("child")) 
                {
                    this.children.add(family.getChildNodes().item(i).getTextContent());
                } 
                if (nodeName.equals("description"))
                {
                    this.setDescription(family.getChildNodes().item(i).getTextContent());
                }
            }            
            
        }    
    }
    
/**
 * 
 * @return count of children in the family
 */    
    public int getCountChildren()
    {
        return this.children.size();
    }
    
/**
 * Return id of child
 * @param index
 * @return 
 */    
    public String getChild(int index)
    {
        String result;
        if (index < this.getCountChildren())
            result = this.children.get(index);
        else
            result = "false";
        return result;
    }
    
}
