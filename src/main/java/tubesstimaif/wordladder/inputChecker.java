/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesstimaif.wordladder;

import java.io.IOException;

/**
 *
 * @author Ojan
 */
public class inputChecker extends wordChecker {

    inputChecker(){
        super();
    }
    
    public boolean checkInput(String starts, String ends) throws IOException {
        return check(starts) && check(ends);
    }
}
