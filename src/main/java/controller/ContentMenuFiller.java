package controller;
import java.util.HashMap;

public class ContentMenuFiller {

    public HashMap<Integer, String> fill(String name) {
        HashMap<Integer, String> contentMenu = new HashMap<>();
        switch(name) {
            case "Main":
                contentMenu.put(1, "Developers");
                contentMenu.put(2, "Projects");
                contentMenu.put(3, "Companies");
                contentMenu.put(4, "Customers");
                contentMenu.put(5, "Exit");
                break;
            case "Developers":
                contentMenu.put(1, "get list of all developers");
                contentMenu.put(2, "get all developer data by his name" );
                contentMenu.put(3, "a special question - get quantity of  all Java - developers");
                contentMenu.put(4, "a special question - get list of all  middle - developers");
                contentMenu.put(5, "add a developer to the database");
                contentMenu.put(6, "update data of a developer");
                contentMenu.put(7, "delete a developer from the database");
                contentMenu.put(8, "return in the previous menu");
                break;
            case "Projects":
                contentMenu.put(1, "get list of all projects");
                contentMenu.put(2, "get all project data by its name" );
                contentMenu.put(3, "a special question -  get list of all developers in a certain project");
                contentMenu.put(4, "a special question -  project expenses (a sum of salaries all its developers)");
                contentMenu.put(5, "a special question -  get a list of projects in a special format");
                contentMenu.put(6, "add a project to the database");
                contentMenu.put(7, "update data of a project");
                contentMenu.put(8, "delete a project from the database");
                contentMenu.put(9, "return in the previous menu");
                break;
            case "Companies":
                contentMenu.put(1, "get list of all companies");
                contentMenu.put(2, "add a company to the database");
                contentMenu.put(3, "delete a company from the database");
                contentMenu.put(4, "return in the previous menu");
                break;
            case "Customers":
                contentMenu.put(1, "get list of all customers");
                contentMenu.put(2, "add a customer to the database");
                contentMenu.put(3, "delete a customer from the database");
                contentMenu.put(4, "return in the previous menu");
        }
        return contentMenu;
    }

}
