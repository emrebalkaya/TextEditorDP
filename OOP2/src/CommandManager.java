
import java.util.List;
import java.util.Iterator;

public class CommandManager implements ICommandManager {// Command Design Pattern ile çalışan fonksiyonlar tetiklendiğinde execute metodunu çağıran sınıf.
    
    private static CommandManager instance = null;
    
    static CommandManager getInstance()
    {
        CommandManager.instance = CommandManager.instance == null ? new CommandManager() : CommandManager.instance;
        
        return CommandManager.instance;
    }
    
    public void execute(List<ICommand> commandList)
    {
        Iterator<ICommand> ite = commandList.iterator(); //Iterator Design Pattern ile dolaşılmıştır.
        while(ite.hasNext()){
            ICommand item = ite.next();
            item.execute();
        }
    }  
}
