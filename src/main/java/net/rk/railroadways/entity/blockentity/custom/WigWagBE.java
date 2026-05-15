package net.rk.railroadways.entity.blockentity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.util.TRRSound;
import net.rk.railroadways.util.Utilities;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WigWagBE extends BlockEntity {
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public int maxTicksAllowed = 32767;
    public float swingAngle = 0.0f;
    public boolean strokeLeft = false; // pitch changer
    public byte signalDesignType = 127;
    public float maxSwingAngle = -45.0f;

    // linking variables
    public boolean linkedToController = false;
    public boolean externalPower = false;
    public BlockPos linkedPosition = BlockPos.ZERO;

    public void pairToLinkedPos(BlockPos attachedPos){
        linkedToController = true;
        linkedPosition = attachedPos;
        updateBlock();
    }

    public void unpair(){
        linkedToController = false;
        linkedPosition = BlockPos.ZERO;
        updateBlock();
    }

    public WigWagBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.WIGWAG_BE.get(), pos, blockState);
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

    @Override
    public void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        pTag.putFloat("y_angle", yAngle);
        pTag.putBoolean("linked_to_controller",linkedToController);
        pTag.put("linked_position", NbtUtils.writeBlockPos(linkedPosition));
        pTag.putFloat("swing_angle",swingAngle);
        pTag.putByte("signal_design_type",signalDesignType);
        pTag.putFloat("max_swing_angle",maxSwingAngle);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        yAngle = pTag.getFloat("y_angle");
        if(pTag.contains("linked_to_controller")){
            linkedToController = pTag.getBoolean("linked_to_controller");
        }
        if(pTag.contains("linked_position")){
            Optional<BlockPos> savedPairPos = NbtUtils.readBlockPos(pTag,"linked_position");
            savedPairPos.ifPresent(blockPos -> linkedPosition = blockPos);
        }
        if(pTag.contains("swing_angle")){
            swingAngle = pTag.getFloat("swing_angle");
        }
        if(pTag.contains("signal_design_type")){
            signalDesignType = pTag.getByte("signal_design_type");
        }
        if(pTag.contains("max_swing_angle")){
            maxSwingAngle = pTag.getFloat("max_swing_angle");
        }
        maxSwingAngle = Mth.clamp(maxSwingAngle,-85.0f,-45.0f);
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, WigWagBE wigWag){
        if(wigWag.linkedToController){
            if(slvl.getBlockEntity(wigWag.linkedPosition) == null) {
                wigWag.linkedToController = false;
                wigWag.linkedPosition = BlockPos.ZERO;
                wigWag.updateBlock();
                return;
            }
            if(wigWag.externalPower){
                if(!sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,true),3);
                }
            }
            else{
                if(sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,false),3);
                }
            }
        }
        else{
            wigWag.ticks++;
            if(wigWag.ticks >= wigWag.maxTicksAllowed){
                wigWag.ticks = 0;
                wigWag.updateBlock();
            }
        }
        // fade in bell sound when moving enough
        if(slvl.tickRateManager().runsNormally()){
            if(slvl.getGameTime() % 20L == 0 && sbs.getValue(BlockStateProperties.POWERED) && wigWag.swingAngle <= -10.0f){
                wigWag.strokeLeft = !wigWag.strokeLeft;
                if(wigWag.strokeLeft){
                    slvl.playSound(null,wigWag.getBlockPos(),
                            TRRSound.MECH_BELL_TWO.get(),SoundSource.BLOCKS,
                            Mth.clamp(Utilities.convertFloatRangeToOther(Mth.abs(wigWag.swingAngle),0.0f,45.0f,0.1f,1.0f),0.1f,1.0f), 1.0f);
                }
                else{
                    slvl.playSound(null,wigWag.getBlockPos(),
                            TRRSound.MECH_BELL_TWO.get(),SoundSource.BLOCKS,
                            Mth.clamp(Utilities.convertFloatRangeToOther(Mth.abs(wigWag.swingAngle),0.0f,45.0f,0.1f,1.0f),0.1f,1.0f), 1.02f);
                }
            }
        }
        // universal behavior
        // the 'physics' of the wigwag
        if(sbs.getValue(BlockStateProperties.POWERED)){
            if(wigWag.swingAngle > wigWag.maxSwingAngle){
                wigWag.swingAngle -= (float)((Util.getMillis() % 1200) / 1400.0f);
                wigWag.updateBlock();
            }
            else if(wigWag.swingAngle < wigWag.maxSwingAngle){
                wigWag.swingAngle += (float)((Util.getMillis() % 1200) / 1400.0f);
                wigWag.updateBlock();
            }
        }
        else {// wind down the 'applied motion'
            if (wigWag.swingAngle > 0.2f) {
                wigWag.swingAngle -= (float)((Util.getMillis() % 1200) / 1400.0f);
                wigWag.updateBlock();
            }
            else if(wigWag.swingAngle < -0.2f){
                wigWag.swingAngle += (float)((Util.getMillis() % 1200) / 1400.0f);
                wigWag.updateBlock();
            }
            else{
                if(wigWag.swingAngle != 0.0f){
                    wigWag.swingAngle = 0.0f; // finally reached our target value
                    wigWag.updateBlock();
                }
            }
            // fade out bell sound when off too
            if(slvl.getGameTime() % 20L == 0 && wigWag.swingAngle <= -9.0f){
                slvl.playSound(null,wigWag.getBlockPos(),
                        TRRSound.MECH_BELL_TWO.get(),SoundSource.BLOCKS,
                        Mth.clamp(Utilities.convertFloatRangeToOther(Mth.abs(wigWag.swingAngle),0.0f,45.0f,0.1f,1.0f),0.1f,1.0f), 1.0f);
            }
        }
    }
}
