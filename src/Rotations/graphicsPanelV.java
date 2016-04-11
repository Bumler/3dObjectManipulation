package Rotations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

public class graphicsPanelV extends JPanel {
	private Rotations.frame owner = null;
	private Cube shape = new Cube();
	private Cube otherShape = new Cube();
	/**
	 * Create the panel.
	 */

	public graphicsPanelV(Rotations.frame frame) {
		// TODO Auto-generated constructor stub
		owner = frame;
		this.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
					int x = e.getX();
					int y = e.getY();
				if (e.getButton() == MouseEvent.BUTTON1) {
					//on mouse click it takes the distance of both shapes centers from the mouse click
					//the one that is closer is selected
					coordinate shapePos = shape.getXY();
					coordinate otherShapePos = otherShape.getXY();
					double dist1 = (Math.sqrt(
							((shapePos.getX() - x)* (shapePos.getX() - x)) 
							+ ((shapePos.getY() - y) * ((shapePos.getY() -y)))
							));
					double dist2 = (Math.sqrt(
							((otherShapePos.getX() - x)* (otherShapePos.getX() - x)) 
							+ ((otherShapePos.getY() - y) * ((otherShapePos.getY() -y)))
							));
					System.out.println("dist1 "+dist1);
					System.out.println("dist2 "+dist2);
					if (dist1 < dist2){
						System.out.println("check A");
						shape.selected(true);
						otherShape.selected(false);
					}
					else {
						System.out.println("Check B");
						shape.selected(false);
						otherShape.selected(true);
					}
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void erase(){
		shape.erase();
		otherShape.erase();
		repaint();
	}
	
	public void scale (float sX, float sY, float sZ, Cube c){
		float[][] vector = c.getMatrix();
		float[][] scale = {
				{sX, 0, 0, 0} ,
				{0, sY, 0, 0} ,
				{0, 0, sZ, 0} ,
				{0, 0, 0, 1}
		};
		
		float[][] product = matrixMult.multiply(scale, vector);
		c.newCoordinates(product);
		c.findCenter();
		repaint();
	}

	public void translate (float tX, float tY, float tZ, Cube c){
		float[][] vector = c.getMatrix();
		float[][] translate = {
				{1, 0, 0, tX} ,
				{0, 1, 0, tY} ,
				{0, 0, 1, tZ} ,
				{0, 0, 0, 1}
		};
		
		float[][] product = matrixMult.multiply(translate, vector);
		c.newCoordinates(product);
		c.findCenter();
		repaint();
	}
	
	public void rotate(int modeXYZ, int modeRotate, int angle, Cube c){
		System.out.println("check 2");
		if(modeXYZ == 0 || modeRotate == 0 || angle == -1 || !c.isDone){
			return;
		}		
		
		float[][] vector = c.getMatrix();
		float[][] affine = createArray(modeXYZ, angle, c);		
		if (modeRotate == 2){
			float[][] trans = createArray(4, 0, c);
			float[][] product = matrixMult.multiply(trans, vector);
			c.newCoordinates(product);
			vector = c.getMatrix();
		}
		
		float[][] product = matrixMult.multiply(affine, vector);
		c.newCoordinates(product);
		
		if (modeRotate == 2){
			float[][] trans = createArray(5, 0, c);
			float[][] tProduct = matrixMult.multiply(trans, c.getMatrix());
			c.newCoordinates(tProduct);
		}
			
		repaint();
	}
	
	public float[][] createArray(int mode, int angle, Cube c){
		double rad = Math.toRadians(angle);
		System.out.println("check 3");
		switch(mode){
		case 1://creates an x rotation
			float[][]rotMatrixX = {
					{1, 0, 0, 0},
					{0, ((float) Math.cos(rad)), (float) -Math.sin(rad), 0},
					{0, (float) Math.sin(rad), (float) Math.cos(rad), 0},
					{0, 0, 0, 1}};
			return rotMatrixX;
		case 2://creates a y rotation
			float[][]rotMatrixY = {
					{(float) Math.cos(rad), 0, (float)Math.sin(rad), 0}, 
					{0, 1, 0, 0},
					{-(float)Math.sin(rad), 0, (float)Math.cos(rad), 0}, 
					{0, 0, 0, 1}};
			return rotMatrixY;
		case 3://creates a z rotation
			float[][]rotMatrixZ = {
					{(float) Math.cos(rad), -(float)Math.sin(rad), 0, 0}, 
					{(float)Math.sin(rad), (float)Math.cos(rad), 0, 0},
					{0, 0, 1, 0}, 
					{0, 0, 0, 1}};
			return rotMatrixZ;
		case 4://creates a translation array to move by the center point
			float[][]negTrans = {
					{1, 0, 0, -1*(c.getCenter().getX())},
					{0, 1, 0, -1*(c.getCenter().getY())},
					{0, 0, 1, -1*(c.getCenter().getZ())},
					{0, 0, 0, 1}};
			return negTrans;
		case 5://creates a translation array it back
			float[][]posTrans = {
					{1, 0, 0, (c.getCenter().getX())},
					{0, 1, 0, (c.getCenter().getY())},
					{0, 0, 1, (c.getCenter().getZ())},
					{0, 0, 0, 1}};
			return posTrans;
		}
		return null;
	}
	
	public void fileRead(Cube c, Cube c2){
		shape = c;
		otherShape = c2;
		repaint();
	}
	public void paint (Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		//checks which shape is has a great z in its center
		//the shape 'further from the screen' is drawn first that way the closer one is drawn over
		float d1 = shape.getDepth();
		float d2 = otherShape.getDepth();
		if (d1 > d2){	
			otherShape.render(g2d);
			shape.render(g2d);
			}
		else {
			shape.render(g2d);
			otherShape.render(g2d);
		}
		System.out.println("repaint?");
	}
	
	public void arbitraryRotation (coordinate cord, int angle){
		System.out.println("ITS ME");
		if (shape.isSelected())
			shape = matrixMult.rotateArbitrary(shape, angle, cord);
		else if (otherShape.isSelected())
			otherShape = matrixMult.rotateArbitrary(otherShape, angle, cord);
		repaint();
	}
	
	public void animate(int modeXYZ, int modeRotate, int angle){
		scale(50,50,50,shape);
		translate(50,50,0,shape);
		repaint();
//		for (int i = 0; i < 1000;i++){
//		System.out.println(i);
//			rotate(modeXYZ, modeRotate, angle);
//			repaint();
//			}
	}
}


