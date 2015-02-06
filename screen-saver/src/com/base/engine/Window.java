package com.base.engine;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Window
{
	//used ln. 64: errorCallback = errorCallbackPrint(System.err)
	private static GLFWErrorCallback errorCallback; 
	
    // The window handle
    private static long window;
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    public static int getWidth()
    {
    	return WIDTH;
    }
    
    public static int getHeight()
    {
    	return HEIGHT;
    }
    
    public static long getWindowLabel()
    {
    	return window;
    }
    
    public static void init()
    {
    	// Setup an error callback. The default implementation
	    // will print the error message in System.err.
	    glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
	
	    // Initialize GLFW. Most GLFW functions will not work before doing this.
	    if ( glfwInit() != GL11.GL_TRUE )
	        throw new IllegalStateException("Unable to initialize GLFW");
	
	    // Configure our window
	    glfwDefaultWindowHints(); // optional, the current window hints are already the default
	    glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
	    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	
	    // Create the window
	    window = glfwCreateWindow(WIDTH, HEIGHT, "Glory To The Wind Then!", NULL, NULL);
	    if ( window == NULL )
	        throw new RuntimeException("Failed to create the GLFW window");
	
	    // Get the resolution of the primary monitor
	    ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	    // Center our window
	    glfwSetWindowPos(
	            window,
	            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
	            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
	    );
	
	    // Make the OpenGL context current
	    System.out.println("At line 97 (glfwMakeContextCurrent) Thread name is: " + Thread.currentThread().getName());
	    glfwMakeContextCurrent(window); //IF THIS LINE IS COMMENTED, IT MAY BREAK THINGS!!!
	    GLContext.createFromCurrent();
	    //System.out.println("Current context is in window: " + glfwGetCurrentContext());
	    
	    // Enable v-sync
	    glfwSwapInterval(1);
	
	    // Make the window visible
	    glfwShowWindow(window);
	}
    
    public static void release()
    {
    	// Release window and window callbacks
        glfwDestroyWindow(window);
    }
    
    public static void terminate()
    {
    	// Terminate GLFW and release the GLFWerrorfun
        glfwTerminate();
        //errorCallback.release();
    }
}
