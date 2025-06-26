package com.risudayooo.ARL.combat;

import com.risudayooo.ARL.entity.HitboxEntity;
import com.risudayooo.ARL.registry.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HitboxManager {

    /**
     * プレイヤーのYawに対して、ローカル座標(localPos)を回転・変換し、
     * ヒットボックスをワールドに出現させます。
     * Pitch（上下方向）は無視し、視点に対して常に垂直な位置に出す。
     */
    public static void spawnHitboxFor(Player player, Level level,
                                      float width, float height, float depth,
                                      Vec3 localPos) {
        // プレイヤーのYaw回転を左回りに補正（数学座標系に合わせる）
        double yawRad = Math.toRadians(-player.getYRot());

        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);

        // ローカル座標（回転前）
        double x = localPos.x;
        double y = localPos.y;
        double z = localPos.z;

        // Yaw回転（水平面上）
        double dx = x * cosYaw + z * sinYaw;
        double dz = x * sinYaw + z * cosYaw;

        // ワールド座標計算
        double worldX = player.getX() + dx;
        double worldY = player.getY() + player.getEyeHeight() + y;
        double worldZ = player.getZ() + dz;

        // ヒットボックス生成と設定
        HitboxEntity hitbox = new HitboxEntity(ModEntities.HITBOX.get(), level);
        hitbox.setPos(worldX, worldY, worldZ);
        hitbox.setCustomSize(width, height, depth);
        hitbox.setRotationYaw(player.getYRot()); // Yawにだけ従って面向きを設定

        level.addFreshEntity(hitbox);

        System.out.println("[Hitbox] Spawned at " + worldX + ", " + worldY + ", " + worldZ);
    }
}