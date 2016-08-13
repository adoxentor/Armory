package com.Adoxentor.TinkerersInShinyArmor.Armors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

/**
 * Created by Iddo on 6/18/2016.
 */
public class ChestPlate extends ArmorCore {

    public ChestPlate(){
        super(PartMaterialType.handle(TinkerTools.toughToolRod),
                PartMaterialType.head(TinkerTools.largePlate),
                PartMaterialType.head(TinkerTools.largePlate),
                PartMaterialType.head(TinkerTools.largePlate),
                PartMaterialType.head(TinkerTools.largePlate));
        this.equipmentSlot= EntityEquipmentSlot.CHEST;
    }



    @Override
    public NBTTagCompound buildTag(List<Material> materials) {
        return buildDefaultTag(materials).get();
    }


    public ChestPlate(PartMaterialType... requiredComponents) {
        super(requiredComponents);
    }

    @Override
    public float damagePotential() {
        return 0;
    }

    @Override
    public double attackSpeed() {
        return 0;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return super.getArmorDisplay(player, armor, slot);
    }

//    @Nonnull
//    @Override
//    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
//        return new TRSRBakedModel( super.getArmorModel(entityLiving, itemStack, armorSlot, _default),0f,0f,1f);
//    }
}
