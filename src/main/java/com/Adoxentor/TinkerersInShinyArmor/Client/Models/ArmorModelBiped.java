package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.Adoxentor.TinkerersInShinyArmor.Armors.ArmorCore;
import com.Adoxentor.TinkerersInShinyArmor.Client.ArmorWearPart;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;

/**
 * Created by Iddo on 6/23/2016.
 */
public class ArmorModelBiped extends ModelBiped{


    EntityEquipmentSlot entityEquipmentSlot;
    ArmorWearPart[] parts;
    ArmorCore armor;
    ImmutableMap<ModelType,BakedArmorModel> BakedParts;


    public ArmorModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn, EntityEquipmentSlot entityEquipmentSlot, ArmorWearPart[] parts) {

        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);

        this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);

        this.entityEquipmentSlot = entityEquipmentSlot;
        this.parts = parts;

    }

    ArmorModelBiped(EntityEquipmentSlot entityEquipmentSlot, ArmorWearPart[] parts) {
        super(0.5F, 0.0F, 64, 32);
        this.entityEquipmentSlot = entityEquipmentSlot;
        this.parts = parts;
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + 0.0F, 0.0F);

    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//        this.isChild = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).isChild();
//        if (true)
//            return;
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        EntityLivingBase entitylivingbaseIn= (EntityLivingBase) entityIn;
        GlStateManager.pushMatrix();
        renderChestBlock(entitylivingbaseIn);

        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
            renderHeadBlock(entitylivingbaseIn);
        }


        GlStateManager.popMatrix();


    }
    private void renderHeadBlock(EntityLivingBase entitylivingbaseIn){
        GlStateManager.pushMatrix();

        float f3 = 0.625F;


        if (this.bipedHead.rotateAngleZ != 0.0F)
        {
            GlStateManager.rotate(this.bipedHead.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (this.bipedHead.rotateAngleY != 0.0F)
        {
            GlStateManager.rotate(this.bipedHead.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (this.bipedHead.rotateAngleX != 0.0F)
        {
            GlStateManager.rotate(this.bipedHead.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.translate(0.0F, -0.25F, 0.0F);

        GlStateManager.scale(f3, -f3, -f3);



        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getItemRenderer().renderItem(entitylivingbaseIn,new ItemStack( Blocks.DIRT), ItemCameraTransforms.TransformType.HEAD);
        GlStateManager.popMatrix();

    }

    private void renderChestBlock(EntityLivingBase entitylivingbaseIn){
        GlStateManager.pushMatrix();

        float f3 = 0.625F;


//        if (this.bipedHead.rotateAngleZ != 0.0F)
//        {
//            GlStateManager.rotate(this.bipedHead.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
//        }
//
//        if (this.bipedHead.rotateAngleY != 0.0F)
//        {
//            GlStateManager.rotate(this.bipedHead.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
//        }
//
//        if (this.bipedHead.rotateAngleX != 0.0F)
//        {
//            GlStateManager.rotate(this.bipedHead.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
//        }
//        GlStateManager.translate(0.0F, -0.25F, 0.0F);
//
//        GlStateManager.scale(f3, -f3, -f3);



        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getItemRenderer().renderItem(entitylivingbaseIn,new ItemStack( Blocks.DIRT), ItemCameraTransforms.TransformType.HEAD);
        GlStateManager.popMatrix();
    }
}


