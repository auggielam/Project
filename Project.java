import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import java.util.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.*;
import java.io.*;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.scene.input.*;

public class Project extends Application
{
   //declaring all necessary components
   BorderPane root = new BorderPane();
   ProjCan can = new ProjCan();
   Label win = new Label();
   
   //creating 2D array for the maze
   int[][] maze = new int[21][21];
   GraphicsContext gc = can.getGraphicsContext2D();
   
   //creating x and y position variables
   int x = 0;
   int y = 0;
   
   //declaring start method
   public void start(Stage stage)
   {     
   //adding canvas to the root
      root.getChildren().add(can);
      //calling my draw method
      draw();      
      //nested for loops to set the color for the squares of the maze
      for(int k = 0; k < 21; k++)
      {
         for(int l = 0; l < 21; l++)
         {  
            if(maze[k][l] == 0)
            {
               gc.setFill(Color.WHITE);
               if(x == 0)
               {
               //setting the position of the initial player position
                  x = l;
                  gc.setFill(Color.CYAN);
               }

            }
            else if(maze[k][l] == 1)
            {
               gc.setFill(Color.BLACK);              
            }
            //drawing all rects
            gc.fillRect(l*25, k*25, 25, 25);    
         }
      }
      
      //calling method to register which keys are pressed
      root.setOnKeyPressed(new KeyListener());
      
      //setting scene
      Scene scene = new Scene(root, 525, 525); 
      stage.setScene(scene);      
      stage.setTitle("Maze!");      
      stage.show();
      
      root.requestFocus();
   }
   //launching application
   public static void main(String[] args)
   {
      launch(args); 
   }
   
   //creating canvas class
   public class ProjCan extends Canvas
   {
      public ProjCan()
      {
      //setting size of canvas
         setWidth(525);
         setHeight(525);
      }
   }
   //creating draw method
   public void draw(){
       try
       {
       //scanning file to put into the array
         Scanner scan = new Scanner(new File("mazeData.txt"));
         for(int i = 0; i < 21; i++)
         {
            for(int j = 0; j < 21; j++)
            {
               maze[i][j] = scan.nextInt();
            }
         }
      
      }catch(FileNotFoundException fnfe)
      {
         System.out.println("File not found!");
      }
   }
   //creating keylistener class
   public class KeyListener implements EventHandler<KeyEvent>{
      public void handle(KeyEvent event)
      {         
      //if up arrow is pressed, then the y position of the square changes
         if(event.getCode().equals(KeyCode.UP))
         {
            try
            {
               if(maze[y-1][x] == 0)
               {
                  y--;
               }
            }catch(ArrayIndexOutOfBoundsException e)
            {
            }
         }
         //if down arrow is pressed, then the y position of the square changes
         if(event.getCode().equals(KeyCode.DOWN))
         {
            try
            {
               if(maze[y+1][x] == 0)
               {
                  y++;
               }
            }catch(ArrayIndexOutOfBoundsException e)
            {
            
            }
         }
         //if left arrow is pressed, then the x position of the square changes
         if(event.getCode().equals(KeyCode.LEFT))
         {
            try
            {   
               if(maze[y][x-1] == 0)
               {   
                  x--;
               }
            }catch(ArrayIndexOutOfBoundsException e)
            {
            }
         }  
         //if right arrow is pressed, then the x position of the square changes
         if(event.getCode().equals(KeyCode.RIGHT))
         {
            try
            {   
               if(maze[y][x+1] == 0)
               {   
                  x++;
               }
            }catch(ArrayIndexOutOfBoundsException e)
            {
            }
         }
         //calling the draw method and setting the new position of the player
         draw();
         maze[y][x] = 2;
         
         //seeing if the y-position has reached the end of the maze to see if the player has won
         if(y == 20)
         {
            root.setCenter(win);
            win.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY))); 
            win.setText("You win!");
         }
         
         //nested for loops to set the color of the squares according to the value of the array element
         for(int m = 0; m < 21; m++)
         {
            for(int n = 0; n < 21; n++)
            {  
               if(maze[m][n] == 2)
               {
                  gc.setFill(Color.CYAN);
               }
               else if(maze[m][n] == 1)
               {
                  gc.setFill(Color.BLACK);
               }
               else if(maze[m][n] == 0)
               {
                  gc.setFill(Color.WHITE);
               }
               gc.fillRect(n*25, m*25, 25, 25);
             }  
         }
      }
   }
}