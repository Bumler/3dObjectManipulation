package Rotations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Cube extends Polygon{
	ArrayList<int[]> edgeList = new ArrayList<int[]>();
	ArrayList<ArrayList<Integer>> faceList = new ArrayList<ArrayList<Integer>>();
	ArrayList<Color> colorList = new ArrayList<Color>();
	boolean init = false;
	boolean selected = false;
	
	public Cube (){
		super();
	}
	
	public Cube (String f){
		super();
		readFile(f);
	}
	
	public Cube (float[][] list){
		super();
		newCoordinates(list);
	}
	
	//sets the cube to be selected, by default no cube is selected
	public void selected (boolean tf){
		selected = tf;
	}
	
	public coordinate getXY(){
		int totalX = 0;
		int totalY = 0;
		for (int i = 0; i < faceList.size(); i++){		
			//each coordinate is a different point on a line from the face list
			coordinate a = cordList.get(faceList.get(i).get(0));
			coordinate b = cordList.get(faceList.get(i).get(1));
			coordinate c = cordList.get(faceList.get(i).get(2));
			coordinate d = cordList.get(faceList.get(i).get(3));
			
			totalX += (int) ((a.getX() + b.getX() + c.getX() + d.getX())/4);
			totalY += (int) ((a.getY() + b.getY() + c.getY() + d.getY())/4);
		}
		totalX /= faceList.size();
		totalY /= faceList.size();
		return new coordinate(totalX, totalY, 0);
	}
	
	//file reading information found at
	//https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
	public void readFile(String fileName){
		boolean vertices = false;
		boolean edges = false;
		boolean faces = false;
	        // The name of the file to open.

	        // This will reference one line at a time
	    String line = null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	                System.out.println(line);
	                if (line.startsWith("vertices")){
	                	vertices = true;
	                	edges = false;
	                	System.out.println("gets here");
	                }
	                else if (line.startsWith("edges")){
	                	//at this point we are done reading in coordinates, and we only do this step once
	                	//this makes it the perfect place to find the center
	                	this.findCenter();
	                	vertices = false;
	                	edges = true;
	                	init = true;
	                }
	                else if (line.startsWith("faces")){
	                	vertices = false;
	                	edges = false;
	                	faces = true;
	                }
	                else if(!line.startsWith("//")&&vertices){
	                	String[] cords = line.split(",");
	                	float x = Float.parseFloat(cords[0]);
	                	float y = Float.parseFloat(cords[1]);
	                	float z = Float.parseFloat(cords[2]);
	                	System.out.println ("point y" + y);
	                	cordList.add(new coordinate (x,y,z));
	                	System.out.println("Vertices: "+x+" "+y+" "+z);
	                }
	                else if (!line.startsWith("//")&&edges){
	                	System.out.println("Edges");
	                	String[] cords = line.split(", ");
	                	int a = Integer.parseInt(cords[0]);
	                	int b = Integer.parseInt(cords[1]);
	                	System.out.println(a);
	                	int[] e = {a,b};
	                	edgeList.add(e);
	                }
	                else if (!line.startsWith("//")&&faces){
	                	System.out.println("Faces");
	                	String[] cords = line.split(", ");
	                	ArrayList<Integer> face = new ArrayList<Integer>();
	                	for (int i = 0; i < cords.length; i++){
	                		face.add(Integer.parseInt(cords[i]));
	                	}
	                	faceList.add(face);
	                }
	                isDone = true;
	            }   

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	        	colorList.add(Color.red);
	        	colorList.add(Color.white);
	        	colorList.add(Color.blue);
	        	colorList.add(Color.cyan);
	        	colorList.add(Color.yellow);
	        	colorList.add(Color.green);
	}
	
	public void erase(){
		super.erase();
		init = false;
		edgeList = new ArrayList<int[]>();
	}
	
	//this method returns the 'depth' of the object, meaning how far in front of the z axis it is
	public float getDepth(){
		float depth = this.center.getZ();
		return depth;
	}
	
	public void render (Graphics2D g2d){
		if (init){
			ArrayList<Integer> spots = new ArrayList<Integer>();
			//we go through each line in the face list drawing squares in ways that appear to be cubelike
			for (int i = 0; i < faceList.size(); i++){		
				//each coordinate is a different point on a line from the face list
				coordinate a = cordList.get(faceList.get(i).get(0));
				coordinate b = cordList.get(faceList.get(i).get(1));
				coordinate c = cordList.get(faceList.get(i).get(2));
				coordinate d = cordList.get(faceList.get(i).get(3));

				//what this project currently does to handle hidden surface removal is check the z of the center of the face
				//if that is behind the center of the cube (when adjusted to 0 z) we don't render it 
				double centerZ = (a.getZ() + b.getZ() + c.getZ() + d.getZ())/4;
				centerZ = centerZ + (-1*this.getDepth());
				//we do point .1 to account for some float cutoff
				if (centerZ > 01){
					spots.add(i);
				}
				
//				//creates two vectors in the correct form
				vector u = new vector(a, b);
				vector v = new vector(a,d);
				
				//computes cross
				int[] cross = u.cross(v);
				vector normal = new  vector();
				//adds in the base of the vector (the common point and the location of the cross)
				normal.addPoint(a.getX(),a.getY(),a.getZ());	
				normal.addPoint((cross[0]), (cross[1]), (cross[2]));
		
				//scales them down to a unit vector and then enlarges them
				normal.shrink();
				
				//translates normal to the center
				int centerX = (int) ((a.getX() + b.getX() + c.getX() + d.getX())/4);
				int centerY = (int) ((a.getY() + b.getY() + c.getY() + d.getY())/4);
				normal.addToVector(new coordinate(centerX, centerY, (float)centerZ));

				g2d.setColor(Color.BLACK);
				normal.render(g2d);
				
			}
			for (int j = 0; j < spots.size(); j++){
				int i = spots.get(j);
				//each coordinate is a different point on a line from the face list
				coordinate a = cordList.get(faceList.get(i).get(0));
				coordinate b = cordList.get(faceList.get(i).get(1));
				coordinate c = cordList.get(faceList.get(i).get(2));
				coordinate d = cordList.get(faceList.get(i).get(3));
				
				//what this project currently does to handle hidden surface removal is check the z of the center of the face
				//if that is behind the center of the cube (when adjusted to 0 z) we don't render it 
				double centerZ = (a.getZ() + b.getZ() + c.getZ() + d.getZ())/4;
				centerZ = centerZ + (-1*this.getDepth());
				//we do point .1 to account for some float cutoff
				if (centerZ > 01){
					//we create an array of x's and y's from our four points and then draw them as a square
					int x[] = {(int)a.getX(), (int)b.getX(), (int)c.getX(), (int)d.getX()};
					int y[] = {(int)a.getY(), (int)b.getY(), (int)c.getY(), (int)d.getY()};
					g2d.setColor(colorList.get(i));
					g2d.fillPolygon(x, y, 4);
					
					//we make our line color black for the outline and the vector below
					g2d.setColor(Color.BLACK);
					//outlines the visible faces of the shape with black lines
					if (selected){
						g2d.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
						g2d.drawLine((int)b.getX(), (int)b.getY(), (int)c.getX(), (int)c.getY());
						g2d.drawLine((int)c.getX(), (int)c.getY(), (int)d.getX(), (int)d.getY());
						g2d.drawLine((int)d.getX(), (int)d.getY(), (int)a.getX(), (int)a.getY());
					}
				}
				
//				//creates two vectors in the correct form
				vector u = new vector(a, b);
				vector v = new vector(a,d);
				//computes cross
				int[] cross = u.cross(v);
				vector normal = new  vector();
				//adds in the base of the vector (the common point and the location of the cross)
				normal.addPoint(a.getX(),a.getY(),a.getZ());	
				normal.addPoint((cross[0]), (cross[1]), (cross[2]));
		
				//scales them down to a unit vector and then enlarges them
				normal.shrink();
				
				//translates normal to the center
				int centerX = (int) ((a.getX() + b.getX() + c.getX() + d.getX())/4);
				int centerY = (int) ((a.getY() + b.getY() + c.getY() + d.getY())/4);
				normal.addToVector(new coordinate(centerX, centerY, (float)centerZ));

				normal.render(g2d);
		}
		}
	}
}
