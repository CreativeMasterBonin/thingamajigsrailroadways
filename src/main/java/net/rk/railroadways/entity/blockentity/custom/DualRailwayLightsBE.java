package net.rk.railroadways.entity.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DualRailwayLightsBE extends BlockEntity{
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public boolean alternateFlash = false;
    public int flasherTickDelay = 50;

    public String offLoc = "thingamajigsrailroadways:textures/entity/dual_off.png";
    public String whiteLoc = "thingamajigsrailroadways:textures/entity/dual_white.png";
    public String redLoc = "thingamajigsrailroadways:textures/entity/dual_red.png";

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
        if(drlbe.linkedToController){
            if(slvl.getBlockEntity(drlbe.linkedPosition) == null) {
                drlbe.linkedToController = false;
                drlbe.linkedPosition = BlockPos.ZERO;
                drlbe.updateBlock();
                return;
            }
            if(drlbe.externalPower){
                if(!sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,true),3);
                }
            }
            else{
                if(sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,false),3);
                }
            }
            return;
        }
        ++drlbe.ticks;
        // hard reset tick counter
        if(drlbe.ticks >= 32767){
            drlbe.ticks = 0;
        }

        if(drlbe.ticks % drlbe.flasherTickDelay == 0){
            drlbe.alternateFlash = !drlbe.alternateFlash;
            drlbe.updateBlock();
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, DualRailwayLightsBE drlbe){
        ++drlbe.ticks;
        // hard reset tick counter
        if(drlbe.ticks >= 32767){
            drlbe.ticks = 0;
        }
    }

    @Override
    public void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        pTag.putFloat("y_angle",yAngle);
        pTag.putInt("flasher_tick_delay",flasherTickDelay);
        pTag.putBoolean("linked_to_controller",linkedToController);
        pTag.put("linked_position", NbtUtils.writeBlockPos(linkedPosition));
        pTag.putBoolean("on_alt_flash",alternateFlash);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        yAngle = pTag.getFloat("y_angle");
        if(pTag.contains("flasher_tick_delay")){
            flasherTickDelay = pTag.getInt("flasher_tick_delay");
        }
        if(flasherTickDelay <= 0){
            flasherTickDelay = 15;
            updateBlock();
        }
        if(pTag.contains("linked_to_controller")){
            linkedToController = pTag.getBoolean("linked_to_controller");
        }
        if(pTag.contains("linked_position")){
            Optional<BlockPos> savedPairPos = NbtUtils.readBlockPos(pTag,"linked_position");
            savedPairPos.ifPresent(blockPos -> linkedPosition = blockPos);
        }
        if(pTag.contains("on_alt_flash")){
            alternateFlash = pTag.getBoolean("on_alt_flash");
        }
    }
}
