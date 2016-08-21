package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * Created by Iddo on 7/7/2016.
 */
public class ArmorPlateModel extends ArmorSubComponentModel {
    TRSRTransformation transformation;

    public ArmorPlateModel(ImmutableList<ResourceLocation> textures) {
        super(textures);
    }
    public ArmorPlateModel(ImmutableList<ResourceLocation> textures,TRSRTransformation transformation) {
        super(textures);
        this.transformation =transformation;
    }

    public BakedSubComponentModel generateBakedComponentModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        if(transformation!=null) {
            state = new ModelStateComposition(state, transformation);
        }
        return super.generateBakedComponentModel(state, format, bakedTextureGetter);
    }

}
