package view;

import java.util.List;

public class Output {
    private static final Output INSTANCE = new Output();

    private Output() {
    }

    public static Output getInstance() { return INSTANCE;};

    public void print (List<String> result) {
        for (String line :result ) {
            System.out.println(line);
        }
    }

}

