package com.tacz.guns.util;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ConcurrentLinkedQueue;

@Mod.EventBusSubscriber
public class TaskScheduler {
    private static final ConcurrentLinkedQueue<ScheduledTask> tasks = new ConcurrentLinkedQueue<>();

    public static void schedule(Runnable task, int delayTicks) {
        tasks.add(new ScheduledTask(task, delayTicks));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tasks.removeIf(scheduledTask -> {
                scheduledTask.ticksRemaining--;
                if (scheduledTask.ticksRemaining <= 0) {
                    scheduledTask.task.run();
                    return true;  // Supprime la tâche terminée
                }
                return false;
            });
        }
    }

    private static class ScheduledTask {
        private final Runnable task;
        private int ticksRemaining;

        public ScheduledTask(Runnable task, int delayTicks) {
            this.task = task;
            this.ticksRemaining = delayTicks;
        }
    }
}
