package net.rk.railroadways.util;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.rk.railroadways.registry.MultipurposeSignType;
import net.rk.railroadways.registry.TRRRegistries;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

public record MultipurposeSignTypeHolderObject(List<HolderSignType> typesHolderObjectList) {
    private static final Logger MultipurposeSignTypeHolderObjectLogger = LogUtils.getLogger();
    public static final MultipurposeSignTypeHolderObject NOTHING = new MultipurposeSignTypeHolderObject(List.of());

    @Override
    public String toString() {
        return "MultipurposeSignTypeHolderObject has list of: " + this.typesHolderObjectList().stream().toList();
    }

    public static MultipurposeSignTypeHolderObject makeDefaultSign(Level level){
        HolderGetter<MultipurposeSignType> registry = level.registryAccess().lookupOrThrow(TRRRegistries.MULTIPURPOSE_SIGN_TYPE);
        return new MultipurposeSignTypeHolderObject.Builder().addIfRegistered(registry,TRRRegistries.PlACEHOLDER).buildSignTypeHolderBuilder();
    }

    public static final Codec<MultipurposeSignTypeHolderObject> CODEC =
            MultipurposeSignTypeHolderObject.HolderSignType.CODEC.listOf().xmap(MultipurposeSignTypeHolderObject::new, MultipurposeSignTypeHolderObject::typesHolderObjectList);

    public static final StreamCodec<RegistryFriendlyByteBuf, MultipurposeSignTypeHolderObject> STREAM_CODEC =
            MultipurposeSignTypeHolderObject.HolderSignType.STREAM_CODEC.apply(ByteBufCodecs.list()).map(MultipurposeSignTypeHolderObject::new, MultipurposeSignTypeHolderObject::typesHolderObjectList);

    public static record HolderSignType(Holder<MultipurposeSignType> signType){
        public static final Codec<MultipurposeSignTypeHolderObject.HolderSignType> CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(MultipurposeSignType.HOLDER_CODEC.fieldOf("signType").forGetter(MultipurposeSignTypeHolderObject.HolderSignType::signType)
            ).apply(builder, MultipurposeSignTypeHolderObject.HolderSignType::new);
        });

        public static final StreamCodec<RegistryFriendlyByteBuf,HolderSignType> STREAM_CODEC =
                StreamCodec.composite(MultipurposeSignType.STREAM_CODEC,
                        HolderSignType::signType,HolderSignType::new);

        public HolderSignType(Holder<MultipurposeSignType> signType){
            this.signType = signType;
        }

        public MutableComponent getDescription(){
            return Component.literal(this.toString()).append(Component.translatable(this.signType.value().translationKey()));
        }

        public MultipurposeSignType getSignType(){
            return this.signType.value();
        }
    }

    public static class Builder{
        private final ImmutableList.Builder<HolderSignType> holderSignTypesList = ImmutableList.builder();

        public MultipurposeSignTypeHolderObject.Builder add(Holder<MultipurposeSignType> holder) {
            return this.add(new MultipurposeSignTypeHolderObject.HolderSignType(holder));
        }

        public MultipurposeSignTypeHolderObject.Builder add(MultipurposeSignTypeHolderObject.HolderSignType holders) {
            this.holderSignTypesList.add(holders);
            return this;
        }

        public MultipurposeSignTypeHolderObject.Builder addAll(MultipurposeSignTypeHolderObject holders) {
            this.holderSignTypesList.addAll(holders.typesHolderObjectList);
            return this;
        }

        public MultipurposeSignTypeHolderObject.Builder addIfRegistered(HolderGetter<MultipurposeSignType> signTypes, ResourceKey<MultipurposeSignType> signTypeKey) {
            Optional<Holder.Reference<MultipurposeSignType>> optional = signTypes.get(signTypeKey);
            if (optional.isEmpty()) {
                MultipurposeSignTypeHolderObjectLogger.warn("Unable to find MultipurposeSignType with ID: '{}'", signTypeKey.location());
                return this;
            }
            else {
                return this.add(optional.get());
            }
        }

        public MultipurposeSignTypeHolderObject buildSignTypeHolderBuilder() {
            return new MultipurposeSignTypeHolderObject(this.holderSignTypesList.build());
        }
    }
}
