
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
import java.awt.HeadlessException;
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
import static java.util.ResourceBundle.getBundle;
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
import javax.swing.border.EtchedBorder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author kvazar
 */
public class MainForm extends JFrame
{
    Relative human;    
    JLabel fieldSurname;
    JLabel fieldName;
    JLabel fieldMName; 
    JLabel labelFullName;
    
    JLabel fieldMother;
    JLabel fieldFather;
    
    JLabel labelAvatar;
    
    JLabel fieldDescription;
    JScrollPane panelDescription;
    
    JLabel fieldBirthDate; 
    JLabel fieldDeathDate;
    
    JLabel fieldBirthPlace;
    
    AvatarLabel fatherAvatar;
    AvatarLabel motherAvatar;
    
    labelID fieldFatherBDate;
    labelID fieldMotherBDate;
    
//  Label для вывода сообщений о результатах поиска    
    JLabel labelSearchResult;
    
    public Font boldFont;
    public Font plainFont;

//  Панель с данными семей    
    JPanel panelFamilies;
    
// Панель для личных данных
    JPanel panelPersonal;    

//  Панель поиска
    JPanel panelSearch;

//  Панель результатов поиска
    JPanel panelResults;
    
//  Панель отца    
    JPanel panelFather;  
    
//  Панель матери    
    JPanel panelMother;

//  Панель родителей    
    JPanel panelParents;
    
//  Панель cписка родов
    JPanel panelClans;
    
    //JPanel panelVisualLinks;
    VisualPanel panelVisualLinks;
    
//  Панель закладок    
    JTabbedPane tabPane;    
    
    JMenu historyMenu;
    
    Dimension avatarSize;
    
    public History history;
    
/**
 * Кнопка "Назад" в истории просмотров
 */    
    final private JButton buttonBack;
    
/**
 * Кнопка "Вперёд" в истории просмотров
 */    
    final private JButton buttonForward;
    
/**
 * Иконка для кнопки "Назад"
 */    
    private final static String BACK_ICON_NAME = "back.png";
    
/**
 * Текстовое поле для ввода поисковых запросов
 */    
    final JTextField fieldSearchText;    

    public MainForm() throws HeadlessException {
        human = null;
        fieldSurname = new JLabel();
        fieldName = new JLabel();
        fieldMName = new JLabel();
        labelFullName = new JLabel();
        history = new History();
        avatarSize = new Dimension(30, 30);
        historyMenu = new JMenu(getBundle("genealogia/Bundle").getString("History"));
        tabPane = new JTabbedPane();
        fieldMother = new JLabel();
        fieldFather = new JLabel();

        labelAvatar = new JLabel("Нет изображения");

        fieldDescription = new JLabel();  

        fieldBirthDate = new JLabel(); 
        fieldDeathDate = new JLabel();

        fieldBirthPlace = new JLabel();

        fatherAvatar = new AvatarLabel();
        motherAvatar = new AvatarLabel();

        fieldFatherBDate = new labelID();
        fieldMotherBDate = new labelID();
        labelSearchResult = new JLabel();
        boldFont = new Font(fieldFatherBDate.getFont().getName(), Font.BOLD, fieldFatherBDate.getFont().getSize());
        plainFont  = new Font(fieldFatherBDate.getFont().getName(), Font.PLAIN, fieldFatherBDate.getFont().getSize());
        panelFamilies = new JPanel(null);
        panelPersonal = new JPanel(null);
        panelSearch = new JPanel(null);
        panelResults = new JPanel(null);    
        panelFather = new JPanel(null);   
        panelMother = new JPanel(null); 
        panelParents = new JPanel(null);
        panelClans = new JPanel(null);
        panelVisualLinks = new VisualPanel(null);
        buttonBack = new JButton();
        buttonForward = new JButton();
        fieldSearchText = new JTextField("", 20);
        panelDescription = new JScrollPane();
    }    
    
/**
 * Установка id человека, данные которого отображает форма
 * @param id - идентификатор человека
 */    
    public void setHuman(String id){
        Relative hmn = new Relative(id);
        this.setData(hmn);
    }
    
    public void setHuman(Relative human){   
        this.human = human;
    }    
    
    public Relative getRelative()
    {
        return this.human;
    }
    
/**
 * 
 * @param hmn 
 */    
    public void setData(Relative hmn)
    {
        clearFields();
        this.setHuman(hmn);
        MainForm.this.setData();
    }

    public Relative getHuman() {
        return this.human;
    } 
    
/**
 * Sets history buttons
 */    
    public void setHistoryButtons()
    {
        String imageName = "images/back.png";
        URL imgURL = MainForm.class.getResource(imageName);
        ImageIcon ii = new ImageIcon(imgURL);
        
        this.buttonBack.setIcon(new ImageIcon(ii.getImage().getScaledInstance(-25, 25, ii.getImage().SCALE_SMOOTH)));
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
        Relative humanInHistory;        
        if(direction == Direction.Back && currentPosition > 0)
        {
            humanInHistory = history.get(currentPosition - 1);
        }
        else
        {
            humanInHistory = history.get(currentPosition + 1);
        }
        
        setData(humanInHistory);
        history.changePosition(direction);        
        setHistoryButtons();        
    }

/**
 * Moves to humanInHistory, defined by index <tt>i</tt>
 * @param index index in the history list
 */    
    private void moveHistory(int index)
    {
        if (index >= 0 && index <= this.history.getSize() - 1)
        {          
            clearFields();
            setData(this.history.get(index));
            this.history.changePosition(index);
            MainForm.this.setData();
            this.setHistoryButtons();           
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
        final int TOOLBAR_WIDTH = 450;
        final int TOOLBAR_HEIGHT = 30;
        
        final int BUTTON_WIDTH = 30;
        final int BUTTON_HEIGHT = 30;
        
        JToolBar jtb = new JToolBar("my toolbar");
        jtb.setFloatable(true);
        jtb.setPreferredSize(new Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT));
        this.getContentPane().add(jtb, BorderLayout.NORTH);
        
        buttonBack.setText("<<");
        //buttonBack.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        buttonBack.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonBack.setVerticalTextPosition(AbstractButton.CENTER);
        buttonBack.setHorizontalTextPosition(AbstractButton.LEADING);
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHistory(Direction.Back);
            }
        });
        buttonBack.setEnabled(false);        
        
        buttonForward.setText(">>");
        buttonForward.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        buttonForward.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonForward.setVerticalTextPosition(AbstractButton.CENTER);
        buttonForward.setHorizontalTextPosition(AbstractButton.LEADING);
        buttonForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveHistory(Direction.Forward);
            }
        });        
        buttonForward.setEnabled(false);

//  Adding buttons to panel        
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

//  Панель родов человека        
        setClansPanel();
        
        //this.panelVisualLinks.setSize(600, 450);
        
        tabPane.addTab(getBundle("genealogia/Bundle").getString("PrivateData"), panelPersonal);
        tabPane.addTab(getBundle("genealogia/Bundle").getString("Parents"), panelParents);
        tabPane.addTab("Семьи и дети", this.panelFamilies);
        tabPane.addTab(getBundle("genealogia/Bundle").getString("VisualLinks"), this.panelVisualLinks);
        tabPane.addTab(getBundle("genealogia/Bundle").getString("Search"), this.panelSearch);
        this.add(tabPane);
        
        this.pack();       
        this.setSize(1000, 450);
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
        
        //  Список родов        
        setClans();
        
        setParentsData();

        //  Панель визуальных связей        
        setVisualPanel();
        
//  Семьи
        this.setFamilyData();        
        
    }
    
    
    private void setClans()
    {
        final int LABEL_WIDTH = 90;     // width of label
        final int LABEL_HEIGHT = 20;    // height of label
        
        int label_x = 10;   // start value of x coordinate
        int label_y = 5;    // start value of y coordinate
        
        ArrayList<String> _clans = this.human.getClans();   // list of clans
        for (String clan : _clans) {
            if (this.panelClans.getWidth() < label_x + LABEL_WIDTH)
            {
                if (this.panelClans.getHeight() < (label_y + 2*LABEL_HEIGHT + 2))
                {
                    this.panelClans.setSize(this.panelClans.getWidth(), this.panelClans.getHeight() + 20);
                }
                label_y += LABEL_HEIGHT + 5;
                label_x = 10;
            }
            JLabel _labelClan = new JLabel(clan);
            _labelClan.setSize(LABEL_WIDTH, LABEL_HEIGHT);
            _labelClan.setLocation(label_x, label_y);
            _labelClan.setFont(plainFont);
            _labelClan.addMouseListener(getMouseListenerOnClan());
            _labelClan.setName(clan);
            this.panelClans.add(_labelClan);
            label_x += LABEL_WIDTH + 5;
        } 
        this.panelClans.repaint();
    }
   
/**
 * Двойной щелчок на родителе для перехода на этого человека
 * @param component
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
            this.history.removeAbovePosition();
            this.history.addToHistoryList(this.getHuman());
            this.history.setLastPosition();
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
 * Sets MouseListener for humanInHistory's label 
 * @return MouseListener
 */    
    public MouseListener getMouseListenerOnHuman()
    {
        MouseListener _ml = new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e) {
                //Получаем кол-во щелчков
                int click = e.getClickCount();
                
                if (click == 2)
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
                setMouseOn(e);
//                if (e.getSource().getClass().toString().indexOf("JPanel") != -1)
//                {
//                    String currentID = e.getComponent().getName();
//                    Component[] components = panelVisualLinks.getComponents();
//                    for(int i = 0; i < components.length; i++)
//                    {
//                        String id = ((JPanel)components[i]).getName();
//                        if (id != null)
//                        {
//                            Relative human = new Relative(id);
//                            if (human.getFather().equals(currentID) || human.getMother().equals(currentID))
//                            {
//                                components[i].setSize(components[i].getWidth() + 2, components[i].getHeight() + 2);
//                            }
//                        }
//                    }
//                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setMouseOff(e);
            }
        };
        return _ml;
    }
    
/**
 * Sets MouseListener for clan's label 
 * @return MouseListener
 */    
    public MouseListener getMouseListenerOnClan()
    {
        MouseListener _ml = new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e) {
                //Получаем кол-во щелчков
                int click = e.getClickCount();
                
                if (click == 2)
                {
                    onClanClick(e.getComponent().getName());
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
                setMouseOn(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setMouseOff(e);
            }
        };
        return _ml;
    }
    
/**
 * Sets cursor and font for component
 * @param e MouseEvent
 */    
    private void setMouseOn(MouseEvent e)
    {
        e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
/**
 * Sets cursor and font for component
 * @param e MouseEvent
 */    
    private void setMouseOff(MouseEvent e)
    {
        e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }  
    
    private void onClanClick(String clan)
    {
        this.fieldSearchText.setText(clan);
        this.getSearchResult(clan);
        
        tabPane.setSelectedComponent(this.panelSearch);
    }
    
/**
 * Clears all data fields
 */    
    private void clearFields()
    {
        this.panelClans.removeAll();
        clearFamilyTab();
        clearParentsTab();
    }
    
/**
 * Clears family data
 */    
    private void clearFamilyTab()
    {
        this.panelFamilies.removeAll();
    }
    
/**
 * Clears search data
 */    
    private void clearSearchTab()
    {
        this.panelResults.removeAll();
    }
    
/**
 * Clears parents data
 */    
    private void clearParentsTab()
    {
        String _empty = "empty.jpg";
        ImageIcon icon = new ImageIcon(_empty);
        icon.getImage().flush();
        this.motherAvatar.setIcon(icon);
        this.fatherAvatar.setIcon(icon);
    }
    
    private void setClansPanel()
    {
        this.panelClans.setLocation(20, fieldDescription.getY() + fieldDescription.getHeight() + 5);
        this.panelClans.setSize(500, 60);
        this.panelClans.setFont(plainFont);
        this.panelClans.setBorder(BorderFactory.createEtchedBorder());
    }
    
/**
 * Sets tab with visual links with children, spouses, parents
 */    
    private void setVisualPanel()
    {        
        panelVisualLinks.setHuman(this.getHuman());
        panelVisualLinks.setSize(new Dimension(800, 500));
        panelVisualLinks.removeAll();
        int x = VisualPanel.PANEL_WIDTH/2 - VisualPanel.COMPONENT_WIDTH/2;
        int y = VisualPanel.PANEL_HEIGHT/2 - VisualPanel.COMPONENT_HEIGHT;
        //Draw main human
        JPanel _panel = getVisualPanel(this.human, Color.RED);
        _panel.setLocation(x, y);
        panelVisualLinks.add(_panel);
        
        if (!this.human.getFather().equals("-1") || !this.human.getMother().equals("-1"))
        {
            drawParents(x, y);      //Draw parents
        }
        
        if (this.human.getFamsCount() > 0 || !this.human.getChildrenWhithoutFamily().isEmpty())
        {
            drawFamilies(x, y); //Draw families
        }
        panelVisualLinks.repaint();
    }
    
/**
 * Draws spouses and children from families
 * @param x coordinate of main rectangle
 * @param y coordinate of main rectangle
 */    
    private void drawFamilies(int x, int y)
    {
        int count = this.human.getFamsCount();
        int left_x = x;     //last x for left spouse
        int right_x = x;    //last x for right spouse
        
        ArrayList<String> fams = this.human.getFamilies();
        
        int last_x = 30;
        int last_y = y + VisualPanel.COMPONENT_HEIGHT + VisualPanel.SPACE_BETWEEN_RAW;
        Point lastPoint = new Point(last_x, last_y);
        
        for (int i = 0; i < count; i++)
        {
            Family family = new Family();
            family.setFamily(fams.get(i));
            Relative spouse = new Relative(); //spouse
            
            if (this.human.getGender() == Gender.Male)
            { 
                spouse.setRelative(family.getMother());
            }
            else
            {
                spouse.setRelative(family.getFather());
            }
            
            Color _tempColor;
            if (i < VisualPanel.colors.length)
            {
                _tempColor = VisualPanel.colors[i];
            }
            else
            {
                _tempColor = Color.BLACK;
            }
            
            JPanel panel = getVisualPanel(spouse, _tempColor);
            
            if (i == 0 || (i%2 == 0))
            {
                left_x = left_x - panel.getWidth() - VisualPanel.SPACE_BETWEEN_CELLS;
                panel.setLocation(left_x, y);
            }
            else
            {
                right_x = right_x + panel.getWidth() + panel.getWidth()*(i-1) + VisualPanel.SPACE_BETWEEN_CELLS;
                panel.setLocation(right_x, y);
            }
            
            if (family.getCountChildren() > 0)
            {
                lastPoint = drawChildrenFromFamily(family, lastPoint, VisualPanel.colors[i]);
            }
            panelVisualLinks.add(panel);
            
        }
        if (!this.human.getChildrenWhithoutFamily().isEmpty())
        {
            drawChildrenWhithoutFamily(lastPoint);
        }        
    }
    
    private void drawChildrenWhithoutFamily(Point point )    
    {
        int last_x = (int)point.getX();
        int last_y = (int)point.getY();
        
        //int childrenLevel = 1;
        final int increment = VisualPanel.COMPONENT_HEIGHT + VisualPanel.SPACE_BETWEEN_RAW;        
        
        ArrayList<String> children = this.human.getChildrenWhithoutFamily();
        for (String childID: children)
        {
            Relative child = new Relative();
            child.setRelative(childID);
            JPanel panel = getVisualPanel(child, Color.BLUE);
            panel.setLocation(last_x, last_y);
            this.panelVisualLinks.add(panel);
            int nextPoint = last_x + VisualPanel.COMPONENT_WIDTH + VisualPanel.SPACE_BETWEEN_CELLS + VisualPanel.COMPONENT_WIDTH;
            if (nextPoint < VisualPanel.PANEL_WIDTH)
            {
                last_x += VisualPanel.COMPONENT_WIDTH + VisualPanel.SPACE_BETWEEN_CELLS;
            }
            else
            {
               last_x = (int)point.getX();
               last_y += increment;
               //childrenLevel++;
            }
        }  
    }
    
    private Point drawChildrenFromFamily(Family family, Point point, Color color)
    {
        int last_x = (int)point.getX();
        int last_y = (int)point.getY();
        Point lastpoint = point;
        
        for (String childID: family.getChildren())
        {
            Relative child = new Relative();
            child.setRelative(childID);
            JPanel panel = getVisualPanel(child, color);
            panel.setLocation(last_x, last_y);
            this.panelVisualLinks.add(panel);
            int nextPoint = last_x + VisualPanel.COMPONENT_WIDTH + VisualPanel.SPACE_BETWEEN_CELLS + VisualPanel.COMPONENT_WIDTH;
            if (nextPoint < VisualPanel.PANEL_WIDTH)
            {
                last_x += VisualPanel.COMPONENT_WIDTH + VisualPanel.SPACE_BETWEEN_CELLS;
            }
            else
            {
               last_x = (int)point.getX();
               last_y += VisualPanel.COMPONENT_HEIGHT + VisualPanel.SPACE_BETWEEN_CELLS;
            }
        }
        lastpoint = new Point(last_x, last_y);
        return lastpoint;
    }
    
/**
 * Draws parents
 * @param x coordinate of main rectangle
 * @param y coordinate of main rectangle
 */    
    private void drawParents(int x, int y)
    {
        Relative _relative = new Relative();
        JPanel panel;
        int x_parent;
        int y_parent;

        if (!this.human.getFather().equals("-1"))
        {
            _relative.setRelative(human.getFather());
            panel = getVisualPanel(_relative, Color.CYAN);
            x_parent = x - VisualPanel.COMPONENT_WIDTH/3 - 30;
            y_parent = y - VisualPanel.COMPONENT_HEIGHT - VisualPanel.SPACE_BETWEEN_RAW;
            addParentPanel(panel, x_parent, y_parent);
        }
        if (!this.human.getMother().equals("-1"))
        {
            _relative.setRelative(human.getMother());
            panel = getVisualPanel(_relative, Color.CYAN);
            x_parent = x + VisualPanel.COMPONENT_WIDTH/3 + 30;
            y_parent = y - VisualPanel.COMPONENT_HEIGHT - VisualPanel.SPACE_BETWEEN_RAW;
            addParentPanel(panel, x_parent, y_parent);
        }
    }
    
    
    private void addParentPanel(JPanel panel, int x, int y)
    {
        panel.setLocation(x, y);
        panelVisualLinks.add(panel);        
    }
    
/**
 * Gets panel with data of human
 * @param human
 * @return JPanel
 */    
    private JPanel getVisualPanel(Relative human, Color borderColor)
    {
        int PANEL_WIDTH = 120;
        int PANEL_HEIGHT = 70;
        int BORDER_WIDTH = 2;
        int LABEL_HEIGHT = 10;
        
        JPanel _panel = new JPanel();
        int width;
        if (this.human == human)
        {
            width = 4;
        }
        else
        {
            width = 2;
        }
        _panel.setBorder(BorderFactory.createLineBorder(borderColor, width));
        _panel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        _panel.setBackground(Color.LIGHT_GRAY);
        _panel.setOpaque(true);
        
        JLabel _surname = new JLabel();
        String temp = human.getSurname();
        if (human.getSurnames().length() > 0)
        {
            temp += "(" + human.getSurnames() + ")";
        }
        _surname.setText(temp);
        _surname.setLocation(0, 0);
        _surname.setFont(boldFont.deriveFont((float) 8));
        _surname.setPreferredSize(new Dimension(PANEL_WIDTH - 2*BORDER_WIDTH, LABEL_HEIGHT));
        _surname.setVerticalAlignment(SwingConstants.TOP);
        _surname.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel _name = new JLabel();
        _name.setText(human.getName() + " " + human.getMiddleName());
        _name.setFont(boldFont.deriveFont((float) 8));
        _name.setPreferredSize(new Dimension(PANEL_WIDTH - 2*BORDER_WIDTH, LABEL_HEIGHT));
        _name.setLocation(0, LABEL_HEIGHT);
        _name.setVerticalAlignment(SwingConstants.TOP);
        _name.setHorizontalAlignment(0);
                
        _panel.add(_surname);
        _panel.add(_name);
        if (human.getDescription().length() > 0)
        {
            _panel.setToolTipText("<html>" + human.getDescription());
        }
        
        MouseListener ml = getMouseListenerOnHuman();
        if (human != this.human)
        {
            _panel.addMouseListener(ml);
            _panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            _panel.setName(human.getID());
        }        
        
        AvatarLabel avatar = new AvatarLabel();
        avatar.setSize(avatarSize);
        avatar.setLocation((int) (PANEL_WIDTH/2 - avatarSize.getWidth()/2), PANEL_HEIGHT - LABEL_HEIGHT*2);
        if (avatar.setAvatar(human.getPathToAvatar()))
        {
            _panel.add(avatar);
        }        
        
        return _panel;
    }
    
/**
 * Sets компонентов панели персональных данных
 */
    private void setPersonalPanel()
    {
        Font titleFont = new Font(boldFont.getName(), Font.BOLD, 16);
        
        JLabel labelSurname = new JLabel(getBundle("genealogia/Bundle").getString("Surname"));
        JLabel labelName = new JLabel(getBundle("genealogia/Bundle").getString("Name"));        
        JLabel labelMiddleName = new JLabel(getBundle("genealogia/Bundle").getString("SecondName"));        
        
        this.fieldSurname.setFont(plainFont);
        
        final int LABEL_X = 170;
        final int FIELD_X = LABEL_X + 110;
        final int LABEL_HEIGHT = 20;
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
        JLabel labelBDate = new JLabel(getBundle("genealogia/Bundle").getString("bdate"));
        labelBDate.setSize(100, LABEL_HEIGHT);
        labelBDate.setLocation(LABEL_X, labelMiddleName.getY() + labelMiddleName.getHeight() +5);
        this.panelPersonal.add(labelBDate);
        
        this.fieldBirthDate.setSize(120, LABEL_HEIGHT);
        this.fieldBirthDate.setLocation(FIELD_X, labelBDate.getY());
        this.fieldBirthDate.setFont(plainFont);
        this.panelPersonal.add(fieldBirthDate);  
        
// Место рождения
        JLabel labelBPlace = new JLabel(getBundle("genealogia/Bundle").getString("bplace"));
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
        labelDescription.setText(getBundle("genealogia/Bundle").getString("annotation"));
        labelDescription.setSize(150, LABEL_HEIGHT);
        labelDescription.setLocation(20, 200);
        
        this.panelPersonal.add(labelDescription);

        this.fieldDescription.setSize(500, 120);
        this.fieldDescription.setLocation(20, labelDescription.getY() + 25);
        this.fieldDescription.setFont(plainFont);
        this.fieldDescription.setVerticalAlignment(SwingConstants.TOP);
        this.fieldDescription.setBorder(BorderFactory.createEtchedBorder());
        this.panelPersonal.add(fieldDescription);
        //this.panelDescription.add(fieldDescription);
        
//        this.panelDescription.setSize(500, 100);
//        this.panelDescription.setLocation(20, labelDescription.getY() + 25);
//        this.panelDescription.setBorder(BorderFactory.createEtchedBorder());
//        this.panelDescription.revalidate();
//        this.panelPersonal.add(panelDescription);
        
        
//  List of clans
        this.panelClans.setLocation(20, fieldDescription.getY() + fieldDescription.getHeight() + 5);
        //this.panelClans.setLocation(20, panelDescription.getY() + panelDescription.getHeight() + 5);
        this.panelClans.setSize(500, 60);
        this.panelClans.setFont(plainFont);
        this.panelClans.setBorder(BorderFactory.createEtchedBorder());
        this.panelPersonal.add(this.panelClans);
        
        this.panelPersonal.repaint();    // перерисовываем        
    
    }
    
/**
 * Установка компонентов панели персональных данных родителей
 */    
    private void setParentPanel()
    {
        panelParents.setSize(600, 400);
        panelParents.setLocation(0,0);  
//  Панель с данными отца        
        panelFather.setSize(550, 80);
        panelFather.setLocation(20, 5);
        panelFather.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

//  Панель с данными матери        
        panelMother.setSize(550, panelFather.getHeight());
        panelMother.setLocation(panelFather.getX(), panelFather.getY() + panelFather.getHeight() + 5);
        panelMother.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
//  Добавляем панель с данными отца        
        panelParents.add(panelFather);

//  Добавляем панель с данными матери        
        panelParents.add(panelMother);  
        
        JLabel labelFather = new JLabel(getBundle("genealogia/Bundle").getString("father"));
        labelFather.setSize(40, 30);
        labelFather.setLocation(10, 0);
        
        JLabel labelMother = new JLabel(getBundle("genealogia/Bundle").getString("mother"));
        labelMother.setSize(labelFather.getWidth(), 30);
        labelMother.setLocation(labelFather.getX(), 0);
        
        MouseListener ml = this.getMouseListenerOnHuman();
        
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
            this.fieldFather.setToolTipText("<html>" + _parent.getDescription());
            fatherID = human.getFather();
        }

        if (!this.human.getMother().equals("-1"))
        {
            _parent.setRelative(human.getMother());
            motherName = _parent.getFullName();
            this.fieldMother.setToolTipText("<html>" + _parent.getDescription());
            motherID = human.getMother();            
        }
        
        this.fieldMother.setText(motherName);
        this.fieldFather.setText(fatherName);
        
        this.fieldFather.setName(fatherID);
        this.fieldMother.setName(motherID);
        
        if (this.human.getAvatar().length() > 1 )
        {
            int avatar_height = 150;
            int avatar_width = 150;
            try
            {
                URL url = new URL(this.human.getPathToAvatar());
                BufferedImage image = ImageIO.read(url);
                ImageIcon ii = new ImageIcon(image);
                float ratio = (float)ii.getIconHeight()/ii.getIconWidth();
                if (ratio != 1)
                {
                    avatar_width = (int)(avatar_width/ratio);
                }
                
                labelAvatar.setIcon(new ImageIcon(ii.getImage().getScaledInstance(avatar_width, avatar_height, Image.SCALE_DEFAULT)));
                labelAvatar.setText(""); 
                labelAvatar.setToolTipText(this.human.getFullName());
            }
            catch(IOException e)
            {

            }
        }
        else
        {
            labelAvatar.setIcon(null);
            labelAvatar.setText(getBundle("genealogia/Bundle").getString("noimage"));
            labelAvatar.setToolTipText("");
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
            fieldFatherBDate.setText(getBundle("genealogia/Bundle").getString("nodata"));
            
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
            fieldMotherBDate.setText(getBundle("genealogia/Bundle").getString("nodata"));
            fieldBirthPlace.setText(getBundle("genealogia/Bundle").getString("nodata"));        
        }    
    }
    
/**
* Sets data of families on the family panel
*/    
    private void setFamilyData()
    {
        ArrayList<String> childrenFromFamilies = new ArrayList<>();
        
        int lastY = 0;  //  Последнее значение Y панели семьи
        int lastHeight = 0; // Последнее значение высоты панели семьи        
        int PANEL_WIDTH = 500; //   ширина панели
        int PANEL_HEIGHT = 100;  //  начальная высота панели
        
        if (this.human.getFamsCount() > 0) // если имеются семьи
        {

            ArrayList<String> listF = this.human.getFamilies();
            

            for (int i=0; i < this.human.getFamsCount(); i++)
            {
                int height = 15;

                Family fam = new Family();
                fam.setFamily(listF.get(i));

                JPanel familyPanel = new JPanel(null);
                Border border = BorderFactory.createTitledBorder("Семья");
                familyPanel.setBorder(border);
                familyPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT + fam.getCountChildren()*35);

                int x = 10;
                int y = lastY + lastHeight + 5;
                lastHeight = familyPanel.getHeight();
                lastY = familyPanel.getY();

                familyPanel.setLocation(x, y);
                String spouseID;                                // id супруга
                if (this.human.getGender() == Gender.Male)
                {
                    spouseID = fam.getMother();
                }
                else
                {
                    spouseID = fam.getFather();
                }

                Relative spouse = new Relative(spouseID);       // spouse
                
                MouseListener ml = this.getMouseListenerOnHuman();
                
                JLabel _spouseTitle = new JLabel("Супруг(а):");
                _spouseTitle.setSize(100, height);
                _spouseTitle.setLocation(20, height);

                JPanel spousePanel = getHumanRow(spouse);
                spousePanel.setLocation(20, _spouseTitle.getY() + _spouseTitle.getHeight() + 5);
                spousePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
                familyPanel.add(_spouseTitle);
                familyPanel.add(spousePanel);
                
//  если в семье есть дети
                if (fam.getCountChildren() > 0)
                {
                    JLabel titleChildren = new JLabel(getBundle("genealogia/Bundle").getString("children") + ":");
                    titleChildren.setSize(40, 20);
                    titleChildren.setLocation(_spouseTitle.getX(), spousePanel.getY() + spousePanel.getHeight() + 10);
                    familyPanel.add(titleChildren);

                    for (int k=0; k < fam.getCountChildren(); k++)
                    {
                        Relative _child = new Relative(fam.getChild(k));
                        JPanel _panel = getHumanRow(_child);
                        x = 20;
                        y = titleChildren.getY() + titleChildren.getHeight() + k*31;                      
                        _panel.setLocation(x, y);
                        familyPanel.add(_panel);
                        
                        childrenFromFamilies.add(fam.getChild(k));
                    }
                }

                this.panelFamilies.add(familyPanel); 
            }                 
        }
        
        // Children without a parent
        ArrayList<String> children = this.human.getChildrenWhithoutFamily();
        if (!children.isEmpty())
        {
            JPanel childrenPanel = new JPanel(null);
            Border border = BorderFactory.createTitledBorder(getBundle("genealogia/Bundle").getString("children"));
            childrenPanel.setBorder(border);
            childrenPanel.setSize(PANEL_WIDTH, 25 + children.size()*35);
            int record_x = 10;
            int record_y = lastY + lastHeight + 10;
            childrenPanel.setLocation(record_x, record_y);
            record_y += 10;
            int height = 30;    //  высота записи о ребёнке
            for (String child : children) {
                Relative human = new Relative(child);
                JPanel _panel = getHumanRow(human);
                _panel.setLocation(record_x, record_y);
                childrenPanel.add(_panel);
                record_y += height + 5;
            }
            this.panelFamilies.add(childrenPanel);
        }
        this.panelFamilies.repaint();    
    }
    
/**
 * 
 * @param _panel
 * @param _point
 * @param humanID 
 */    
    private JPanel getHumanRow(Relative human)
    {
        int PANEL_HEIGHT = 30;
        JPanel _panel = new JPanel(null);
        _panel.setSize(450, PANEL_HEIGHT);
        
        //ФИО человека
        JLabel fieldHuman = new JLabel();
        fieldHuman.setSize(250, PANEL_HEIGHT);
        fieldHuman.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        fieldHuman.setLocation(0, 0);
        fieldHuman.setText(human.getFullName());
        fieldHuman.setName(human.getID()); 
        fieldHuman.setFont(plainFont);
        fieldHuman.setVerticalAlignment(SwingConstants.BOTTOM);
        fieldHuman.setHorizontalAlignment(SwingConstants.LEFT);
        fieldHuman.setToolTipText(human.getDescription());
        MouseListener ml = this.getMouseListenerOnHuman();
        fieldHuman.addMouseListener(ml);        
        _panel.add(fieldHuman);
        
        //Дата рождения
        JLabel fieldBDate = new JLabel();                        
        fieldBDate.setSize(120, PANEL_HEIGHT);
        fieldBDate.setFont(plainFont);        
        fieldBDate.setLocation(fieldHuman.getWidth() + 5, 0);
        fieldBDate.setText(Relative.displayDate(human.getBDate()));
        fieldBDate.setVerticalAlignment(SwingConstants.BOTTOM);
        _panel.add(fieldBDate);
        
        //Аватар
        AvatarLabel avatar = new AvatarLabel();
        avatar.setSize(avatarSize);
        avatar.setLocation(fieldBDate.getX() + fieldBDate.getWidth(), 0);
        avatar.setVerticalAlignment(SwingConstants.TOP);
        if (avatar.setAvatar(human.getPathToAvatar()))
        {
            _panel.add(avatar);
        }   
        return _panel;
    }
    
/**
 * Sets search panel components
 */    
    private void setSearchPanel()
    {
        this.panelSearch.setSize(600, 400);
        this.panelSearch.setLocation(0,0);
        
        JLabel labelSearch = new JLabel(getBundle("genealogia/Bundle").getString("searchString"));
        labelSearch.setSize(100, 20);
        labelSearch.setLocation(100,5);  
        this.panelResults.setLocation(new Point(0, 0));
        
        ActionListener al = new ActionListener() 
        {            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                getSearchResult(fieldSearchText.getText());
            }
        };
        
    //  Строка запроса
        fieldSearchText.setSize(400, 20);
        fieldSearchText.setLocation(100, 40);
        fieldSearchText.addActionListener(al);
        
    //Кнопка выполнения запроса
        JButton buttonSearch = new JButton(getBundle("genealogia/Bundle").getString("find"));
        buttonSearch.setSize(80, 20);
        buttonSearch.setLocation(520, 40);     
 
        buttonSearch.addActionListener(al);
        
        labelSearchResult.setSize(250, 20);
        labelSearchResult.setLocation(50, 75);
        labelSearchResult.setFont(plainFont);
        
        this.panelSearch.add(labelSearchResult);
        this.panelSearch.add(labelSearch);
        this.panelSearch.add(fieldSearchText);
        this.panelSearch.add(buttonSearch);

        JScrollPane panelScrolable = new JScrollPane(this.panelResults);
        panelScrolable.setSize(new Dimension(600, 250));
        panelScrolable.setLocation(new Point(50, 100));
        panelScrolable.setBorder(BorderFactory.createBevelBorder(1));
        panelScrolable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelScrolable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelScrolable.repaint();
        panelScrolable.revalidate();
        
        this.panelSearch.add(panelScrolable);
        this.panelSearch.repaint();        
    }
    
/**
 * Gets search result
 */    
    private void getSearchResult(String keywords)
    {
        final int RAW_HEIGHT = 30;
        
        clearSearchTab();
        Data result = new Data();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Document doc = result.getSearchResult(keywords);
        if (doc != null) 
        {
            NodeList nodelist;
            nodelist = doc.getChildNodes();
            Node root = nodelist.item(0); // получаем рутовый нод
            Node results = root.getChildNodes().item(0);   //получаем

            panelResults.setPreferredSize(new Dimension(600, results.getChildNodes().getLength()*30 + 10));

            for (int i = 0; i < results.getChildNodes().getLength(); i++)
            {
                JLabel number = new JLabel(String.valueOf(i+1) + ".");
                number.setLocation(5, i*RAW_HEIGHT + 5);
                number.setSize(25, 30);
                number.setVerticalAlignment(SwingConstants.BOTTOM);
                
                String id = results.getChildNodes().item(i).getTextContent();
                Relative tempHuman = new Relative(id);
                JPanel panel = getHumanRow(tempHuman);
                panel.setLocation(new Point(30, i*RAW_HEIGHT + 5));
                //panel.setBorder(BorderFactory.createEtchedBorder());
                panelResults.add(number);
                panelResults.add(panel);
            } 
            if (results.getChildNodes().getLength() == 0)
            {
                labelSearchResult.setText("Нет данных соответствующих запросу");
            }
            else
            {
                labelSearchResult.setText("Количество совпадений - " + results.getChildNodes().getLength());            
            }
            panelResults.repaint();
        }                
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));    
    }
    
/**
 * Создание главного меню
 */    
    private void setMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem closeMenu = new JMenuItem(getBundle("genealogia/Bundle").getString("Close"));
        closeMenu.addActionListener(new ActionListener() 
        {           
            @Override
            public void actionPerformed(ActionEvent e) {
                onClose();             
            }           
        });
        
        fileMenu.add(closeMenu);
        
        JMenu toolsMenu = new JMenu(getBundle("genealogia/Bundle").getString("Tools"));
        JMenuItem settingsMenu = new JMenuItem(getBundle("genealogia/Bundle").getString("Settings"));
        ActionListener al = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettins();
            }
        };
        settingsMenu.addActionListener(al);
        toolsMenu.add(settingsMenu);
        
        JMenu helpMenu = new JMenu(getBundle("genealogia/Bundle").getString("Help"));
        
        JMenuBar mb = new JMenuBar();
        mb.add(fileMenu);
        mb.add(toolsMenu);
        mb.add(historyMenu);
        mb.add(helpMenu);
        
        this.setJMenuBar(mb);    
    }
    
/**
 * 
 */    
    private void showSettins()
    {
        SettingsForm form = new SettingsForm(this);
        //form.pack();
        form.setForm();
        form.setVisible(true);
    }
    
//    public void init()
//    {
//        repaint();
//    }    
    
/**
 * Added function onClose()
 */    
    public void initForm()
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
