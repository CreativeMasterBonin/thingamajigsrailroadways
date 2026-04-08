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
import net.rk.railroadways.block.custom.PoleWithCrossingStopLight;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PoleWithCrossingStopLightBE extends BlockEntity {
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public boolean flashing = false;
    public int flashingInterval = 20;
    public boolean areLightsOn = false;

    public Orientation currentOrientation = Orientation.VERTICAL;
    public int brightness = 0;

    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null) {
            BlockState bs2 = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(), bs2, bs2, 3);
        }
    }

    public PoleWithCrossingStopLightBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.POLE_WITH_CROSSING_STOP_LIGHT_BE.get(), pos, blockState);
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
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag,registries);
        return tag;
    }

    public static void serverTick(Level slvl, BlockPos pos, BlockState blockState, PoleWithCrossingStopLightBE be){
        be.ticks++;
        if(be.ticks >= 32767){
            be.ticks = 0;
            be.setChanged();
        }

        if(blockState.getValue(PoleWithCrossingStopLight.POWERED)){
            be.areLightsOn = true;
            // slowly turn on lights
            if(be.ticks % 1 == 0 && !be.flashing)
                if(be.brightness < 3){
                    be.brightness += 1;
                    be.setChanged();
                    be.updateBlock();
                    if(be.brightness >= 3){
                        be.setChanged();
                        be.updateBlock();
                    }
                }

            // if in flasher mode and the light is bright
            if(be.flashing){
                if(be.ticks % be.flashingInterval == 0){
                    be.areLightsOn = !be.areLightsOn;
                    be.setChanged();
                    be.updateBlock();
                    if(be.areLightsOn){
                        be.brightness += 1;
                    }
                    else{
                        be.brightness -= 1;
                    }
                }
                if(be.areLightsOn){
                    be.brightness += 1;
                }
                else{
                    be.brightness -= 1;
                }
                be.brightness = Mth.clamp(be.brightness,0,3);
                be.updateBlock();
            }
            be.setChanged();
        }
        else{
            // dim the lights out
            if(be.ticks % 1 == 0)
                if(be.brightness > 0){
                    be.brightness -= 1;
                    be.setChanged();
                    be.updateBlock();
                }
            // if the lights are still on and the brightness is low
            if(be.areLightsOn && be.brightness <= 0){
                be.areLightsOn = false;
                be.updateBlock();
            }
        }

        if(be.brightness > 3){
            be.brightness = 3;
        }
        else if(be.brightness < 0){
            be.brightness = 0;
        }
        be.setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putFloat("y_angle",yAngle);
        tag.putString("orientation",currentOrientation.name());
        tag.putInt("flashing_interval",flashingInterval);
        tag.putBoolean("flashing",flashing);
        tag.putBoolean("lights_on",areLightsOn);
        tag.putInt("brightness",brightness);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(tag.contains("y_angle")){
            yAngle = tag.getFloat("y_angle");
        }
        if(tag.contains("orientation")){
            currentOrientation = Orientation.valueOf(tag.getString("orientation"));
        }
        if(tag.contains("flashing_interval")){
            flashingInterval = tag.getInt("flashing_interval");
        }
        if(tag.contains("flashing")){
            flashing = tag.getBoolean("flashing");
        }
        if(tag.contains("lights_on")){
            areLightsOn = tag.getBoolean("lights_on");
        }
        if(tag.contains("brightness")){
            brightness = tag.getInt("brightness");
        }
    }
}
