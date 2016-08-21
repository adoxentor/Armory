package com.smithsmodding.armory.api.model.deserializers.definition;



import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iddo on 8/15/2016.
 */
public class ArmorLayerModelDefinition {
    private LinkedList<Pair<ResourceLocation,TRSRTransformation>> plates;

    public ArmorLayerModelDefinition() {
        plates = new LinkedList<>();
    }

    public void AddPlate(ResourceLocation location,TRSRTransformation transformation){
        plates.add(new Pair<>(location,transformation));
    }

    public LinkedList<Pair<ResourceLocation, TRSRTransformation>> getPlates() {
        return plates;
    }
}
