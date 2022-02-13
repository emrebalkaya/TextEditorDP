
import java.util.List;


public class CommandManagerAdapter implements ICommandManager {//ICommandManager ve ICommand arayüzlerinin adapte edilmesi.
    
    static ICommandManager commandManager;
    
    public static ICommandManager getInstance(String commandManagerType)
    {
       return CommandManagerFactory.generate(commandManagerType);
    }
    
    @Override
    public void execute(List<ICommand> commandList) {
        CommandManagerAdapter.commandManager.execute(commandList);
    }
    
}
