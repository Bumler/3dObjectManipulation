package Rotations;

import java.awt.Graphics2D;

public class vector extends Point{
	private int[] vec;
	
	public vector (){
		super();
	}
	
	public vector (coordinate c1, coordinate c2){
		super();
		this.addPoint((int)c1.getX(), (int)c1.getY(), (int)c1.getZ());
		this.addPoint((int)c2.getX(), (int)c2.getY(), (int)c2.getZ());
	}
	
	public void addPoint(int x, int y, int z){
		if (cordList.size() > 2){
			return;
		}
		else if (cordList.size() == 1){
			super.addPoint(x, y, z);
			this.vectorCords();
		}
		
		else {
			super.addPoint(x, y, z);}
		
	}
	
	public void vectorCords(){
		vec = new int[3];
		vec[0] = (int) (cordList.get(1).getX() - cordList.get(0).getX());
		vec[1] = (int) (cordList.get(1).getY() - cordList.get(0).getY());
		vec[2] = (int) (cordList.get(1).getZ() - cordList.get(0).getZ());
	}
	
	public int getVX(){
		return vec[0];
	}
	
	public int getVY(){
		return vec[1];
	}
	
	public int getVZ(){
		return vec[2];
	}
	
	//VECTOR MATH below------------------------------------------------
	public float magnitude(){
		return (float) Math.sqrt(
				Math.pow(vec[0], 2)
				+ Math.pow(vec[1], 2) 
				+ Math.pow(vec[2], 2));
	}
	
	public void addToVector (coordinate c){
		for (int i = 0; i < cordList.size(); i++){
			cordList.get(i).setX(cordList.get(i).getX() + c.getX());
			cordList.get(i).setY(cordList.get(i).getY() + c.getY());
			cordList.get(i).setZ(cordList.get(i).getZ() + c.getZ());
		}
	}
	
	public int angle(vector v2){
		return (int)Math.toDegrees(Math.cos(
				(this.dot(v2) / (this.magnitude() * v2.magnitude()))));
//		return (int)Math.toDegrees(Math.cos(((vec[0] * v2.getVX()) + (vec[1]*v2.getVY()))/
//				(Math.sqrt((v2.getVX()*v2.getVX()) + (vec[1] * vec[1])) *
//						Math.sqrt((vec[0]*vec[0]) + (v2.getVY() * v2.getVY())))));
	}
	
	public int[] cross (vector v2){
		int[] cross = new int[3];
		cross[0] = (vec[1]*v2.getVZ())-(vec[2]*v2.getVY());
		cross[1] = (vec[2]*v2.getVX())-(vec[0]*v2.getVZ());
		cross[2] = (vec[0]*v2.getVY())-(vec[1]*v2.getVX());
		
		return cross;
	}
	
	public int dot (vector v2){
		System.out.println("v2 x: "+v2.getVX()+ " v2 y: "+v2.getVY());
		return ((vec[0]*v2.getVX())+(vec[1]*v2.getVY()));
	}
	
	public void shrink(){
		float dividend = this.magnitude();
		for (int i = 0; i < cordList.size(); i++){
			cordList.get(i).setX((cordList.get(i).getX()/dividend)*10);
			cordList.get(i).setY((cordList.get(i).getY()/dividend)*10);
			cordList.get(i).setZ((cordList.get(i).getZ()/dividend)*10);
		}
	}
	//RENDER-----------------------------------------------------------
	public void render (Graphics2D g2d){
		if (cordList.size()!=0 && cordList.size() != 1){
			g2d.drawLine((int)cordList.get(1).getX(), 
					(int)cordList.get(1).getY(), 
					(int)cordList.get(0).getX(), 
					(int)cordList.get(0).getY());
	}}
	
	}
