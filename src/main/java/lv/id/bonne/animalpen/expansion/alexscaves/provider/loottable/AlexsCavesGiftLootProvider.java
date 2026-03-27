package lv.id.bonne.animalpen.expansion.alexscaves.provider.loottable;


import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import java.util.function.BiConsumer;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;


public class AlexsCavesGiftLootProvider implements LootTableSubProvider
{
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(
            ResourceLocation.tryBuild(AlexsCaves.MODID, "animal_interactions/magma_cube/froglight"),
            LootTable.lootTable().withPool(LootPool.lootPool().
                setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(ACBlockRegistry.CARMINE_FROGLIGHT.get())))
        );
    }
}