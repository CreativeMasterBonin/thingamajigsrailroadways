package net.rk.railroadways.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PurpleActivatorRailBlock extends BaseRailBlock {
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final MapCodec<PurpleActivatorRailBlock> CODEC = simpleCodec(PurpleActivatorRailBlock::new);

    public PurpleActivatorRailBlock(BlockBehaviour.Properties properties) {
        this(properties.noCollission(), false);
    }

    protected PurpleActivatorRailBlock(Properties builder, boolean isPoweredRail) {
        super(true,builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(POWERED,false).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsrailroadways.purple_activator_rail.desc").withStyle(ChatFormatting.RED));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE, POWERED, WATERLOGGED);
    }


    protected void updateState(BlockState bs, Level lvl, BlockPos bp, Block block1) {
        if(lvl.hasNeighborSignal(bp)) {
            lvl.setBlock(bp, bs.setValue(POWERED, lvl.hasNeighborSignal(bp)),3);
            lvl.updateNeighborsAt(bp.below(), this);
            if (bs.getValue(getShapeProperty()).isAscending()) {
                lvl.updateNeighborsAt(bp.above(), this);
            }
        }
    }

    @Override
    protected MapCodec<? extends BaseRailBlock> codec() {
        return CODEC;
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk1, BlockPos bp2, boolean bool1) {
        if(bs.getValue(POWERED) != lvl.hasNeighborSignal(bp)){
            lvl.setBlock(bp,bs.setValue(POWERED,lvl.hasNeighborSignal(bp)),3);
            lvl.updateNeighborsAt(bp,this);
        }
    }

    @Override
    public void onMinecartPass(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        if(state.getValue(POWERED) && level.getGameTime() % 200 == 0){
            if(!cart.getPassengers().isEmpty()){
                List<Entity> entityList = cart.getPassengers();
                if(entityList.get(0) instanceof Monster){
                    ((Monster) entityList.get(0)).addEffect(new MobEffectInstance(MobEffects.WITHER,1,100));
                    level.playSound(null,pos, SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.BLOCKS,0.5f,2.0f);
                    entityList.get(0).hurt(entityList.get(0).damageSources().magic(),2);
                }
            }
        }
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }
}
