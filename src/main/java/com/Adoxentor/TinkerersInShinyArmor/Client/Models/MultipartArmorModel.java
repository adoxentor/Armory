package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.client.model.item.unbaked.ArmorPartModel;
import com.smithsmodding.armory.client.model.item.unbaked.BakedArmorPartModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.tconstruct.library.client.model.ModelHelper;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.Collection;
import java.util.Map;


/**
 * Created by Iddo on 8/6/2016.
 */
public class MultipartArmorModel implements IModel {
    protected Map<String,ArmorPartModel> parts;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultipartArmorModel(Map<String, ArmorPartModel> parts,ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.parts = parts;
        this.transforms=transforms;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

        for (Map.Entry<String,ArmorPartModel> entry:parts.entrySet()) {
            builder.addAll(((ArmorPartModel)entry.getValue()).getTextures());
        }
        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<String,BakedArmorPartModel> builder = new ImmutableMap.Builder<String, BakedArmorPartModel>();
        for (Map.Entry<String,ArmorPartModel> entry:parts.entrySet()) {
            builder.put(entry.getKey(),entry.getValue().generateBakedComponentModel(state, format, bakedTextureGetter));
        }
        return new BakedMultipartArmorModel(builder.build(),transforms);
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
