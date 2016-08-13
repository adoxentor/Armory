package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.tconstruct.library.client.model.BakedMaterialModel;
import slimeknights.tconstruct.library.client.model.MaterialModel;

import javax.vecmath.Vector3f;
import java.util.Collection;

/**
 * Created by Iddo on 7/7/2016.
 */
public class ArmorPlateModel extends MaterialModel {
    TRSRTransformation transformation;

    public ArmorPlateModel(ImmutableList<ResourceLocation> textures) {
        super(textures);
    }
    public ArmorPlateModel(ImmutableList<ResourceLocation> textures,TRSRTransformation transformation) {
        super(textures);
        this.transformation =transformation;
    }

    @Override
    public BakedMaterialModel bakeIt(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        if(transformation!=null) {
            state = new ModelStateComposition(state, transformation);
        }
        return super.bakeIt(state, format, bakedTextureGetter);
    }

    @Override
    public int getXOffset() {
        return super.getXOffset();
    }

    @Override
    public int getYOffset() {
        return super.getYOffset();
    }
}
