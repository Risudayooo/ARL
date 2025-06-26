package com.risudayooo.ARL.registry;

import com.risudayooo.ARL.ARL;
import com.risudayooo.ARL.entity.HitboxEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ARL.MOD_ID);

    public static final RegistryObject<EntityType<HitboxEntity>> HITBOX =
            ENTITIES.register("hitbox", () ->
                    EntityType.Builder.<HitboxEntity>of(HitboxEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .build(new ResourceLocation(ARL.MOD_ID, "hitbox").toString())
            );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}