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
    public static List<List<String>> dictionary;

    public static boolean isWordNotExist(String word) {
        return !wordList.containsKey(word);
    }

    public static List<String> getWordList(String word) {
        return wordList.get(word);
    }

    public static String getRandomWord(int length) {
        List<String> list = dictionary.get(length - 2);
        return list.get((int) (Math.random() * list.size()));
    }

    public static List<String> getDictionary() {
        List<String> output = new ArrayList<>();
        for (List<String> wordList : dictionary) {
            output.addAll(wordList);
        }
        return output;
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


        dictionary = new ArrayList<>();
        try {
            File file = new File("src/main/java/tubesstimaif/wordladder/dictionary/names.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                int len = Integer.parseInt(scanner.nextLine());
                List<String> list = new ArrayList<>();
                for (int j = 0; j < len; j++) {
                    list.add(scanner.nextLine());
                }
                dictionary.add(list);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "An error occurred.", e);
        }
    }
}