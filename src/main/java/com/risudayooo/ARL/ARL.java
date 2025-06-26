package com.risudayooo.ARL;

import com.risudayooo.ARL.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ARL.MOD_ID)
public class ARL {

    public static final String MOD_ID = "arl";

    public ARL() {
        IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventbus);
    }
}