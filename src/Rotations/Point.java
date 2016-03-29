package Rotations;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Point {
	protected ArrayList<coordinate> cordList = new ArrayList<coordinate>();
	final int radius = 2;
	
	public Point (){
		
	}
	
	public void addPoint(float x, float y , float z){
		cordList.add(new coordinate(x,y,z));
	}
	
	public coordinate getCoord(int i){
		return cordList.get(i);
	}
	
	public void erase(){
		cordList = new ArrayList<coordinate>();
	}
	
	public void render (Graphics2D g2d){
		for (int i = 0; i < cordList.size(); i++){
			g2d.drawOval((int)cordList.get(i).getX()-1, (int)cordList.get(i).getY()-1, radius, radius);
		}
	}
	//render by drawing a small circle on the coordinate

}

