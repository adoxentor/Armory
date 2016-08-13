package com.Adoxentor.ElytraCore.API;

import net.minecraft.entity.EntityLivingBase;

/**
 * Created by Iddo on 6/17/2016.
 */
public interface IElytraItem{
    public boolean canFallFly(EntityLivingBase entity);
    public void damageOnFly(EntityLivingBase entity);
}
