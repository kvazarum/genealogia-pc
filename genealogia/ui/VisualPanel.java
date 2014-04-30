
package genealogia.ui;

import genealogia.Family;
import genealogia.Relative;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Gapchich I.B.
 */
public class VisualPanel extends JPanel{
    Relative human;
    final static int COMPONENT_WIDTH = 120;
    final static int COMPONENT_HEIGHT = 70;
    
    final static int SPACE_BETWEEN_RAW = 40;
    final static int SPACE_BETWEEN_CELLS = 10;
    final static int LINE_WIDTH = 2;
    
    final static int PANEL_WIDTH = 1000;
    final static int PANEL_HEIGHT = 500;
    Graphics2D g2D;
    
    final static Color[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.RED, Color.PINK};

    public VisualPanel() {
        super();
        initClass();        
    }
    
    public VisualPanel(LayoutManager layout) {
        super(layout);
        initClass();
    }
    
    private void initClass()
    {
        human = new Relative();
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.g2D = (Graphics2D)g;      
        BasicStroke pen = new BasicStroke(LINE_WIDTH - 1);
        this.g2D.setStroke(pen);        
        setVisual(g); 
    }
    
    public void setHuman(Relative _human)
    {
        this.human = _human;
    }
    
    private void setVisual(Graphics g)
    {
        int x = PANEL_WIDTH/2 - COMPONENT_WIDTH/2;
        int y = PANEL_HEIGHT/2 - COMPONENT_HEIGHT;
        
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        
        drawSectionFamilies();
        
        drawSectionParents();
        
        
        if (!human.getFather().equals("-1") || !human.getMother().equals("-1"))
        {
            drawParentsRaws(new Point(x, y), this.human, Color.RED, g);
        }
        
        if (this.human.getFamsCount() > 0)
        {
            DrawFamiliesRaws(x, y);
        }
    }
    
    private void drawSectionFamilies()
    {
        int x = PANEL_WIDTH/2 - COMPONENT_WIDTH/2;
        int y = PANEL_HEIGHT/2 - COMPONENT_HEIGHT;
        
        Point[] pointsArray = new Point[4];
        Point pnt = new Point(20, y - (int)SPACE_BETWEEN_RAW/4);
        pointsArray[0] = pnt;
        pnt = new Point(2, (int)pnt.getY());
        pointsArray[1] = pnt;
        pnt = new Point((int)pnt.getX(), y + COMPONENT_HEIGHT);
        pointsArray[2] = pnt;
        pnt = new Point((int)pnt.getX() + 20, (int)pnt.getY());
        pointsArray[3] = pnt;
        drawCurve(pointsArray);
        
        int labelX = 10;
        int labelY = y + 10;
        int tab = 12;
        this.g2D.drawString("С", labelX, labelY);
        this.g2D.drawString("е", labelX, labelY + tab);
        this.g2D.drawString("м", labelX, labelY + 2*tab);
        this.g2D.drawString("ь", labelX, labelY + 3*tab);
        this.g2D.drawString("и", labelX, labelY + 4*tab);    
    }
    
    private void drawSectionParents()
    {
        int x = PANEL_WIDTH/2 - COMPONENT_WIDTH/2;
        int y = PANEL_HEIGHT/2 - COMPONENT_HEIGHT;
        
        Point[] pointsArray = new Point[4];        
        Point pnt = new Point(20, y - SPACE_BETWEEN_RAW - COMPONENT_HEIGHT - 25);
        pointsArray[0] = pnt;
        pnt = new Point(2, (int)pnt.getY());
        pointsArray[1] = pnt;
        pnt = new Point((int)pnt.getX(), (int)pnt.getY() + 110);
        pointsArray[2] = pnt;
        pnt = new Point((int)pnt.getX() + 20, (int)pnt.getY());
        pointsArray[3] = pnt;
        drawCurve(pointsArray);
        
        int labelX = 10;
        int labelY = y - SPACE_BETWEEN_RAW - COMPONENT_HEIGHT - 10;
        int tab = 12;
        this.g2D.drawString("Р", labelX, labelY);
        this.g2D.drawString("о", labelX, labelY + tab);
        this.g2D.drawString("д", labelX, labelY + 2*tab);
        this.g2D.drawString("и", labelX, labelY + 3*tab);
        this.g2D.drawString("т", labelX, labelY + 4*tab);
        this.g2D.drawString("е", labelX, labelY + 5*tab);
        this.g2D.drawString("л", labelX, labelY + 6*tab);
        this.g2D.drawString("и", labelX, labelY + 7*tab);    
    }
    
    private void DrawFamiliesRaws(int x, int y)
    {
        int count = this.human.getFamsCount();
        
        ArrayList<String> fams = this.human.getFamilies();
        
        y += COMPONENT_HEIGHT;  // start y
        
        int left_x = x;     //last x for left spouse
        int right_x = x;    //last x for right spouse
        
        
        Point[] pointsArray = new Point[4];
        
        for (int i = 0; i < count; i++)
        {
            Family family = new Family();
            family.setFamily(fams.get(i));
            
            Color _tempColor;
            if (i < VisualPanel.colors.length)
            {
                _tempColor = VisualPanel.colors[i];
            }
            else
            {
                _tempColor = Color.BLACK;
            }
            this.g2D.setColor(_tempColor);
            
            Point pnt;
            Point familyNode;
            final int tab = SPACE_BETWEEN_RAW/(count + 2);
            int lineHeight, nodeX, nodeY;
            if (i == 0 || (i%2 == 0))
            {
                if (count < 3)
                {
                    lineHeight = 15;
                }
                else
                {
                    lineHeight = tab + tab*i;
                }
                
                pnt = new Point(x + COMPONENT_WIDTH/2 - 10*i, y);
                pointsArray[0] = pnt;
                
                nodeY = (int)pnt.getY() + lineHeight;
                nodeX = (int)pnt.getX();
                pnt = new Point((int)pnt.getX(), (int)pnt.getY() + lineHeight);
                pointsArray[1] = pnt;
                
                pnt = new Point(left_x - COMPONENT_WIDTH/2, (int)pnt.getY());
                pointsArray[2] = pnt;
                
                nodeX = (int)pnt.getX() + (nodeX - (int)pnt.getX())/2;
                
                pnt = new Point((int)pnt.getX(), (int)pnt.getY() - lineHeight);
                pointsArray[3] = pnt;  
                
                drawCurve(pointsArray);
                
                left_x = left_x - COMPONENT_WIDTH - SPACE_BETWEEN_CELLS;
                familyNode = new Point(nodeX, nodeY);
                
            }
            else
            {
                if (count < 3)
                {
                    lineHeight = 15;
                }
                else
                {
                    lineHeight = tab + tab*i;
                }
                
                pnt = new Point(x + COMPONENT_WIDTH/2 + 10*i, y);
                pointsArray[0] = pnt;
                
                nodeY = (int)pnt.getY() + lineHeight;
                nodeX = (int)pnt.getX();   
                
                pnt = new Point((int)pnt.getX(), (int)pnt.getY() + lineHeight);
                pointsArray[1] = pnt;
                
                pnt = new Point(right_x + COMPONENT_WIDTH/2 + COMPONENT_WIDTH + SPACE_BETWEEN_CELLS, (int)pnt.getY());
                pointsArray[2] = pnt;
                
                nodeX = (int)pnt.getX() + ((int)pnt.getX() - nodeX)/2;
                
                pnt = new Point((int)pnt.getX(), (int)pnt.getY() - lineHeight);
                pointsArray[3] = pnt;  
                
                drawCurve(pointsArray);
                right_x += COMPONENT_WIDTH + SPACE_BETWEEN_CELLS;
                familyNode = new Point(nodeX, nodeY);
            }           
        }        
    
    }
    
    private void drawParentsRaws(Point point, Relative human, Color color, Graphics g)
    {        
        int x = (int)point.getX();
        int y = (int)point.getY();
        
        int last_x;
        int last_y;

        last_x = x + COMPONENT_WIDTH/2;
        last_y = y - SPACE_BETWEEN_RAW/2;
        
        //setting width and color of the pen
        BasicStroke pen = new BasicStroke(LINE_WIDTH);
        this.g2D.setStroke(pen);
        this.g2D.setColor(color);
        
        Point[] pointArray = new Point[2];
        Point pnt = new Point(x + COMPONENT_WIDTH/2, y);
        pointArray[0] = pnt;
        pnt = new Point(last_x, last_y);
        pointArray[1] = pnt;
        drawCurve(pointArray);
        
        if (!human.getFather().equals("-1"))
        {
            pointArray = new Point[3];
            pnt = new Point(last_x, last_y);
            pointArray[0] = pnt;
            pnt = new Point(last_x - COMPONENT_WIDTH/3 - 25, last_y);
            pointArray[1] = pnt;
            pnt = new Point(last_x - COMPONENT_WIDTH/3 - 25, last_y);
            last_x = last_x - COMPONENT_WIDTH/3 - 25;
            pnt = new Point(last_x, last_y - SPACE_BETWEEN_RAW/2);
            pointArray[2] = pnt;
            drawCurve(pointArray);
        }
        last_x = x + COMPONENT_WIDTH/2;
        last_y = y - SPACE_BETWEEN_RAW/2;        
        if (!human.getMother().equals("-1"))
        {
            pointArray = new Point[3];
            pnt = new Point(last_x, last_y);
            pointArray[0] = pnt;
            pnt = new Point(last_x + COMPONENT_WIDTH/3 + 25, last_y);
            pointArray[1] = pnt;
            last_x = last_x + COMPONENT_WIDTH/3 + 25;
            pnt = new Point(last_x, last_y - SPACE_BETWEEN_RAW/2);
            pointArray[2] = pnt;
            drawCurve(pointArray);
        }
    }
    
    private void drawCurve(Point[] points)
    {
        for (int i = 0; i < points.length - 1; i++)
        {
            this.g2D.drawLine((int)points[i].getX(), (int)points[i].getY(), (int)points[i + 1].getX(), (int)points[i + 1].getY());
        }
    }
}
