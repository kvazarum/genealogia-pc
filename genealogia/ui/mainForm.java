/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genealogia.ui;

import genealogia.Data;
import genealogia.Family;
import genealogia.History;
import genealogia.Relative;
import genealogia.enums.Direction;
import genealogia.enums.Gender;
import java.awt.BorderLayout;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
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
import javax.swing.JToolBar;
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
    JPanel panelFamilies = new JPanel(null);
    
// Панель для личных данных
    JPanel panelPersonal = new JPanel(null);    

//  Панель поиска
    JPanel panelSearch = new JPanel(null);
    
    JPanel panelResults = new JPanel(null);
    
    JPanel panelFather = new JPanel(null);  
    JPanel panelMother = new JPanel(null);
    
    JPanel panelParents = new JPanel(null);
    
    JMenu historyMenu = new JMenu("History");
    
    Dimension avatarSize = new Dimension(30, 30);
    
    public History history = new History();

/**
 * Кнопка "Назад" в истории просмотров
 */    
    private JButton buttonBack = new JButton();
    
/**
 * Кнопка "Вперёд" в истории просмотров
 */    
    private JButton buttonForward = new JButton();        
    
    private final static String BACK_ICON_NAME = "back.png";
    
    
/**
 * Установка id человека, данные которого отображает форма
 * @param id - идентификатор человека
 */    
    public void setHuman(String id){
//        Thread dr = new DataReceiver(id, this);
//        dr.start();
        Relative hmn = new Relative(id);      
        this.setHuman(hmn);
    }
    
    public Relative getRelative()
    {
        return this.human;
    }
    
/**
 * 
 * @param hmn 
 */    
    public void setHuman(Relative hmn)
    {
        clearFields();        
        this.human = hmn;
        setData();
    }

    public Relative getHuman() {
        return this.human;
    } 
    
    public void setHistoryButtons()
    {
        if (this.history.getSize() > 1)
        {
            if (this.history.getPosition() == 0)
             {
                setButtonBackEnable(false);
                this.buttonBack.setToolTipText("");
                setButtonForwardEnable(true);
             }
             if (this.history.getPosition() > 0 && this.history.getPosition() < this.history.getSize() - 1)
             {
                 setButtonBackEnable(true);
                 setButtonForwardEnable(true);
                 this.buttonBack.setToolTipText(history.get(history.getPosition() - 1).getFullName());
                 this.buttonForward.setToolTipText(history.get(history.getPosition() + 1).getFullName());
             } 

             if (this.history.getPosition() == this.history.getSize() - 1)
             {
                 setButtonForwardEnable(false);
                 setButtonBackEnable(true);
                 this.buttonForward.setToolTipText("");
                 this.buttonBack.setToolTipText(history.get(history.getPosition() - 1).getFullName());
             }
        }
        this.setHistoryMenu();
    }
    
    private void moveHistory(Direction direction)
    {
        int currentPosition = history.getPosition();
        Relative human = null;        
        if(direction == Direction.Back && currentPosition > 0)
        {
            human = history.get(currentPosition - 1);  
            
            if (history.getPosition() != 0)
            {
                Relative prev = history.get(history.getPosition() - 1);
            }
        }
        else
        {
            human = history.get(currentPosition + 1);           
            if (history.getPosition() >= history.getSize() - 1 || currentPosition == history.getMax())
            {
                this.buttonForward.setEnabled(false);
            }
        }
        
        setHuman(human);
        history.changePosition(direction);        
        setHistoryButtons();        
    }
    
    private void moveHistory(int i)
    {
        int currentPosition = history.getPosition();
        if (i >= 0 && i<= this.history.getSize() - 1)
        {          
            clearFields();
            setHuman(this.history.get(i));
            this.history.changePosition();
            setData();
            this.setHistoryButtons();   
            setHistoryButtons();            
        }
    }    
    
    
    protected Image createImageIcon(String path) {
        Image img = null;
        try
        {
            img = ImageIO.read(getClass().getResource(path));
        }
        catch(IOException | IllegalArgumentException ex)
        {
            System.out.println("Error in function setToolBar()");
        }        
        return img;
    }
    
    private void setToolBar()
    {
        JToolBar jtb = new JToolBar("my toolbar");
        jtb.setFloatable(true);
        jtb.setPreferredSize(new Dimension(450, 20));
        this.getContentPane().add(jtb, BorderLayout.NORTH);

        //buttonBack.setIcon(new ImageIcon(BACK_ICON_NAME, "Назад"));
        
        buttonBack.setText("<<");
        buttonBack.setPreferredSize(new Dimension(100,20));
        buttonBack.setSize(100,20);
        buttonBack.setVerticalTextPosition(AbstractButton.CENTER);
        buttonBack.setHorizontalTextPosition(AbstractButton.LEADING);
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHistory(Direction.Back);
            }
        });
        
        buttonForward.setText(">>");
        buttonForward.setPreferredSize(new Dimension(100,20));
        buttonForward.setSize(100,20);
        buttonForward.setVerticalTextPosition(AbstractButton.CENTER);
        buttonForward.setHorizontalTextPosition(AbstractButton.LEADING);
        buttonForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHistory(Direction.Forward);
            }
        });        
        buttonBack.setEnabled(false);
        buttonForward.setEnabled(false);
        jtb.add(this.buttonBack);        
        jtb.add(this.buttonForward);
        
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
        
//  Тулбар
        this.setToolBar();

//  Панель поиска        
        this.setSearchPanel();        

// Панель для данных родителей
        this.setParentPanel();

//  Панель персональных данных        
        this.setPersonalPanel();
        
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("PrivateData"), panelPersonal);
        tabPane.addTab(java.util.ResourceBundle.getBundle("genealogia/Bundle").getString("Parents"), panelParents);
        tabPane.addTab("Семьи и дети", this.panelFamilies);
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
    private void onClick(Component component)
    {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        
        String _componentName = component.getName();
        if (!_componentName.equals("-1"))
        {          
            clearFields();
            setHuman(_componentName);
            this.history.removeHighPosition();
            this.history.addToHistoryList(this.getHuman());
            this.history.changePosition();
            setData();
            this.setHistoryButtons();
        }

        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
    }
    
/**
 * Executes on close of application,
 * saves history data to the XML-file
 */    
    private void onClose()
    {
        history.saveToXML();
        history.saveToFile();
        System.exit(0);
    }
    
/**
 * 
 * @return MouseListener
 */    
    public MouseListener getMouseListener()
    {
        MouseListener _ml = new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e) {
                //Получаем кол-во щелчков
                int click = e.getClickCount();
                
                if (click == 1)
                {
                    onClick(e.getComponent());
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
        return _ml;
    }
    
    private void clearFields()
    {
        clearFamilyTab();
        clearParentsTab();
    }
    
    private void clearFamilyTab()
    {
        this.panelFamilies.removeAll();
    }
    
    private void clearSearchTab()
    {
        this.panelResults.removeAll();
    }
    
    private void clearParentsTab()
    {
        String _empty = "empty.jpg";
        ImageIcon icon = new ImageIcon(_empty);
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
        
        Relative _parent = new Relative();
        
        if (!this.human.getFather().equals("-1"))
        {
            _parent.setRelative(human.getFather());
            fatherName = _parent.getFullName();
            this.fieldFather.setToolTipText(_parent.getDescription());
            fatherID = human.getFather();
        }

        if (!this.human.getMother().equals("-1"))
        {
            _parent.setRelative(human.getMother());
            motherName = _parent.getFullName();
            this.fieldMother.setToolTipText(_parent.getDescription());
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
            _parent.setRelative(this.human.getFather());
            if (_parent.getAvatar().length() > 0)
            {
                this.fatherAvatar.setAvatar(_parent.getPathToAvatar());
            }
            else
            {
                this.fatherAvatar.setVisible(false);
            }            
            fieldFatherBDate.setText(Relative.displayDate(_parent.getBDate()));
        } 
        else
        {
            fieldFatherBDate.setText("Нет данных");
            
        }
        
//  Mother's avatar        
        if (!this.human.getMother().equals("-1"))
        {
            _parent.setRelative(this.human.getMother());
            if (_parent.getAvatar().length() > 0)
            {
                this.motherAvatar.setAvatar(_parent.getPathToAvatar());
            }
            else
            {
                this.motherAvatar.setVisible(false);
            }
            
            fieldMotherBDate.setText(Relative.displayDate(_parent.getBDate()));
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
        ArrayList<String> childrenFromFamilies = new ArrayList<>();
        
        int lastY = 0;  //  Последнее значение Y панели семьи
        int lastHeight = 0; // Последнее значение высоты панели семьи        
        int PANEL_WIDTH = 500; //   ширина панели
        int PANEL_HEIGHT = 70;  //  начальная высота панели
        
        if (this.human.getFamsCount() > 0) // если имеются семьи
        {

            ArrayList<String> listF = this.human.getFamilies();
            

            for (int i=0; i < this.human.getFamsCount(); i++)
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
                familyPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT + fam.getCountChildren()*30);

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
//                        setHumanRow(familyPanel, new Point(x, y + 50), fam.getChild(k));
//                        y += height + 17;
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

                        Relative _child = new Relative();
                        _child.setRelative(fam.getChild(k));

                        fieldChild.setText(_child.getFullName());
                        familyPanel.add(fieldChild);

                        fieldBDate.setText(Relative.displayDate(_child.getBDate()));
                        fieldBDate.setLocation(x + 300, y);
                        familyPanel.add(fieldBDate);

                        AvatarLabel avatar = new AvatarLabel();
                        avatar.setSize(avatarSize);
                        avatar.setLocation(x + 430, y);
                        if (avatar.setAvatar(_child.getPathToAvatar()))
                        {
                            familyPanel.add(avatar);
                        }
                        childrenFromFamilies.add(fam.getChild(k));
                    }
                }

                this.panelFamilies.add(familyPanel); 
            }                 
        }
        ArrayList<String> children = this.human.getChildrenWhithoutFamily();
        if (!children.isEmpty())
        {
            JPanel childrenPanel = new JPanel(null);
            Border border = BorderFactory.createTitledBorder("Дети");
            childrenPanel.setBorder(border);
            childrenPanel.setSize(PANEL_WIDTH, 20 + children.size()*35);
            int x = 10;
            int y = lastY + lastHeight + 10;
            childrenPanel.setLocation(x, y);
            y += 10;
            int height = 15;    //  высота записи о ребёнке
            for (String child : children) {                
                setHumanRow(childrenPanel, new Point(x, y), child);
                y = y + height + 17;
            }
            
            this.panelFamilies.add(childrenPanel);
        }
        this.panelFamilies.repaint();    
    }
    
    private void setHumanRow(JPanel _panel, Point _point, String humanID)
    {
        int height = 15;
        Relative human = new Relative(humanID);
        //ФИО человека
        JLabel fieldHuman = new JLabel();
        fieldHuman.setSize(300, height);
        fieldHuman.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        fieldHuman.setLocation(_point);
        fieldHuman.setText(human.getFullName());
        fieldHuman.setName(human.getID()); 
        fieldHuman.setFont(plainFont);
        MouseListener ml = this.getMouseListener();
        fieldHuman.addMouseListener(ml);        
        _panel.add(fieldHuman);
        //Дата рождения
        JLabel fieldBDate = new JLabel();                        
        fieldBDate.setSize(120, height);
        fieldBDate.setFont(plainFont);        
        fieldBDate.setLocation(_point.x + fieldHuman.getWidth(), _point.y);
        fieldBDate.setText(Relative.displayDate(human.getBDate())); 
        _panel.add(fieldBDate);
        //Аватар
        AvatarLabel avatar = new AvatarLabel();
        avatar.setSize(avatarSize);
        avatar.setLocation(_point.x + 430, _point.y);
        if (avatar.setAvatar(human.getPathToAvatar()))
        {
            _panel.add(avatar);
        }        
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
        final JTextField fieldSearchText = new JTextField("Ключевые слова", 20);
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
                                    onClick(e.getComponent());
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
                onClose();             
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
        mb.add(historyMenu);
        
        this.setJMenuBar(mb);    
    }
    
/**
 * 
 */    
    private void showSettins()
    {
        JFrame settForm = new JFrame("Settings");
        settForm.setSize(400, 200);
        settForm.setVisible(true);
    }
    
/**
 * Added function onClose()
 */    
    public void init()
    {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
        this.history = new History();
        this.setHistoryMenu();
    }
    
/**
 * Sets enable of the button
 * @param mode 
 */    
    public void setButtonBackEnable(boolean mode)
    {
        this.buttonBack.setEnabled(mode);
    }
    
/**
 * Sets enable of the button
 * @param mode 
 */    
    public void setButtonForwardEnable(boolean mode)
    {
        this.buttonForward.setEnabled(mode);
    }
    
    /**
     * Sets the history menu based on a history state
     */
    private void setHistoryMenu()
    {
        if (this.historyMenu.getItemCount() > 0)
        {
            this.historyMenu.removeAll();
        }
        for (int i = 0; i < this.history.getSize(); i++)
        {
            JMenuItem element = new JMenuItem(this.history.get(i).getFullName());
            if (i == this.history.getPosition())
            {
                element.setFont(boldFont);
            }
            else
            {
                element.setFont(plainFont);
            }
            final int index = i;
            ActionListener al = new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveHistory(index);
                }
            };
            element.addActionListener(al);
            this.historyMenu.add(element);
        }
    }
}
