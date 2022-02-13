
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author 
 */
public class TextEditorDocumentListener implements DocumentListener {

    private static final ArrayList<String> changeHistory = new ArrayList<String>();
    private static final ArrayList<String> punctuationList = new ArrayList<String>();
    private final JLabel jCharCountLabel;
    private final JLabel jWordCountLabel;
    private static Integer checkPoint = 0;
    private static Boolean checkpointChange = false;
    public static Boolean checkThread = false;
    
    
    public TextEditorDocumentListener(JLabel charCountLabel,JLabel wordCountLabel) {//constructer 
        
        this.jCharCountLabel = charCountLabel;
        this.jWordCountLabel = wordCountLabel;
        
        TextEditorDocumentListener.punctuationList.add(" ");
        TextEditorDocumentListener.punctuationList.add(".");
        TextEditorDocumentListener.punctuationList.add(",");
        TextEditorDocumentListener.punctuationList.add("!");
        TextEditorDocumentListener.punctuationList.add(":");
        TextEditorDocumentListener.punctuationList.add(";");
        TextEditorDocumentListener.punctuationList.add("?");
        TextEditorDocumentListener.punctuationList.add("\t");
        TextEditorDocumentListener.punctuationList.add("\n");
        
    }

 
    
    @Override
    public void insertUpdate(DocumentEvent e) {//Textteki değişim durumunu sağlar.
        this.saveHistory(e);
        this.updateInformation(e);
        try {
            this.spellChecker(e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditorDocumentListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(TextEditorDocumentListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.saveHistory(e);
        this.updateInformation(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.saveHistory(e);
        this.updateInformation(e); 
 
    }
    
    public void undoHistory(Integer step) {//yapılan bir işlemi geri alır.
        TextEditorDocumentListener.checkPoint -= step;
    }
    
    public void redoHistory(Integer step) {//önceden yapılan işlemi tekrar yapar. 
        TextEditorDocumentListener.checkPoint += (TextEditorDocumentListener.checkPoint + step) > TextEditorDocumentListener.changeHistory.size() ? TextEditorDocumentListener.changeHistory.size() : 0; 
    }
    
    public void saveHistory(DocumentEvent e) {//Textte yapılan bütün değişimleri tutar.
        if (TextEditorDocumentListener.checkpointChange) {
                       
            TextEditorDocumentListener.checkpointChange = false;
            
            return;
        }
            
        
        Document document = e.getDocument();
        
        Integer documentTextLenght = document.getLength();
        
        String historyPoint;
        
        try {
            historyPoint = document.getText(0, documentTextLenght);
            
            if(TextEditorDocumentListener.checkPoint >= TextEditorDocumentListener.changeHistory.size())
                TextEditorDocumentListener.changeHistory.add(historyPoint);
            else
                TextEditorDocumentListener.changeHistory.set(TextEditorDocumentListener.checkPoint, historyPoint);
                
            TextEditorDocumentListener.checkPoint ++;
        
        } catch (BadLocationException ex) {
            Logger.getLogger(TextEditorDocumentListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void spellChecker(DocumentEvent e) throws FileNotFoundException, BadLocationException {//Texte yazılan kelimelerin doğruluğunu araştırır ve düzeltir.
      
        Document document = e.getDocument();//
        
        String sentence = document.getText(0, document.getLength());//Cümle parametre olarak gelen documentten çekilir.
        
        String[] splitSentence = sentence.split(",|\\ |\\\t|\\\n|\\.|\\:|\\;|\\!|\\?");   //gerekli ayraçlara göre ayrılır ve kelimeler elde edilir.     
        
        this.jWordCountLabel.setText(splitSentence.length + "");
         
       
        if(splitSentence[splitSentence.length - 1].length() > 2 &&//Kelimelerin dictionaryde olup olmadığını kontrol eder hatalı kelimeleri bulur ve düzeltir.
                TextEditorDocumentListener.punctuationList.indexOf(sentence.substring(sentence.length()-1)) != -1 &&
                !TextEditorDocumentListener.checkThread && !TextEditorHelper.getDictionary().contains(splitSentence[splitSentence.length - 1])) {
            TextEditorDocumentListener.checkThread = true;
            TextEditorHelper.spellCheck(splitSentence[splitSentence.length - 1]);
            
        } else if(splitSentence[splitSentence.length - 1].length() > 2 && 
                TextEditorDocumentListener.punctuationList.indexOf(sentence.substring(sentence.length()-1)) != -1 &&
                TextEditorDocumentListener.checkThread && !TextEditorHelper.getDictionary().contains(splitSentence[splitSentence.length - 1])) { 
            TextEditorDocumentListener.checkThread = false;
        }
        
    }
    
    public void updateInformation(DocumentEvent e) {//textteki kelime sayısını tutar
        Document doc = (Document)e.getDocument();
        Integer changeLength = doc.getLength();
        
        this.jCharCountLabel.setText(changeLength.toString());
    }
    
    public static void setCheckpoint(int checkpoint) {
        TextEditorDocumentListener.checkPoint = checkpoint;
    }
    
    public static String goToCheckpoint() {
        TextEditorDocumentListener.checkpointChange = true;
        return (String)TextEditorDocumentListener.changeHistory.get(TextEditorDocumentListener.checkPoint);
    }
    
    public static int getCheckpoint()
    {
        return TextEditorDocumentListener.checkPoint;
    }
}
