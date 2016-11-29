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
                if (held.getType() != Material.WRITTEN_BOOK && held.getType() != Material.BOOK_AND_QUILL) return false;

                meta = (BookMeta) held.getItemMeta();
                if (meta.hasGeneration())
                    player.sendMessage("Held book has generation: " + meta.getGeneration());
                else
                    player.sendMessage("Held book does not have generation");
                break;

            case "set":
                if (args.length < 2) return false;

                genArg = args[1];

                held = player.getInventory().getItemInMainHand();
                if (held.getType() != Material.WRITTEN_BOOK && held.getType() != Material.BOOK_AND_QUILL) return false;

                target = genArg.equals("null") ? null : Generation.valueOf(genArg);
                meta   = (BookMeta) held.getItemMeta();
                meta.setGeneration(target);
                held.setItemMeta(meta);
                player.sendMessage("Held book set to generation: " + target);
                break;

            case "equals":
                if (args.length < 2) return false;

                String genEqual = args[1];

                ItemStack book1 = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta  meta1 = (BookMeta) book1.getItemMeta();

                meta1.setAuthor(player.getName());
                meta1.setTitle("Equal Test");
                meta1.setGeneration(Generation.ORIGINAL);
                meta1.addPage( "Test book for equality test" );
                book1.setItemMeta(meta1);

                ItemStack book2 = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta  meta2 = (BookMeta) book2.getItemMeta();

                meta2.setAuthor(player.getName());
                meta2.setTitle("Equal Test");
                meta2.setGeneration(genEqual.equalsIgnoreCase("true") ? Generation.ORIGINAL : null);
                meta2.addPage( "Test book for equality test" );
                book2.setItemMeta(meta2);

                player.sendMessage("Are books equal? " + book1.equals(book2));
                player.sendMessage("Are metas equal? " + meta1.equals(meta2));
                player.sendMessage("Book hash codes:  " + book1.hashCode() + ", " + book2.hashCode());
                player.sendMessage("Meta hash codes:  " + meta1.hashCode() + ", " + meta2.hashCode());
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
