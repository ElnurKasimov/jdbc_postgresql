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
                contentMenu.put(5, "Выйти из программы");
                break;
            case "Developers":
                contentMenu.put(1, "вывести список всех разработчиков");
                contentMenu.put(2, "по фамилии и имени разработчика получить по нему все данные" );
                contentMenu.put(3, "специальный вопрос - сколько всех Java - разработчиков");
                contentMenu.put(4, "специальный вопрос - список всех middle - разработчиков");
                contentMenu.put(5, "добавить разработчика");
                contentMenu.put(6, "изменить данные разработчика");
                contentMenu.put(7, "удалить разработчика");
                contentMenu.put(8, "перейти в верхнее меню");
                break;
            case "Projects":
                contentMenu.put(1, "вывести список всех проектов");
                contentMenu.put(2, "по названию проекта вы получите по нему расширенные данные" );
                contentMenu.put(3, "специальный вопрос -  список всех разработчиков конкретного проекта");
                contentMenu.put(4, "специальный вопрос -  затратная часть конкретного проекта (сумму зарплат всех его разработчиков)");
                contentMenu.put(5, "специальный вопрос -  список проектов в следующем формате:\n " +
                        "\t\tдата создания - название проекта - количество разработчиков на этом проекте");
                contentMenu.put(6, "добавить проект");
                contentMenu.put(7, "изменить данные проекта");
                contentMenu.put(8, "удалить проект");
                contentMenu.put(9, "перейти в верхнее меню");
                break;
            case "Companies":
                contentMenu.put(1, "вывести список всех компаний");
                contentMenu.put(2, "добавить компанию");
                contentMenu.put(3, "удалить компанию");
                contentMenu.put(4, "перейти в верхнее меню");
                break;
            case "Customers":
                contentMenu.put(1, "вывести список всех заказчиков");
                contentMenu.put(2, "добавить заказчика");
                contentMenu.put(3, "удалить заказчика");
                contentMenu.put(4, "перейти в верхнее меню");
        }
        return contentMenu;
    }
}
