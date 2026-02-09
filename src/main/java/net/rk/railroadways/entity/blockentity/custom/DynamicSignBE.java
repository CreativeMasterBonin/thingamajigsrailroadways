package net.rk.railroadways.entity.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamicSignBE extends BlockEntity{
    BlockPos bp;
    public float yAngle = 0.0f;
    public int ticks;
    public final List<String> locs = List.of(
            "thingamajigsrailroadways:textures/entity/signs/sign_aust_alt.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_aust.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_canadian.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_cateye.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_czech.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_finnish.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_german_horz.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_german_vert.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_inverted_cateye.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_japan.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_usa_crossbuck.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_rr_ahead.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_rr_ahead_old.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_swrls.png",
            "thingamajigsrailroadways:textures/entity/signs/sign_inverted_usa_crossbuck.png"
    );
    public int signType = 0;
    public String signOverrideLoc = locs.get(signType);
    public boolean customSign = false;
    public String customTextureLoc = "thingamajigsrailroadways:textures/entity/signs/sign_template.png";

    public DynamicSignBE(BlockPos pos, BlockState blockState){
        super(TRRBlockEntity.DYNAMIC_SIGN_BE.get(), pos, blockState);
        bp = pos;
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

    public ResourceLocation rl(){
        return ResourceLocation.parse(getRLAsFull());
    }

    public String getRLAsFull(){
        if(ResourceLocation.tryParse(signOverrideLoc) != null){
            return signOverrideLoc;
        }
        return "thingamajigsrailroadways:textures/entity/signs/sign_template.png";
    }

    public ResourceLocation getCustomRLAsFull(){
        if(ResourceLocation.tryParse(customTextureLoc) != null){
            return ResourceLocation.parse(customTextureLoc);
        }
        return ResourceLocation.parse("thingamajigsrailroadways:textures/entity/signs/sign_template.png");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider slp) {
        super.saveAdditional(pTag, slp);
        pTag.putFloat("y_angle",yAngle);
        pTag.putString("sign_override_loc",signOverrideLoc);
        pTag.putInt("sign_type",signType);
        pTag.putBoolean("custom_sign",customSign);
        pTag.putString("custom_texture",customTextureLoc);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider lp) {
        yAngle = pTag.getFloat("y_angle");
        signOverrideLoc = pTag.getString("sign_override_loc");
        signType = pTag.getInt("sign_type");
        customSign = pTag.getBoolean("custom_sign"); // override for custom texture location loading
        customTextureLoc = pTag.getString("custom_texture"); // texture that replaces original sign when valid
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, DynamicSignBE rrcbe){
        ++rrcbe.ticks;
        if(rrcbe.customSign == true){

        }
        else {
            if (rrcbe.signType > rrcbe.locs.size() - 1) {
                rrcbe.signType = rrcbe.locs.size() - 1;
            }
            else{
                rrcbe.signOverrideLoc = rrcbe.locs.get(rrcbe.signType);
            }
        }
        // hard reset tick counter
        if(rrcbe.ticks >= 32767){
            rrcbe.ticks = 0;
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, DynamicSignBE rrcbe){
        ++rrcbe.ticks;
        // hard reset tick counter
        if(rrcbe.ticks >= 32767){
            rrcbe.ticks = 0;
        }
    }
}
