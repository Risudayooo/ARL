package com.risudayooo.ARL.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

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
        }

        if (tickCount == 1 && !level().isClientSide) {
            System.out.println("[HitboxEntity] spawning particles...");
        }

        // 一瞬で消える設定（必要に応じてコメントアウト）
        if (this.tickCount > 2) {
            this.discard();
        }
    }

    private void showBoxOutlineWithParticles() {
        double w = boxWidth;
        double h = boxHeight;
        double d = boxDepth;
        Vec3 center = this.position();

        Vec3 p000 = rotate(new Vec3(-w / 2, 0, -d / 2)).add(center);
        Vec3 p100 = rotate(new Vec3(w / 2, 0, -d / 2)).add(center);
        Vec3 p110 = rotate(new Vec3(w / 2, 0, d / 2)).add(center);
        Vec3 p010 = rotate(new Vec3(-w / 2, 0, d / 2)).add(center);
        Vec3 p001 = rotate(new Vec3(-w / 2, h, -d / 2)).add(center);
        Vec3 p101 = rotate(new Vec3(w / 2, h, -d / 2)).add(center);
        Vec3 p111 = rotate(new Vec3(w / 2, h, d / 2)).add(center);
        Vec3 p011 = rotate(new Vec3(-w / 2, h, d / 2)).add(center);

        drawEdge(p000, p100); drawEdge(p100, p110); drawEdge(p110, p010); drawEdge(p010, p000);
        drawEdge(p001, p101); drawEdge(p101, p111); drawEdge(p111, p011); drawEdge(p011, p001);
        drawEdge(p000, p001); drawEdge(p100, p101); drawEdge(p110, p111); drawEdge(p010, p011);
    }

    private Vec3 rotate(Vec3 point) {
        double yawRad = Math.toRadians(+this.yawDegrees); // マイナスで左回りに補正
        double cos = Math.cos(yawRad);
        double sin = Math.sin(yawRad);
        double x = point.x * cos + point.z * sin;
        double z = point.x * sin - point.z * cos;
        return new Vec3(x, point.y, z);
    }

    private void drawEdge(Vec3 from, Vec3 to) {
        int segments = 3;
        for (int i = 0; i <= segments; i++) {
            double t = i / (double) segments;
            double x = from.x + (to.x - from.x) * t;
            double y = from.y + (to.y - from.y) * t;
            double z = from.z + (to.z - from.z) * t;

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