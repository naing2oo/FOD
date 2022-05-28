package Design;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

/**
 * @author NaingNaingOo
 *This class extends JButton to create a button with rounded rectangle border
 */
public class RoundedRectangleButton extends JButton {
	
	public RoundedRectangleButton(String buttontext,Color color){
		
		super(buttontext);
		
		setFont(new Font("Times New Roman", Font.BOLD,24));
		setForeground(color);
		setBackground(Color.white);
		setFocusable(false);
		
		Dimension size=getPreferredSize();
		size.width=size.height=Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
	}//end of constructor 
	
	protected void paintComponent(Graphics g){
		if(getModel().isArmed()){
			g.setColor(new Color(102,255,102));
		}
		else{
			g.setColor(getBackground());
		}
		g.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, getHeight(), getWidth());
		
		super.paintComponent(g);
	}//end of paintComponet function
	
	protected void paintBorder(Graphics g){
		
		g.setColor(Color.magenta);
		g.drawRoundRect(0, 0, getSize().width-1,  getSize().height-1,getHeight(), getWidth() );
		}//end of paintBorder
	Shape shape;
	
	public boolean coantins(int x,int y){
		if(shape==null || !shape.getBounds().equals(getBounds())){
			shape=new Ellipse2D.Float(0,0,getWidth(),getHeight());
		}
		return shape.contains(x,y);
	}//end of coantins function
}//end of class