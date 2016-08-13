package com.Adoxentor.TinkerersInShinyArmor.Armors;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.IModifyable;
import slimeknights.tconstruct.library.tinkering.IRepairable;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.tinkering.TinkersItem;

import java.util.List;

/**
 * Created by Iddo on 7/15/2016.
 */
public class myArmorCore extends TinkersItem implements ISpecialArmor {
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

    }


    @Override
    public NBTTagCompound buildTag(List<Material> materials) {
        return null;
    }

    @Override
    public List<String> getInformation(ItemStack stack) {
        return null;
    }

    @Override
    public void getTooltipDetailed(ItemStack stack, List<String> tooltips) {

    }

    @Override
    public void getTooltipComponents(ItemStack stack, List<String> tooltips) {

    }
}
