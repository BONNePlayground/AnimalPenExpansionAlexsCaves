package lv.id.bonne.animalpen.expansion.alexscaves;


import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.living.*;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import lv.id.bonne.animalpen.expansion.alexscaves.functions.GummyBearPotionDigesting;
import lv.id.bonne.animalpen.expansion.alexscaves.mixin.GloomothEntityAccessor;
import lv.id.bonne.animalpen.processing.function.wrapper.EntityFunctionEntry;
import lv.id.bonne.animalpen.registries.AnimalPenFunctionRegistry;
import lv.id.bonne.animalpen.registries.AnimalPenMobAnimationsRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(AnimalPenExpansionAlexsCaves.MOD_ID)
public class AnimalPenExpansionAlexsCaves
{
    public AnimalPenExpansionAlexsCaves()
    {
        IEventBus modBusEvent = FMLJavaModLoadingContext.get().getModEventBus();
        modBusEvent.addListener(this::registerCustomAnimations);

        ENTITY_FUNCTIONS.register(modBusEvent);
    }


    public void registerCustomAnimations(FMLClientSetupEvent event)
    {
        AnimalPenMobAnimationsRegistry.AVIARY_ANIMATIONS.put(ACEntityRegistry.GLOOMOTH.get(),
            mob -> {
                if (mob instanceof GloomothEntity bird)
                {
                    bird.setFlying(true);

                    GloomothEntityAccessor accessor = (GloomothEntityAccessor) bird;
                    accessor.setFlapAmount(1f);
                    accessor.setPrevFlapAmount(1f);
                    accessor.setFlyProgress(5f);
                    accessor.setPrevFlyProgress(5f);
                }
            });

        AnimalPenMobAnimationsRegistry.AVIARY_ANIMATIONS.put(ACEntityRegistry.NOTOR.get(),
            Mob::tick);

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.TRILOCARIS.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.RADGILL.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.LANTERNFISH.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.SEA_PIG.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.GOSSAMER_WORM.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.TRIPODFISH.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

        AnimalPenMobAnimationsRegistry.AQUARIUM_ANIMATIONS.put(ACEntityRegistry.SWEETISH_FISH.get(),
            mob -> {
                mob.walkAnimation.update(0.6F, 0.4F);
            });

    }


    public static final DeferredRegister<EntityFunctionEntry> ENTITY_FUNCTIONS =
        DeferredRegister.create(AnimalPenFunctionRegistry.ENTITY_FUNCTIONS_REGISTRY_KEY, AnimalPenExpansionAlexsCaves.MOD_ID);


    public static final RegistryObject<EntityFunctionEntry> GUMMY_BEAR_POTIONS =
        ENTITY_FUNCTIONS.register("gummy_bear_potion", () ->
            new EntityFunctionEntry(new GummyBearPotionDigesting())
        );


    public static final String MOD_ID = "animal_pen_expansion_alexs_caves";


    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
}
