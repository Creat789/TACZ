package com.tacz.guns.event;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.GunMod;
import com.tacz.guns.util.TaskScheduler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerPlayerCloneEventHandler {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        GunMod.LOGGER.info("Player clone event detected. Scheduling gun operator initialization...");

        TaskScheduler.schedule(() -> {
            IGunOperator operator = IGunOperator.fromLivingEntity(event.getEntity());

            // Vérifier que les données sont bien présentes avant de continuer
            if (operator.getDataHolder() == null) {
                GunMod.LOGGER.error("DataHolder is null! Retrying in 10 ticks...");
                TaskScheduler.schedule(() -> operator.initialData(), 10);
                return;
            }

            operator.initialData();
            GunMod.LOGGER.info("Gun operator data reinitialized on server for player clone (delayed)");

        }, 30); // Délai initial de 30 ticks
    }




}
