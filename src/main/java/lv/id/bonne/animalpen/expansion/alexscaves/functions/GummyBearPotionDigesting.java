//
// Created by BONNe
// Copyright - 2026
//


package lv.id.bonne.animalpen.expansion.alexscaves.functions;


import com.github.alexmodguy.alexscaves.server.entity.living.GummyBearEntity;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import lv.id.bonne.animalpen.blocks.entities.AbstractAnimalPenBlockEntity;
import lv.id.bonne.animalpen.interaction.value.Value;
import lv.id.bonne.animalpen.processing.function.api.EntityFunction;
import lv.id.bonne.animalpen.util.ItemTransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;


public class GummyBearPotionDigesting implements EntityFunction
{
    @Override
    public boolean interactPlayer(ServerPlayer player,
        InteractionHand interactionHand,
        ItemStack itemConsumed,
        int amount,
        Mob mob,
        CompoundTag mobNBT,
        BlockPos blockPos,
        @Nullable String dataKey,
        @Nullable Value dataValue)
    {
        return this.generatePotion(player.serverLevel(), itemConsumed, mob, blockPos);
    }


    @Override
    public boolean interactDispenser(ServerLevel serverLevel,
        Container dispenserInventory,
        ItemStack itemConsumed,
        int amount,
        Mob mob,
        CompoundTag mobNBT,
        BlockPos blockPos,
        @Nullable String dataKey,
        @Nullable Value dataValue)
    {
        return this.generatePotion(serverLevel, itemConsumed, mob, blockPos);
    }


    private boolean generatePotion(ServerLevel serverLevel,
        ItemStack itemConsumed,
        Mob mob,
        BlockPos blockPos)
    {
        if (!(mob instanceof GummyBearEntity)) return false;

        ItemStack itemStack = ACEffectRegistry.createJellybean(PotionUtils.getPotion(itemConsumed));

        int dropAmount = mob.getRandom().nextInt(2) + 3;
        List<ItemStack> scuteList = new ArrayList<>(dropAmount);

        for (int i = 0; i < dropAmount; i++)
        {
            scuteList.add(itemStack);
        }

        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);

        if (blockEntity instanceof AbstractAnimalPenBlockEntity animalPenBlockEntity)
        {
            scuteList.forEach(stack ->
                ItemTransferUtil.insertBellowOrDrop(serverLevel,
                    stack,
                    animalPenBlockEntity.getBlockPos(),
                    animalPenBlockEntity.dropPosition()));
        }
        else
        {
            // This should never happen
            scuteList.forEach(stack ->
                Block.popResource(mob.level(), blockPos.above(), stack));
        }

        return false;
    }
}
