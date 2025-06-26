package com.risudayooo.ARL.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
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

        System.out.println("[HitboxEntity] tick count: " + this.tickCount + " at position: " + this.position());

        if (!level().isClientSide) {
            showBoxOutlineWithParticles();
            level().addParticle(ParticleTypes.FLAME, getX(), getY() + 1, getZ(), 0, 0, 0);
        }

        if (tickCount == 1 && !level().isClientSide) {
            System.out.println("[HitboxEntity] spawning particles...");
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

        drawEdge(minX, minY, minZ, maxX, minY, minZ);
        drawEdge(minX, maxY, minZ, maxX, maxY, minZ);
        drawEdge(minX, minY, maxZ, maxX, minY, maxZ);
        drawEdge(minX, maxY, maxZ, maxX, maxY, maxZ);
        drawEdge(minX, minY, minZ, minX, maxY, minZ);
        drawEdge(maxX, minY, minZ, maxX, maxY, minZ);
        drawEdge(minX, minY, maxZ, minX, maxY, maxZ);
        drawEdge(maxX, minY, maxZ, maxX, maxY, maxZ);
        drawEdge(minX, minY, minZ, minX, minY, maxZ);
        drawEdge(maxX, minY, minZ, maxX, minY, maxZ);
        drawEdge(minX, maxY, minZ, minX, maxY, maxZ);
        drawEdge(maxX, maxY, minZ, maxX, maxY, maxZ);
    }

    private void drawEdge(double x1, double y1, double z1, double x2, double y2, double z2) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        int segments = 10;
        for (int i = 0; i <= segments; i++) {
            double t = i / (double) segments;
            double x = x1 + (x2 - x1) * t;
            double y = y1 + (y2 - y1) * t;
            double z = z1 + (z2 - z1) * t;

            serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 10, 0, 0, 0, 0.05);
            System.out.printf("[Particle] %f, %f, %f%n", x, y, z);
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