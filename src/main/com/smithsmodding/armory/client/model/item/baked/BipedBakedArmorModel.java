package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import com.smithsmodding.smithscore.util.common.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.*;

/**
 * Created by Iddo on 8/16/2016.
 */
public class BipedBakedArmorModel extends ModelBiped implements IPerspectiveAwareModel {

    private final BakedMultiLayeredArmorItemModel model;
    private final ImmutableList<BakedQuad> quads;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    private ItemStack stack;

    public BipedBakedArmorModel(BakedMultiLayeredArmorItemModel model, ImmutableList<BakedQuad> quads, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,ItemStack stack) {
        super(0);
        this.model = model;
        this.quads = quads;
        this.transforms = transforms;
        this.stack =stack;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityIn instanceof EntityLivingBase)
            renderIt((EntityLivingBase) entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, stack);
    }

    public void renderIt(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale,ItemStack itemStack) {
        //NBTTagCompound toolTag = TagUtil.getToolTag(stack);
        NBTTagCompound baseTag = NBTHelper.getTagCompound(stack);

        if (baseTag.hasNoTags()) {
            return;
        }

        ArrayList<MLAAddon> installedAddons = new ArrayList<MLAAddon>();
        installedAddons.addAll(ArmorNBTHelper.getAddonMap(stack).keySet());

        //Sort the list based on priority.
        Collections.sort(installedAddons, new Comparator<MLAAddon>() {
            @Override
            public int compare(MLAAddon o1, MLAAddon o2) {
                return Integer.compare(o1.getLayerPriority(), o2.getLayerPriority());
            }
        });

        // render each part

        boolean broken = ArmorNBTHelper.checkIfStackIsBroken(stack);

        for (MLAAddon addon : installedAddons) {
            String addonID = addon.getUniqueID();
            String modelID = addonID;
            if (addon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) addon).getMaterialIndependentID();
                modelID = ((MaterialDependentMLAAddon) addon).getUniqueMaterialID();
            }

            BakedArmorPartModel partModel;
            if (broken && model.brokenParts.containsKey(addonID) && model.brokenParts.get(addonID) != null) {
                partModel = model.brokenParts.get(addonID);
            } else if (model.parts.containsKey(addonID) && model.parts.get(addonID) != null) {
                partModel = model.parts.get(addonID);
            } else {
                continue;
            }
            if (partModel instanceof BakedMaterialPart)
                partModel= ((BakedMaterialPart) partModel).getModelByIdentifier(modelID);

            partModel.render(entitylivingbaseIn,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale,itemStack);
        }

    }




    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState iBlockState, @Nullable EnumFacing enumFacing, long l) {
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return model.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return model.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }
}
