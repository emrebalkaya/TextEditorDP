
public class RedoCommand implements ICommand {//Redo fonksiyonunun Command Design Pattern ile gerçekleştirilmesi.
        
    private static TextEditorDocumentListener textEditorDocumentListener;
    
    public RedoCommand(TextEditorDocumentListener textEditorDocumentListener)
    {
        RedoCommand.textEditorDocumentListener = RedoCommand.textEditorDocumentListener == null ? textEditorDocumentListener : RedoCommand.textEditorDocumentListener;
    }
    
    @Override
    public void execute() {//Redo işleminin gerçekleştirimi
       
        
        int changedCheckPoint = RedoCommand.textEditorDocumentListener.getCheckpoint();

        
        RedoCommand.textEditorDocumentListener.setCheckpoint(changedCheckPoint);
        
        
    }   
}