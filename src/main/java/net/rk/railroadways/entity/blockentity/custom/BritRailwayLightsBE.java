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
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.block.custom.BritRailwayLightsBlock;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

public class BritRailwayLightsBE extends BlockEntity{
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public int delayTicks = 60;

    public String offLoc = "thingamajigsrailroadways:textures/entity/brit_off.png";
    public String amberLoc = "thingamajigsrailroadways:textures/entity/brit_amber.png";
    public String on0 = "thingamajigsrailroadways:textures/entity/brit_on_0.png";
    public String on1 = "thingamajigsrailroadways:textures/entity/brit_on_1.png";

    public enum BritRailwayLightsState {
        OFF,
        AMBER,
        ON
    }

    public BritRailwayLightsState lightsState = BritRailwayLightsState.OFF;

    public BritRailwayLightsBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.BRIT_RR_LIGHTS_BE.get(), pos, blockState);
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

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, BritRailwayLightsBE brlbe){
        ++brlbe.ticks;
        // hard reset tick counter
        if(brlbe.ticks >= brlbe.delayTicks){
            if(slvl.getBlockState(sbp).is(TRRBlocks.BRITISH_RAILWAY_LIGHTS)){
                if(sbs.getValue(BritRailwayLightsBlock.POWERED)){
                    if(brlbe.lightsState == BritRailwayLightsState.ON){
                        brlbe.delayTicks = 40;
                    }
                    else{
                        if(brlbe.lightsState == BritRailwayLightsState.OFF){
                            brlbe.lightsState = BritRailwayLightsState.AMBER;
                            brlbe.delayTicks = 120;
                            brlbe.updateBlock();
                        }
                        else{
                            if(brlbe.lightsState == BritRailwayLightsState.AMBER){
                                brlbe.lightsState = BritRailwayLightsState.ON;
                                brlbe.delayTicks = 60;
                                brlbe.updateBlock();
                            }
                        }
                    }
                }
                else{
                    if(brlbe.lightsState != BritRailwayLightsState.OFF){
                        brlbe.lightsState = BritRailwayLightsState.OFF;
                        brlbe.delayTicks = 60;
                        brlbe.updateBlock();
                    }
                }
            }
            brlbe.ticks = 0;
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, BritRailwayLightsBE brlbe){
        ++brlbe.ticks;
        // hard reset tick counter
        if(brlbe.ticks >= brlbe.delayTicks){
            brlbe.ticks = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        super.saveAdditional(pTag, slp);
        pTag.putString("brit_light_state",lightsState.name());
        pTag.putFloat("y_angle",yAngle);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        lightsState = BritRailwayLightsBE.BritRailwayLightsState.valueOf(pTag.getString("brit_light_state"));
        yAngle = pTag.getFloat("y_angle");
    }
}
