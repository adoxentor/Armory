package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.mantle.client.model.BakedWrapper;

/**
 * Created by Iddo on 7/30/2016.
 */
public class ArmorModelPart extends BakedWrapper.Perspective {

    ModelBiped modelBiped;


    public ArmorModelPart(IBakedModel parent, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(parent, transforms);
    }


}
