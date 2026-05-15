package net.rk.railroadways.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rk.railroadways.entity.blockentity.custom.WigWagBE;

public class WigwagMenu extends AbstractContainerMenu {
    public WigWagBE be;
    public Level level;

    public WigwagMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(TRRMenu.WIGWAG_MENU.get(),id);
        if(extraData != null){
            be = (WigWagBE) inv.player.level().getBlockEntity(extraData.readBlockPos());
            level = inv.player.level();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.level().getBlockEntity(be.getBlockPos()) instanceof WigWagBE
                && player.canInteractWithBlock(be.getBlockPos(),7);
    }
}
