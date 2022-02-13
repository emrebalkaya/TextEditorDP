/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
package */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;

/**
 *
 * @author medianova
 */
public class TextEditorHelper {
    
    static JTextPane textPane;
    private static final ArrayList<String> dictionary = new ArrayList<String>();
    private static final ArrayList<String> changeHistory = new ArrayList<String>();
    
    private static final Integer checkPoint = 0;
    
    public static void setTextPane(JTextPane textPane){
        TextEditorHelper.textPane = textPane;
    }
    
    public static String openFile(File file)//dosya okuma 
    {
        String data = null;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
              data = myReader.nextLine();
              System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {//gerekli hata kontrolü 
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return data;
    }
    
    public static boolean saveToFile(File file,String text)//dosya kaydetme 
    {        
        try {
            if (file.createNewFile()) {
              FileWriter writer = new FileWriter(file.getAbsoluteFile());
              writer.write(text);
              writer.close();
              return true;
            }
        } catch (IOException e) {// gerekli hata kontrolü 
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static void spellCheck(String compare) {//hatalı kelime kontrolü ve düzeltilmesi     
        
        for(String word : TextEditorHelper.dictionary) {//verilen text dosyasındaki doğru kelimeler alınır

          Integer value = DamerauLevenshtein.calculateDistance(compare, word);// iki harf arası uzaklık DamerauLevenshtein algoritması ile hesaplanır
          
          if (value == 1) {//yan yana olan iki harf hatalı ise değişim yapılır.
            
            TextEditorHelper.assistText(
                    TextEditorHelper
                            .textPane
                            .getText()
                            .replace(compare, word)
            );
            
            return;
          }
        }
        
    }
    
    private static void assistText(String text) {//aynı anda 2 thread durumunda thread durumunun ilkini askıya alır ve diğer sonradan gelen thread durumu yapılır. Daha sonra ilk thread yapılır.
        
        Runnable doAssist = () -> {
            synchronized (TextEditorHelper.textPane){
                TextEditorHelper.textPane.setText(text);
                System.out.println("ok3 : " + TextEditorDocumentListener.checkThread);
                TextEditorDocumentListener.checkThread = false;
            }
        };
        
        SwingUtilities.invokeLater(doAssist);
    }

    public static void loadDictionary() throws FileNotFoundException, IOException//Verilen words.txt dosyasının okunması ve kelimelerin dictionary'e aktarılması.
    {
        File dictionary = new File("words.txt");
        BufferedReader readedFile = new BufferedReader(new FileReader(dictionary));
        String word = "";
        while ( (word = readedFile.readLine()) != null) {
            TextEditorHelper.dictionary.add(word);
        }        
    }
    
    public static ArrayList<String> getDictionary()
    {
        return TextEditorHelper.dictionary;
    }


}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author win-7BİLGİSAYAR
 */

