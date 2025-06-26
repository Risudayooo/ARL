package com.risudayooo.ARL.handler;

import com.risudayooo.ARL.network.PacketHandler;
import com.risudayooo.ARL.network.SpawnHitboxPacket;
import com.risudayooo.ARL.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arl")
public class InputHandler {

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack heldItem = event.getEntity().getMainHandItem();
        if (heldItem.is(ModItems.LONG_SWORD_1.get())) {
            System.out.println("LeftClickEmpty detected!");
            PacketHandler.sendToServer(new SpawnHitboxPacket());
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack heldItem = event.getEntity().getMainHandItem();
        if (heldItem.is(ModItems.LONG_SWORD_1.get())) {
            System.out.println("LeftClickBlock detected!");
            PacketHandler.sendToServer(new SpawnHitboxPacket());
        }
    }
}