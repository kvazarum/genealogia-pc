/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia;

import genealogia.enums.Gender;
import abstracts.Human;
import java.util.ArrayList;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Гапчич И.Б.
 */
public class Relative extends Human{

    private String id;  // номер в системе    
    
    private String surnames = ""; // фамилии, полученные после рождения
    
    private String hidden;
    
    private String description;
    
    private String avatar;

//  Массив семей    
    private ArrayList<String> fams = new ArrayList<String>();
    
    public Relative(){
    }    
    
    public Relative(String id)
    {
        setRelative(id);
    }
    
/**
 * Возвращает список id семей
 * @return 
 */    
    public ArrayList<String> getFamilies()
    {
        return this.fams;
    }
    
/**
 * full path to avatar
 * @return полный путь к файлу аватара, включая имя сайта
 */    
    public String getPathToAvatar()
    {
        String path = Settings.getPATH()+ "pics/" + this.getAvatar();
        return path;
    }
    
/**
 * Возвращает количество семей
 * @return количество семей
 */   
    public int getFamsCount()
    {
        return this.fams.size();
    }
    
/**
 * Получение уникального идентификатора человека
 * @return идентификатор человека
 */    
    public String getID(){
        return this.id;
    }
    
/**
 * Установка уникального идентификатора человека
 * @param id уникальный идентификатор человека
 */    
    public void setID(String id){
        this.id = id;
    } 

/**
 * Получение полного имени человека
 * @return полное имя человека в формате Фамилия (другие фамилии) Имя Отчество
 */
    public String getFullName() {
        String result = this.getSurname();
        if (this.getSurnames().length() > 0){
            result += "(" + this.getSurnames() + ")";
        }
        result = result + " " +this.getName()+ " " +this.getMiddleName();
        return result;
    }

/** 
 * Получение примечаний о человеке
 * @return примечания
 */
    public String getDescription() {
        return description;
    }

/**
 * Установка примечаний
 * @param description the comments to set
 */
    public void setDescription(String description) {
        this.description = description;
    }

/**
 * Получение имени файла аватара
 * @return the avatar
 */
    public String getAvatar() {
        return avatar;
    }

/**
 * Установка имени файла аватара
 * @param avatar the avatar to set
 */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

/**
 * Получение фамилий, полученных после первой фамилии
 * @return the surnames
 */
    public String getSurnames() {
        return surnames;
    }

/**
 * Установка фамилий
 * @param surnames the surnames to set
 */
    public void setSurnames(String surnames) 
    {
        this.surnames = surnames;
    }
    
/**
 * 
 * @return текстовую информация о человеке
 */    
    public String showInfo(){
        String result, isUser;
        
        result = "Род: " + this.getRod()+ "\n";
        result = result + "Имя: " + this.getName() + "\n";
        result = result + "Отчество: " + this.getMiddleName() + "\n";
        result = result + "Фамилия: " + this.getSurname() + "\n";

        result = result + "\nРодители\n";
        if (this.getFather().compareTo("-1") != 0)
        {
            Relative father = new Relative();
            father.setRelative(this.getFather());
            result = result + "Отец: " + father.getSurname() + " " + father.getName() + " " + father.getMiddleName() + "\n";
        }
        if (this.getMother().compareTo("-1") != 0)
        {
            Relative mother = new Relative();
            mother.setRelative(this.getMother());
            result = result + "Мать: " + mother.getSurname() + " " + mother.getName() + " " + mother.getMiddleName() + "\n";
        }
        result = result + "\n";
        if (!this.getDescription().isEmpty())
            result = result + "Примечания: " + this.getDescription() + "\n";
        
        if (this.isUser()){
            isUser = "Уже пользователь";
        }
        else{
            isUser = "Пока ещё не пользователь.";
        }
        result = result + "\nЯвляется пользователем системы: " + isUser + "\n";
        

        return result;
    }
    
/**
 * Функция определения, зарегистрирован ли этот человек как пользователь системы
 * @return  <code>true</code> если да, 
 *          <code>false</code> если нет
 */    
    public boolean isUser(){
        return false;
    }
    
/**
 * Заполнение данных о человеке на основании уникального идентификатора id
 * @param id уникальный идентификатор
 * @return  <code>true</code> если успешно, 
 *          <code>false</code> если нет
 */    
    public boolean setRelative(String id)
    {            
        setID(id);
        
        boolean result = false;
        Data data = new Data();
        Document doc = data.getHumanData(id);
        NodeList nodelist;
        
        if (doc != null) 
        {            
            nodelist = doc.getChildNodes();
            Node root = nodelist.item(0); // получаем рутовый нод
            Node human = root.getChildNodes().item(0);   //получаем нод человека
            
            for (int i = 0; i < human.getChildNodes().getLength(); i++)
            {
                
                String nodeName = human.getChildNodes().item(i).getNodeName();
                String value = human.getChildNodes().item(i).getTextContent();
                
                if (nodeName.compareTo("rod") == 0) 
                {
                    this.setRod(value);
                    continue;
                }
                
                if (nodeName.compareTo("firstName") == 0) {
                    this.setName(value);
                    continue;
                }

                if (nodeName.compareTo("middleName") == 0) {
                    this.setMiddleName(value);
                    continue;
                }

                if (nodeName.compareTo("surname") == 0) {
                    this.setSurname(value);
                    continue;
                }

                if (nodeName.equals("fatherID")) 
                {
                    if (!value.equals("0"))
                    {
                        this.setFather(value);
                    } 
                    else
                    {
                        this.setFather("-1");
                    }
                    continue;
                }

                if (nodeName.equals("motherID"))
                {
                    if (!value.equals("0")) 
                    {
                        this.setMother(value);
                    }
                    else
                    {
                        this.setMother("-1");
                    }
                    continue;
                }

                if (nodeName.compareTo("gender") == 0)
                {
                    if (value.equals("1")) 
                    {
                        this.setGender(Gender.Female);
                    } 
                    else
                    {
                        this.setGender(Gender.Male);
                    }
                    continue;
                }
                
                if (nodeName.compareTo("second_sname") == 0) {
                    this.setSurnames(value);
                    continue;
                }                

                if (nodeName.compareTo("description") == 0) 
                {
                    this.setDescription(value);
                    continue;
                }

                if (nodeName.compareTo("img") == 0)
                {
                    this.setAvatar(value);
                    continue;
                }
                
                if (nodeName.compareTo("bdate") == 0)
                {
                    this.setBDate(value);
                    continue;
                }   
                
                if (nodeName.compareTo("ddate") == 0)
                {
                    this.setDDate(value);
                    continue;
                }                 
                
                if (nodeName.compareTo("bplace") == 0)
                {
                    this.setBPlace(value);
                    continue;
                }    
                
                if (nodeName.compareTo("family") == 0)
                {
                    this.fams.add(value);
                    continue;
                }                 
            }
            result = true;
        }
        return result;
    }
    
/**
 * Преобразование даты из формата Юникс в формат русских настроек
 * @param param дата в формате дд-мм-гггг
 * @return дату в формате "29 октября 1967г."
 */
    public static String displayDate(String param)
    {
        String result = "Нет данных";
        String[] bdt = param.split("-");
        if (bdt[2].compareTo("0000") != 0) 
        {
            result = "";
            String[] ms = new String[] {
                "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"
            };
            String[] mths = new String[] {
                "январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"
            };            
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Integer.parseInt(bdt[2]), Integer.parseInt(bdt[1]) , Integer.parseInt(bdt[0]));
            
            if (!bdt[0].equals("00"))
            {
                result = bdt[0] + " ";
            }
            
            if (!bdt[1].equals("00"))
            {
                result += ms[Integer.valueOf(bdt[1]) - 1];
            }
            
            result += " " + bdt[2] + "г.";
        }        
        return result;
    }
    
    public ArrayList<String> getChildrenWhithoutFamily()
    {
        ArrayList<String> _childrenWhithoutFamily = new ArrayList<>();
        Data data = new Data();

        Document doc = data.getChildrenWithoutFamily(this.getID());
        
        NodeList nodelist;
        
        if (doc != null) 
        {            
            nodelist = doc.getChildNodes();

            Node root = nodelist.item(0); // получаем рутовый нод

            Node children = root.getChildNodes().item(0);   //получаем нод человека
            
            for (int i = 0; i < children.getChildNodes().getLength(); i++)
            {
                
                String nodeName = children.getChildNodes().item(i).getNodeName();
                _childrenWhithoutFamily.add(children.getChildNodes().item(i).getTextContent());
            }
        }
        return _childrenWhithoutFamily;
    }
    
    public ArrayList<String> getClans()
    {
        ArrayList<String> _clans = new ArrayList<>();
        Data data = new Data();
        Document doc = data.getClans(this.getID());        
        NodeList nodelist;
        
        if (doc != null) 
        {            
            nodelist = doc.getChildNodes();

            Node root = nodelist.item(0); // получаем рутовый нод

            Node _list = root.getChildNodes().item(0);   //получаем нод человека
            
            for (int i = 0; i < _list.getChildNodes().getLength(); i++)
            {
                _clans.add(_list.getChildNodes().item(i).getTextContent());
            }
        }        
        return _clans;
    }
    
}
