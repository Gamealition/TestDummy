package roycurtis.testdummy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/** Chainable class that creates a sequence of titles that immediately begins */
public class TitleSequence
{
    private boolean cancelled    = false;
    private boolean relayToChat  = false;
    private int     currentDelay = 0;
    private Plugin  plugin;
    private Player  player;

    public TitleSequence(Plugin plugin, Player player)
    {
        this.plugin = plugin;
        this.player = player;
    }

    /**
     * Sets whether this sequence is cancelled. Note that cancelling the sequence does not remove
     * tasks from the scheduler. They will continue to execute, but they will do nothing.
     */
    public TitleSequence setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
        return this;
    }

    /** Sets whether this sequence should relay title messages to chat simulataneously */
    public TitleSequence setRelayToChat(boolean relayToChat)
    {
        this.relayToChat = relayToChat;
        return this;
    }

    /** Adds a delay (in ticks) to the sequence */
    public TitleSequence delay(int ticks)
    {
        currentDelay += ticks;
        return this;
    }

    /** Adds a title with blank subtitle and default time values */
    public TitleSequence send(String title)
    {
        return send(title, "", 10, 80, 10);
    }

    /** Adds a title and subtitle with default time values */
    public TitleSequence send(String title, String subtitle)
    {
        return send(title, subtitle, 10, 80, 10);
    }

    /** Adds a title and subtitle with specified time values */
    public TitleSequence send(String title, String subTitle, int fadeIn, int duration, int fadeOut)
    {
        Bukkit.getScheduler().runTaskLater(plugin, () ->
        {
            if (cancelled)
                return;

            player.sendTitle(title, subTitle, fadeIn, duration, fadeOut);

            if (relayToChat)
                player.sendMessage(title + " §r" + subTitle);
        }, currentDelay);

        return this;
    }
}
