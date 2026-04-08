package net.rk.railroadways.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rk.railroadways.entity.blockentity.custom.PoleWithCrossingStopLightBE;

public class PoleWithCrossingStopLightMenu extends AbstractContainerMenu {
    public PoleWithCrossingStopLightBE be;
    public Level level;

    public PoleWithCrossingStopLightMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(TRRMenu.POLE_WITH_CROSSING_STOP_LIGHT_MENU.get(), id);
        if(extraData != null){
            be = (PoleWithCrossingStopLightBE) inv.player.level().getBlockEntity(extraData.readBlockPos());
            level = inv.player.level();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
