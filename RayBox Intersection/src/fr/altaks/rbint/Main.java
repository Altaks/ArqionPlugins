package fr.altaks.rbint;

public class Main {

	public static void main(String[] args) {
		
		AxisAlignedBoudingBox aabb_box = new AxisAlignedBoudingBox(0d, 0d, 0d, 0.8d, 1.8d);
		Ray playerViewVector = new Ray(0f, 0f, 0f, 0.4f, 0.4f, 0.4f);
		
		// vecteur = fonction sur deux axes en y = mx + p.
		
	}
	
	public static class AxisAlignedBoudingBox {
		
		public double x, y, z;
		public double hitboxWidth, hitboxHeight;
		
		public AxisAlignedBoudingBox(double x, double y, double z, double hw, double hh) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.hitboxWidth = hw;
			this.hitboxHeight = hh;
		}
		
	}
	
	public static class Ray {
		
		private float 	dx, dy, dz, // directional 
						x, y, z; // starting point
		
		public Ray(float x, float y, float z, float dx, float dy, float dz) {
			this.dx = dx;
			this.dy = dy;
			this.dz = dz;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		
	}

}
