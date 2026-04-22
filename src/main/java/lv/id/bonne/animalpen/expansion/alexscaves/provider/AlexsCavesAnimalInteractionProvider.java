//
// Created by BONNe
// Copyright - 2026
//


package lv.id.bonne.animalpen.expansion.alexscaves.provider;


import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.github.alexmodguy.alexscaves.server.entity.util.GummyColors;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import lv.id.bonne.animalpen.AnimalPen;
import lv.id.bonne.animalpen.data.listener.AnimalInteractionEntry;
import lv.id.bonne.animalpen.data.provider.AnimalInteractionProvider;
import lv.id.bonne.animalpen.expansion.alexscaves.AnimalPenExpansionAlexsCaves;
import lv.id.bonne.animalpen.interaction.condition.ConditionEntry;
import lv.id.bonne.animalpen.interaction.condition.Operator;
import lv.id.bonne.animalpen.interaction.cooldown.CooldownEntry;
import lv.id.bonne.animalpen.interaction.function.FunctionKey;
import lv.id.bonne.animalpen.interaction.ingredient.ConsumerEntry;
import lv.id.bonne.animalpen.interaction.ingredient.CustomIngredient;
import lv.id.bonne.animalpen.interaction.loot.LootEntry;
import lv.id.bonne.animalpen.interaction.model.AnimalInteraction;
import lv.id.bonne.animalpen.interaction.model.AnimalInteractionBuilder;
import lv.id.bonne.animalpen.interaction.textentry.TextEntry;
import lv.id.bonne.animalpen.interaction.textentry.TextEntryVisibility;
import lv.id.bonne.animalpen.interaction.value.IntValue;
import lv.id.bonne.animalpen.interaction.value.StringValue;
import lv.id.bonne.animalpen.registries.AnimalPenFunctionRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;


public class AlexsCavesAnimalInteractionProvider extends AnimalInteractionProvider
{
    public AlexsCavesAnimalInteractionProvider(PackOutput generator,
        CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(generator, lookupProvider);
    }


    @Override
    @NotNull
    public CompletableFuture<?> generateData(CachedOutput cache)
    {
        return this.registries.thenCompose(provider ->
        {
            List<CompletableFuture<?>> futureList = new ArrayList<>();

            // flying
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.GLOOMOTH.get(),
                List.of(this.generateAmbientSound(ACSoundRegistry.GLOOMOTH_FLAP.get())),
                AlexsCaves.MODID));
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.NOTOR.get(),
                List.of(this.generateAmbientSound(ACSoundRegistry.NOTOR_FLYING.get())),
                AlexsCaves.MODID));

            // land animal
            futureList.add(this.generateWithModLocation(cache,
                EntityType.FROG,
                List.of(this.addPrimordialFrogLight()),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.GAMMAROACH.get(),
                List.of(this.generateFood(CustomIngredient.of(ACItemRegistry.SPELUNKIE.get())),
                    this.generateAmbientSound(ACSoundRegistry.GAMMAROACH_IDLE.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.RAYCAT.get(),
                List.of(this.generateFood(CustomIngredient.of(ACItemRegistry.RADGILL.get())),
                    this.generateAmbientSound(ACSoundRegistry.RAYCAT_IDLE.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.CANDICORN.get(),
                List.of(this.generateFood(CustomIngredient.of(ACBlockRegistry.CANDY_CANE.get())),
                    this.generateAmbientSound(ACSoundRegistry.CANDICORN_IDLE.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.GUMMY_BEAR.get(),
                List.of(this.generateGummyBearNoFood(),
                    this.generateGummyBearFood(ACItemRegistry.SWEETISH_FISH_RED.get(), GummyColors.RED),
                    this.generateGummyBearFood(ACItemRegistry.SWEETISH_FISH_GREEN.get(), GummyColors.GREEN),
                    this.generateGummyBearFood(ACItemRegistry.SWEETISH_FISH_YELLOW.get(), GummyColors.YELLOW),
                    this.generateGummyBearFood(ACItemRegistry.SWEETISH_FISH_BLUE.get(), GummyColors.BLUE),
                    this.generateGummyBearFood(ACItemRegistry.SWEETISH_FISH_PINK.get(), GummyColors.PINK),
                    this.generateAmbientSound(ACSoundRegistry.GUMMY_BEAR_IDLE.get()),
                    this.generatePotionEffects()),
                AlexsCaves.MODID));

            // Water bucketable
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.TRILOCARIS.get(),
                List.of(this.generateBucketable(Items.WATER_BUCKET, ACItemRegistry.TRILOCARIS_BUCKET.get(), 1),
                    this.generateAmbientSound(ACSoundRegistry.TRILOCARIS_STEP.get())),
                AlexsCaves.MODID));
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.RADGILL.get(),
                List.of(this.generateBucketable(ACItemRegistry.ACID_BUCKET.get(), ACItemRegistry.RADGILL_BUCKET.get(), 1),
                    this.generateAmbientSound(ACSoundRegistry.RADGILL_FLOP.get())),
                AlexsCaves.MODID));
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.LANTERNFISH.get(),
                List.of(this.generateBucketable(Items.WATER_BUCKET, ACItemRegistry.LANTERNFISH_BUCKET.get(), 1),
                    this.generateAmbientSound(ACSoundRegistry.LANTERNFISH_FLOP.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.SEA_PIG.get(),
                List.of(this.generateBucketable(Items.WATER_BUCKET, ACItemRegistry.SEA_PIG_BUCKET.get(), 1),
                    this.generateSeaPigInteraction(),
                    this.generateAmbientSound(ACSoundRegistry.SEA_PIG_IDLE.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.GOSSAMER_WORM.get(),
                List.of(this.generateBucketable(Items.WATER_BUCKET, ACItemRegistry.GOSSAMER_WORM_BUCKET.get(), 1),
                    this.generateAmbientSound(ACSoundRegistry.GOSSAMER_WORM_IDLE.get())),
                AlexsCaves.MODID));
            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.TRIPODFISH.get(),
                List.of(this.generateBucketable(Items.WATER_BUCKET, ACItemRegistry.TRIPODFISH_BUCKET.get(), 1),
                    this.generateAmbientSound(ACSoundRegistry.TRIPODFISH_FLOP.get())),
                AlexsCaves.MODID));

            futureList.add(this.generateWithInteractions(cache,
                ACEntityRegistry.SWEETISH_FISH.get(),
                List.of(this.generateSweetFishBucketable(ACItemRegistry.PURPLE_SODA_BUCKET.get(),
                        ACItemRegistry.SWEETISH_FISH_RED_BUCKET.get(),
                        GummyColors.RED),
                    this.generateSweetFishBucketable(ACItemRegistry.PURPLE_SODA_BUCKET.get(),
                        ACItemRegistry.SWEETISH_FISH_GREEN_BUCKET.get(),
                        GummyColors.GREEN),
                    this.generateSweetFishBucketable(ACItemRegistry.PURPLE_SODA_BUCKET.get(),
                        ACItemRegistry.SWEETISH_FISH_YELLOW_BUCKET.get(),
                        GummyColors.YELLOW),
                    this.generateSweetFishBucketable(ACItemRegistry.PURPLE_SODA_BUCKET.get(),
                        ACItemRegistry.SWEETISH_FISH_PINK_BUCKET.get(),
                        GummyColors.PINK),
                    this.generateSweetFishBucketable(ACItemRegistry.PURPLE_SODA_BUCKET.get(),
                        ACItemRegistry.SWEETISH_FISH_BLUE_BUCKET.get(),
                        GummyColors.BLUE),
                    this.generateAmbientSound(ACSoundRegistry.SWEETISH_FISH_FLOP.get())),
                AlexsCaves.MODID));


            return CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new));
        });
    }


    private AnimalInteraction generateSeaPigInteraction()
    {
        CustomIngredient lootItems = CustomIngredient.of(ACBlockRegistry.MUCK.get(),
            ACItemRegistry.PEARL.get(),
            ACItemRegistry.MARINE_SNOW.get());

        return AnimalInteractionBuilder.create("digest").
            ingredient(CustomIngredient.of(ACTagRegistry.SEA_PIG_DIGESTS)).
            lootEntry(LootEntry.of(SeaPigEntity.DIGESTION_LOOT_TABLE)).
                consume(new ConsumerEntry.Consume(true)).
            cooldown(new CooldownEntry.Static(20)).
            sound(ACSoundRegistry.SEA_PIG_EAT.getId()).
            redstoneBit(2).
            textLines(TextEntry.ready("display.animal_pen.full_ready", lootItems)).
            textLines(TextEntry.cooldown("display.animal_pen.cooldown", lootItems)).
            build();
    }


    private AnimalInteraction addPrimordialFrogLight()
    {
        return AnimalInteractionBuilder.create("froglight").
            ingredient(CustomIngredient.of(Items.MAGMA_BLOCK)).
            lootEntry(LootEntry.of(AnimalPen.resourceOf("alexscaves/magma_cube/froglight"))). // carmine_froglight need to add
                consume(new ConsumerEntry.Consume(true)).
            conditions(new ConditionEntry.MobCondition("variant", Operator.MATCH, new StringValue("alexscaves:primordial"))).
            cooldown(new CooldownEntry.Linear(6000, -20, 200)).
            sound(SoundEvents.FROG_EAT.getLocation()).
            redstoneBit(2).
            textLines(TextEntry.ready("display.animal_pen.full_ready", CustomIngredient.of(ACBlockRegistry.CARMINE_FROGLIGHT.get()))).
            textLines(TextEntry.cooldown("display.animal_pen.frog_light_cooldown", CustomIngredient.of(ACBlockRegistry.CARMINE_FROGLIGHT.get()))).
            build();
    }


    private AnimalInteraction generateGummyBearFood(Item foodItem, GummyColors color)
    {
        CustomIngredient food = CustomIngredient.of(foodItem);

        return AnimalInteractionBuilder.create("feeding").
            ingredient(food).
            even(true).
            consume(new ConsumerEntry.Consume(true)).
            conditions(new ConditionEntry.AmountCondition(Operator.GTE, 2)).
            conditions(new ConditionEntry.MobCondition("GummyColor", Operator.EQ, new IntValue(color.ordinal()))).
            runFunctions(FunctionKey.of(AnimalPenFunctionRegistry.FEEDING.get())).
            cooldown(new CooldownEntry.Linear(1160, 20, 6000)).
            textLines(TextEntry.ready("display.animal_pen.food_ready", food)).
            textLines(TextEntry.cooldown("display.animal_pen.food_cooldown", food)).
            redstoneBit(1).
            build();
    }


    private AnimalInteraction generateGummyBearNoFood()
    {
        CustomIngredient food = CustomIngredient.of(ACItemRegistry.SWEETISH_FISH_GREEN.get(),
            ACItemRegistry.SWEETISH_FISH_RED.get(),
            ACItemRegistry.SWEETISH_FISH_BLUE.get(),
            ACItemRegistry.SWEETISH_FISH_YELLOW.get(),
            ACItemRegistry.SWEETISH_FISH_PINK.get());

        return AnimalInteractionBuilder.create("no_feeding").
            ingredient(food).
            even(true).
            conditions(new ConditionEntry.AmountCondition(Operator.LT, 2)).
            textLines(new TextEntry("",
                "display.animal_pen.requires_food", CustomIngredient.EMPTY, food, TextEntryVisibility.ON_MATCH, "2")).
            build();
    }


    private AnimalInteraction generateSweetFishBucketable(Item bucketItem, Item resultItem, GummyColors color)
    {
        return AnimalInteractionBuilder.create("bucketable_pickup").
            ingredient(CustomIngredient.of(bucketItem)).
            conditions(new ConditionEntry.MobCondition("GummyColor", Operator.EQ, new IntValue(color.ordinal()))).
            runFunctions(FunctionKey.of(AnimalPenFunctionRegistry.BUCKETABLE_PICKUP.get())).
            redstoneBit(1).
            textLines(TextEntry.ready("display.animal_pen.full_ready",
                CustomIngredient.of(resultItem))).build();
    }


    private AnimalInteraction generatePotionEffects()
    {
        ItemStack[] potionList = ForgeRegistries.POTIONS.getValues().stream().
            filter(potion -> !potion.hasInstantEffects() && !potion.getEffects().isEmpty()).
            map(potion -> PotionUtils.setPotion(Items.POTION.getDefaultInstance(), potion)).
            toArray(ItemStack[]::new);

        return AnimalInteractionBuilder.create("digesting").
            ingredient(CustomIngredient.of(potionList)).
            consume(new ConsumerEntry.Consume(true)).
            runFunctions(FunctionKey.of(AnimalPenExpansionAlexsCaves.GUMMY_BEAR_POTIONS.get())).
            cooldown(new CooldownEntry.Linear(48000, -20, 2400)).
            redstoneBit(2).
            textLines(TextEntry.ready("display.animal_pen.full_ready",
                CustomIngredient.of(ACItemRegistry.JELLY_BEAN.get()))).
            textLines(TextEntry.cooldown("display.animal_pen.cooldown",
                CustomIngredient.of(potionList))).build();
    }



    public CompletableFuture<?> generateWithModLocation(CachedOutput cache, EntityType<?> entityType, List<AnimalInteraction> interactions, String... mods) {
        JsonElement json = AnimalInteractionEntry.CODEC.encodeStart(JsonOps.INSTANCE,
            new AnimalInteractionEntry(Optional.of(entityType.builtInRegistryHolder().key()),
                Arrays.stream(mods).toList(), interactions)).
            getOrThrow(false, IllegalStateException::new);

        Path file = this.pathProvider.json(ResourceLocation.fromNamespaceAndPath(AlexsCaves.MODID,
            entityType.builtInRegistryHolder().key().location().getPath()));

        return DataProvider.saveStable(cache, json, file);
    }
}
