package com.risudayooo.ARL.registry;

import com.risudayooo.ARL.ARL;
import com.risudayooo.ARL.item.weapon.LongSword1Item;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;
//import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ARL.MOD_ID);

    public static final RegistryObject<Item> LONG_SWORD_1 = ITEMS.register("long_sword1",
            () -> new LongSword1Item(Tiers.IRON, 3, -2.4f, new Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}