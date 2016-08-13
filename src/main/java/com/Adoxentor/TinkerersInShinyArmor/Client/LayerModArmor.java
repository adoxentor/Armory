package com.Adoxentor.TinkerersInShinyArmor.Client;

import com.Adoxentor.TinkerersInShinyArmor.Armors.ArmorCore;
import com.Adoxentor.TinkerersInShinyArmor.Client.Models.ArmorModelBiped;
import com.Adoxentor.TinkerersInShinyArmor.Client.Models.BakedMultipartArmorModel;
import com.Adoxentor.TinkerersInShinyArmor.Client.Models.MultipartArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 6/23/2016.
 */
public class LayerModArmor implements LayerRenderer<EntityLivingBase>{

    private final RenderPlayer renderer;
    ArmorModelBiped armorModelBiped;
    BakedMultipartArmorModel bakedMultipartArmorModel;
    BakedMultipartArmorModel bakedMultipartArmorModelItem;
    public LayerModArmor(RenderPlayer rendererIn)
    {
        this.renderer = rendererIn;
        armorModelBiped=new ArmorModelBiped(1.0f,0.0f,64,32,EntityEquipmentSlot.HEAD,new ArmorWearPart[0]);
//        bakedMultipartArmorModel= new BakedMultipartArmorModel(ModelType.HEAD);
//        bakedMultipartArmorModelItem=new BakedMultipartArmorModel(ModelType.HEAD);
//        bakedMultipartArmorModelItem.itemStack=new ItemStack(Items.APPLE);
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        for (EntityEquipmentSlot entityEquipmentSlot:EntityEquipmentSlot.values()
             ) {
            if (entityEquipmentSlot.getSlotType()== EntityEquipmentSlot.Type.HAND)
                continue;
            ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(entityEquipmentSlot);
            if (itemstack != null && itemstack.getItem() instanceof ArmorCore) {
                ArmorCore armorCore = (ArmorCore) itemstack.getItem();
                Minecraft minecraft = Minecraft.getMinecraft();
                BakedMultipartArmorModel modelBiped= (BakedMultipartArmorModel) armorCore.getArmorModel(entitylivingbaseIn,itemstack,armorCore.getEquipmentSlot(),null);
                if(modelBiped!=null)
                {
                    modelBiped.renderIt(entitylivingbaseIn,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale,itemstack);
                }

            }

        }
//            armorModelBiped.setModelAttributes(this.renderer.getMainModel());


//            renderer.bindTexture(new ResourceLocation("tinkerersinshinyarmor", "textures/models/armor/iron_layer_1.png"));
//            armorModelBiped.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//            bakedMultipartArmorModel.setModelAttributes(this.renderer.getMainModel());
//            bakedMultipartArmorModel.itemStack=itemstack;
//            bakedMultipartArmorModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//            bakedMultipartArmorModelItem.setModelAttributes(this.renderer.getMainModel());
//            bakedMultipartArmorModelItem.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

//            renderer.bindTexture(new ResourceLocation("tinkerersinshinyarmor", "textures/models/armor/gold_layer_1.png"));
//            armorModelBiped.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
