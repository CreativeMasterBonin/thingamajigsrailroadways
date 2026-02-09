package net.rk.railroadways.entity.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

public class DualRailwayLightsBE extends BlockEntity{
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public int delayTicks = 50;

    public String offLoc = "thingamajigsrailroadways:textures/entity/dual_off.png";
    public String whiteLoc = "thingamajigsrailroadways:textures/entity/dual_white.png";
    public String redLoc = "thingamajigsrailroadways:textures/entity/dual_red.png";

    public DualRailwayLightsBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.DUAL_RR_LIGHTS_BE.get(), pos, blockState);
        this.bp = pos;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.loadAdditional(tag,lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lp) {
        CompoundTag ct = new CompoundTag();
        saveAdditional(ct,lp);
        return ct;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null) {
            BlockState bs2 = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(), bs2, bs2, 3);
        }
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, DualRailwayLightsBE drlbe){
        ++drlbe.ticks;
        // hard reset tick counter
        if(drlbe.ticks >= drlbe.delayTicks){
            drlbe.ticks = 0;
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, DualRailwayLightsBE drlbe){
        ++drlbe.ticks;
        // hard reset tick counter
        if(drlbe.ticks >= drlbe.delayTicks){
            drlbe.ticks = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        super.saveAdditional(pTag, slp);
        pTag.putFloat("y_angle",yAngle);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        yAngle = pTag.getFloat("y_angle");
    }
}
