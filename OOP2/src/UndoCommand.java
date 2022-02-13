
public class UndoCommand implements ICommand {//Undo fonksiyonunun Command Design Pattern ile gerçekleştirilmesi.
    
    private static TextEditorDocumentListener textEditorDocumentListener;
    
    public UndoCommand(TextEditorDocumentListener textEditorDocumentListener)
    {
        UndoCommand.textEditorDocumentListener = UndoCommand.textEditorDocumentListener == null ? textEditorDocumentListener : UndoCommand.textEditorDocumentListener;
    }
    
    @Override
    public void execute() {//Undo işleminin gerçekleştirimi
                
        
        int changedCheckPoint = UndoCommand.textEditorDocumentListener.getCheckpoint() - 2;
        
        
        UndoCommand.textEditorDocumentListener.setCheckpoint(changedCheckPoint);
        
    
    }
    
}
