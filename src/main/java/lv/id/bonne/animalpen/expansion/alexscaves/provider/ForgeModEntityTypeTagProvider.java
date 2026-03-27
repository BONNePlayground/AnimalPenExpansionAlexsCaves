package lv.id.bonne.animalpen.expansion.alexscaves.provider;


import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import lv.id.bonne.animalpen.registries.AnimalPenTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ForgeModEntityTypeTagProvider extends EntityTypeTagsProvider
{
    public ForgeModEntityTypeTagProvider(PackOutput arg,
        CompletableFuture<HolderLookup.Provider> completableFuture,
        String modId,
        @Nullable ExistingFileHelper existingFileHelper)
    {
        super(arg, completableFuture, modId, existingFileHelper);
    }


    @Override
    protected void addTags(@NotNull HolderLookup.Provider arg)
    {
        this.tag(AnimalPenTags.ANIMAL_CAGE_PICKABLE).
            addOptional(ACEntityRegistry.GAMMAROACH.getId()).
            addOptional(ACEntityRegistry.RAYCAT.getId()).
            addOptional(ACEntityRegistry.CANDICORN.getId()).
            addOptional(ACEntityRegistry.GUMMY_BEAR.getId());

        this.tag(AnimalPenTags.BIRD_CATCHER_PICKABLE).
            addOptional(ACEntityRegistry.GLOOMOTH.getId()).
            addOptional(ACEntityRegistry.NOTOR.getId());

        this.tag(AnimalPenTags.WATER_MOB_CONTAINER_PICKABLE).
            addOptional(ACEntityRegistry.TRILOCARIS.getId()).
            addOptional(ACEntityRegistry.RADGILL.getId()).
            addOptional(ACEntityRegistry.LANTERNFISH.getId()).
            addOptional(ACEntityRegistry.SEA_PIG.getId()).
            addOptional(ACEntityRegistry.GOSSAMER_WORM.getId()).
            addOptional(ACEntityRegistry.TRIPODFISH.getId()).
            addOptional(ACEntityRegistry.SWEETISH_FISH.getId());
    }
}