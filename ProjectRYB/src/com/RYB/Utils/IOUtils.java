package com.RYB.Utils;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Aaron
 */
public class IOUtils {
    public static File browseToSaveMap(){
        final JFileChooser fc = new JFileChooser("src\\com\\RYB\\Assets\\Levels");
        fc.setFileFilter(new FileNameExtensionFilter("RYB Map (*.txt)", "txt"));
        
        int returnVal = fc.showSaveDialog(new JFrame());
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            return fc.getSelectedFile();
        }
        else{
            return null;
        }
    }
}
