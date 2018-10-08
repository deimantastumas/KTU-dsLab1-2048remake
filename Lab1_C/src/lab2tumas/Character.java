/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2tumas;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import studijosKTU.*;

/**
 *
 * @author Deimantas
 */
public class Character implements KTUable<Character> {
    private String chClass;
    private String chName;
    private double chExperience;
    private int chLevel;                    
    private int attack, defence, agility;
    
    private static String[] availableClasses = {"Tank", "Mage", "Warrior", "Wizard"};
    private static String[] forbiddenNames = {"Carrot", "Potato"};
    
    public Character() { }

    public Character(String chName, String chClass, double chExperience, int chLevel, int a, int b, int c) {
        this.chClass = chClass;
        this.chName = chName;
        this.chExperience = chExperience;
        this.chLevel = chLevel;
        setStats(a, b, c);
    }
    
    private void setStats(int a, int b, int c) {
        attack = a;
        defence = b;
        agility = c;
    }
    
    @Override
    public void parse(String dataString) {
        try {
            Scanner ed = new Scanner(dataString);
            chName = ed.next();
            chClass = ed.next();
            chExperience = ed.nextDouble();
            chLevel = ed.nextInt();
            setStats(ed.nextInt(), ed.nextInt(), ed.nextInt());
        } catch (InputMismatchException  e) {
            Ks.ern("Blogas duomenų formatas -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + dataString);
        }
    }
    
    @Override
    public Character create(String dataString) {
        Character a = new Character();
        a.parse(dataString);
        return a;
    }

    @Override
    public String validate() {
        String errorMessage = "";
        //Check whether chosen class is available
        boolean classIsAvailable = false;
        for (String a : availableClasses) {
            if (chClass == a)
                classIsAvailable = true;
        }
        if (!classIsAvailable)
            errorMessage += "| Your chosen class is not available |";
        
        //Check whether chosen name is not forbidden
        for (String a : forbiddenNames) {
            if (chName == a) {
                errorMessage += "| Your chosen name is forbidden |";
                break;
            }
        }
        return errorMessage;
    }

    @Override
    public int compareTo(Character e) {
        int nextLevel = e.getChLevel();
        if (chLevel < nextLevel) return -1;
        if (chLevel > nextLevel) return +1;
        
        if (chLevel == nextLevel) {
            return chName.compareTo(e.getChName());
        }
        return 0;
    }
    
     public final static Comparator<Character> byName = new Comparator<Character>() {
       @Override
       public int compare(Character a1, Character a2) {
          return a1.getChName().compareTo(a2.getChName());
       }
    };
     
     public final static Comparator<Character> byClass = new Comparator<Character>() {
       @Override
       public int compare(Character a1, Character a2) {
          int cmp = a1.getChClass().compareTo(a2.getChClass());
          if (cmp == 0) {
              if (a1.getChLevel() < a2.getChLevel()) cmp = -1;
              else if (a1.getChLevel() > a2.getChLevel()) cmp = +1;
              
              if (cmp == 0) {
                  if (a1.getChExperience() < a2.getChExperience()) cmp = -1;
                  if (a1.getChExperience() > a2.getChExperience()) cmp = +1;
              }
              return cmp;
          }
          return cmp;
       }
    };

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.chName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Character other = (Character) obj;
        if (!Objects.equals(this.chName, other.chName)) {
            return false;
        }
        return true;
    }
     
     
    @Override
    public String toString(){  // surenkama visa reikalinga informacija
        return String.format("%-10s %-8s %-3d %-8.2f %-3d %-3d %-3d",
               chName, chClass, chLevel, chExperience, attack, defence, agility, validate());
    };

    public String getChClass() {
        return chClass;
    }

    public String getChName() {
        return chName;
    }

    public double getChExperience() {
        return chExperience;
    }

    public int getChLevel() {
        return chLevel;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    public double getAgility() {
        return agility;
    }
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
}
