package CS355.LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your mileage may vary. Don't feel restricted by this list of imports.
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;	// Used with glClear
import static org.lwjgl.opengl.GL11.GL_LINES;				// 
import static org.lwjgl.opengl.GL11.glMatrixMode;			// ex. glMatrixMode(GL_MODELVIEW)
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;			//
import static org.lwjgl.opengl.GL11.GL_PROJECTION;			//
import static org.lwjgl.opengl.GL11.glClear;				// Clear the screen
import static org.lwjgl.opengl.GL11.glColor3f;				// Set the color of what is drawn
import static org.lwjgl.opengl.GL11.glLoadIdentity;			// Reset the currently selected matrix
import static org.lwjgl.opengl.GL11.glPushMatrix;			// Save the currently applied matrix

					// Drawing functions
import static org.lwjgl.opengl.GL11.glBegin;				// Start drawing
import static org.lwjgl.opengl.GL11.glEnd;					// Finish drawing

					// Matrices used for drawing
import static org.lwjgl.opengl.GL11.glRotatef;				// Rotation (mod. matrix)
import static org.lwjgl.opengl.GL11.glTranslatef;			// Translation (mod. matrix)
import static org.lwjgl.opengl.GL11.glVertex3d;				// Draw point (mod. matrix)
import static org.lwjgl.opengl.GL11.glViewport;				
import static org.lwjgl.opengl.GL11.glOrtho;				// Used for orthographic mode
import static org.lwjgl.util.glu.GLU.gluPerspective;		// Used for perspective mode

/** QUESTIONS
 * On startup, how do I center the image in perspective and orthographic mode? Change the ViewPort?
 * 
 */

/**
 *
 * @author Stephen Clarkson
 */
public class StudentLWJGLController implements CS355LWJGLController 
{
	//This is a model of a house.
	//It has a single method that returns an iterator full of Line3Ds.
	//A "Line3D" is a wrapper class around two Point2Ds.
	//It should all be fairly intuitive if you look at those classes.
	//If not, I apologize.
	private WireFrame model = new HouseModel();
	private float x = 0;
	private float y = 0;
	private float z = 0;
	private float angle = 0;
	
	//This method is called to "resize" the viewport to match the screen.
	//When you first start, have it be in perspective mode.
	@Override
	public void resizeGL() 
	{
		// init OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		// Orthographic projection
//		glOrtho(-1, 1, -1, 1, 500, -1);
		// Perspective projection
		float fovy = 60f;
		float aspect = 800/600;
		float zNear = 0.1f;
		float zFar = 100f;
		gluPerspective(fovy, aspect, zNear, zFar);
//		glViewport(0, 0, 800, 600);
		glMatrixMode(GL_MODELVIEW);
	}

	@Override
	public void update() 
	{
	    // don't need to use for this lab
	}
	
	//This is called every frame, and should be responsible for keyboard updates.
	//An example keyboard event is captured below.
	//The "Keyboard" static class should contain everything you need to finish
	// this up.
	@Override
	public void updateKeyboard() 
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			System.out.println("Orthographic mode========");
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			System.out.println("Perspective mode=========");
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			y--;	// to go up we shift the world down
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			y++;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {	// forward
			z++;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {	// left
			x++;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {	// backward
			z--;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {	// right
			x--;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {	// turn to the left
			angle--;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {	// turn to the right
			angle++;
		}
	    
	}
	
	//This method is the one that actually draws to the screen.
	@Override
	public void render() 
	{
	    //This clears the screen.
	    glClear(GL_COLOR_BUFFER_BIT);
	    
	    // Set color
	    glColor3f(0.5f,0.5f,1.0f);

	    // Clear model matrix
	    glLoadIdentity();
	    // Translate
	    glTranslatef(x, y, z);
	    // Rotate
//	    System.out.println("y: " + y);
//	    System.out.println("angle: " + angle);
	    glRotatef(angle, 0, y, 0);	// only rotating about the y axis
	    
	    // Start drawing model
	    glBegin(GL_LINES);
	    Iterator<Line3D> iter = model.getLines();
	    while (iter.hasNext()) {
	    	Line3D line = iter.next();
	    	glVertex3d(line.start.x, line.start.y, line.start.z);
	    	glVertex3d(line.end.x, line.end.y, line.end.z);
	    }
	    glEnd();
	    Display.update();
	}
    
}
