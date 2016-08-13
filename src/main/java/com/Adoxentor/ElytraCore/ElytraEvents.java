package com.Adoxentor.ElytraCore;

import com.Adoxentor.ElytraCore.API.IElytraItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 6/17/2016.
 */
public class ElytraEvents {
    public static boolean check(EntityLivingBase entity) {
        ItemStack itemstack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
            return true;
        if (itemstack != null && itemstack.getItem() instanceof IElytraItem) {
            if (((IElytraItem) itemstack.getItem()).canFallFly(entity))
                return true;
        }

        return false;
    }

    public static void damageOnFlying(EntityLivingBase entity) {
        ItemStack itemstack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
            itemstack.damageItem(1,entity);
        if (itemstack != null && itemstack.getItem() instanceof IElytraItem) {
            if (((IElytraItem) itemstack.getItem()).canFallFly(entity))
                ((IElytraItem) itemstack.getItem()).damageOnFly(entity);
        }
    }
}