/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2tumas;

import java.util.Comparator;
import studijosKTU.*;

/**
 *
 * @author Deimantas
 */
public class CharacterTesting {
    ListKTUx<Character> allCharacters = new ListKTUx<>(new Character());
    ListKTUx<Character> testSubjects = new ListKTUx<>(new Character());
    final String[] data = {
        "Audron Tank 2000.10 33 156 231 18",
        "Anderson Mage 23.6 36 26 32 156",
        "Aston Warrior 984.8 1 165 231 153",
        "Allison Wizard 123.4 47 165 32 15",
        "Aaron Tank 79.62 99 951 321 465",
        "Michael Mage 115.1 80 23 18 156",
        "Peter Warrior 190.9 5 615 21 65",
        "Johnny Wizard 2115.13 27 159 321 489",
        "Slayer Tank 1651.12 33 354 126 849",
        "Jackson Mage 2.6 33 555 161 321",
        "Henry Warrior 156.5 26 156 12 156",
        "Ford Wizard 915.13 1 189 156 348"
    };
    
    public void checkFeatures() {
        ListKTUx<Character> testSubjects2 = new ListKTUx<>();
        
        testSubjects.remove(0);
        testSubjects.remove(3);
        testSubjects.remove(30);
        printList("Modified list #1", testSubjects);
        
        testSubjects.removeFirstOccurrence(new Character("Aston", "Warrior", 984.8, 1, 165, 231, 153));
        printList("Modified list #2", testSubjects);
        
        testSubjects.push(new Character("Donelaitis", "Wizard", 9999.9, 99, 999, 999, 999));
        
        testSubjects2.addAll(testSubjects);
        printList("New list", testSubjects2); 
    }
    
    public void getSpecificCharacters() {
        SelectCharacter a = new SelectCharacter();
        
        ListKTUx<Character> mages = new ListKTUx<>();
        mages = a.selectByClass("Mage", allCharacters);
        printList("All mages", mages);
        
        ListKTUx<Character> eliteClan = new ListKTUx<>();
        int minAttack = 150, minDefence = 200, minAgility = 200;
        eliteClan = a.selectByStats(minAttack, minDefence, minAgility, allCharacters);
        printList("All characters that meet required stats", eliteClan);
        
        ListKTUx<Character> experiencedCharacters = new ListKTUx<>();
        int minLevel = 50, maxLevel = 99;
        experiencedCharacters = a.selectByLevel(minLevel, maxLevel, allCharacters);
        printList("All characters that meet required level", experiencedCharacters);
        
        Character strongestCharacter = new Character();
        strongestCharacter = a.getStrongesCharacter(allCharacters);
        Ks.oun(strongestCharacter);
    }
    
    public void executeMethods() {
        fillList();
        checkSorting();
        checkFeatures();
        getSpecificCharacters();
    }
    
    public void checkSorting() {
        sortList(0, testSubjects);
        printList("Characters sorted by level and name", testSubjects);

        sortList(1, testSubjects);
        printList("Characters sorted by name", testSubjects);
        
        sortList(2, testSubjects);
        printList("Characters sorted by class and level", testSubjects);
    }
    
    public void fillList() {
        for (String a : data) {
            Character temp = new Character();
            temp = temp.create(a);
            testSubjects.add(temp);
        }
        
        allCharacters.addAll(testSubjects);
        printList("All characters", allCharacters);
    }
    
    public void printList(String key, ListKTUx<Character> list) {
        list.println(key);
    }
    
    public boolean sortList(int sortNumber, ListKTUx<Character> list) {
        // sortNumber: 0(default) - by level and name, 1 - by name, 2 - by class and level
        switch (sortNumber) {
            case 0: list.sortBuble(); return true;
            case 1: list.sortBuble(Character.byName); return true;
            case 2: list.sortBuble(Character.byClass); return true;
            default: System.out.println("Wrong sort number!"); return false;
        }
    }
    
    public static void main(String... args) {
        new CharacterTesting().executeMethods();
    }
    
}
