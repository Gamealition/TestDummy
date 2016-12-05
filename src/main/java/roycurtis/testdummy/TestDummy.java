package roycurtis.testdummy;

import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestDummy extends JavaPlugin
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if ( !(sender instanceof Player) ) return false;
        if (args.length < 1) return false;

        Player player   = (Player) sender;
        String biomeArg = args[0];

        try
        {
            Biome biome = Biome.valueOf(biomeArg);

            for (int x = -256; x < 256; x++)
            for (int z = -256; z < 256; z++)
                player.getWorld().setBiome(x, z, biome);

            player.sendMessage("Set biomes from -256,-256 to 256,256 to " + biome);
        }
        catch (Exception e)
        {
            player.sendMessage("Could not set biome to " + biomeArg);
            e.printStackTrace();
        }

        return true;
    }
}
