package roycurtis.testdummy;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestDummy extends JavaPlugin
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if ( !(sender instanceof Player) ) return false;
        if (args.length < 1) return false;

        Player player = (Player) sender;
        String target = args[0];

        for ( World  world  : getServer().getWorlds()   )
        for ( Entity entity : world.getLivingEntities() )
        if  ( entity.getName().equalsIgnoreCase(target) )
        {
            entity.setPassenger(player);
            return true;
        }

        return true;
    }
}
