package com.base.engine;

import com.base.game.Game;
import com.base.game.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main
{	
	public static void main(String[] args)
	{		
		System.out.println("At line 11 (main) Thread name is: " + Thread.currentThread().getName());
		System.out.println("At line 11 (start of main) context is in window: " + glfwGetCurrentContext());
		try
		{
			initDisplay();
			initGL();
			initGame();
			
			gameLoop();

            cleanUp();
        } 
		finally
		{
			Window.terminate();
        }

	}
	
	private static void initGame()
	{
		Game.game = new Game();
	}
	
	private static void getInput()
	{
		Game.game.getInput();
	}
	
	private static void update()
	{
		Game.game.update();
	}
	
	private static void render()
	{
		/*
		 * The following is obsolete, but may still function
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	  	glClear(GL_COLOR_BUFFER_BIT);
		//glColor3f(1.0f, 1.0f, 1.0f); // unsupported line
		glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
		glBegin(GL_POLYGON);
			glVertex3f (0.25f, 0.25f, 0.0f);
		    glVertex3f (0.75f, 0.25f, 0.0f);
		    glVertex3f (0.75f, 0.75f, 0.0f);
		    glVertex3f (0.25f, 0.75f, 0.0f);
		glEnd();
		glFlush();
		*/ 
		
		Game.game.render();
		
	}
	
	private static void initDisplay()
	{
		Window.init();
	}
	
	private static void initGL()
	{
		//glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	  	//glClear(GL_COLOR_BUFFER_BIT);
		//glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void gameLoop()
	{
		Time.init();
		
		while (glfwWindowShouldClose(Window.getWindowLabel()) == GL_FALSE)
		{
			Time.update();
			getInput();
			update();
			render();
		}
	}
	
	private static void cleanUp()
	{
		Window.release();
        Game.releaseInput();
	}
}