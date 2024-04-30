/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.io.IOException;
import java.util.List;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

/**
 *
 * @author Ojan
 */
public class wordChecker {
    private final JLanguageTool langTool;

    wordChecker(){
        langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
    }

    public boolean check(String word) throws IOException {
        List<RuleMatch> matches = langTool.check(word);
        return matches.isEmpty();
    }
}
