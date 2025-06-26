package com.risudayooo.ARL;

import com.risudayooo.ARL.network.PacketHandler;
import com.risudayooo.ARL.registry.ModEntities;
import com.risudayooo.ARL.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ARL.MOD_ID)
public class ARL {
    public static final String MOD_ID = "arl";

    public ARL() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);

        modEventBus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }
}