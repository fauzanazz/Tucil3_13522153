package tubesstimaif.wordladder;

import javax.swing.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;

/**
 * Kelas yang merepresentasikan panel untuk menampilkan daftar kata dan definisi
 * Kelas ini merupakan turunan dari JPanel
 * @see JPanel
 */
public class DisplayEntryList extends JPanel {

    /**
     * Constructor untuk DisplayEntryList
     * @param entries List berisi kata dan definisi
     * @param nameList JList yang berisi kata
     */
    public DisplayEntryList(List<EntryWrapper> entries, JList<String> nameList) {
        createView(entries);
    }

    /**
     * Membuat view untuk menampilkan daftar kata dan definisi
     * @param entries List berisi kata dan definisi
     */
    private void createView(List<EntryWrapper> entries) {
        List<String> definitions = new ArrayList<>();
        for (EntryWrapper entry : entries) {
            definitions.add(entry.getEntryValue());
        }

        JList<String> definitionList = new JList<>(definitions.toArray(new String[0]));

        JScrollPane definitionScrollPane = new JScrollPane(definitionList);

        add(definitionScrollPane);
    }

    /**
     * Kelas yang merepresentasikan wrapper untuk SimpleEntry
     */
    static class EntryWrapper {
        private final SimpleEntry<String, String> entry;

        public EntryWrapper(SimpleEntry<String, String> entry) {
            this.entry = entry;
        }

        public String getEntryKey() {
            return entry.getKey();
        }

        public String getEntryValue() {
            return entry.getValue();
        }

        @Override
        public String toString() {
            return "Name: " + getEntryKey() + ", Definition: " + getEntryValue();
        }
    }
}