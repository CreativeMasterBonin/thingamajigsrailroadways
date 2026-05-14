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

public class RailroadCrossingCantLightsBE extends BlockEntity{
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public int maxTicksAllowed = 32767;
    private boolean hideOverride = false;

    public float frontLeftAngle = 0.0f;
    public float frontRightAngle = 0.0f;
    public float backLeftAngle = 0.0f;
    public float backRightAngle = 0.0f;

    public boolean showFrontLights = true;
    public boolean showBackLights = true;

    public boolean alternateFlashCycle = false;
    public int flasherTickDelay = 15;

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

    public boolean getFlashState(){
        return alternateFlashCycle;
    }

    public RailroadCrossingCantLightsBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.RR_CANTILEVER_LIGHTS_BE.get(), pos, blockState);
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
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        super.saveAdditional(pTag, slp);
        pTag.putFloat("y_angle",yAngle);
        pTag.putFloat("front_left_angle",frontLeftAngle);
        pTag.putFloat("front_right_angle",frontRightAngle);
        pTag.putFloat("back_left_angle",backLeftAngle);
        pTag.putFloat("back_right_angle",backRightAngle);
        pTag.putBoolean("show_front_lights",showFrontLights);
        pTag.putBoolean("show_back_lights",showBackLights);
        pTag.putInt("flasher_tick_delay",flasherTickDelay);
        pTag.putBoolean("linked_to_controller",linkedToController);
        pTag.put("linked_position", NbtUtils.writeBlockPos(linkedPosition));
        pTag.putBoolean("on_alt_flash",alternateFlashCycle);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        yAngle = pTag.getFloat("y_angle");
        frontLeftAngle = pTag.getFloat("front_left_angle");
        frontRightAngle = pTag.getFloat("front_right_angle");
        backLeftAngle = pTag.getFloat("back_left_angle");
        backRightAngle = pTag.getFloat("back_right_angle");
        showFrontLights = pTag.getBoolean("show_front_lights");
        showBackLights = pTag.getBoolean("show_back_lights");
        flasherTickDelay = pTag.getInt("flasher_tick_delay");
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
            alternateFlashCycle = pTag.getBoolean("on_alt_flash");
        }
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, RailroadCrossingCantLightsBE rclbe){
        if(rclbe.linkedToController){
            if(slvl.getBlockEntity(rclbe.linkedPosition) == null) {
                rclbe.linkedToController = false;
                rclbe.linkedPosition = BlockPos.ZERO;
                rclbe.updateBlock();
                return;
            }

            if(rclbe.externalPower){
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
        else{
            ++rclbe.ticks;
            if(rclbe.ticks % rclbe.flasherTickDelay == 0){
                rclbe.alternateFlashCycle = !rclbe.alternateFlashCycle;
            }
            // hard reset tick counter
            if(rclbe.ticks >= rclbe.maxTicksAllowed){
                rclbe.ticks = 0;
            }
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, RailroadCrossingCantLightsBE rclbe){
        if(rclbe.linkedToController){
            return;
        }
        else{
            ++rclbe.ticks;
            if(rclbe.ticks % rclbe.flasherTickDelay == 0){
                rclbe.alternateFlashCycle = !rclbe.alternateFlashCycle;
            }
            //
            if(lvl.hasNearbyAlivePlayer(bp.getX(),bp.getY(),bp.getZ(),5)){
                rclbe.hideOverride = false;
            }
            else{
                rclbe.hideOverride = true;
            }
            // hard reset tick counter
            if(rclbe.ticks >= rclbe.maxTicksAllowed){
                rclbe.ticks = 0;
            }
        }
    }
}
