package net.rk.railroadways.entity.blockentity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.registry.MultipurposeSignType;
import net.rk.railroadways.registry.TRRRegistries;
import net.rk.railroadways.util.MultipurposeSignTypeHolderObject;

import java.util.List;

public class MultipurposeSignBE extends BlockEntity{
    public BlockPos blockPos = BlockPos.ZERO;
    public int ticks = 0;
    // the rotation around the y-axis
    public float yAngle = 0;
    // the rotation around the z-axis
    public float zAngle = 0;
    // the multipurpose sign type id selected
    public int indexId = 0;
    // whether to be alternating textures
    public boolean alternatingTextures = false;
    // the ticks to wait till an alternate
    // this is determined by the resource, not any settings nor the blockentity itself, a data-driven value
    public int alternatingInterval = 42;
    // whether on the alternate texture
    public boolean alternateTexture = false;
    public String texture = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign"; // the off texture
    public String textureOn = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign"; // the on texture
    public String textureOnAlt = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign"; // the alternate on texture

    // fallback texture for failed resources
    public final String fallbackSignTexture = "thingamajigsrailroadways/textures/entity/multipurpose_sign_variants/multipurpose_sign";

    // the list of registered multipurpose sign types
    public MultipurposeSignTypeHolderObject holderList;

    // update the textures and interval of the sign (no caching of data)
    public void updateSign(){
        List<MultipurposeSignType> list = this.level.registryAccess().registryOrThrow(TRRRegistries.MULTIPURPOSE_SIGN_TYPE).stream().toList();

        //LogUtils.getLogger().debug("The indexID is now: {}", indexId);

        try{
            holderList = new MultipurposeSignTypeHolderObject.Builder()
                    .add(new MultipurposeSignTypeHolderObject.HolderSignType(Holder.direct(list.get(this.indexId))))
                    .buildSignTypeHolderBuilder();
            //LogUtils.getLogger().debug("new list holder {}", holderList.toString());
        }
        catch (Exception e){
            LogUtils.getLogger().error("Something went wrong when trying to apply a change to MultipurposeSignType in MultipurposeSignBE! ERR: {}",e.getMessage());
            holderList = MultipurposeSignTypeHolderObject.makeDefaultSign(level);
        }

        texture = holderList.typesHolderObjectList().get(0).getSignType().offStateTexture();
        textureOn = holderList.typesHolderObjectList().get(0).getSignType().onStateTexture();
        textureOnAlt = holderList.typesHolderObjectList().get(0).getSignType().alternateOnStateTexture();

        alternatingInterval = holderList.typesHolderObjectList().get(0).getSignType().alternatingInterval();
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null){
            BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(),blockState,blockState,3);
        }
    }

    public MultipurposeSignBE(BlockPos pos, BlockState blockState) {
        super(TRRBlockEntity.MULTIPURPOSE_SIGN_BE.get(), pos, blockState);
    }

    // used for displaying block entities
    public MultipurposeSignBE(){
        super(TRRBlockEntity.MULTIPURPOSE_SIGN_BE.get(), new BlockPos(0,0,0), TRRBlocks.MULTIPURPOSE_SIGN.get().defaultBlockState());
        this.blockPos = new BlockPos(0,0,0);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if(level != null){
            if(holderList == null){
                // it is always null when loaded but this keeps it safe
                holderList = MultipurposeSignTypeHolderObject.makeDefaultSign(level);
            }
        }
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

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider slp) {
        super.saveAdditional(compoundTag, slp);
        compoundTag.putFloat("y_angle",yAngle);
        compoundTag.putFloat("z_angle",zAngle);
        compoundTag.putBoolean("alternating_textures",alternatingTextures);

        compoundTag.putString("off_texture",texture);
        compoundTag.putString("on_texture",textureOn);
        compoundTag.putString("alt_on_texture",textureOnAlt);
        compoundTag.putInt("alt_interval",alternatingInterval);

        /*if (!(this.holderList == null)) {
            compoundTag.put("multipurpose_sign_types", MultipurposeSignTypeHolderObject.CODEC.encodeStart(slp.createSerializationContext(NbtOps.INSTANCE),this.holderList).getOrThrow());
        }
        else{
            holderList = MultipurposeSignTypeHolderObject.makeDefaultSign(this.level);
            compoundTag.put("multipurpose_sign_types", MultipurposeSignTypeHolderObject.CODEC.encodeStart(slp.createSerializationContext(NbtOps.INSTANCE),this.holderList).getOrThrow());
        }*/
    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider lp) {
        if(compoundTag.contains("y_angle")){
            yAngle = compoundTag.getFloat("y_angle");
        }
        if(compoundTag.contains("z_angle")){
            zAngle = compoundTag.getFloat("z_angle");
        }
        if(compoundTag.contains("alternating_textures")){
            alternatingTextures = compoundTag.getBoolean("alternating_textures");
        }
        if(compoundTag.contains("off_texture")){
            texture = compoundTag.getString("off_texture");
        }
        if(compoundTag.contains("on_texture")){
            textureOn = compoundTag.getString("on_texture");
        }
        if(compoundTag.contains("alt_on_texture")){
            textureOnAlt = compoundTag.getString("alt_on_texture");
        }
        if(compoundTag.contains("alt_interval")){
            alternatingInterval = Math.clamp(compoundTag.getInt("alt_interval"),2,1024);
        }

        /*if (compoundTag.contains("multipurpose_sign_types")) {
            MultipurposeSignTypeHolderObject.CODEC
                    .parse(lp.createSerializationContext(NbtOps.INSTANCE), compoundTag.get("multipurpose_sign_types"))
                    .resultOrPartial(str -> LogUtils.getLogger().error("Failed to parse multipurpose sign types: '{}'", str))
                    .ifPresent(str -> this.holderList = str);
        }*/
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, MultipurposeSignBE sign){
        ++sign.ticks;
        if(sign.ticks % sign.alternatingInterval == 0){
            sign.alternateTexture = !sign.alternateTexture;
            sign.updateBlock();
        }
        // hard reset tick counter
        if(sign.ticks >= 32767){
            sign.ticks = 0;
        }
    }

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, MultipurposeSignBE sign){
        ++sign.ticks;
        if(sign.ticks % sign.alternatingInterval == 0){
            sign.alternateTexture = !sign.alternateTexture;
            sign.updateBlock();
        }
        // hard reset tick counter
        if(sign.ticks >= 32767){
            sign.ticks = 0;
        }
    }
}
