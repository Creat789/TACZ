package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageSyncGunData {
    public ClientMessageSyncGunData() {}

    public static void encode(ClientMessageSyncGunData msg, FriendlyByteBuf buf) {
        // Aucun champ à encoder si l'action se limite à déclencher l'initialisation
    }

    public static ClientMessageSyncGunData decode(FriendlyByteBuf buf) {
        return new ClientMessageSyncGunData();
    }

    public static void handle(ClientMessageSyncGunData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null) {
                IGunOperator.fromLivingEntity(ctx.get().getSender()).initialData();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
