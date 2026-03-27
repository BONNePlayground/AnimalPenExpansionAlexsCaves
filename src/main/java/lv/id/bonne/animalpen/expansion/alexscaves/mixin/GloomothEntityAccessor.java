//
// Created by BONNe
// Copyright - 2026
//


package lv.id.bonne.animalpen.expansion.alexscaves.mixin;


import com.github.alexmodguy.alexscaves.server.entity.living.GloomothEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(GloomothEntity.class)
public interface GloomothEntityAccessor
{
    @Accessor
    void setFlyProgress(float flyProgress);


    @Accessor
    void setPrevFlyProgress(float prevFlyProgress);


    @Accessor
    void setFlapAmount(float flapAmount);


    @Accessor
    void setPrevFlapAmount(float prevFlapAmount);
}
