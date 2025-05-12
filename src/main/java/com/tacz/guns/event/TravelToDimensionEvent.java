package com.tacz.guns.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessageSyncGunData;
import com.tacz.guns.util.DelayedTask;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TravelToDimensionEvent {
    @SubscribeEvent
    public static void onTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LocalPlayer) {
            DelayedTask.add(() -> {
                GunMod.LOGGER.info("Sending SyncGunDataPacket to server after dimension travel");
                NetworkHandler.CHANNEL.sendToServer(new ClientMessageSyncGunData());


            }, 20);
        }
    }


}
