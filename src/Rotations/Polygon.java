package Rotations;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Polygon extends Point{
	
	boolean isDone = false;
	coordinate center = new coordinate(0, 0, 0);
	float[][] matrix = null;
	
	public Polygon(){
		super();
		//center.addPoint(-1, -1, -1);
	}
	
	public Polygon (coordinate a, coordinate b, coordinate c, coordinate d){
		this.cordList.add(a);
		this.cordList.add(b);
		this.cordList.add(c);
		this.cordList.add(d);
		this.createPolygon();
		this.findCenter();
	}
	
	public void addPoint(int x, int y, int z){
		if (!isDone)	
			super.addPoint(x, y, z);
		
		else{
			
		}
	}
	
	public void erase(){
		super.erase();
		isDone = false;
		center = new coordinate(0, 0, 0);
		matrix = null;
	}
	public void createPolygon(){
		isDone = true;
		findCenter();
		createMatrix();
	}
	
	public boolean isDone(){
		return isDone;
	}
	
	public void findCenter(){
		if (!isDone){
			return;
		}
		int x = 0;
		int y = 0;
		int z = 0;
		for (int i = 0; i < cordList.size(); i++){
			x += cordList.get(i).getX();
		}
		x = x/cordList.size();
		
		for (int i = 0; i < cordList.size(); i++){
			y += cordList.get(i).getY();
		}
		y = y/cordList.size();
		
		for (int i = 0; i < cordList.size(); i++){
			z += cordList.get(i).getZ();
		}
		z = z/cordList.size();
		
		center = new coordinate(x, y, z);
	}
	
	public coordinate getCenter(){
		return center; 
	}
	
	public void createMatrix(){
		matrix = new float[4][cordList.size()];
		for(int i = 0; i < 4; i++){
			for (int j = 0; j < cordList.size(); j++){
				switch(i){
				case 0:
					matrix[i][j] = cordList.get(j).getX();
					break;
				case 1:
					matrix[i][j] = cordList.get(j).getY();
					break;
				case 2:
					matrix[i][j] = cordList.get(j).getZ();
					break;
				case 3:
					matrix[i][j] = (float)1.0;
					break;
				}
			}
		}
	}
	
	public float[][] getMatrix(){
		createMatrix();
		return matrix;
	}
	
	public void newCoordinates(float[][] matrixIn){
		if (matrix[0].length != cordList.size()){
			return;
		}
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < cordList.size(); j++){
				switch(i){
				case 0:
					cordList.get(j).setX(matrixIn[i][j]);
					break;
				case 1:
					cordList.get(j).setY(matrixIn[i][j]);
					break;
				case 2:
					cordList.get(j).setZ(matrixIn[i][j]);
					break;
				}
			}
		}
	}
	
	public void render (Graphics2D g2d){; 
		if (isDone){	
		for (int j = 0; j < (cordList.size()) - 1; j++){
				g2d.drawLine((int)cordList.get(j).getX(), (int)cordList.get(j).getY(), (int)cordList.get(j+1).getX(), (int)cordList.get(j+1).getY());
			}
			g2d.drawLine((int)cordList.get(0).getX(), (int)cordList.get(0).getY(), (int)cordList.get(cordList.size()-1).getX(), (int)cordList.get(cordList.size()-1).getY());
		}
		}
		
}