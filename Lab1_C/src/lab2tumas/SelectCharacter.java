/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2tumas;

import studijosKTU.*;

/**
 *
 * @author Deimantas
 */
public class SelectCharacter {
    public ListKTUx<Character> selectByClass(String selectedClass, ListKTUx<Character> fullList) {
        ListKTUx<Character> selectedList = new ListKTUx<>();
        for (Character a : fullList) {       
            if (selectedClass.equals(a.getChClass()))
                selectedList.add(a);
        }
        return selectedList;
    }
    
    public ListKTUx<Character> selectByStats(int minAttack, int minDefence, int minAgility, ListKTUx<Character> fullList) {
        ListKTUx<Character> selectedList = new ListKTUx<>();
        for (Character a : fullList) {
            if (a.getAttack() >= minAttack && a.getDefence() >= minDefence && a.getAgility() >= minAgility)
                selectedList.add(a);
        }
        return selectedList;
    }
    
    public ListKTUx<Character> selectByLevel(int min, int max, ListKTUx<Character> fullList) {
        ListKTUx<Character> selectedList = new ListKTUx<>();
        for (Character a : fullList) {
            if (a.getChLevel() >= min && a.getChLevel() <= max)
                selectedList.add(a);
        }
        return selectedList;
    }
    
    public Character getStrongesCharacter(ListKTUx<Character> fullList) {
        Character strongest = new Character();
        int maxLevel = 0;
        double maxExperience = 0, maxStats = 0;
        
        //Stronges character is determined by these criteries in descending order:
        // 1. Level, 2. Experience, 3. Average of stats
        
        for (Character a : fullList) {
            if (a.getChLevel() > maxLevel) {
                maxLevel = a.getChLevel();
                maxExperience = a.getChExperience();
                maxStats = getAverage(a);
                strongest = a;
            }
            else if (a.getChLevel() == maxLevel) {
                if (a.getChExperience() > maxExperience) {
                    maxExperience = a.getChExperience();
                    maxStats = getAverage(a);
                    strongest = a;
                }
                else if (a.getChExperience() == maxExperience) {
                    if (getAverage(a) > maxStats) {
                        maxStats = getAverage(a);
                        strongest = a;
                    }
                }
            }
        }
        return strongest ;
    }
    
    public double getAverage (Character a) {
        return (a.getAttack() + a.getDefence() + a.getAgility()) / 3;
    }
}
