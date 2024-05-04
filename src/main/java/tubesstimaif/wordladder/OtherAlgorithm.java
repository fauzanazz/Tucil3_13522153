package tubesstimaif.wordladder;

import java.util.Arrays;

/**
 * Kelas yang berisi algoritma yang digunakan untuk membantu algoritma path finding
 */
public class OtherAlgorithm {
    /**
     * Menghitung jarak hamming antara dua kata, Fungsi ini digunakan sebagai fungsi heuristik
     * @param word1 Kata ke-1
     * @param word2 Kata ke-2
     * @return jarak hamming antara dua kata
     */
    public static int hammingDistance(String word1, String word2) {
        int count = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the Levenshtein Distance between two strings
     * @param x The first string
     * @param y The second string
     * @return The Levenshtein Distance between x and y
     */
    static int getLevenshteinDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    /**
     * Get the minimum of numbers
     * @param numbers List of numbers
     * @return Minimum number
     */
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    /**
     * Cost of substitution
     * @param a the first character
     * @param b the second character
     * @return  0 if a == b, 1 otherwise
     */
    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
}
