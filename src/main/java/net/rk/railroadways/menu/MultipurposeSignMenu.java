package net.rk.railroadways.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rk.railroadways.entity.blockentity.custom.MultipurposeSignBE;
import net.rk.railroadways.registry.MultipurposeSignType;
import net.rk.railroadways.registry.TRRRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class MultipurposeSignMenu extends AbstractContainerMenu {
    public Level level;
    public Player player;
    public MultipurposeSignBE be;
    public BlockPos pos;
    public final DataSlot signTypeData = DataSlot.standalone();
    public static List<MultipurposeSignType> signTypes;
    public int indexSelected;
    private final HolderGetter<MultipurposeSignType> multipurposeSignTypeGetter;

    public static final String translationKey = "menu.railroadways.multipurpose_sign.title";

    public MultipurposeSignMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
        multipurposeSignTypeGetter = null;
    }

    public int getIndex(){
        return indexSelected;
    }

    public List<MultipurposeSignType> getList(){
        return signTypes;
    }

    public MultipurposeSignMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(TRRMenu.MULTIPURPOSE_SIGN_MENU.get(),id);
        this.player = inv.player;
        this.level = player.level();
        multipurposeSignTypeGetter = player.registryAccess().lookupOrThrow(TRRRegistries.MULTIPURPOSE_SIGN_TYPE);

        if(extraData != null) {
            BlockPos pos1 = extraData.readBlockPos();
            this.pos = pos1;
            if (level.getBlockEntity(pos) instanceof MultipurposeSignBE) {
                this.be = (MultipurposeSignBE) level.getBlockEntity(pos);
            }
        }
        this.addDataSlot(signTypeData);
        MultipurposeSignMenu.signTypes = this.level.registryAccess().registryOrThrow(TRRRegistries.MULTIPURPOSE_SIGN_TYPE).stream().toList();

        // the sign type data to send and retrieve
        addDataSlot(new DataSlot(){
            @Override
            public int get() {
                return be.indexId & 0xffff;
            }

            @Override
            public void set(int pValue) {
                MultipurposeSignMenu.this.indexSelected = (MultipurposeSignMenu.this.indexSelected & 0xffff0000) | (pValue & 0xffff);
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.level().getBlockEntity(be.getBlockPos()) instanceof MultipurposeSignBE
                && player.canInteractWithBlock(be.getBlockPos(),7);
    }

    public boolean clickedSignTypeSelectorButton(Player player, int id) {
        if (id >= 0 && id < this.signTypes.size()) {
            return true;
        }
        else {
            return false;
        }
    }
}
