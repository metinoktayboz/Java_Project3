package metin_oktay_boz_hw3;


import metin_oktay_boz_hw3.MazeInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MONSTER
 */
public class MazeClass implements MazeInterface {
    public char[][] maze;  //maze array
    public int column;     
    public int row;
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();  //alphabet array which is used to find next character
    static MyStack<Position> path = new MyStack<Position>();       //path stack which is used to store loop locations
    static MyStack<Position> out = new MyStack<Position>();        //out stack which is used to store for printing loop locations
    public int alp=0;
    public int loop=0;
    public String[] outp = new String[100];

    @Override
    public void InputMaze(String FileName) {
        
        int[] size = new int[2];
        int i=0;
        int count=0;
        String txtmaze="";
        String newmaze;
        
       File f = new File(FileName);  //to scan file
        try {
            Scanner dosya = new Scanner(f);   
            while(dosya.hasNextLine()) {   //to read datas from file
            if(dosya.hasNextInt()){                
                size[i] = Integer.parseInt(dosya.next());     //to take row and column datas           
                i++;
            }
            else{
                txtmaze+=dosya.nextLine();     //to take maze from file
            }            
    }
                row = size[0];
                column = size[1];
                newmaze= txtmaze.replaceAll("\\s+","");    //to delete blanks from string
                char[][] maze= new char[row+2][column+2];  // 
                this.maze = maze;
                for (int j = 0; j <= column+1 ; j++) {
		      maze[0][j] = '*';
                      maze[row+1][j] = '*';// bottom and top
		      }
                for (int j = 0; j <= row+1; j++) {
		    maze[j][0] = '*';
                    maze[j][column+1] = '*';// left and right 
		      }		     
                    
                    for(int j=1; j<=row;j++){      //to fill the maze with datas
                        for(int k=1; k<=column; k++){                                                      
                        maze[j][k]=newmaze.charAt(count);
                        count++;    
                        }
                    }
                    
                    
        } catch (FileNotFoundException ex) {
            System.out.println("File can not be found!");
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Array bounds are invalid! Please try again.");
        }
        catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Array is not completed! Please control the array.");
        }
                    
    }
    @Override
    public void FindLoops() {
                Position [] offset = new Position[4];
                for(int i =0; i<outp.length; i++){
                    outp[i]="";
                }
		for(int i = 0; i <= 3; i++){
	        offset[i] = new Position();
                }
		offset[0].row = 0; 
		offset[0].col = 1; // right
		offset[1].row = 1;
		offset[1].col = 0; // down
		offset[2].row = 0; 
		offset[2].col = -1; // left
		offset[3].row = -1; 
		offset[3].col = 0; // up
                
                Position here = new Position();
                Position tempo = new Position();
		   Position next = new Position();
		   here.row = 1;
		   here.col = 1;
                   int option = 0; // next move
		   int LastOption = 3;
                   int arow=0;    
                   int acol=0;    //to control loop's final location with first char 'a'
                   try{
                   while(here.row != row || here.col != column){
                      // find a neighbor to move to
		      int r = 0;
		      int c = 0;
                      
                      if(maze[here.row][here.col]==alphabet[alp]){
                      while (option <= LastOption) {
		         r = here.row + offset[option].row;
		         c = here.col + offset[option].col;
		         if (maze[r][c] == alphabet[alp+1]){
                             if(alp==0){
                             arow =here.row;
                             acol = here.col;                                 
                             }
                             path.push(here);
                             alp+=1;
                             break;
                         }
                      if (r==arow && c==acol && alp>2){ //to complete loops
                      path.push(here);
                      maze[here.row][here.col] ='?';
		      while (!path.IsEmpty()) {  //from path to out stack 
		      here = path.pop();
                      out.push(here);                                         
                      if(path.IsEmpty()){   // to go first location and continue to search
                        option = -1;
                        alp = 0;
                      }
                         }
                             while(!out.IsEmpty()){   //to fill string array to print loops at the end
                                 tempo = out.pop();
                                 outp[loop] +=  tempo.row +"," + tempo.col + " - ";
                             }
                             outp[loop] = outp[loop].substring(0, outp[loop].length() - 2);
                             loop+=1;
                         }
		         option++; // next option
		         }
                      if (option <= LastOption) {// move to maze[r][c]
		    	 here = new Position(r,c);
		         here.row = r; 
		         here.col = c;
		         option = 0;                         
		         }
                      else {// no neighbor to move to, back up
		         if (path.IsEmpty()){  //to go next column
                             here.col+=1;
                             option =0;
                             if(here.col>column){  //to go next row
                                 here.col = 1;
                                 here.row +=1;
                             }
                         } else {
                         next = path.pop();   //to go back
		         if (next.row == here.row){   
		         option = 2 + next.col - here.col;
                         alp-=1;
                         }
                         else{ 
                         alp-=1;    
                         option = 3 + next.row - here.row;                         
                         }
		         here = next;
                         }
		         }
                      }else {
                        here.col+=1;
                        option = 0;
                        if(here.col>column){
                        here.col = 1;
                        here.row +=1;
                             }
                      }                                                
                   }
                   System.out.println("The maze has "+loop+" loops:");   //to print loops
                   for(int i = 0; i<outp.length; i++){
                      if(outp[i]!=""){
                           System.out.print("Loop " + (i+1)+": ");
                           System.out.print(outp[i]);
                           System.out.println("");
                      }
                   }
                   }
                   catch(java.lang.NullPointerException ex){
                       System.out.println("Maze file can not be found!");
                   }
    }

    @Override
    public void FindLoops(String FileName) {
        try{  //Try - catch for invalid locations
         
     FileWriter fw = new FileWriter(FileName);
     PrintWriter pw = new PrintWriter(fw);
     
     Position [] offset = new Position[4];
                for(int i =0; i<outp.length; i++){
                    outp[i]="";
                }
		for(int i = 0; i <= 3; i++){
	        offset[i] = new Position();
                }
		offset[0].row = 0; 
		offset[0].col = 1; // right
		offset[1].row = 1;
		offset[1].col = 0; // down
		offset[2].row = 0; 
		offset[2].col = -1; // left
		offset[3].row = -1; 
		offset[3].col = 0; // up
                
                Position here = new Position();
                Position tempo = new Position();
		   Position next = new Position();
		   here.row = 1;
		   here.col = 1;
                   int option = 0; // next move
		   int LastOption = 3;
                   int arow=0;    
                   int acol=0;    //to control loop's final location with first char 'a'
                   try{
                   while(here.row != row || here.col != column){
                      // find a neighbor to move to
		      int r = 0;
		      int c = 0;
                      
                      if(maze[here.row][here.col]==alphabet[alp]){
                      while (option <= LastOption) {
		         r = here.row + offset[option].row;
		         c = here.col + offset[option].col;
		         if (maze[r][c] == alphabet[alp+1]){
                             if(alp==0){
                             arow =here.row;
                             acol = here.col;                                 
                             }
                             path.push(here);
                             alp+=1;
                             break;
                         }
                      if (r==arow && c==acol && alp>2){ //to complete loops
                      path.push(here);
                      maze[here.row][here.col] ='?';
		      while (!path.IsEmpty()) {  //from path to out stack 
		      here = path.pop();
                      out.push(here);                                         
                      if(path.IsEmpty()){   // to go first location and continue to search
                        option = -1;
                        alp = 0;
                      }
                         }
                             while(!out.IsEmpty()){   //to fill string array to print loops at the end
                                 tempo = out.pop();
                                 outp[loop] +=  tempo.row +"," + tempo.col + " - ";
                             }
                             outp[loop] = outp[loop].substring(0, outp[loop].length() - 2);
                             loop+=1;
                         }
		         option++; // next option
		         }
                      if (option <= LastOption) {// move to maze[r][c]
		    	 here = new Position(r,c);
		         here.row = r; 
		         here.col = c;
		         option = 0;                         
		         }
                      else {// no neighbor to move to, back up
		         if (path.IsEmpty()){  //to go next column
                             here.col+=1;
                             option =0;
                             if(here.col>column){  //to go next row
                                 here.col = 1;
                                 here.row +=1;
                             }
                         } else {
                         next = path.pop();   //to go back
		         if (next.row == here.row){   
		         option = 2 + next.col - here.col;
                         alp-=1;
                         }
                         else{ 
                         alp-=1;    
                         option = 3 + next.row - here.row;                         
                         }
		         here = next;
                         }
		         }
                      }else {
                        here.col+=1;
                        option = 0;
                        if(here.col>column){
                        here.col = 1;
                        here.row +=1;
                             }
                      }                                                
                   }
                   pw.println("This program has been written by: Metin Oktay BOZ a.k.a. mo-B");
                   pw.println("The maze has "+loop+" loops:");   //to print loops
                   for(int i = 0; i<outp.length; i++){
                      if(outp[i]!=""){
                           pw.print("Loop " + (i+1)+": ");
                           pw.print(outp[i]);
                           pw.println("");
                      }
                   }
                   }
                   catch(java.lang.NullPointerException ex){
                       pw.println("Maze file can not be found!");
                   }     
     pw.close();
     
     }  catch (IOException ex) {
            Logger.getLogger(MazeClass.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
