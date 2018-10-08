/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2tumas;

import java.util.ArrayList;
import java.util.Random;
import studijosKTU.*;

/**
 *
 * @author Deimantas
 */
public class SpeedPerformace {
    int[] experiments = {2_000, 4_000, 8_000, 16_000};
    ArrayList<Integer> arrayNumbers = new ArrayList();
    
    public static void main(String[] args){
        new SpeedPerformace().getTime();
   }
    
    public void fillArrayList() {
        Random a = new Random();
        a.setSeed(96845);
        
        for (int i = 0; i < 10000; i++) {
            arrayNumbers.add(a.nextInt(300));
        } 
    }
    
    public void getTime() {
        fillArrayList();
        
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= "+memTotal + " bytes");
        
        
        Timekeeper timer = new Timekeeper(experiments);
        for (int count : experiments) {
            timer.start();
            Math.pow(27, 1.0/3);
            timer.finish("Math.pow");
            Math.cbrt(27);
            timer.finish("Math.cbrt");
            timer.seriesFinish();
        }
        System.out.println("");
        
        Timekeeper timer1 = new Timekeeper(experiments);
        for (int count : experiments) {
            timer1.start();
            arrayNumbers.indexOf(5);
            timer1.finish("indexOf(object o)");
            arrayNumbers.lastIndexOf(5);
            timer1.finish("lastIndexOf(object o)");
            timer1.seriesFinish();
        }
    }
}
