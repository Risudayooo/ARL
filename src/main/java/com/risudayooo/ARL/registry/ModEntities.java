package com.risudayooo.ARL.registry;

import com.risudayooo.ARL.ARL;
import com.risudayooo.ARL.entity.HitboxEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ARL.MOD_ID);

    public static final RegistryObject<EntityType<HitboxEntity>> HITBOX =
            ENTITIES.register("hitbox",() ->
                    EntityType.Builder.<HitboxEntity>of(HitboxEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f) //デフォルトサイズ(後で上書き可能)
                            .setUpdateInterval(1)
                            .setTrackingRange(64)
                            .noSave()
                            .noSummon()
                            .build("hitbox"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}