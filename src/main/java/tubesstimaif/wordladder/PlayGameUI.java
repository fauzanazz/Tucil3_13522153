/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tubesstimaif.wordladder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import javax.swing.BorderFactory;

/**
 *
 * @author Ojan
 */
public class PlayGameUI extends javax.swing.JFrame {

    /**
     * Creates new form PlayGameUI
     */
    public PlayGameUI() {
        initComponents();
        InitGame();
        InitInput();
    }

    private String start;
    private String end;

    private void InitGame() {

        while (true) {
            // Random length from 2 to 14
            int length = (int) (Math.random() * 13) + 2;

            start = MapParser.getRandomWord(length);
            end = MapParser.getRandomWord(length);

            if (MapParser.isWordNotExist(start) && MapParser.isWordNotExist(end) && !start.equals(end)) {
                continue;
            }

            Solver solver = new A_star();
            Result result = solver.solve(start, end);

            if (result != null) {
                break;
            }
        }
    }

    private Stack<String> inputStack;

    private void InitInput() {
        int game_length = start.length();
        int labelWidth = 50;
        int totalLabelWidth = game_length * labelWidth;

        // Setup jPanel1 width = game_length * 50
        jPanel1.setPreferredSize(new Dimension(game_length * 50, 50));

        // Calculate the starting x-coordinate for the first JLabel
        int startX = (jPanel1.getWidth() - totalLabelWidth) / 2;

        // Setup JPanel4
        jPanel4.setLayout(new GridLayout(1, game_length));
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Add start string to the top
        for (int i = 0; i < start.length(); i++) {
            JLabel label = new JLabel(String.valueOf(start.charAt(i)));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
            label.setBounds(startX + i * labelWidth, 0, labelWidth, 50); // Set bounds
            jPanel4.add(label);
        }

        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Add JLabels for user input in the middle
        final JLabel[] inputs = new JLabel[game_length];
        for (int i = 0; i < start.length(); i++) {
            inputs[i] = new JLabel();
            inputs[i].setHorizontalAlignment(JLabel.CENTER);
            inputs[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
            inputs[i].setBounds(startX + i * labelWidth, 0, labelWidth, 50); // Set bounds
            jPanel1.add(inputs[i]);
        }

        // Set the preferred size of jPanel1
        jPanel1.setPreferredSize(new Dimension(game_length * 50, 50));

        // Setup jPanel3
        jPanel3.setLayout(new GridLayout(1, game_length));
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Add end string to the bottom
        for (int i = 0; i < end.length(); i++) {
            JLabel label = new JLabel(String.valueOf(end.charAt(i)));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
            label.setBounds(startX + i * labelWidth, 0, labelWidth, 50); // Set bounds
            jPanel3.add(label);
        }

        inputStack = new Stack<>();

        // Add KeyListener to jPanel1
        jPanel1.addKeyListener(new KeyAdapter() {
            int current = 0;

            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();

                if (keyChar == '\n') { // If the user presses Enter
                    // Check if inputted is not full
                    if (!(current == game_length)) {
                        return;
                    }

                    // Check if inputted is in dictionary
                    StringBuilder sb = new StringBuilder();
                    for (JLabel input : inputs) {
                        sb.append(input.getText());
                    }

                    if (MapParser.isWordNotExist(sb.toString().toUpperCase())) {
                        JOptionPane.showMessageDialog(null, "The word is not in the dictionary!");
                        return;
                    }


                    // Check if inputted has > 1 different characters from top Stack
                    int diff = 0;
                    for (int i = 0; i < game_length; i++) {

                        if (inputStack.isEmpty()){
                            if (inputs[i].getText().charAt(0) != start.charAt(i)) {
                                diff++;
                            }
                            continue;
                        }

                        if (inputs[i].getText().charAt(0) != inputStack.peek().charAt(i)) {
                            diff++;
                        }
                    }

                    if (diff > 1) {
                        JOptionPane.showMessageDialog(null, "You can only change 1 character from the previous!");
                        return;
                    }

                    // Check if the inputted word is correct
                    StringBuilder tempsb = new StringBuilder();
                    for (JLabel input : inputs) {
                        tempsb.append(input.getText());
                    }
                    String input = sb.toString();

                    if (input.equals(end)) {
                        JOptionPane.showMessageDialog(null, "Correct!");
                        // Push the input to the stack
                        inputStack.push(input);

                        // Copy the stack to a temporary stack
                        Stack<String> tempStack = new Stack<>();
                        tempStack.addAll(inputStack);

                        // Pop The stack to list of String
                        String[] inputList = new String[tempStack.size()];
                        for (int i = 0; i < inputList.length; i++) {
                            inputList[i] = tempStack.pop();
                        }

                        // Reverse the list
                        for (int i = 0; i < inputList.length / 2; i++) {
                            String temp = inputList[i];
                            inputList[i] = inputList[inputList.length - i - 1];
                            inputList[inputList.length - i - 1] = temp;
                        }

                        // Redraw jPanel1
                        jPanel1.removeAll();
                        jPanel1.setLayout(null); // Update the number of rows in the GridLayout

                        // Make the label of past input
                        for (int i = 0; i < inputList.length; i++) {
                            for (int j = 0; j < inputList[i].length(); j++) {
                                JLabel label = new JLabel(String.valueOf(inputList[i].charAt(j)));
                                label.setHorizontalAlignment(JLabel.CENTER);
                                label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
                                label.setBounds( startX + j * labelWidth, i * 50, 50, 50);
                                // if inputted char is the same as ends string in the same index set to green
                                if (inputList[i].charAt(j) == end.charAt(j)) {
                                    label.setOpaque(true);
                                    label.setBackground(Color.GREEN);
                                }
                                jPanel1.add(label);
                            }
                        }

                        jPanel1.validate();
                        jPanel1.repaint();

                        // Lock window
                        jPanel1.setFocusable(false);
                    } else {
                        // Push the input to the stack
                        inputStack.push(input);

                        // Copy the stack to a temporary stack
                        Stack<String> tempStack = new Stack<>();
                        tempStack.addAll(inputStack);

                        // Pop The stack to list of String
                        String[] inputList = new String[tempStack.size()];
                        for (int i = 0; i < inputList.length; i++) {
                            inputList[i] = tempStack.pop();
                        }

                        // Reverse the list
                        for (int i = 0; i < inputList.length / 2; i++) {
                            String temp = inputList[i];
                            inputList[i] = inputList[inputList.length - i - 1];
                            inputList[inputList.length - i - 1] = temp;
                        }

                        // Redraw jPanel1
                        jPanel1.removeAll();
                        jPanel1.setLayout(null); // Update the number of rows in the GridLayout

                        // Make the label of past input
                        for (int i = 0; i < inputList.length; i++) {
                            for (int j = 0; j < inputList[i].length(); j++) {
                                JLabel label = new JLabel(String.valueOf(inputList[i].charAt(j)));
                                label.setHorizontalAlignment(JLabel.CENTER);
                                label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
                                label.setBounds( startX + j * labelWidth, i * 50, 50, 50);
                                // if inputted char is the same as ends string in the same index set to green
                                if (inputList[i].charAt(j) == end.charAt(j)) {
                                    label.setOpaque(true);
                                    label.setBackground(Color.GREEN);
                                }
                                jPanel1.add(label);
                            }
                        }


                        // Add JLabels for user input in the middle
                        for (int i = 0; i < start.length(); i++) {
                            inputs[i] = new JLabel();
                            inputs[i].setHorizontalAlignment(JLabel.CENTER);
                            inputs[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
                            inputs[i].setBounds(startX + i * labelWidth, inputStack.size() * 50, 50, 50);
                            jPanel1.add(inputs[i]);
                        }

                        jPanel1.setPreferredSize(new Dimension(game_length * 50, (inputStack.size() + 1) * 50));
                        current = 0;

                        // Redraw jPanel1
                        jPanel1.revalidate();
                        jPanel1.repaint();
                    }
                } else if (keyChar == '\b') { // If the user presses Backspace
                    // Delete the last input
                    if (current > 0) {
                        inputs[--current].setText("");
                        return;
                    }

                    // If the user deletes all input
                    // Delete the current row and go edit the prev row
                    if (current == 0 && !inputStack.isEmpty()) {
                        // Save the last input
                        String lastInput = inputStack.pop();

                        // Copy the stack to a temporary stack
                        Stack<String> tempStack = new Stack<>();
                        tempStack.addAll(inputStack);

                        // Pop The stack to list of String
                        String[] inputList = new String[tempStack.size()];
                        for (int i = 0; i < inputList.length; i++) {
                            inputList[i] = tempStack.pop();
                        }

                        // Redraw jPanel1
                        jPanel1.removeAll();
                        jPanel1.setLayout(null); // Update the number of rows in the GridLayout

                        // Make the label of past input
                        for (int i = 0; i < inputList.length; i++) {
                            for (int j = 0; j < inputList[i].length(); j++) {
                                JLabel label = new JLabel(String.valueOf(inputList[i].charAt(j)));
                                label.setHorizontalAlignment(JLabel.CENTER);
                                label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
                                label.setBounds( startX + j * labelWidth, i * 50, 50, 50);
                                jPanel1.add(label);
                            }
                        }

                        // Make the input of the last input
                        for (int i = 0; i < start.length(); i++) {
                            inputs[i] = new JLabel();
                            inputs[i].setHorizontalAlignment(JLabel.CENTER);
                            inputs[i].setText(String.valueOf(lastInput.charAt(i)));
                            inputs[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
                            inputs[i].setBounds(startX + i * labelWidth, (inputList.length) * 50 , 50, 50);
                            jPanel1.add(inputs[i]);
                        }

                        jPanel1.setPreferredSize(new Dimension(game_length * 50, (inputList.length + 1) * 50));

                        current = start.length();
                        jPanel1.revalidate();
                        jPanel1.repaint();
                    }

                } else if (Character.isLetter(keyChar) && current < inputs.length) { // If the user presses a letter
                    inputs[current++].setText(String.valueOf(keyChar).toUpperCase());
                }
            }
        });

        jPanel1.setFocusable(true);
        jPanel1.requestFocusInWindow();

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PlayGame");

        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel1.setText("WORD LADDER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayGameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PlayGameUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
