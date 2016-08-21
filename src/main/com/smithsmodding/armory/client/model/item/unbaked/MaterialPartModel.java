package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.client.model.item.baked.BakedMaterialPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

/**
 * Created by Iddo on 8/13/2016.
 */
public class MaterialPartModel extends ArmorPartModel {
    ArmorLayerModel model;

    public MaterialPartModel(IArmorPartRenderer armorPartRenderer, ArmorLayerModel model) {
        super(armorPartRenderer, model);
        this.model = model;
    }


    public BakedMaterialPart generateBakedComponentModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedMaterialPart(model.generateBakedComponentModel(state,format,bakedTextureGetter),armorPartRenderer);
    }
}
