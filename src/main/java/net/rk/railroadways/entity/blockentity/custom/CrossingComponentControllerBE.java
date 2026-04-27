package net.rk.railroadways.entity.blockentity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrossingComponentControllerBE extends BlockEntity{
    public int universalTicks = 0;
    public int universalFlashInterval = 20;
    public boolean universalAlternatingFlash = false;

    public static final int hardLimitPairs = 20;

    public boolean pairingMode = false;

    public ArrayList<BlockPos> pairedPositions = new ArrayList<>(0);

    public CrossingComponentControllerBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.CROSSING_COMPONENT_CONTROLLER_BE.get(), pos, blockState);
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null){
            BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(),blockState,blockState,3);
        }
    }

    public void removePosition(BlockPos blockPos,boolean silent){
        if(pairedPositions.contains(blockPos)){
            for(int positionIndex = 0; positionIndex < pairedPositions.size() - 1; positionIndex++){
                boolean xIsOk = pairedPositions.get(positionIndex).getX() == blockPos.getX();
                boolean yIsOk = pairedPositions.get(positionIndex).getY() == blockPos.getY();
                boolean zIsOk = pairedPositions.get(positionIndex).getZ() == blockPos.getZ();

                System.out.println(blockPos.toShortString() + " comparing to " + pairedPositions.get(positionIndex).toShortString() + " is result " + (xIsOk && yIsOk && zIsOk));

                if(xIsOk && yIsOk && zIsOk){
                    pairedPositions.remove(positionIndex);
                    // silent removal for invalidation checks
                    if(!silent){
                        // notification for this block pos changed
                        this.getLevel().playSound(null,this.getBlockPos(),
                                SoundEvents.SCULK_CLICKING,SoundSource.BLOCKS,
                                1.0f,1.25f);
                        // notification for block pos changed
                        this.getLevel().playSound(null,blockPos,
                                SoundEvents.SCULK_CLICKING,SoundSource.BLOCKS,
                                1.0f,1.25f);
                    }
                    this.updateBlock();
                    break;
                }
            }
        }
        else{
            System.out.println("Problem removing pos: " + blockPos + "... is it supposed to be a mismatch?");
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag,registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.loadAdditional(tag,lookupProvider);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("universal_ticks",universalTicks);
        tag.putInt("universal_flash_interval",universalFlashInterval);
        tag.putBoolean("universal_alternating_flash",universalAlternatingFlash);
        int index = 0;

        for(BlockPos pos : this.pairedPositions){
            tag.put("paired_pos_" + index, NbtUtils.writeBlockPos(pos));
            index++;
        }
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, CrossingComponentControllerBE be){
        be.universalTicks++;
        if(be.universalTicks >= 32767){
            be.universalTicks = 0;
        }
        if(be.universalTicks % be.universalFlashInterval == 0){
            be.universalAlternatingFlash = !be.universalAlternatingFlash;
        }
        try{
            for (BlockPos pos : be.pairedPositions) {
                if(slvl.isLoaded(pos)){ // no erroneous chunk loading
                    if (slvl.getBlockEntity(pos) != null) {
                        if (slvl.getBlockEntity(pos) instanceof RailroadCrossingArmWithLights lightedArm) {
                            if(lightedArm.linkedToController){
                                if (lightedArm.flasherTickDelay != be.universalFlashInterval) {
                                    lightedArm.flasherTickDelay = be.universalFlashInterval;
                                    lightedArm.updateBlock();
                                }
                                lightedArm.ticks = be.universalTicks;
                                lightedArm.alternateFlashCycle = be.universalAlternatingFlash;

                                if (sbs.getValue(BlockStateProperties.POWERED)) {
                                    lightedArm.externalPower = true;
                                    lightedArm.updateBlock();
                                } else {
                                    lightedArm.externalPower = false;
                                    lightedArm.updateBlock();
                                }
                            }
                            else{
                                be.pairedPositions.remove(pos);
                                be.setChanged();
                                break;
                            }
                        }
                        else if (slvl.getBlockEntity(pos) instanceof RailroadCrossingBE gate) {
                            if(gate.linkedToController){
                                gate.ticks = be.universalTicks;

                                if (sbs.getValue(BlockStateProperties.POWERED)) {
                                    gate.externalPower = true;
                                    gate.updateBlock();
                                }
                                else {
                                    gate.externalPower = false;
                                    gate.updateBlock();
                                }
                            }
                            else{
                                be.pairedPositions.remove(pos);
                                be.setChanged();
                                break;
                            }
                        }
                        else if(slvl.getBlockEntity(pos) instanceof BritRailwayLightsBE britLights){
                            if(britLights.linkedToController){
                                britLights.ticks = be.universalTicks;
                                britLights.onLeftFlash = be.universalAlternatingFlash;

                                if (sbs.getValue(BlockStateProperties.POWERED)) {
                                    britLights.externalPower = true;
                                    britLights.updateBlock();
                                }
                                else {
                                    britLights.externalPower = false;
                                    britLights.updateBlock();
                                }
                            }
                            else{
                                be.pairedPositions.remove(pos);
                                be.setChanged();
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            LogUtils.getLogger().error(e.getLocalizedMessage());
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(tag.contains("universal_ticks")){
            universalTicks = tag.getInt("universal_ticks");
        }
        if(tag.contains("universal_flash_interval")){
            universalFlashInterval = tag.getInt("universal_flash_interval");
        }
        if(tag.contains("universal_alternating_flash")){
            universalAlternatingFlash = tag.getBoolean("universal_alternating_flash");
        }
        this.pairedPositions.clear(); // we do not want any duplicate positions when loading data from disk
        for(String key : tag.getAllKeys().stream().filter(key ->
                key.startsWith("paired_pos_")).collect(Collectors.toSet())){
            Optional<BlockPos> savedPairPos = NbtUtils.readBlockPos(tag,key);
            savedPairPos.ifPresent(blockPos -> {
                this.pairedPositions.add(blockPos);
            });
        }
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, CrossingComponentControllerBE crossingComponentControllerBE) {

    }
}
