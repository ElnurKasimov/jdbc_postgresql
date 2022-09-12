package controller;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Data
public class Menu {
    private String name;
    private HashMap<Integer, String> contentMenu;

    public Menu  (String name) {
        this.name = name;
    }

    public void printMenu () {
        if(name.equals("Main")) {System.out.println("\tYou can manage  such tables in the DB :");}
        else {System.out.println("\tYou can operate such data in the table:");}
        for (Map.Entry<Integer, String> element : contentMenu.entrySet()) {
            System.out.println(element.getKey() + " - " + element.getValue());
        }
        System.out.print("Make Your choice (1,2,3 etc.) : ");
    }

    public int makeChoice() {
        int result = 0;
        boolean isRightChoice = true;
        Scanner sc = new Scanner(System.in);
        do {
            try{
                result = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("\tInvalid value. Use digits.");
            }
            isRightChoice = checkChoice(result);
        } while (!isRightChoice);
        return result;
    }

    public boolean checkChoice(int actualChoice) {
        boolean result = false;
        for (Map.Entry<Integer, String> element : getContentMenu().entrySet()) {
            if (element.getKey() == actualChoice) {
                result = true;
            }
        }
        if (result) return result;
        else {
            System.out.print("\tYour choice isn't correct, repeat please :");
            return result;
        }
    }
}

