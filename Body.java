public class Body
{
	public static final double G = 6.67E-11;  //Newtons' gravitational constant

	private double x, y; //planet's x and y coordinate position
	private double xVelocity, yVelocity;
	private double mass;
	private double xNetForce, yNetForce; //net forces action on this planet
	private double xAccel, yAccel;
	private String image; //image of this planet (for drawing)


	public Body(double x1, double y1, double xVelocity1, double yVelocity1, double mass1, String image1)
	{
		x = x1;
		y = y1;
		xVelocity = xVelocity1;
		yVelocity = yVelocity1;
		mass = mass1;
		xNetForce = 0;
		yNetForce = 0;
		xAccel = 0;
		yAccel = 0;
		image = image1;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	private double getDistance(Body other)
	{
		double dx = other.getX() - x;
		double dy = other.getY() - y;
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public double getPairwiseForce(Body other)
	{
		double r = getDistance(other);
		double force = (mass * other.getMass() * G)/(r * r);
		return force;
	}
	
	public double getPairwiseForceX(Body other)
	{
		double force = getPairwiseForce(other);
		double r = getDistance(other);
		double forceX = force * ((other.getX() - x)/r);
		return forceX;
	}
	
	public double getPairwiseForceY(Body other)
	{
		double force = getPairwiseForce(other);
		double r = getDistance(other);
		double forceY = force * ((other.getY() - y)/r);
		return forceY;
	}

	/** calculates / sets the net force exerted on this body by all the (input) bodies */
	public void setNetForce(Body[] bodies)
	{
		xNetForce = 0;
		yNetForce = 0;
		for(int a = 0; a < bodies.length; a++)
		{
			if (this == bodies[a])
			{
				continue;
			}
			xNetForce += getPairwiseForceX(bodies[a]);
			yNetForce += getPairwiseForceY(bodies[a]);
		}
	}

	/** updates this body's accel, vel, and position, given the time step */
	public void update(double dt)
	{
		xAccel = xNetForce/mass;
		yAccel = yNetForce/mass;
		
		xVelocity += xAccel * dt;
		yVelocity += yAccel * dt;
		
		x += xVelocity * dt;
		y += yVelocity * dt;
		
	}

	/** Draws the body using the StdDraw library file's drawing method */
	public void draw()
	{
		StdDraw.picture(x, y, image);
	}
}
