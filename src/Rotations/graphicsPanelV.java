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
	/**
	 * Create the panel.
	 */

	public graphicsPanelV(Rotations.frame frame) {
		// TODO Auto-generated constructor stub
		owner = frame;
	}

	public void erase(){
		shape.erase();
		repaint();
	}
	
	public void scale (float sX, float sY, float sZ){
		float[][] vector = shape.getMatrix();
		float[][] scale = {
				{sX, 0, 0, 0} ,
				{0, sY, 0, 0} ,
				{0, 0, sZ, 0} ,
				{0, 0, 0, 1}
		};
		
		float[][] product = matrixMult.multiply(scale, vector);
		shape.newCoordinates(product);
		shape.findCenter();
		repaint();
	}

	public void translate (float tX, float tY, float tZ){
		float[][] vector = shape.getMatrix();
		float[][] translate = {
				{1, 0, 0, tX} ,
				{0, 1, 0, tY} ,
				{0, 0, 1, tZ} ,
				{0, 0, 0, 1}
		};
		
		float[][] product = matrixMult.multiply(translate, vector);
		shape.newCoordinates(product);
		shape.findCenter();
		repaint();
	}
	
	public void rotate(int modeXYZ, int modeRotate, int angle){
		System.out.println("check 2");
		if(modeXYZ == 0 || modeRotate == 0 || angle == -1 || !shape.isDone){
			return;
		}		
		
		float[][] vector = shape.getMatrix();
		float[][] affine = createArray(modeXYZ, angle);		
		if (modeRotate == 2){
			float[][] trans = createArray(4, 0);
			float[][] product = matrixMult.multiply(trans, vector);
			shape.newCoordinates(product);
			vector = shape.getMatrix();
			System.out.println(shape.getCenter());
		}
		
		float[][] product = matrixMult.multiply(affine, vector);
		shape.newCoordinates(product);
		
		if (modeRotate == 2){
			float[][] trans = createArray(5, 0);
			float[][] tProduct = matrixMult.multiply(trans, shape.getMatrix());
			shape.newCoordinates(tProduct);
		}
			
		repaint();
	}
	
	public float[][] createArray(int mode, int angle){
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
					{1, 0, 0, -1*(shape.getCenter().getX())},
					{0, 1, 0, -1*(shape.getCenter().getY())},
					{0, 0, 1, -1*(shape.getCenter().getZ())},
					{0, 0, 0, 1}};
			return negTrans;
		case 5://creates a translation array it back
			float[][]posTrans = {
					{1, 0, 0, (shape.getCenter().getX())},
					{0, 1, 0, (shape.getCenter().getY())},
					{0, 0, 1, (shape.getCenter().getZ())},
					{0, 0, 0, 1}};
			return posTrans;
		}
		return null;
	}
	
	public void fileRead(Cube c){
		shape = c;
		repaint();
	}
	public void paint (Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		shape.render(g2d);
		System.out.println("repaint?");
	}
	
	public void arbitraryRotation (coordinate cord, int angle){
		System.out.println("ITS ME");
		shape = matrixMult.rotateArbitrary(shape, angle, cord);
		repaint();
	}
	
	public void animate(int modeXYZ, int modeRotate, int angle){
		scale(50,50,50);
		translate(50,50,0);
		repaint();
//		for (int i = 0; i < 1000;i++){
//		System.out.println(i);
//			rotate(modeXYZ, modeRotate, angle);
//			repaint();
//			}
	}
}


