package net.rk.railroadways.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rk.railroadways.entity.TRREntities;
import net.rk.railroadways.item.TRRItems;

public class DieselLocomotive extends AbstractLocomotive {
    public DieselLocomotive(Level level, double x, double y, double z) {
        super(TRREntities.DIESEL_LOCOMOTIVE.get(), level, x, y, z);
    }
    public DieselLocomotive(Level level) {
        super(TRREntities.DIESEL_LOCOMOTIVE.get(), level);
    }
    public DieselLocomotive(EntityType<DieselLocomotive> dieselLocomotiveEntityType, Level level) {
        super(TRREntities.DIESEL_LOCOMOTIVE.get(),level);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(TRRItems.DIESEL_LOCOMOTIVE_PLACER.asItem());
    }

    @Override
    public LocomotiveType getLocomotiveType(){
        return LocomotiveType.DIESEL;
    }

    @Override
    public Item getDropItem() {
        return TRRItems.DIESEL_LOCOMOTIVE_PLACER.asItem();
    }
}
