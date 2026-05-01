package net.rk.railroadways.entity.blockentity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.rk.railroadways.block.custom.EnhancedDirectionalCrossingLight;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;

import java.util.Optional;

public class EnhancedDirectionalCrossingLightBE extends BlockEntity {
    public int ticks = 0;
    public float yAngle = 0;
    // the current pattern index for the directional lights
    //public int directionalLightPatternIndex = 0;
    public boolean orangeLightsShowDirectionOfTravel = false;
    //public int iteratingRedLightSpeed = 10;
    //public int iteratingOrangeLightSpeed = 4;
    public boolean checksNorthSouthTrack = false;
    public boolean swapNSEWcheck = false;
    public byte flasherConfiguration = 127;

    // the current pattern index for the red strobe lights
    // the real lights usually flash in a left to right pattern, always with a strobe double flash
    //public int currentRedLightPatternIndex = 0;
    // the real lights never had a centered mode, this is for usage where direction of travel isn't needed
    public EnhancedDirectionalCrossingLightBE.DirectionalLightStates orangeLightState = DirectionalLightStates.CENTER;

    // common values associated with connecting to controllers
    public boolean linkedToController = false;
    public boolean externalPower = false;
    public BlockPos linkedPosition = BlockPos.ZERO;

    // left starts from right-most light and goes to the left, ending in a white light double flash
    public enum DirectionalLightStates {
        LEFT,
        RIGHT,
        CENTER
    }

    // NOTE: these were planned and working-ish but were replaced by block model rendering in the BER instead
    // same concept, but without entity models and layers
    // 0 is no light, 1 is left light, 2 is right light
    /*public static final int[] redLightPattern = {0,0,0,1,0,1,0,0,0,2,0,2};
    // -99 is no light, 0 is left white light, 1-7 is orange lights, 8 is right white light
    public static final int[] directionalLightPatternLeft = {7,6,5,4,3,2,1,0,-99,0,-99,-99,-99,-99};
    public static final int[] directionalLightPatternRight = {1,2,3,4,5,6,7,8,-99,8,-99,-99,-99,-99};
    public static final int[] centeredLightPattern = {4,3,5,2,6,1,7,8,0,-99,-99,-99,-99,-99,-99,-99};*/

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

    public EnhancedDirectionalCrossingLightBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.ENHANCED_DIRECTIONAL_CROSSING_LIGHT_BE.get(), pos, blockState);
    }

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
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag,lp);
        return tag;
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null){
            BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(),blockState,blockState,3);
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(tag.contains("y_angle")){
            yAngle = tag.getFloat("y_angle");
        }
        if(tag.contains("flasher_configuration")){
            flasherConfiguration = tag.getByte("flasher_configuration");
        }
        if(tag.contains("show_direction")){
            orangeLightsShowDirectionOfTravel = tag.getBoolean("show_direction");
        }
        if(tag.contains("checks_north_south_signal")){
            checksNorthSouthTrack = tag.getBoolean("checks_north_south_signal");
        }
        if(tag.contains("direction_of_travel")){
            try{
                orangeLightState = DirectionalLightStates.valueOf(tag.getString("direction_of_travel"));
            }
            catch (Exception e){
                LogUtils.getLogger().error("DirectionalLightState was: {} but should have been any of: {}",tag.getString("direction_of_travel"),DirectionalLightStates.values());
                LogUtils.getLogger().warn("Modified DirectionLightState to center mode since an error occurred");
                orangeLightState = DirectionalLightStates.CENTER;
            }
        }
        if(tag.contains("swap_signal_check")){
            swapNSEWcheck = tag.getBoolean("swap_signal_check");
        }
        if(tag.contains("linked_to_controller")){
            linkedToController = tag.getBoolean("linked_to_controller");
        }
        if(tag.contains("linked_position")){
            Optional<BlockPos> savedPairPos = NbtUtils.readBlockPos(tag,"linked_position");
            savedPairPos.ifPresent(blockPos -> linkedPosition = blockPos);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putFloat("y_angle",yAngle);
        tag.putByte("flasher_configuration",flasherConfiguration);
        tag.putBoolean("show_direction",orangeLightsShowDirectionOfTravel);
        tag.putBoolean("checks_north_south_signal",checksNorthSouthTrack);
        tag.putString("direction_of_travel",orangeLightState.toString());
        tag.putBoolean("swap_signal_check",swapNSEWcheck);
        tag.putBoolean("linked_to_controller",linkedToController);
        tag.put("linked_position", NbtUtils.writeBlockPos(linkedPosition));
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, EnhancedDirectionalCrossingLightBE lights){
        if(lights.linkedToController){
            if(slvl.getBlockEntity(lights.linkedPosition) == null){
                lights.linkedToController = false;
                lights.linkedPosition = BlockPos.ZERO;
                lights.updateBlock();
                return;
            }
            if(lights.externalPower){
                if(!sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,true)
                            .setValue(EnhancedDirectionalCrossingLight.redLightState, EnhancedDirectionalCrossingLight.RedLightStates.NONE)
                            .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.NONE),3);
                }
            }
            else{
                if(sbs.getValue(BlockStateProperties.POWERED)){
                    slvl.setBlock(sbp,sbs.setValue(BlockStateProperties.POWERED,false)
                            .setValue(EnhancedDirectionalCrossingLight.redLightState, EnhancedDirectionalCrossingLight.RedLightStates.NONE)
                            .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.NONE),3);
                }
            }
            return;
        }
        else{
            ++lights.ticks;
            // hard reset tick counter
            if(lights.ticks >= 32767) {
                lights.ticks = 0;
            }

            boolean north = false;
            boolean south = false;
            boolean east = false;
            boolean west = false;

            if(lights.orangeLightsShowDirectionOfTravel){
                if(lights.checksNorthSouthTrack){
                    north = slvl.getControlInputSignal(sbp.north(), Direction.NORTH,false) > 0;
                    south = slvl.getControlInputSignal(sbp.south(), Direction.SOUTH,false) > 0;
                }
                else{
                    east = slvl.getControlInputSignal(sbp.east(), Direction.EAST,false) > 0;
                    west = slvl.getControlInputSignal(sbp.west(), Direction.WEST,false) > 0;
                }

                if(north){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    lights.updateBlock();
                }
                else if(south){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    lights.updateBlock();
                }
                else if(east){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    lights.updateBlock();
                }
                else if(west){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    lights.updateBlock();
                }
                else{
                    lights.orangeLightState = DirectionalLightStates.CENTER;
                    lights.updateBlock();
                }


            /*if(lights.ticks % lights.iteratingOrangeLightSpeed == 0)
                switch(lights.orangeLightState){
                    case LEFT -> {
                        if(lights.directionalLightPatternIndex <= directionalLightPatternLeft.length - 1){
                            lights.directionalLightPatternIndex += 1;
                        }
                        else{
                            lights.directionalLightPatternIndex = 0;
                        }
                        lights.updateBlock();
                    }
                    case RIGHT -> {
                        if(lights.directionalLightPatternIndex <= directionalLightPatternRight.length - 1){
                            lights.directionalLightPatternIndex += 1;
                        }
                        else{
                            lights.directionalLightPatternIndex = 0;
                        }
                        lights.updateBlock();
                    }
                    default -> {
                        lights.directionalLightPatternIndex = 0;
                    }
                }*/
            }
            else{
                lights.orangeLightState = DirectionalLightStates.CENTER;
            /*if(lights.ticks % lights.iteratingOrangeLightSpeed == 0){
                if(lights.directionalLightPatternIndex <= centeredLightPattern.length - 1){
                    lights.directionalLightPatternIndex += 1;
                }
                else{
                    lights.directionalLightPatternIndex = 0;
                }
                lights.updateBlock();
            }*/
            }

        /*if(lights.ticks % lights.iteratingRedLightSpeed == 0){
            if(lights.currentRedLightPatternIndex <= redLightPattern.length - 1){
                lights.currentRedLightPatternIndex += 1;
            }
            else{
                lights.currentRedLightPatternIndex = 0;
            }
            lights.updateBlock();
        }*/
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, EnhancedDirectionalCrossingLightBE lights){
        if(lights.linkedToController) {
            return;
        }
        else{
            ++lights.ticks;
            // hard reset tick counter
            if(lights.ticks >= 32767) {
                lights.ticks = 0;
            }

            boolean north = false;
            boolean south = false;
            boolean east = false;
            boolean west = false;

            if(lights.orangeLightsShowDirectionOfTravel){
                if(lights.checksNorthSouthTrack){
                    north = lvl.getControlInputSignal(bp.north(), Direction.NORTH,false) > 0;
                    south = lvl.getControlInputSignal(bp.south(), Direction.SOUTH,false) > 0;
                }
                else{
                    east = lvl.getControlInputSignal(bp.east(), Direction.EAST,false) > 0;
                    west = lvl.getControlInputSignal(bp.west(), Direction.WEST,false) > 0;
                }

                if(north){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    lights.updateBlock();
                }
                else if(south){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    lights.updateBlock();
                }
                else if(east){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    lights.updateBlock();
                }
                else if(west){
                    if(lights.swapNSEWcheck){
                        lights.orangeLightState = DirectionalLightStates.LEFT;
                    }
                    else{
                        lights.orangeLightState = DirectionalLightStates.RIGHT;
                    }
                    lights.updateBlock();
                }
                else{
                    lights.orangeLightState = DirectionalLightStates.CENTER;
                    lights.updateBlock();
                }
            }
            else{
                lights.orangeLightState = DirectionalLightStates.CENTER;
            }
        }
    }
}
