package Rotations;

public class matrixMult {

	// STATIC allows us to call a method without having to actually create the
	// object

	private static float M[][] = {{}, {}, {}, {}};

	public static float[][] multiply(float a[][], float b[][]) {
		float result[][] = null;

		// check size constraint
		// #columns == v[#].length
		// #rows == v.length
		if (a[0].length != b.length) {
			return null;
		}

		// Creates output array
		result = new float[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) { // pick a row out of a
			for (int j = 0; j < b[0].length; j++) { // pick a column out of b
				result[i][j] = 0; // Initialize
				for (int k = 0; k < a[0].length; k++) { // Select an individual
														// element out of both a
														// & b
					result[i][j] += a[i][k] * b[k][j];
				}

			}
		}

		return result;
	}
	
	public float[][] getM(){
		return M;
	}

	public static Cube rotateArbitrary(Cube c, float angle, coordinate axis) {
		coordinate P = c.getCenter();

		Cube result = new Cube();
		float anglerads = (float) Math.toRadians(angle);

		// create a unit vector for the axis
		System.out.println("Not far at all");
		float mag = (float) Math
				.sqrt(axis.getX() * axis.getX() + axis.getY() * axis.getY() + axis.getZ() * axis.getZ());
		coordinate unitV = new coordinate((axis.getX() / mag), (axis.getY() / mag), (axis.getZ() / mag));
		float d = (float) Math.sqrt(unitV.getY() * unitV.getY() + unitV.getZ() * unitV.getZ());

		float Tneg[][] = { { 1, 0, 0, -P.getX() }, 
				{ 0, 1, 0, -P.getY() }, 
				{ 0, 0, 1, -P.getZ() }, 
				{ 0, 0, 0, 1 } };
		float Rx0[][] = { { 1, 0, 0, 0 }, 
				{ 0, unitV.getZ() / d, -unitV.getY() / d, 0 },
				{ 0, unitV.getY() / d, unitV.getZ() / d, 0 }, 
				{ 0, 0, 0, 1 } };
		float Ry0[][] = { { d, 0, -unitV.getX(), 0 }, 
				{ 0, 1, 0, 0 }, 
				{ unitV.getX(), 0, d, 0 }, 
				{ 0, 0, 0, 1 } };
		float Rz[][] = { { (float) Math.cos(anglerads), 
			(float) -Math.sin(anglerads), 0, 0 },
				{ (float) Math.sin(anglerads), (float) Math.cos(anglerads), 0, 0 }, 
				{ 0, 0, 1, 0 }, 
				{ 0, 0, 0, 1 } };
		float Ry1[][] = { { d, 0, unitV.getX(), 0 }, 
				{ 0, 1, 0, 0 }, 
				{ -unitV.getX(), 0, d, 0 }, 
				{ 0, 0, 0, 1 } };
		float Rx1[][] = { { 1, 0, 0, 0 }, 
				{ 0, unitV.getZ() / d, unitV.getY() / d, 0 },
				{ 0, -unitV.getY() / d, unitV.getZ() / d, 0 }, { 0, 0, 0, 1 } };
		float Tpos[][] = { { 1, 0, 0, P.getX() }, 
				{ 0, 1, 0, P.getX() }, 
				{ 0, 0, 1, P.getZ() }, 
				{ 0, 0, 0, 1 } };

		System.out.println("We get kind of far");
		
		M = multiply(Rx0, Tneg);
		M = multiply(Ry0, M);
		M = multiply(Rz, M);
		M = multiply(Ry1, M);
		M = multiply(Rx1, M);
		M = multiply(Tpos, M);

		matrixMult.displayMatrix(c.getMatrix());
		matrixMult.displayMatrix(M);
		
		M = multiply(M, c.getMatrix());
		matrixMult.displayMatrix(M);
		
		c.newCoordinates(M);
		return c;
	}

	// Render
	public static void displayMatrix(float a[][]) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
