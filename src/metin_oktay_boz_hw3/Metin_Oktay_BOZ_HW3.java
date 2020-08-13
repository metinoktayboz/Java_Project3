/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metin_oktay_boz_hw3;


public class Metin_Oktay_BOZ_HW3 {

    public static void main(String[] args) {
        MazeClass mymaze = new MazeClass();
        mymaze.InputMaze("C:\\Users\\MONSTER\\Desktop\\dosya.txt");        
       // mymaze.FindLoops();
        
        mymaze.FindLoops("C:\\Users\\MONSTER\\Desktop\\deneme.txt");
    }
    
}
