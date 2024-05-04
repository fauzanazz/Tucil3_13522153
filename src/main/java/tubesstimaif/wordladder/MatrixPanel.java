package tubesstimaif.wordladder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Kelas yang digunakan untuk membuat panel yang berisi matriks kata
 * Panel ini digunakan untuk menampilkan kata-kata yang dihasilkan oleh algoritma
 */
public class MatrixPanel extends JPanel {
    public MatrixPanel(List<String> strings, String end) {
        int rows = strings.size();
        int cols = strings.getFirst().length();
        setLayout(new GridLayout(rows, cols));

        for (String str : strings) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                JLabel label = new JLabel(String.valueOf(c));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

                // Set border color
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (i < end.length() && c == end.charAt(i)) {
                    label.setOpaque(true);
                    label.setBackground(Color.GREEN);
                }

                add(label);
            }
        }
    }
}