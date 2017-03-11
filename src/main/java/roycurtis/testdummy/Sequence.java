package roycurtis.testdummy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Queue;

/** Chainable class that creates a sequence of runnables that can start and stop anytime */
public class Sequence
{
    public interface RunnableLambda
    {
        void run(Sequence sequence);
    }

    private class SequenceTask
    {
        public RunnableLambda lambda;
        public int            delay;
    }

    private Queue<SequenceTask> tasks = new ArrayDeque<>();

    private boolean    running   = false;
    private boolean    cancelled = false;
    private BukkitTask currentTask;
    private Plugin     plugin;

    public Sequence(Plugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Adds a task to the sequence
     *
     * @param ticks Delay (in ticks) to run this task after the previous one has finished
     * @param lambda Task's code to run
     * @return This sequence
     */
    public Sequence add(int ticks, RunnableLambda lambda)
    {
        assert(!cancelled);

        SequenceTask task = new SequenceTask();
        task.lambda = lambda;
        task.delay  = ticks;

        tasks.add(task);

        return this;
    }

    /** Starts or resumes this sequence */
    public Sequence start()
    {
        assert(!running);
        assert(!cancelled);

        running = true;
        runNextTask();

        return this;
    }

    /** Pauses this sequence. Does not interrupt any currently running tasks */
    public void pause()
    {
        assert(!cancelled);

        if (currentTask != null)
        {
            currentTask.cancel();
            currentTask = null;
        }

        running = false;
    }

    /** Stops and cancels this sequence. Does not interrupt any currently running tasks */
    public void cancel()
    {
        pause();
        tasks.clear();
        this.cancelled = true;
    }

    private void runNextTask()
    {
        final Sequence     sequence = this;
        final SequenceTask task     = tasks.peek();

        if (task == null)
        {
            pause();
            return;
        }

        currentTask = Bukkit.getScheduler().runTaskLater(plugin, () ->
        {
            // Do nothing if the sequence was paused/cancelled in the meantime
            if (!sequence.running || sequence.cancelled)
                return;

            // Finally removes the next task from the queue. As a sanity check, it is compared to
            // the earlier reference.
            if ( task != tasks.poll() )
                throw new ConcurrentModificationException();

            task.lambda.run(sequence);

            runNextTask();
        }, task.delay);
    }
}
