package com.Adoxentor.TinkerersInShinyArmor.Armors;

import com.Adoxentor.TinkerersInShinyArmor.ArmorRegistry;
import com.Adoxentor.TinkerersInShinyArmor.Client.Models.BakedMultipartArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.model.BakedSimple;
import slimeknights.mantle.client.model.BakedWrapper;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.ToolCore;

import javax.annotation.Nonnull;

/**
 * Created by Iddo on 6/18/2016.
 */
public abstract class ArmorCore extends ToolCore implements ISpecialArmor {

    public int ArmorPoints;
    protected EntityEquipmentSlot equipmentSlot;


    public ArmorCore(PartMaterialType... requiredComponents) {
        super(requiredComponents);

        this.setNoRepair();

        ArmorRegistry.registerArmor(this);
        this.setCreativeTab(ArmorRegistry.tabAromr);
        this.setNoRepair(); // >_>

        addCategory(ArmorRegistry.CATEGORY_ARMOR);

    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {

        EntityEquipmentSlot equipmentSlot = ((ArmorCore) stack.getItem()).getEquipmentSlot();
        return equipmentSlot!=null&&equipmentSlot.getSlotIndex()==armorType.getSlotIndex();
    }

    @Nonnull
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        // TODO: 6/23/2016 return invisible texture
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Nonnull
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        Minecraft minecraft = Minecraft.getMinecraft();
        IBakedModel bakedModel= minecraft.getRenderItem().getItemModelWithOverrides(itemStack, minecraft.theWorld,entityLiving);
        if(bakedModel instanceof BakedMultipartArmorModel.Simple)
            return ((BakedMultipartArmorModel.Simple) bakedModel).getBase();
        return _default;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return new ArmorProperties(0,0,0);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

    }

    @SideOnly(Side.CLIENT)
    public EntityEquipmentSlot getEquipmentSlot() {
        return this.equipmentSlot;
    }



}
