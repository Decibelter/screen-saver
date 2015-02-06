package com.base.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.awt.Rectangle;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.base.engine.GameObject;
import com.base.engine.Physics;
import com.base.engine.Window;
import com.base.game.gameobject.Imp;
import com.base.game.gameobject.Player;
import com.base.game.gameobject.item.Cube;

public class Game
{	
	private static final int VERTSPERGO = 18;
	public static Game game;
	public static FloatBuffer vertices;
	
	private static GLFWKeyCallback keyCallback;
	
	private ArrayList<GameObject> objects;
	private ArrayList<GameObject> remove;
	private Player player;
	
	public Game()
	{
		objects = new ArrayList<GameObject>();
		remove = new ArrayList<GameObject>();
		
		player = new Player(Window.getWidth() / 2 - Player.SIZE / 2, Window.getHeight() / 2 - Player.SIZE / 2);
		
		vertices = BufferUtils.createFloatBuffer(0);
		
		objects.add(player);
		objects.add(new Cube(32, 32));
		objects.add(new Imp(300, 500, 1));
	}
	
	public void getInput()
	{
		//player.getInput(); //scrapped as game should detect inputs, which classes act on
		//There is nothing here glfwSetKeyCallback() must know about the window, so moved to initDisplay()
		//Moved back. GAME WANT INPUT. NO INPUT FOR PUNY WINDOW.
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(Window.getWindowLabel(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in gameLoop
            }
        });
	}
	
	public static void releaseInput()
	{
        keyCallback.release();
	}
	
	public void update()
	{
		for(GameObject go : objects )
		{
			if(!go.getRemove())
				go.update();
			else
			remove.add(go);
		}
		
		for(GameObject go : remove)
			objects.remove(go);
		
		//TODO: WorldUI.update();
	}
	
	public void render()
	{
		//setCurrentBuffer(objects.size());
		vertices = BufferUtils.createFloatBuffer(objects.size() * VERTSPERGO );
		for(GameObject go : objects)
			go.render();
		
		int vbo = glGenBuffers();
		int vao = glGenVertexArrays();
		
		glBindBuffer (GL_ARRAY_BUFFER, vbo);
        glBufferData (GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindVertexArray(vao);

        glEnableVertexAttribArray (0);
        glBindBuffer (GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer (0, 3, GL_FLOAT, false, 0, 0);
        
        final String vertex_shader =
                "#version 410\n" +
                        "in vec3 vp;\n" +
                        "void main () {\n" +
                        "  gl_Position = vec4 (vp, 1.0);\n" +
                        "}";

        final String frag_shader =
                "#version 400\n"    +
                        "out vec4 frag_colour;" +
                        "void main () {"         +
                        "  frag_colour = vec4 (0.5, 0.0, 0.5, 1.0);" +
                        "}";

        int shader_programme = glCreateProgram();


        int vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, vertex_shader);
        glCompileShader (vertexShaderID);

        if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(vertexShaderID, 1024));
            System.exit(1);
        }

        glAttachShader (shader_programme, vertexShaderID);

        int fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, frag_shader);
        glCompileShader (fragmentShaderID);

        if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(fragmentShaderID, 1024));
            System.exit(1);
        }

        glAttachShader (shader_programme, fragmentShaderID);

        glLinkProgram (shader_programme);

        if(glGetProgrami(shader_programme, GL_LINK_STATUS) == 0){
            System.err.println(glGetProgramInfoLog(shader_programme, 1024));
            System.exit(1);
        }

        glValidateProgram(shader_programme);

        if(glGetProgrami(shader_programme, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetProgramInfoLog(shader_programme, 1024));
            System.exit(1);
        }
        
        glBindVertexArray (vao);
        // draw points 0-3 from the currently bound VAO with current in-use shader
        
        glDrawArrays(GL_TRIANGLES, 0, objects.size() * VERTSPERGO);
        // update other events like input handling
        glfwPollEvents ();
        // put the stuff we've been drawing onto the display
        glfwSwapBuffers (Window.getWindowLabel());
	}
	
	public void setCurrentBuffer(int verts)
	{
		vertices = BufferUtils.createFloatBuffer(verts);
	}
	
	public ArrayList<GameObject> getObjects()
	{
		return objects;
	}
	
	public static ArrayList<GameObject> sphereCollide(float x, float y, float radius)
	{
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		
		for(GameObject go : game.getObjects())
		{
			if(Util.dist(go.getX(), go.getY(), x, y) < radius)
				res.add(go);
		}
		
		return res;
	}
	
	public static ArrayList<GameObject> rectangleCollide(float x1, float y1, float x2, float y2)
	{
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		
		float sx = x2 - x1;
		float sy = y2 - y1;
		
		Rectangle collider = new Rectangle((int)x1, (int)y1, (int)sx, (int)sy);
		
		for(GameObject go : game.getObjects())
		{
			if(Physics.checkCollision(collider, go) != null)
				res.add(go);
		}
		
		return res;
	}
}
