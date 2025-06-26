package com.risudayooo.ARL.combat;

import com.risudayooo.ARL.entity.HitboxEntity;
import com.risudayooo.ARL.registry.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class HitboxManager {

    private static final Vec2[] OFFSETS = {
            new Vec2(0f, 1f),   // 0° (南)
            new Vec2(1f, 1f),   // 45°
            new Vec2(1f, 0f),   // 90° (西)
            new Vec2(1f, -1f),  // 135°
            new Vec2(0f, -1f),  // 180° (北)
            new Vec2(-1f, -1f), // 225°
            new Vec2(-1f, 0f),  // 270° (東)
            new Vec2(-1f, 1f)   // 315°
    };

    public static void spawnHitboxFor(Player player, Level level,
                                      float width, float height, float depth,
                                      Vec3 localPos) {
        double yawRad = Math.toRadians(player.getYRot()); // 符号を反転しない版

        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);

        double x = localPos.x;
        double y = localPos.y;
        double z = localPos.z;

        // こちらを試してください（符号反転パターン）
        double dx = x * cosYaw - z * sinYaw;
        double dz = x * sinYaw + z * cosYaw;

        double worldX = player.getX() + dx;
        double worldY = player.getY() + y;
        double worldZ = player.getZ() + dz;

        HitboxEntity hitbox = new HitboxEntity(ModEntities.HITBOX.get(), level);
        hitbox.setPos(worldX, worldY, worldZ);
        hitbox.setCustomSize(width, height, depth);
        hitbox.setRotationYaw(player.getYRot());

        level.addFreshEntity(hitbox);

        System.out.println("[Hitbox] Spawned at " + worldX + ", " + worldY + ", " + worldZ);
    }
}