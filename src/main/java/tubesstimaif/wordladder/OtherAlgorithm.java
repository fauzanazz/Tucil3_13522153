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

        // To check how many characters need to be added to x to make it y
        for (int i = 0; i <= x.length(); i++) {
            dp[i][0] = i;
        }
        // To check how many characters need to be added to y to make it x
        for (int j = 0; j <= y.length(); j++) {
            dp[0][j] = j;
        }

        // Fill the dp table
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                int substitutionCost = x.charAt(i - 1) == y.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + substitutionCost, dp[i - 1][j] + 1), dp[i][j - 1] + 1);
            }
        }
        return dp[x.length()][y.length()];
    }
}
