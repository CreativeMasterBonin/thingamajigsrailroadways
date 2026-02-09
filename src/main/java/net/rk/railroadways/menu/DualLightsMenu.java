package net.rk.railroadways.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rk.railroadways.entity.blockentity.custom.DualRailwayLightsBE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DualLightsMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>>{
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;

    private DualRailwayLightsBE be;

    public DualLightsMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(TRRMenu.DUAL_LIGHTS_MENU.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(0);
        BlockPos pos;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            this.be = (DualRailwayLightsBE)inv.player.level().getBlockEntity(pos);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public Map<Integer,Slot> get() {
        return customSlots;
    }
}
