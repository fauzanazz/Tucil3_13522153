package tubesstimaif.wordladder;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.AbstractMap.SimpleEntry;

/**
 * Kelas yang digunakan untuk memparsing file-file yang berisi kata-kata
 * dan membuat struktur data yang diperlukan untuk menyelesaikan word ladder
 * Struktur data yang dibuat adalah Map<String, List<String>> yang berisi kata-kata
 * dan List<List<SimpleEntry<String, String>>> yang berisi kata-kata dan definisinya
 */
public class Parser {

    /*
        * wordList adalah map yang menyimpan kata-kata yang ada pada file
        * dictionary adalah list yang menyimpan kata-kata dan definisinya
        * LOGGER adalah logger yang digunakan untuk mencatat log
     */
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    public static Map<String, List<String>> wordList = new HashMap<>();
    public static List<List<SimpleEntry<String, String>>> dictionary;

    /**
     * Fungsi untuk mengecek apakah kata ada pada kamus
     * @param word kata yang ingin dicek
     * @return true jika kata tidak ada pada kamus, false jika kata ada pada kamus
     */
    public static boolean isWordNotExist(String word) {
        return !wordList.containsKey(word);
    }

    /**
     * Fungsi untuk mendapatkan list kata yang berjarak satu karakter dari kata yang diberikan
     * @param word kata yang ingin dicari
     * @return list kata yang berjarak satu karakter dari kata yang diberikan
     */
    public static List<String> getWordList(String word) {
        return wordList.get(word);
    }

    /**
     * Fungsi untuk mendapatkan kata acak dari kamus
     * @param length panjang kata yang ingin dicari
     * @return kata acak dari kamus
     */
    public static String getRandomWord(int length) {
        List<SimpleEntry<String, String>> list = dictionary.get(length);
        return list.get((int) (Math.random() * list.size())).getKey();
    }

    /**
     * Fungsi untuk memasukkan hasil pencarian dari file ke dalam cache
     * @return hasil pencarian dari file
     */
    public static Map<String, Result> load_search() {
        Map<String, Result> searchCache = new HashMap<>();
        try (FileInputStream fis = new FileInputStream("searchCache.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            searchCache = (Map<String, Result>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return searchCache;
    }

    /**
     * Fungsi untuk mendapatkan seluruh kata yang ada di kamus
     * @return seluruh kata yang ada di kamus
     */
    public static List<SimpleEntry<String, String>> getDictionary() {
        List<SimpleEntry<String, String>> output = new ArrayList<>();
        for (List<SimpleEntry<String, String>> words : dictionary) {
            output.addAll(words);
        }
        return output;
    }


    /**
     * Fungsi untuk memuat kata-kata dari file-file yang ada
     */
    public static void load() {
        wordList = new HashMap<>();
        try {
            for (int i = 2; i <= 8; i++) {
                InputStream in = Parser.class.getResourceAsStream("/dictionary/data" + i + ".txt");
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
            InputStream in = Parser.class.getResourceAsStream("/dictionary/names.txt");
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