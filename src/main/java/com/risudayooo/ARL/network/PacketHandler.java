package com.risudayooo.ARL.network;

import com.risudayooo.ARL.ARL;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ARL.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static int nextID() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.registerMessage(
                nextID(),
                SpawnHitboxPacket.class,
                SpawnHitboxPacket::encode,
                SpawnHitboxPacket::decode,
                SpawnHitboxPacket::handle
        );
    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }
}