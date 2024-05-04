/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package tubesstimaif.wordladder;

import java.io.IOException;

/**
 * Main class untuk menjalankan program
 * @author Ojan
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Parser.load();
        MainWindowsUI ui = new MainWindowsUI();
        ui.setVisible(true);
    }
}


