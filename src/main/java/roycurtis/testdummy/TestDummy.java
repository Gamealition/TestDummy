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
            int   posX  = player.getLocation().getBlockX();
            int   posZ  = player.getLocation().getBlockZ();

            for (int x = posX - 256; x < posX + 256; x++)
            for (int z = posZ - 256; z < posZ + 256; z++)
                player.getWorld().setBiome(x, z, biome);

            player.sendMessage("Set biomes in 256 block radius around you to " + biome);
        }
        catch (Exception e)
        {
            player.sendMessage("Could not set biome to " + biomeArg);
            e.printStackTrace();
        }

        return true;
    }
}
