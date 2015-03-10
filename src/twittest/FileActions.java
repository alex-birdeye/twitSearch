/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author youser
 */
public class FileActions {
    private static FileOutputStream fos = null;
    
    public static void saveFile(File file, JTable data){
        try {
            fos = new FileOutputStream(file);
            StringBuffer sb = new StringBuffer();
            TableModel dtm = data.getModel();
            String line =  "";
            for (int i = 0; i < dtm.getRowCount(); i++) {
                sb.append("=====================================================================================");
                line = "||\t" + data.getValueAt(i, 1) + "\t||" + "\t" + data.getValueAt(i, 2) + "\t\t\t||" + "\n";
                sb.append(line);
            }
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static byte[] openFile(File file){
        
        return null;
    }
}
