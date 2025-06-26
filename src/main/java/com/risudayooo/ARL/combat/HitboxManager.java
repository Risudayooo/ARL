package com.risudayooo.ARL.combat;

import com.risudayooo.ARL.entity.HitboxEntity;
import com.risudayooo.ARL.registry.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HitboxManager {

    public static void spawnHitboxFor(Player player, Level level,
                                      float width, float height, float depth,
                                      Vec3 localPos) {
        double yawRad = Math.toRadians(-player.getYRot());
        double pitchRad = Math.toRadians(-player.getXRot());

        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);
        double cosPitch = Math.cos(pitchRad);
        double sinPitch = Math.sin(pitchRad);

        double x = localPos.x;
        double y = localPos.y;
        double z = localPos.z;

        double dx = x * cosYaw - z * sinYaw;
        double dz = x * sinYaw + z * cosYaw;

        double dy = y * cosPitch - dz * sinPitch;
        dz = y * sinPitch + dz * cosPitch;

        double worldX = player.getX() + dx;
        double worldY = player.getY() + dy;
        double worldZ = player.getZ() + dz;

        HitboxEntity hitbox = new HitboxEntity(ModEntities.HITBOX.get(), level);
        hitbox.setPos(worldX, worldY, worldZ);
        hitbox.setCustomSize(width, height, depth);
        hitbox.setRotationYaw(player.getYRot());

        level.addFreshEntity(hitbox);

        System.out.println("[Hitbox] Spawned at " + worldX + ", " + worldY + ", " + worldZ);
    }
}