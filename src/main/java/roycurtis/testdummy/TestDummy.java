package roycurtis.testdummy;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.plugin.java.JavaPlugin;

public class TestDummy extends JavaPlugin
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if ( !(sender instanceof Player) ) return false;
        if (args.length < 1) return false;

        Player player = (Player) sender;
        String what   = args[0].toLowerCase();

        Generation target;
        BookMeta   meta;
        ItemStack  held, book;
        String     genArg;

        switch (what)
        {
            default:
                return false;

            case "generate":
                for (int i = 0; i < 4; i++)
                {
                    book   = new ItemStack(Material.WRITTEN_BOOK);
                    meta   = (BookMeta) book.getItemMeta();
                    target = getGenerationByMagicValue(i);

                    meta.setAuthor(player.getName());
                    meta.setTitle("Test gen " + i);
                    meta.setGeneration(target);
                    meta.addPage( "Test book for generation " + target.name() + " (" + i + ")" );

                    book.setItemMeta(meta);
                    player.getInventory().addItem(book);
                }
                break;

            case "get":
                held = player.getInventory().getItemInMainHand();
                if (held.getType() != Material.WRITTEN_BOOK) return false;

                meta = (BookMeta) held.getItemMeta();
                player.sendMessage("Held book has generation: " + meta.getGeneration());
                break;

            case "set":
                if (args.length < 2) return false;

                genArg = args[1];

                held = player.getInventory().getItemInMainHand();
                if (held.getType() != Material.WRITTEN_BOOK) return false;

                target = genArg.equals("null") ? null : Generation.valueOf(genArg);
                meta   = (BookMeta) held.getItemMeta();
                meta.setGeneration(target);
                held.setItemMeta(meta);
                player.sendMessage("Held book set to generation: " + target);
                break;
        }

        return true;
    }

    public Generation getGenerationByMagicValue(int i) {
        switch (i) {
        case 0:
            return Generation.ORIGINAL;
        case 1:
            return Generation.COPY_OF_ORIGINAL;
        case 2:
            return Generation.COPY_OF_COPY;
        case 3:
            return Generation.TATTERED;
        default:
            return null;
        }
    }
}
