package roycurtis.testdummy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class TestDummy extends JavaPlugin implements Listener
{
    private static final Gson      gson  = new GsonBuilder().disableHtmlEscaping().create();
    private static final TypeToken token = new TypeToken< Map<String, Object> >() {};

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e)
    {
        if ( !e.hasItem() )
            return;

        ItemStack held = e.getItem();
        String    data = gson.toJson( held.serialize() );

        Map<String, Object> deserialized = gson.fromJson( data, token.getType() );
        ItemStack newItem = ItemStack.deserialize(deserialized);
//        ItemStack newItem = ItemStack.deserialize( held.serialize() );

        e.setCancelled(true);
        e.getPlayer().getInventory().addItem(newItem);
        e.getPlayer().sendMessage("*** " + held.getType() + " (de)serialized and cloned");
    }
}
