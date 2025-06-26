package com.risudayooo.ARL.handler;

import com.risudayooo.ARL.combat.HitboxManager;
import com.risudayooo.ARL.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InputHandler {

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack heldItem = event.getEntity().getMainHandItem();
        if (heldItem.is(ModItems.LONG_SWORD_1.get())) {
            event.getEntity().sendSystemMessage(Component.literal("左クリック検知(空中)"));

            Vec3 localPos = new Vec3(0, 2.0, 2.0);  // 例: 前方2ブロック、頭上2ブロック
            HitboxManager.spawnHitboxFor(event.getEntity(), event.getLevel(),
                    1.0f, 1.0f, 1.5f,
                    localPos);
        }
    }
}