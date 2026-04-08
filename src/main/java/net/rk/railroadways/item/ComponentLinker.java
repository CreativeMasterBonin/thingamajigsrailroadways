package net.rk.railroadways.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ComponentLinker extends Item{
    public ComponentLinker(Properties properties) {
        super(properties.fireResistant().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide){
            if(player.isSecondaryUseActive()){
                if(player.getItemInHand(usedHand).has(DataComponents.BLOCK_ENTITY_DATA)){
                    player.getItemInHand(usedHand).remove(DataComponents.BLOCK_ENTITY_DATA);
                    player.playSound(SoundEvents.BUCKET_EMPTY,0.75f,1.0f);
                }
                if(player.getItemInHand(usedHand).has(DataComponents.LODESTONE_TRACKER)){
                    player.getItemInHand(usedHand).remove(DataComponents.LODESTONE_TRACKER);
                    player.playSound(SoundEvents.LODESTONE_COMPASS_LOCK,0.60f,1.75f);
                }
            }
        }
        else{
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }*/

    /*@Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag beData = new CompoundTag();
        Player player = context.getPlayer();
        Optional<GlobalPos> optionalPos = Optional.empty();

        if(!level.isClientSide){
            if(player.isSecondaryUseActive()){
                if(level.getBlockEntity(blockPos) instanceof SyncableNode){
                    if(level.getBlockEntity(blockPos) instanceof PoleWithCrossingStopLightBE poleStop){

                    }
                }
            }
            else{
                if(level.getBlockEntity(blockPos) instanceof SyncableNode){
                    if(level.getBlockEntity(blockPos) instanceof PoleWithCrossingStopLightBE poleStop) {
                        if (stack.has(DataComponents.BLOCK_ENTITY_DATA)){
                            beData = stack.get(DataComponents.BLOCK_ENTITY_DATA).copyTag();
                        }
                        if (stack.has(DataComponents.LODESTONE_TRACKER)){
                            if(!optionalPos.isPresent()){
                                optionalPos = stack.get(DataComponents.LODESTONE_TRACKER).target();
                            }
                        }

                        if (optionalPos.isPresent()){
                            GlobalPos posOfBE = optionalPos.get();
                            BlockPos blockPosOfBE = posOfBE.pos();

                        }
                    }
                }
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }*/
}
