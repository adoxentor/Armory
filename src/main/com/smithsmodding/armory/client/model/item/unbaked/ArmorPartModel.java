package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.client.model.item.baked.BakedArmorPartModel;
import com.smithsmodding.armory.client.model.item.baked.BakedMaterialPart;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;

/**
 * Created by Iddo on 8/6/2016.
 */
public class ArmorPartModel implements IModel {
    IArmorPartRenderer armorPartRenderer;
    IModel parent;

    public ArmorPartModel(IArmorPartRenderer armorPartRenderer, IModel parent) {
        this.armorPartRenderer = armorPartRenderer;
        this.parent = parent;

    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return parent.getDependencies();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return parent.getTextures();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBakedComponentModel(state, format, bakedTextureGetter);
    }
    public BakedArmorPartModel generateBakedComponentModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        if(parent instanceof ArmorLayerModel)
            return new BakedMaterialPart(((ArmorLayerModel) parent).generateBakedComponentModel(state,format,bakedTextureGetter),armorPartRenderer);
        return new BakedArmorPartModel(parent.bake(state,format,bakedTextureGetter),armorPartRenderer);
    }

    @Override
    public IModelState getDefaultState() {
        return parent.getDefaultState();
    }


}
