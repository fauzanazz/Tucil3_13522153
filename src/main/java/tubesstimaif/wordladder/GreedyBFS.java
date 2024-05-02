package tubesstimaif.wordladder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GreedyBFS implements Solver {
    private List<String> result;
    private int NodeCount;

    public GreedyBFS() {
        this.result = new ArrayList<String>();
        this.NodeCount = 0;
    }

    public Result solve(String startWord, String endWord) {
        System.gc();
        long startTime = System.nanoTime();

        int lengthWord = startWord.length();
        String tempWord = startWord;

        while (Utils.getSimiliarity(tempWord, endWord) != lengthWord) {
            this.result.add(tempWord);

            List<String> linkingWordList = MapParser.getWordList(tempWord);
            NodeCount++;

            // No Solution
            if (linkingWordList.isEmpty()) {
                System.out.println("No Solution");
                return null;
            }

            String pattern = Utils.getSimiliarPattern(tempWord, endWord);
            List<String> similiarPatternList = Utils.getSimiliarPatternList(pattern, linkingWordList, result);

            // No Solution
            if (similiarPatternList.isEmpty()) {
                // Cheat Solution
                break;
            }

            // Get Most Similiar
            tempWord = Utils.getMostSimiliar(similiarPatternList, endWord);
        }


        this.result.add(endWord);
        int executionTime = (int) ((System.nanoTime() - startTime) / 1000000);
        int memoryUsed = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

        System.gc();
        return new Result(executionTime, memoryUsed, this.result, NodeCount);
    }


    public static class Utils {
        public static String getSimiliarPattern(String word1, String word2){
            if (word1.length() != word2.length()) {
                return "";
            }
            int length = word1.length();
            String result = "";
            for (int i = 0; i < length; i++) {
                if (word1.charAt(i) != word2.charAt(i)) {
                    result += "_";
                } else {
                    result += word1.charAt(i);
                }
            }
            return result;
        }

        public static int getSimiliarity(String word1, String word2){
            if (word1.length() != word2.length()) {
                return 0;
            }
            int length = word1.length();
            int result = 0;
            for (int i = 0; i < length; i++) {
                if (word1.charAt(i) == word2.charAt(i)) {
                    result++;
                }
            }
            return result;
        }

        public static String getMostSimiliar(List<String> wordList, String word){
            int maxSimiliarity = -1;
            List<String> resultList = new ArrayList<String>();
            for (String temp : wordList) {
                int similiarity = getSimiliarity(temp, word);
                if (similiarity >= maxSimiliarity) {
                    maxSimiliarity = similiarity;
                    resultList.add(temp);
                }
                if (similiarity == word.length()) {
                    return temp;
                }
            }
            return resultList.get(new Random().nextInt(resultList.size()));
        }

        public static List<String> getSimiliarPatternList(String pattern, List<String> wordList, List<String> bannedWordList){
            List<String> result = new ArrayList<String>();
            for (String temp : wordList) {
                if (!bannedWordList.contains(temp) && isPatternMatch(pattern, temp)) {
                    result.add(temp);
                }
            }
            return result;
        }

        public static List<String> getSimiliarPatternListFromMap(String pattern, List<String> bannedWordList){
            List<String> result = new ArrayList<String>();
            for (String temp : MapParser.wordList.keySet()) {
                if (!bannedWordList.contains(temp) && isPatternMatch(pattern, temp)) {
                    result.add(temp);
                }
            }
            return result;
        }

        private static boolean isPatternMatch(String pattern, String word){
            if (pattern.length() != word.length()) {
                return false;
            }
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '_' && pattern.charAt(i) != word.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }
}