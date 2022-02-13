
public class CommandManagerFactory {//getInstance() metodunun içinden geçen parametreye göre Command Manager üretilir.
 
    public static ICommandManager generate(String commandManagerType)
    {
        switch(commandManagerType)
        {
            case "CommandManager":
                
                CommandManagerAdapter.commandManager = CommandManagerAdapter.commandManager == null ? CommandManager.getInstance() : CommandManagerAdapter.commandManager;
                
                return CommandManagerAdapter.commandManager;
            default:
                
                CommandManagerAdapter.commandManager = CommandManagerAdapter.commandManager == null ? CommandManager.getInstance() : CommandManagerAdapter.commandManager;
                
                return CommandManagerAdapter.commandManager;
        }
    }
}
