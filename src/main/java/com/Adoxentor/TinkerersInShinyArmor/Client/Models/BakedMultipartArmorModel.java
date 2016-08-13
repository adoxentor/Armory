package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javafx.scene.Parent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.mantle.client.model.BakedSimple;
import slimeknights.tconstruct.library.utils.TagUtil;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;

/**
 * Created by Iddo on 6/29/2016.
 */
public class BakedMultipartArmorModel extends ModelBiped implements IPerspectiveAwareModel {
    public boolean hidden = false;

    ItemStack itemStack = new ItemStack(Items.APPLE);
    ImmutableMap<String, BakedArmorPartModel> parts;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;


    BakedMultipartArmorModel(ImmutableMap<String, BakedArmorPartModel> parts, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(1);
        this.parts = parts;
        this.transforms = transforms;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityIn instanceof EntityLivingBase)
            renderIt((EntityLivingBase) entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,itemStack);
    }

    public void renderIt(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale,ItemStack itemStack) {
        //NBTTagCompound toolTag = TagUtil.getToolTag(itemStack);
        for (Map.Entry entry : parts.entrySet()) {
            if (entry.getValue() != null && entry.getValue() instanceof BakedArmorPartModel){
                ((BakedArmorPartModel) entry.getValue()).render(entitylivingbaseIn,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale,itemStack);
            }

        }
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
        for (Map.Entry entry : parts.entrySet()) {
            for (BakedArmorPartModel model : ((ImmutableList<BakedArmorPartModel>) entry.getValue())) {
                quads.addAll(model.getQuads(state, side, rand));
            }
        }
        return quads.build();
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
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return MultipartArmorItemOverrideList.INSTANCE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return MapWrapper.handlePerspective(this, transforms, cameraTransformType);
    }

    private static class MultipartArmorItemOverrideList extends ItemOverrideList {

        static MultipartArmorItemOverrideList INSTANCE = new MultipartArmorItemOverrideList();

        private MultipartArmorItemOverrideList() {
            super(ImmutableList.<ItemOverride>of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            NBTTagCompound baseTag = TagUtil.getBaseTag(stack);
            if (!baseTag.hasNoTags()) {
                BakedMultipartArmorModel original = (BakedMultipartArmorModel) originalModel;
                ImmutableMap<String, BakedArmorPartModel> originalParts = original.parts;
                ImmutableList.Builder<BakedQuad> builder = new ImmutableList.Builder<BakedQuad>();
                for (Map.Entry entry : originalParts.entrySet()) {
                    builder.addAll(((IBakedModel) entry.getValue()).getOverrides().handleItemState((IBakedModel) entry.getValue(), stack, world, entity).getQuads(null, null, 0));
                }
                return new Simple(builder.build(), original);
            }
            return super.handleItemState(originalModel, stack, world, entity);
        }
    }

    public static class Simple extends BakedSimple.Wrapper {

        BakedMultipartArmorModel base;

        public Simple(ImmutableList<BakedQuad> quads, BakedMultipartArmorModel base) {
            super(quads, base);
            this.base=base;
        }

        public BakedMultipartArmorModel getBase() {
            return base;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            if (cameraTransformType == ItemCameraTransforms.TransformType.HEAD)
                return Pair.of(EmptyBakedModel.INSTANCE, null);
            return super.handlePerspective(cameraTransformType);
        }

    }
}
