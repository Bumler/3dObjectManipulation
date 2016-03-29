package Rotations;

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
	                		face.add(Integer.parseInt(cords[0]));
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
	}
	
	public void erase(){
		super.erase();
		init = false;
		edgeList = new ArrayList<int[]>();
	}
	
	public void render (Graphics2D g2d){
		if (init){
			for (int i = 0; i < faceList.size(); i++){
				coordinate a = cordList.get(faceList.get(i).get(0));
				coordinate b = cordList.get(faceList.get(i).get(1));
				coordinate c = cordList.get(faceList.get(i).get(2));
				coordinate d = cordList.get(faceList.get(i).get(3));
				System.out.println(faceList.get(i).get(0)+ " " +faceList.get(i).get(1)+ " "+faceList.get(i).get(2)+" " +faceList.get(i).get(3));
				System.out.println(a+"    "+b+"    "+c+"    "+d);
				int x[] = {(int)a.getX(), (int)b.getX(), (int)c.getX(), (int)d.getX()};
				int y[] = {(int)a.getY(), (int)b.getY(), (int)c.getY(), (int)d.getY()};
				g2d.fillPolygon(x, y, 4);
			}
		}
	}
}
