package roycurtis.testdummy;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TestDummy extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e)
    {
        final Entity ent = e.getRightClicked();

        if ( !(ent instanceof Llama) ) return;

        final Llama     llama  = (Llama) ent;
        final ItemStack carpet = new ItemStack(171, 1, (short) 4);

        llama.getInventory().setDecor(carpet);
        e.getPlayer().sendMessage("Carpet set");
    }
}
