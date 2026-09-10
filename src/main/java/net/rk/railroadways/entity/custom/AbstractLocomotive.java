package net.rk.railroadways.entity.custom;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.rk.railroadways.Thingamajigsrailroadways;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public abstract class AbstractLocomotive extends AbstractMinecart implements Leashable {
    public enum LocomotiveType {
        MANUAL,
        STEAM,
        DIESEL,
        ELECTRIC,
        DIESEL_ELECTRIC,
        BIOFUEL,
        CUSTOM
    }
    public LocomotiveType getLocomotiveType(){
        return LocomotiveType.CUSTOM;
    }

    @Nullable
    private Leashable.LeashData leashData;
    @Nullable
    @Override
    public LeashData getLeashData() {
        return null;
    }
    @Override
    public void setLeashData(@Nullable Leashable.LeashData leashData) {
        this.leashData = leashData;
    }

    @Override
    public void dropLeash(boolean broadcastPacket, boolean dropItem) {
        Leashable.super.dropLeash(broadcastPacket, dropItem);
        if(this.getLeashData() == null){

        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        writeLeashData(compound,leashData);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        leashData = readLeashData(compound);
    }

    public static AbstractLocomotive createLocomotive(ServerLevel level, double x, double y, double z,LocomotiveType locomotiveType, ItemStack stack, @Nullable Player player){
        AbstractLocomotive locomotive;
        switch (locomotiveType.ordinal()){
            case 0->{
                locomotive = new DieselLocomotive(level,x,y,z);
                break;
            }
            default -> {
                locomotive = new DieselLocomotive(level,x,y,z);
                break;
            }
        }
        EntityType.createDefaultStackConfig(level, stack, player).accept(locomotive);
        return locomotive;
    }

    public boolean autoMove = false;
    public float currentSpeed = 0.0f;
    public float maxSpeed = 1.25f;
    private Mode mode = Mode.STOPPED;
    public boolean isDerailed = false;
    private static final EntityDataAccessor<Boolean> DATA_DERAILED = SynchedEntityData.defineId(AbstractLocomotive.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_CURRENT_SPEED = SynchedEntityData.defineId(AbstractLocomotive.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_MAX_SPEED = SynchedEntityData.defineId(AbstractLocomotive.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_AUTO_MOVE = SynchedEntityData.defineId(AbstractLocomotive.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Mode> DATA_MODE = SynchedEntityData.defineId(AbstractLocomotive.class, Thingamajigsrailroadways.MODE_ENTITY_DATA_SERIALIZER);

    public static enum Mode implements StringRepresentable {
        STOPPED("stopped",0),
        REVERSE("reverse",1),
        IDLE("idle",2),
        FORWARD("forward",3);
        private final String name;
        private final int id;

        Mode(String name,int id){
            this.name = name;
            this.id = id;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        // for codecs and such
        public int id() {
            return id;
        }

        public static Mode fromName(String name){
            return (Mode)CODEC.byName(name);
        }

        private static final StringRepresentable.EnumCodec<AbstractLocomotive.Mode> CODEC = StringRepresentable.fromEnum(AbstractLocomotive.Mode::values);
        private static final IntFunction<AbstractLocomotive.Mode> BY_ID = ByIdMap.continuous(AbstractLocomotive.Mode::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf,AbstractLocomotive.Mode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, AbstractLocomotive.Mode::id);
    }

    public Mode getMode(){
        return this.mode;
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_AUTO_MOVE,false);
        builder.define(DATA_DERAILED,false);
        builder.define(DATA_CURRENT_SPEED,0.0f);
        builder.define(DATA_MAX_SPEED,maxSpeed);
        builder.define(DATA_MODE,Mode.STOPPED);
    }

    public AbstractLocomotive(EntityType<?> entityType, Level level) {
        super(entityType, level);

    }

    public AbstractLocomotive(EntityType<?> entityType, Level level, double x, double y, double z) {
        super(entityType, level, x, y, z);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult ret = super.interact(player, hand);
        if (ret.consumesAction()) return ret;
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else if (this.isVehicle()) {
            return InteractionResult.PASS;
        } else if (!this.level().isClientSide) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void activateMinecart(int x, int y, int z, boolean powered) {

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected Item getDropItem() {
        return Items.STONE;
    }

    @Override
    public Type getMinecartType() {
        return Type.RIDEABLE;
    }
}
