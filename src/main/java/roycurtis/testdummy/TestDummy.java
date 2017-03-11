package roycurtis.testdummy;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TestDummy extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    private TitleSequence titleSequence = null;
    private Sequence      sequence      = null;

    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        switch ( e.getAction() )
        {
            /* = = = = =
             * BasicSendMessagesAndTitles
             * = = = = = */
            case LEFT_CLICK_AIR:
                BasicSendMessagesAndTitles.sendMessagesAndTitles(this, e.getPlayer(), 80,
                    "This is a title", "This is a subtitle",
                    "§cFormatted §lTitle", "...and a §aformatted §esubtitle",
                    "I'd put something portugese here", "But I do not know any :("
                );

                break;

            /* = = = = =
             * TitleSequence
             * = = = = = */
            case RIGHT_CLICK_AIR:
                if (titleSequence != null)
                    titleSequence.setCancelled(true);

                titleSequence = new TitleSequence( this, e.getPlayer() )
                    .setRelayToChat(true)
                    .send("This is a title", "This is a subtitle")
                    .delay(100)
                    .send("This is a long title", "", 40, 60, 40)
                    .delay(150)
                    .send("", "This is a shy title", 5, 10, 5)
                    .delay(20).send("§CBAM!")
                    .delay(20).send("§CBAM!!")
                    .delay(20).send("§CBAM!!!");

                break;

            /* = = = = =
             * Sequence
             * = = = = = */
            case LEFT_CLICK_BLOCK:
                if (sequence != null)
                    sequence.cancel();

                sequence = new Sequence(this)
                    .add(0, s ->
                    {
                        e.getPlayer().sendTitle("This is a title", "Sent by sequence", 10, 80, 10);
                        e.getPlayer().sendMessage("This is a message sent by sequence");
                    })

                    .add(80, s ->
                    {
                        e.getPlayer().sendTitle("This might be the last title", "", 10, 80, 10);

                        if ( new Random().nextBoolean() )
                        {
                            s.pause();
                            e.getPlayer().sendMessage("Sequence paused by random chance");
                        }
                    })

                    .add(80, s ->
                    {
                        e.getPlayer().sendTitle("It's the end of the sequence!", "", 10, 80, 10);
                        e.getPlayer().playSound(
                            e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.F, 1.F
                        );
                    })

                    .start();

                break;
        }
    }
}
