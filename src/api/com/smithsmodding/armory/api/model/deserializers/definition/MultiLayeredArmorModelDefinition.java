package com.smithsmodding.armory.api.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDefinition {

    final Map<String, Pair<IArmorPartRenderer,ResourceLocation>> layerLocations;
    final Map<String, Pair<IArmorPartRenderer,ResourceLocation>> brokenLocations;
    final Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiLayeredArmorModelDefinition( Map<String, Pair<IArmorPartRenderer,ResourceLocation>> layerLocations, Map<String, Pair<IArmorPartRenderer,ResourceLocation>> brokenLocations, Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.layerLocations = layerLocations;
        this.brokenLocations = brokenLocations;
        this.transforms = transforms;

        if (layerLocations.isEmpty())
            throw new IllegalArgumentException("Cannot create a MultiLayeredArmorModel without components!");
    }


    public ImmutableMap<String, Pair<IArmorPartRenderer,ResourceLocation>> getLayerLocations() {
        return ImmutableMap.copyOf(layerLocations);
    }

    public ImmutableMap<String, Pair<IArmorPartRenderer,ResourceLocation>> getBrokenLocations() {
        return ImmutableMap.copyOf(brokenLocations);
    }

    public Map<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
