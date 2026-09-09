package net.rk.railroadways.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;

public abstract class AbstractLocomotive extends AbstractMinecart {
    public boolean autoMove = false;

    public AbstractLocomotive(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public AbstractLocomotive(EntityType<?> entityType, Level level, double x, double y, double z) {
        super(entityType, level, x, y, z);
    }
}
