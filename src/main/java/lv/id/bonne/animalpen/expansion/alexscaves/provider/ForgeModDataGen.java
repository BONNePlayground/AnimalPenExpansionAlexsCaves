package lv.id.bonne.animalpen.expansion.alexscaves.provider;


import java.util.concurrent.CompletableFuture;

import lv.id.bonne.animalpen.expansion.alexscaves.AnimalPenExpansionAlexsCaves;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeModDataGen
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),
            new AlexsCavesAnimalInteractionProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(),
            new ForgeModEntityTypeTagProvider(packOutput,
                lookupProvider,
                AnimalPenExpansionAlexsCaves.MOD_ID,
                event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(),
            new ForgeModLootTableProvider(packOutput));
    }
}