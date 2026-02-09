package net.rk.railroadways.entity.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.block.custom.RailroadCrossingArmBlock;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

public class RailroadCrossingArmWithLights extends BlockEntity {
    BlockPos bp;
    public float armAngle = 0;
    public float startingArmAngle = 1.35f;
    public float endArmAngle = 0.0f;
    public float yAngle = 0.0f;
    public float armLength = 1.0f;
    public float armGateOffsetZ = 0.0f;

    public boolean alternateFlashCycle = false;
    public int flasherTickDelay = 15;

    public int ticks;
    public RailroadCrossingArmWithLights.RailroadCrossingArmState railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.UP;

    public enum RailroadCrossingArmState{
        UP,
        MOVING,
        DOWN
    }

    public RailroadCrossingArmWithLights(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.RAILROAD_CROSSING_ARM_LIGHTED_BE.get(), pos, blockState);
        this.bp = pos;
    }

    public boolean getFlashState(){
        return alternateFlashCycle;
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

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, RailroadCrossingArmWithLights rrcbe){
        ++rrcbe.ticks;
        if(rrcbe.ticks % rrcbe.flasherTickDelay == 0){
            rrcbe.alternateFlashCycle = !rrcbe.alternateFlashCycle;
        }
        //
        if(slvl.getBlockState(sbp).getBlock() == TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get()){
            if(slvl.getBlockState(sbp).getValue(RailroadCrossingArmBlock.POWERED) == true){
                //
                if(rrcbe.armAngle > rrcbe.endArmAngle){
                    rrcbe.armAngle = Mth.rotLerp(0.05f,rrcbe.armAngle,rrcbe.armAngle - 0.15f);

                    rrcbe.railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.MOVING;
                    rrcbe.updateBlock();
                }
                else{
                    if(rrcbe.railroadCrossingArmState != RailroadCrossingArmWithLights.RailroadCrossingArmState.DOWN){
                        rrcbe.railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.DOWN;
                        rrcbe.updateBlock();
                    }
                }
            }
            else{
                if(rrcbe.armAngle < rrcbe.startingArmAngle){
                    rrcbe.armAngle = Mth.rotLerp(0.05f,rrcbe.armAngle,rrcbe.armAngle + 0.15f);
                    rrcbe.railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.MOVING;
                    rrcbe.updateBlock();
                }
                else{
                    if(rrcbe.railroadCrossingArmState != RailroadCrossingArmWithLights.RailroadCrossingArmState.UP){
                        rrcbe.railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.UP;
                        rrcbe.updateBlock();
                    }
                }
            }
        }
        // hard reset tick counter
        if(rrcbe.ticks >= 32767){
            rrcbe.ticks = 0;
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, RailroadCrossingArmWithLights rrcbe){
        ++rrcbe.ticks;
        if(rrcbe.ticks % rrcbe.flasherTickDelay == 0){
            rrcbe.alternateFlashCycle = !rrcbe.alternateFlashCycle;
        }
        // hard reset tick counter
        if(rrcbe.ticks >= 32767){
            rrcbe.ticks = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        super.saveAdditional(pTag, slp);
        pTag.putFloat("arm_angle",armAngle);
        pTag.putFloat("starting_arm_angle",startingArmAngle);
        pTag.putFloat("end_arm_angle",endArmAngle);
        pTag.putString("railroad_crossing_state",railroadCrossingArmState.name());
        pTag.putFloat("y_angle",yAngle);
        pTag.putFloat("arm_length",armLength);
        pTag.putFloat("arm_gate_offset_z",armGateOffsetZ);
        pTag.putInt("flasher_tick_delay",flasherTickDelay);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        armAngle = pTag.getFloat("arm_angle");
        startingArmAngle = pTag.getFloat("starting_arm_angle");
        endArmAngle = pTag.getFloat("end_arm_angle");
        railroadCrossingArmState = RailroadCrossingArmWithLights.RailroadCrossingArmState.valueOf(pTag.getString("railroad_crossing_state"));
        yAngle = pTag.getFloat("y_angle");
        armLength = pTag.getFloat("arm_length");
        armGateOffsetZ = pTag.getFloat("arm_gate_offset_z");
        flasherTickDelay = pTag.getInt("flasher_tick_delay");
        if(flasherTickDelay <= 0){
            flasherTickDelay = 15;
            updateBlock();
        }
    }
}
