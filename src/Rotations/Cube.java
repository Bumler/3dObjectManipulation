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

//				//creates two vectors in the correct form
				vector u = new vector(a, b);
				vector v = new vector(a,d);
				
				//computes the cross product
				
				//we must now convert the normal into a unit vector
				//normal.shrink();
				
				//now we translate the normal to the center
				
				//what this project currently does to handle hidden surface removal is check the z of the center of the face
				//if that is behind the center of the cube (when adjusted to 0 z) we don't render it 
				double centerZ = (a.getZ() + b.getZ() + c.getZ() + d.getZ())/4;
				centerZ = centerZ + (-1*this.getDepth());
				//we do point .1 to account for some float cutoff
				if (centerZ > 01){
					spots.add(i);
				}
				
				int[] cross = u.cross(v);
				vector normal = new  vector();
				int centerX = (int) ((a.getX() + b.getX() + c.getX() + d.getX())/4);
				int centerY = (int) ((a.getY() + b.getY() + c.getY() + d.getY())/4);
				normal.addPoint(centerX, centerY, (int)centerZ);	
				normal.addPoint((cross[0] + centerX), (cross[1] + centerY), (cross[2] + (int)centerZ));

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

//				//creates two vectors in the correct form
				vector u = new vector(a, b);
				vector v = new vector(a,d);
				
				//computes the cross product
				
				//we must now convert the normal into a unit vector
				//normal.shrink();
				
				//now we translate the normal to the center
				
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
				}
				
				int[] cross = u.cross(v);
				vector normal = new  vector();
				int centerX = (int) ((a.getX() + b.getX() + c.getX() + d.getX())/4);
				int centerY = (int) ((a.getY() + b.getY() + c.getY() + d.getY())/4);
				normal.addPoint(centerX, centerY, (int)centerZ);	
				normal.addPoint((cross[0] + centerX), (cross[1] + centerY), (cross[2] + (int)centerZ));

				g2d.setColor(Color.BLACK);
				normal.render(g2d);
		}
		}
	}
}
