package com.risudayooo.ARL.network;

import com.risudayooo.ARL.combat.HitboxManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnHitboxPacket {

    public SpawnHitboxPacket() {}

    public static void encode(SpawnHitboxPacket msg, FriendlyByteBuf buf) {}

    public static SpawnHitboxPacket decode(FriendlyByteBuf buf) {
        return new SpawnHitboxPacket();
    }

    public static void handle(SpawnHitboxPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                System.out.println("[SpawnHitboxPacket] handle called for player: " + player.getName().getString());
                HitboxManager.spawnHitboxFor(player, player.level(),
                        1.0f, 1.0f, 1.0f,
                        new Vec3(0, 0.5, 2.0));
            } else {
                System.out.println("[SpawnHitboxPacket] player is null");
            }
        });
        ctx.get().setPacketHandled(true);
    }
}