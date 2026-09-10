package net.rk.railroadways.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.rk.railroadways.entity.custom.AbstractLocomotive;

public class LocomotivePlacer extends Item {
    public final AbstractLocomotive.LocomotiveType typeToSpawn;

    public LocomotivePlacer(AbstractLocomotive.LocomotiveType locomotiveType,Properties properties) {
        super(properties.stacksTo(1).setNoRepair().fireResistant());
        this.typeToSpawn = locomotiveType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        InteractionHand hand = context.getHand();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(blockPos);

        if(typeToSpawn == null){
            return InteractionResult.PASS;
        }

        if(level.isClientSide()){

        }
        else{
            if(!state.is(BlockTags.RAILS)) {
                return InteractionResult.FAIL;
            }
            else {
                ItemStack itemstack = context.getItemInHand();
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)level;
                    RailShape railshape = state.getBlock() instanceof BaseRailBlock ? ((BaseRailBlock)state.getBlock()).getRailDirection(state, level, blockPos, null) : RailShape.NORTH_SOUTH;
                    double d0 = 0.0;
                    if (railshape.isAscending()) {
                        d0 = 0.5;
                    }
                    AbstractLocomotive abstractLocomotive = AbstractLocomotive.createLocomotive(serverlevel, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.0625 + d0, (double)blockPos.getZ() + 0.5, typeToSpawn, itemstack, context.getPlayer());
                    serverlevel.addFreshEntity(abstractLocomotive);
                    serverlevel.gameEvent(GameEvent.ENTITY_PLACE, blockPos, GameEvent.Context.of(context.getPlayer(), serverlevel.getBlockState(blockPos.below())));
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }
}
