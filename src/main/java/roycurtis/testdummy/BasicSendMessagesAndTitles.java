package roycurtis.testdummy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BasicSendMessagesAndTitles
{
    /** Sends a collection of title and subtitle pairs, with a delay (in ticks) between them */
    public static void sendMessagesAndTitles(Plugin plugin, Player player, int ticks, String... pairs)
    {
        assert(pairs.length % 2 == 0);

        int baseDelay = 0;

        for (int i = 0; i < pairs.length; i += 2)
        {
            String title    = pairs[i];
            String subTitle = pairs[i + 1];

            Bukkit.getScheduler().runTaskLater(plugin, () ->
            {
                player.sendTitle(title, subTitle, 10, 80, 10);
                player.sendMessage(title + " §r" + subTitle);
            }, baseDelay);

            baseDelay += ticks;
        }
    }

}
