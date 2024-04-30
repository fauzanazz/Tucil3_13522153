package tubesstimaif.wordladder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class MapParser {
    public static Map<String, List<String>> wordList = new HashMap<String, List<String>>();

    public static boolean isWordExist(String word) {
        return wordList.containsKey(word);
    }

    public static void load() {
        wordList = new HashMap<>();
        try {
            for (int i = 1; i <= 15; i++) {
                File file = new File("src/main/java/com/mycompany/tucil3_13522164/dict/data" + i + ".txt");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String temp = scanner.nextLine();
                    int len = Integer.parseInt(scanner.nextLine());
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < len; j++) {
                        list.add(scanner.nextLine());
                    }
                    if (!wordList.containsKey(temp)) {
                        wordList.put(temp, list);
                    }
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}