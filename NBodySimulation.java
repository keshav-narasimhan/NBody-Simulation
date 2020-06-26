import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NBodySimulation
{
	private Body[] bodies;    //stores all the bodies in the simulation
	private int    numBodies; //number of bodies in this simulation
	private double uRadius;   //radius of the universe
	private String fileName;  //file providing the input

	public NBodySimulation(String nameOfFile)
	{
		fileName = nameOfFile;
		File file = new File(fileName);
		
		Scanner input = null;
		
		try
		{
			input = new Scanner(file);
		}
		catch(IOException e)
		{
			System.out.println("");
		}
		
		numBodies = input.nextInt();
		uRadius = input.nextDouble();
		
		bodies = new Body[numBodies];
		
		for(int a = 0; a < numBodies; a++)
		{
			bodies[a] = new Body(input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble(), input.next());
		}

		initCanvas(); //don't move, should be the last line of the constructor
	}

	/** initialize the drawing canvas */
	private void initCanvas()
	{
		StdDraw.setScale(-uRadius, uRadius); //set canvas size / scale
		StdDraw.picture(0, 0, "starfield.jpg"); //paint background

		//below is a for-each loop, which we will cover in the next lab
		//more info is available in the powerpoints, for the curious
		for (Body body : bodies)
			body.draw();
	}

	/**
	 * run the n-bodies simulation
	 * @param T total time to run the simulation, in seconds
	 * @param dt time step, in seconds
	 */
	public void run(double T, double dt)
	{
		for(int t = 0; t < T; t += dt)
		{
			for(int a = 0; a < numBodies; a++)
			{
				bodies[a].setNetForce(bodies);
				bodies[a].update(dt);
			}
			StdDraw.picture(0,  0,  "starfield.jpg");
			
			for(int b = 0; b < numBodies; b++)
			{
				bodies[b].draw();
			}
			
			StdDraw.show(10);
		}
	}
}
