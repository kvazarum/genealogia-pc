/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia.ui;

import genealogia.enums.Gender;
import genealogia.Data;
import genealogia.DataReceiver;
import genealogia.Family;
import genealogia.Relative;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author kvazar
 */
public class mainForm extends JFrame
{
    
    Relative human = null;
    
    JLabel fieldSurname = new JLabel();
    JLabel fieldName = new JLabel();
    JLabel fieldMName = new JLabel(); 
    JLabel labelFullName = new JLabel();
    
    JLabel fieldMother = new JLabel();
    JLabel fieldFather = new JLabel();
    
    JLabel labelAvatar = new JLabel("Нет изображения");
    
    JLabel fieldDescription = new JLabel();  
    
    JLabel fieldBirthDate = new JLabel(); 
    JLabel fieldDeathDate = new JLabel();
    
    JLabel fieldBirthPlace = new JLabel();
    
    AvatarLabel fatherAvatar = new AvatarLabel();
    AvatarLabel motherAvatar = new AvatarLabel();
    
    labelID fieldFatherBDate = new labelID();
    labelID fieldMotherBDate = new labelID();
    
    Font boldFont = new Font(fieldFatherBDate.getFont().getName(), Font.BOLD, fieldFatherBDate.getFont().getSize());
    Font plainFont  = new Font(fieldFatherBDate.getFont().getName(), Font.PLAIN, fieldFatherBDate.getFont().getSize());

//  Панель с данными семей    
    JPanel panelFamily = new JPanel(null);
    
// Панель для личных данных
    JPanel panelPersonal = new JPanel(null);    

//  Панель поиска
    JPanel panelSearch = new JPanel(null);
    
    JPanel panelResults = new JPanel(null);
    
    JPanel panelFather = new JPanel(null);  
    JPanel panelMother = new JPanel(null);
    
    JPanel panelParents = new JPanel(null);
    
    Dimension avatarSize = new Dimension(30, 30);    
    


    
/**
 * Установка id человека, данные которого отображает форма
 * @param id - идентификатор человека
 */    
    public void setHuman(String id){
        Thread dr = new DataReceiver(id, this);
        dr.start();
    }
    
/**
 * 
 * @param hmn 
 */    
    public void setHuman(Relative hmn)
    {
        this.human = hmn;
    }

    public Relative getHuman() {
        return this.human;
    }    
    
/**
 * Установка элементов формы
 */    
    public void setForm()
    {
//  Действие при закрытии        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
//  Меню        
        this.setMenu();

//  Панель поиска        
        this.setSearchPanel();        

// Панель для данных родителей
        this.setParentPanel();

//  Панель персональных данных        
        this.setPersonalPanel();
        
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("PrivateData"), panelPersonal);
        tabPane.addTab(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("Parents"), panelParents);
        tabPane.addTab("Семьи", this.panelFamily);
        tabPane.addTab(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("Search"), this.panelSearch);
        this.add(tabPane);
        
        this.pack();       
        this.setSize(600, 450);
        
//Заполнение данными человека        
        if (getHuman() != null)
        {
            this.setData();
        }
    }
    
/**
 * Функция заполнения формы данными выбранного человека
 */    
    private void setData()
    {        
        //  Полное имя 
        this.labelFullName.setText(this.human.getFullName());
        
        //  Фамилия
        this.fieldSurname.setText(this.human.getSurname());
        
        //  Имя
        this.fieldName.setText(human.getName());
        
        //  Описание
        this.fieldDescription.setText("<html>" + human.getDescription()+ "</html>");
        
        // Дата рождения        
        this.fieldBirthDate.setText(Relative.displayDate(human.getBDate()));

        // Отчество
        this.fieldMName.setText(human.getMiddleName());
        
        setParentsData();
        
//  Семьи
        this.setFamilyData();        
        
    }
   
/**
 * Двойной щелчок на родителе для перехода на этого человека
 * @param componentName 
 */    
    private void doubleClick(Component component)
    {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        
        if (!component.equals("-1"))
        {
            clearFields();
            setHuman(component.getName());                            
            setData();
        }

        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
    }
    
/**
 * 
 * @return MouseListener
 */    
    public MouseListener getMouseListener()
    {
        MouseListener ml = new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e) {
                //Получаем кол-во щелчков
                int click = e.getClickCount();
                
                if (click == 1)
                {
                    doubleClick(e.getComponent());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        };
        return ml;
    }
    
    private void clearFields()
    {
        clearFamilyTab();
        clearParentsTab();
    }
    
    private void clearFamilyTab()
    {
        this.panelFamily.removeAll();
    }
    
    private void clearSearchTab()
    {
        this.panelResults.removeAll();
    }
    
    private void clearParentsTab()
    {
        String empty = "empty.jpg";
        ImageIcon icon = new ImageIcon(empty);
        icon.getImage().flush();
        this.motherAvatar.setIcon(icon);
        this.fatherAvatar.setIcon(icon);
    }
    
/**
 * Установка компонентов панели персональных данных
 */
    private void setPersonalPanel()
    {
        Font titleFont = new Font(boldFont.getName(), Font.BOLD, 16);
        
        JLabel labelSurname = new JLabel(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("Surname"));        

        JLabel labelName = new JLabel(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("Name"));
        
        JLabel labelMiddleName = new JLabel(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("SecondName"));        
        
        this.fieldSurname.setFont(plainFont);
        
        
        int LABEL_X = 170;
        int FIELD_X = LABEL_X + 110;
        int LABEL_HEIGHT = 20;
//  Полное имя        
        this.labelFullName.setSize(450, LABEL_HEIGHT);
        this.labelFullName.setLocation(240, 5);
        this.labelFullName.setFont(titleFont);
        this.panelPersonal.add(labelFullName);  
        
//  Аватар        
        this.labelAvatar.setSize(150, 150);
        this.labelAvatar.setLocation(5, 5);
        this.labelAvatar.setVerticalAlignment(SwingConstants.CENTER);
        this.labelAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        this.panelPersonal.add(labelAvatar); 
        
//  Фамилия        
        labelSurname.setSize(80, LABEL_HEIGHT);
        labelSurname.setLocation(LABEL_X, 30);
        this.panelPersonal.add(labelSurname);
        
        fieldSurname.setSize(150, LABEL_HEIGHT);
        fieldSurname.setLocation(FIELD_X, labelSurname.getY());
        this.panelPersonal.add(fieldSurname);
        
//  Имя
        labelName.setSize(80, LABEL_HEIGHT);
        labelName.setLocation(LABEL_X, fieldSurname.getY() + fieldSurname.getHeight() +5);
        this.panelPersonal.add(labelName);
        
        fieldName.setSize(100, LABEL_HEIGHT);
        fieldName.setLocation(FIELD_X, labelName.getY());
        fieldName.setFont(plainFont);
        this.panelPersonal.add(fieldName);
        
//  Отчество
        labelMiddleName.setSize(150, LABEL_HEIGHT);
        labelMiddleName.setLocation(LABEL_X, fieldName.getY() + fieldName.getHeight() +5);
        this.panelPersonal.add(labelMiddleName);
        
        this.fieldMName.setSize(100, LABEL_HEIGHT);
        this.fieldMName.setLocation(FIELD_X, labelMiddleName.getY());
        this.fieldMName.setFont(plainFont);
        this.panelPersonal.add(this.fieldMName);

// Дата рождения
        JLabel labelBDate = new JLabel("Дата рождения");
        labelBDate.setSize(100, LABEL_HEIGHT);
        labelBDate.setLocation(LABEL_X, labelMiddleName.getY() + labelMiddleName.getHeight() +5);
        this.panelPersonal.add(labelBDate);
        
        this.fieldBirthDate.setSize(120, LABEL_HEIGHT);
        this.fieldBirthDate.setLocation(FIELD_X, labelBDate.getY());
        this.fieldBirthDate.setFont(plainFont);
        this.panelPersonal.add(fieldBirthDate);  
        
// Место рождения
        JLabel labelBPlace = new JLabel("Место рождения");
        labelBPlace.setSize(100, LABEL_HEIGHT);
        labelBPlace.setLocation(LABEL_X, labelBDate.getY() + labelBDate.getHeight() +5);
        this.panelPersonal.add(labelBPlace);
        
        this.fieldBirthPlace.setSize(550, LABEL_HEIGHT);
        this.fieldBirthPlace.setLocation(FIELD_X, labelBPlace.getY());
        this.fieldBirthPlace.setFont(plainFont);
        this.panelPersonal.add(this.fieldBirthPlace);
        
//  Дата смерти
        if (this.getHuman() != null && !this.human.getDDate().equals(0000-00-00))
        {
        
        }
        
//  Описание        
        JLabel labelDescription = new JLabel();
        labelDescription.setText("Примечание:");
        labelDescription.setSize(150, LABEL_HEIGHT);
        labelDescription.setLocation(30, 200);
        
        this.panelPersonal.add(labelDescription);

        this.fieldDescription.setSize(500, 200);
        this.fieldDescription.setLocation(20, labelDescription.getY() + 25);
        this.fieldDescription.setFont(plainFont);
        this.fieldDescription.setVerticalAlignment(SwingConstants.TOP);
        this.panelPersonal.add(fieldDescription);    
        
        this.panelPersonal.repaint();    // перерисовываем        
    
    }
    
/**
 * Установка компонентов панели персональных данных родителей
 */    
    private void setParentPanel()
    {
//  Панель с данными отца
        panelParents.setSize(600, 400);
        panelParents.setLocation(0,0);        
        panelFather.setSize(550, 80);
        panelFather.setLocation(20, 5);
        panelFather.setBorder(BorderFactory.createLineBorder(Color.black));
                

//  Панель с данными матери        
        panelMother.setSize(550, panelFather.getHeight());
        panelMother.setLocation(panelFather.getX(), panelFather.getY() + panelFather.getHeight() + 5);
        panelMother.setBorder(BorderFactory.createLineBorder(Color.black)); 
        
//  Добавляем панель с данными отца        
        panelParents.add(panelFather);

//  Добавляем панель с данными матери        
        panelParents.add(panelMother);  
        
        JLabel labelFather = new JLabel("Отец:");
        labelFather.setSize(40, 30);
        labelFather.setLocation(10, 0);
        
        JLabel labelMother = new JLabel("Мать:");
        labelMother.setSize(labelFather.getWidth(), 30);
        labelMother.setLocation(labelFather.getX(), 0);
        
        MouseListener ml = this.getMouseListener();
        
        this.fieldFather.addMouseListener(ml);
        this.fieldMother.addMouseListener(ml);
        
        this.fieldFather.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.fieldMother.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        this.fieldFather.setFont(plainFont);
        this.fieldMother.setFont(plainFont);
        
//  Размеры полей полного имени       
        this.fieldFather.setSize(300, 20);
        this.fieldMother.setSize(300, 20);
        
//  Размеры полей с днями рождения
        this.fieldFatherBDate.setSize(120, 20);
        this.fieldMotherBDate.setSize(120, 20);
        
        this.fieldFather.setLocation(labelFather.getX(), labelFather.getY() + labelFather.getHeight() + 5);
        this.fieldMother.setLocation(labelMother.getX(), labelMother.getY() + labelMother.getHeight() + 5);
        this.fieldFatherBDate.setLocation(this.fieldFather.getX() + this.fieldFather.getWidth() + 5, fieldFather.getY());
        this.fieldMotherBDate.setLocation(this.fieldMother.getX() + this.fieldMother.getWidth() + 5, fieldMother.getY());
        
        this.fieldFatherBDate.setFont(plainFont);
        this.fieldMotherBDate.setFont(plainFont);
        
        this.panelFather.add(labelFather);
        this.panelFather.add(this.fieldFather);
        
        if (this.getHuman() != null && !this.human.getFather().equals("-1"))
        {
            Relative father = new Relative();
            father.setRelative(this.human.getFather());
            if (father.getAvatar().length() > 0)
            {
                this.fatherAvatar.setSize(avatarSize);
                this.fatherAvatar.setLocation(fieldFatherBDate.getX() + fieldFatherBDate.getWidth() +5, fieldFatherBDate.getY());                
                this.panelFather.add(this.fatherAvatar);
            }
            
            if (!father.getBDate().equals("00-00-0000"))
            {
                panelFather.add(this.fieldFatherBDate);
            }
        }
        
        panelMother.add(labelMother);
        panelMother.add(fieldMother);
        
        if (this.getHuman() != null && !this.human.getMother().equals("-1"))
        {
            Relative mother = new Relative();
            mother.setRelative(this.human.getMother());
            if (mother.getAvatar().length() > 0)
            {
                this.motherAvatar.setSize(avatarSize);
                this.motherAvatar.setLocation(fieldMotherBDate.getX() + fieldMotherBDate.getWidth() +5, fieldMotherBDate.getY());
                panelMother.add(this.motherAvatar);
            }
            if (!mother.getBDate().equals("00-00-0000"))
            {
                panelMother.add(this.fieldMotherBDate);
            }            
        }        
    }
    
/**
 * Установка персональных данных родителей
 */
    private void setParentsData()
    {
        String motherName = "Нет данных";
        String fatherName = "Нет данных";
        
        String fatherID = "-1";
        String motherID = "-1";
        
        if (!this.human.getFather().equals("-1"))
        {
            Relative father = new Relative();
            father.setRelative(human.getFather());
            fatherName = father.getFullName();
            this.fieldFather.setToolTipText(father.getDescription());
            fatherID = human.getFather();
        }

        if (!this.human.getMother().equals("-1"))
        {
            Relative mother = new Relative();
            mother.setRelative(human.getMother());
            motherName = mother.getFullName();
            this.fieldMother.setToolTipText(mother.getDescription());
            motherID = human.getMother();            
        }
        
        this.fieldMother.setText(motherName);
        this.fieldFather.setText(fatherName);
        
        this.fieldFather.setName(fatherID);
        this.fieldMother.setName(motherID);
        
        if (this.human.getAvatar().length() > 1 )
        {
            try
            {
                URL url = new URL(this.human.getPathToAvatar());
                BufferedImage image = ImageIO.read(url);
                ImageIcon ii = new ImageIcon(image);
                labelAvatar.setIcon(new ImageIcon(ii.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                labelAvatar.setText("");
            }
            catch(IOException e)
            {

            }
        }
        else
        {
            labelAvatar.setIcon(null);
            labelAvatar.setText("Нет фото");
        }
        
        labelAvatar.setBorder(BorderFactory.createLineBorder(Color.black));
        
        this.setTitle(this.human.getFullName());
        
//  Father's avatar        
        if (!this.human.getFather().equals("-1"))
        {
            Relative father = new Relative();
            father.setRelative(this.human.getFather());
            if (father.getAvatar().length() > 0)
            {
                if (fatherAvatar.setAvatar(father.getPathToAvatar()))
                {
                    
                }
                else
                {
                
                }
               
            }            
            fieldFatherBDate.setText(Relative.displayDate(father.getBDate()));
        } 
        else
        {
            fieldFatherBDate.setText("Нет данных");
            
        }
        
//  Mother's avatar        
        if (!this.human.getMother().equals("-1"))
        {
            Relative mother = new Relative();
            mother.setRelative(this.human.getMother());
            if (mother.getAvatar().length() > 0)
            {
                try
                {
                    URL url = new URL(mother.getPathToAvatar());
                    BufferedImage image = ImageIO.read(url);
                    ImageIcon ii = new ImageIcon(image);
                    this.motherAvatar.setIcon(new ImageIcon(ii.getImage().getScaledInstance(30, 30, ii.getImage().SCALE_DEFAULT)));
                    this.motherAvatar.setText("");
                    this.motherAvatar.setVisible(true);
                }
                catch(java.io.IOException e)
                {

                }                
            }
            else
            {
                this.motherAvatar.setVisible(false);
            }
            
            fieldMotherBDate.setText(Relative.displayDate(mother.getBDate()));
            fieldBirthPlace.setText(this.human.getBPlace());            
        }
        else
        {
            fieldMotherBDate.setText("Нет данных");
            fieldBirthPlace.setText("Нет данных");        
        }    
    }
    
 /**
 * Установка компонентов панели семей
 */    
    private void setFamilyData()
    {
        if (this.human.getSizeFams() > 0) // если имеются семьи
        {

            ArrayList<String> listF = this.human.getFamilies();
            
            int lastY = 0;  //  Последнее значение Y панели семьи
            int lastHeight = 0; // Последнее значение высоты панели семьи
            for (int i=0; i < this.human.getSizeFams(); i++)
            {
                int height = 15;
                
                JLabel fieldSpouse = new JLabel();
                fieldSpouse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));                

                fieldSpouse.setSize(290, 15);                

                Family fam = new Family();
                fam.setFamily(listF.get(i));

                JPanel familyPanel = new JPanel(null);
                Border border = BorderFactory.createTitledBorder("Семья");
                familyPanel.setBorder(border);
                familyPanel.setSize(500, 70 + fam.getCountChildren()*30);

                int x = 10;
                int y = lastY + lastHeight + 5;
                familyPanel.setLocation(x, y);
                lastHeight = 70 + fam.getCountChildren()*30;
                lastY = familyPanel.getY();

                String param;                                   // id супруга
                if (this.human.getGender() == Gender.Male)
                {
                    param = fam.getMother();
                }
                else
                {
                    param = fam.getFather();
                }

                Relative spouse = new Relative();               // супруг                  
                spouse.setRelative(param);

                fieldSpouse.setText(spouse.getFullName());       //  полное имя супруга
                fieldSpouse.setLocation(85, 15);
                fieldSpouse.setFont(plainFont);

                JLabel labelSpouse = new JLabel("Супруг(а):");
                labelSpouse.setSize(100, height);
                labelSpouse.setLocation(20, height);

                familyPanel.add(labelSpouse);
                familyPanel.add(fieldSpouse);     
                
//  если в семье есть дети
                if (fam.getCountChildren() > 0)
                {
                    JLabel labelChildren = new JLabel("Дети:");
                    labelChildren.setSize(40, 20);
                    labelChildren.setLocation(labelSpouse.getX(), labelChildren.getY() + labelChildren.getHeight() + 10);
                    familyPanel.add(labelChildren);

                    for (int k=0; k < fam.getCountChildren(); k++)
                    {
                        JLabel fieldChild = new JLabel();
                        fieldChild.setSize(300, height);
                        fieldChild.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        
                        JLabel fieldBDate = new JLabel();                        
                        fieldBDate.setSize(120, height);
                        fieldBDate.setFont(plainFont);                            
                        x = 20;
                        y = labelChildren.getY() + labelChildren.getHeight() + k*31;
                        fieldChild.setLocation(x, y);
                        fieldChild.setFont(plainFont);
                        fieldChild.setName(fam.getChild(k));

                        MouseListener ml = this.getMouseListener();
                        fieldChild.addMouseListener(ml);

                        Relative ch = new Relative();
                        ch.setRelative(fam.getChild(k));

                        fieldChild.setText(ch.getFullName());
                        familyPanel.add(fieldChild);

                        fieldBDate.setText(ch.displayDate(ch.getBDate()));
                        fieldBDate.setLocation(x + 300, y);
                        familyPanel.add(fieldBDate);

                        AvatarLabel avatar = new AvatarLabel();
                        avatar.setSize(avatarSize);
                        avatar.setLocation(x + 430, y);
                        if (avatar.setAvatar(ch.getPathToAvatar()))
                        {
                            familyPanel.add(avatar);
                        }
                    }
                }

                this.panelFamily.add(familyPanel); 
            }                 
        }
        this.panelFamily.repaint();    
    }
    
    private void addChildToForm()
    {
    
    }
    
/**
 * Установка панели поиска
 */    
    private void setSearchPanel()
    {
        this.panelSearch.setSize(600, 400);
        this.panelSearch.setLocation(0,0);
        
        JLabel labelSearch = new JLabel("Текст запроса");
        labelSearch.setSize(100, 20);
        labelSearch.setLocation(100,5);  
        this.panelResults.setLocation(new Point(0, 0));
        
//  Строка запроса        
        final JTextField fieldSearchText = new JTextField("Гапчич Игорь", 20);
        fieldSearchText.setSize(400, 20);
        fieldSearchText.setLocation(100, 40);
        
        JButton buttonSearch = new JButton("Найти");
        buttonSearch.setSize(80, 20);
        buttonSearch.setLocation(520, 40);     

        buttonSearch.addActionListener(new ActionListener() 
        {            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                clearSearchTab();
                Data result = new Data();
                fieldSearchText.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Document doc = result.getSearchResult(fieldSearchText.getText());
                if (doc != null) 
                {
                    NodeList nodelist;
                    nodelist = doc.getChildNodes();
                    Node root = nodelist.item(0); // получаем рутовый нод
                    Node results = root.getChildNodes().item(0);   //получаем

                    panelResults.setPreferredSize(new Dimension(600, results.getChildNodes().getLength()*30));
                    System.out.println(results.getChildNodes().getLength()*30);
                    //panelResults.setSize(new Dimension(600, 300));
                    
                    for (int i = 0; i < results.getChildNodes().getLength(); i++)
                    {
                        String id = results.getChildNodes().item(i).getTextContent();
                        Relative tempHuman = new Relative();
                        tempHuman.setRelative(id);
                        
                        JLabel name = new JLabel();
                        name.setText(tempHuman.getFullName());
                        name.setSize(new Dimension(300, 20));
                        name.setLocation(new Point(10, i*25+10));
                        name.setName(id);
                        name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        name.setToolTipText(tempHuman.getDescription());
                        
                        MouseListener ml = new MouseInputListener() 
                        {

                            @Override
                            public void mouseClicked(MouseEvent e) 
                            {
                                //Получаем кол-во щелчков
                                int click = e.getClickCount();

                                if (click == 1)
                                {
                                    doubleClick(e.getComponent());
                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                         
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                         
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                         
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                         
                            }

                            @Override
                            public void mouseDragged(MouseEvent e) {
                         
                            }

                            @Override
                            public void mouseMoved(MouseEvent e) {
                         
                            }
                        } ;
                        
                        name.addMouseListener(ml);
                        
                        JLabel dateBirth = new JLabel();
                        dateBirth.setText(tempHuman.getFullName());
                        dateBirth.setSize(new Dimension(100, 20));
                        dateBirth.setLocation(new Point(name.getX() + name.getWidth() + 5, i*25+10));                       
                        dateBirth.setText(Relative.displayDate(tempHuman.getBDate()));
                        
                        panelResults.add(name);
                        panelResults.add(dateBirth);
                    }                    
                    panelResults.repaint();
                }                
                fieldSearchText.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });  
        
        this.panelSearch.add(labelSearch);
        this.panelSearch.add(fieldSearchText);
        this.panelSearch.add(buttonSearch);

        JScrollPane panelScrolable = new JScrollPane(this.panelResults);
        panelScrolable.setSize(new Dimension(600, 250));
        panelScrolable.setLocation(new Point(50, 80));
        panelScrolable.setBorder(BorderFactory.createBevelBorder(1));
        panelScrolable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelScrolable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelScrolable.repaint();
        panelScrolable.revalidate();
        
        
        this.panelSearch.add(panelScrolable);
        this.panelSearch.repaint();        
    }
    
/**
 * Создание главного меню
 */    
    private void setMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem closeMenu = new JMenuItem("Close");
        closeMenu.addActionListener(new ActionListener() 
        {           
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });
        
        fileMenu.add(closeMenu);
        
        JMenu settingsMenu = new JMenu("Settings");
        ActionListener al = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettins();
            }
        };
        settingsMenu.addActionListener(al);
        
        JMenuBar mb = new JMenuBar();
        mb.add(fileMenu);
        mb.add(settingsMenu);
        
        this.setJMenuBar(mb);    
    }
    
    private void showSettins()
    {
        JFrame settForm = new JFrame("Settings");
        settForm.setSize(400, 200);
        settForm.setVisible(true);
    }
}
