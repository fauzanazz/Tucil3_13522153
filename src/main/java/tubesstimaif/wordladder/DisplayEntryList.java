package tubesstimaif.wordladder;

import javax.swing.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;

public class DisplayEntryList extends JPanel {

    private JList<String> nameList;

    public DisplayEntryList(List<EntryWrapper> entries, JList<String> nameList) {
        this.nameList = nameList;
        createView(entries);
    }

    private void createView(List<EntryWrapper> entries) {
        List<String> definitions = new ArrayList<>();
        for (EntryWrapper entry : entries) {
            definitions.add(entry.getEntryValue());
        }

        JList<String> definitionList = new JList<>(definitions.toArray(new String[0]));

        JScrollPane definitionScrollPane = new JScrollPane(definitionList);

        add(definitionScrollPane);
    }

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