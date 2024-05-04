package tubesstimaif.wordladder;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.AbstractMap.SimpleEntry;

public class MapParser {

    private static final Logger LOGGER = Logger.getLogger(MapParser.class.getName());
    public static Map<String, List<String>> wordList = new HashMap<>();
    public static List<List<SimpleEntry<String, String>>> dictionary;

    public static boolean isWordNotExist(String word) {
        return !wordList.containsKey(word);
    }

    public static List<String> getWordList(String word) {
        return wordList.get(word);
    }

    public static String getRandomWord(int length) {
        List<SimpleEntry<String, String>> list = dictionary.get(length);
        return list.get((int) (Math.random() * list.size())).getKey();
    }

    public static List<SimpleEntry<String, String>> getDictionary() {
        List<SimpleEntry<String, String>> output = new ArrayList<>();
        for (List<SimpleEntry<String, String>> words : dictionary) {
            output.addAll(words);
        }
        return output;
    }


    public static void load() {
        wordList = new HashMap<>();
        try {
            for (int i = 2; i <= 15; i++) {
                InputStream in = MapParser.class.getResourceAsStream("/dictionary/data" + i + ".txt");
                assert in != null;
                Scanner scanner = new Scanner(in);
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred.", e);
        }

        dictionary = new ArrayList<>();
        try {
            InputStream in = MapParser.class.getResourceAsStream("/dictionary/names.txt");
            assert in != null;
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", 2);
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String definition = parts[1].trim();
                    AbstractMap.SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry<>(name, definition);
                    while (dictionary.size() <= name.length()) {
                        dictionary.add(new ArrayList<>());
                    }
                    dictionary.get(name.length()).add(entry);
                }
            }
            scanner.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred.", e);
        }
    }
}