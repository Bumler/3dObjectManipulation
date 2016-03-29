package Rotations;

public class coordinate{
	float x;
	float y;
	float z;
	int w;
	
	public coordinate (float inX, float inY, float inZ){
		this.x = inX;
		this.y = inY;
		this.z = inZ;
		this.w = 1;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float getW() {
		return w;
	}

	public String toString(){
		return new String (x+" "+y+" "+z);
	}
}
