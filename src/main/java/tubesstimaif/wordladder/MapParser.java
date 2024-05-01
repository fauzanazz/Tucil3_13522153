package tubesstimaif.wordladder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapParser {
    private static final Logger LOGGER = Logger.getLogger(MapParser.class.getName());
    public static Map<String, List<String>> wordList = new HashMap<>();

    public static boolean isWordNotExist(String word) {
        return !wordList.containsKey(word);
    }

    public static List<String> getWordList(String word) {
        return wordList.get(word);
    }

    public static void load() {
        wordList = new HashMap<>();
        try {
            for (int i = 2; i <= 15; i++) {
                File file = new File("src/main/java/tubesstimaif/wordladder/dictionary/data" + i + ".txt");
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
            LOGGER.log(Level.SEVERE, "An error occurred.", e);
        }
    }
}