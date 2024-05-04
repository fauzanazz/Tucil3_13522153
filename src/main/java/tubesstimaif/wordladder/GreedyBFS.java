package tubesstimaif.wordladder;

import java.util.*;

public class GreedyBFS implements Solver {
    private final List<String> result;
    private int NodeCount;

    public GreedyBFS() {
        this.result = new ArrayList<>();
        this.NodeCount = 0;
    }

    public Result solve(String startWord, String endWord) {
        System.gc();
        int memoryStart = (int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        long startTime = System.nanoTime();

        String tempWord = startWord;

        while (!tempWord.equals(endWord)) {
            this.result.add(tempWord);

            List<String> linkingWordList = MapParser.getWordList(tempWord);
            NodeCount++;

            if (linkingWordList.isEmpty()) {
                System.gc();
                return null;
            }

            List<String> similiarPatternList = Utils.getSimiliarPatternList(Utils.getSimiliarPattern(tempWord, endWord), linkingWordList, result);

            if (similiarPatternList.isEmpty()) {
                System.gc();
                return null;
            }

            tempWord = Utils.getMostSimiliar(similiarPatternList, endWord);
        }

        this.result.add(endWord);
        int memoryUsed = Result.getMemoryUsed(memoryStart);
        System.gc();
        return new Result(Result.getExecutionTime(startTime), memoryUsed, this.result, NodeCount);
    }

    public static class Utils {
        public static String getSimiliarPattern(String word1, String word2){
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < word1.length(); i++) {
                result.append(word1.charAt(i) == word2.charAt(i) ? word1.charAt(i) : "_");
            }
            return result.toString();
        }

        public static int getSimiliarity(String word1, String word2){
            int result = 0;
            for (int i = 0; i < word1.length(); i++) {
                if (word1.charAt(i) == word2.charAt(i)) {
                    result++;
                }
            }
            return result;
        }

        public static String getMostSimiliar(List<String> wordList, String word){
            return wordList.stream().max(Comparator.comparingInt(w -> getSimiliarity(w, word))).orElse("");
        }

        public static List<String> getSimiliarPatternList(String pattern, List<String> wordList, List<String> bannedWordList){
            List<String> result = new ArrayList<>();
            for (String temp : wordList) {
                if (!bannedWordList.contains(temp) && pattern.equals(getSimiliarPattern(temp, pattern))) {
                    result.add(temp);
                }
            }
            return result;
        }
    }
}