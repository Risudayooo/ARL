package com.risudayooo.ARL.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.phys.Vec3;

public class HitboxEntity extends Entity {

    private float boxWidth = 1.0f;
    private float boxHeight = 1.0f;
    private float boxDepth = 1.0f;
    private float yawDegrees = 0.0f;

    public HitboxEntity(EntityType<? extends HitboxEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public void setCustomSize(float width, float height, float depth) {
        this.boxWidth = width;
        this.boxHeight = height;
        this.boxDepth = depth;
    }

    public void setRotationYaw(float yawDegrees) {
        this.yawDegrees = yawDegrees;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            showBoxOutlineWithParticles();
            level().addParticle(ParticleTypes.FLAME, getX(), getY() + 1, getZ(), 0, 0, 0.01);
        }

        if (tickCount == 1 && !level().isClientSide) {
            System.out.println("[HitboxEntity] spawning particles...");
        }

        if (tickCount > 2) {
            this.discard();
            System.out.println("[HitboxEntity] discarded after short lifespan");
        }
    }

    private void showBoxOutlineWithParticles() {
        double w = boxWidth;
        double h = boxHeight;
        double d = boxDepth;

        Vec3 base = this.position();

        double minX = base.x - w / 2;
        double maxX = base.x + w / 2;
        double minY = base.y;
        double maxY = base.y + h;
        double minZ = base.z - d / 2;
        double maxZ = base.z + d / 2;

        drawEdge(minX, minY, minZ, maxX, minY, minZ); // 前面下辺
        drawEdge(minX, maxY, minZ, maxX, maxY, minZ); // 前面上辺
        drawEdge(minX, minY, maxZ, maxX, minY, maxZ); // 背面下辺
        drawEdge(minX, maxY, maxZ, maxX, maxY, maxZ); // 背面上辺
        drawEdge(minX, minY, minZ, minX, maxY, minZ); // 左前柱
        drawEdge(maxX, minY, minZ, maxX, maxY, minZ); // 右前柱
        drawEdge(minX, minY, maxZ, minX, maxY, maxZ); // 左後柱
        drawEdge(maxX, minY, maxZ, maxX, maxY, maxZ); // 右後柱
        drawEdge(minX, minY, minZ, minX, minY, maxZ); // 底面左辺
        drawEdge(maxX, minY, minZ, maxX, minY, maxZ); // 底面右辺
        drawEdge(minX, maxY, minZ, minX, maxY, maxZ); // 上面左辺
        drawEdge(maxX, maxY, minZ, maxX, maxY, maxZ); // 上面右辺
    }

    private void drawEdge(double x1, double y1, double z1, double x2, double y2, double z2) {
        int segments = 3;
        for (int i = 0; i <= segments; i++) {
            double t = i / (double) segments;
            double x = x1 + (x2 - x1) * t;
            double y = y1 + (y2 - y1) * t;
            double z = z1 + (z2 - z1) * t;

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT, x, y, z, 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}