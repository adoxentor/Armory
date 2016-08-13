package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.Adoxentor.TinkerersInShinyArmor.TinkerersInShinyArmor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.model.DummyModel;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.client.model.ModelHelper;
import slimeknights.tconstruct.library.client.model.ModifierModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Iddo on 8/6/2016.
 */
public class MultipartArmorModelLoader implements ICustomModelLoader {
    protected Map<String,Map<String,Pair<IArmorPartRenderer,ResourceLocation>>> armors = Maps.newHashMap();
    public static String EXTENSION = ".mpa";

    public static String[] sides = {"front","back","top","bottom","left","right"};
    public static String[] partTypes = {"in", "out", "bind"};

    public void registerPart(String armorName,String identifier,IArmorPartRenderer armorPartRenderer, ResourceLocation part ){
        Map<String,Pair<IArmorPartRenderer,ResourceLocation>> parts= armors.get(armorName);
        if(parts==null) {
            parts = Maps.newHashMap();
            armors.put(armorName,parts);
        }
        parts.put(identifier,Pair.of(armorPartRenderer,part));
    }


    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // armormodel extension. Foo.mpam.json
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if(!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }
        String armorName=getArmorName(modelLocation);

        Map<String,ArmorPartModel> armorPartModels= new HashMap<String, ArmorPartModel>();
        Map<String,Pair<IArmorPartRenderer,ResourceLocation>> parts= armors.get(armorName);
        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);

//        IModel base = loadArmorModel(modelLocation, false);
//        if (base == ModelLoaderRegistry.getMissingModel() && parts == null) {
//            TinkerersInShinyArmor.logger.error("no parts registered for {}", modelLocation.toString());
//            return ModelLoaderRegistry.getMissingModel();
//        }
//        armorPartModels.put("base", new ArmorPartModel(new BodyArmorPartRenderer(ModelType.HEAD), base));

        if(parts!=null)
            for (Map.Entry entry : parts.entrySet()) {
                ResourceLocation resourceLocation = ((Pair<IArmorPartRenderer, ResourceLocation>) entry.getValue()).getRight();
                IModel model = ModelLoaderRegistry.getModelOrMissing(resourceLocation);
                if (model == ModelLoaderRegistry.getMissingModel()) {
                    TinkerersInShinyArmor.logger.error("Could not load multimodelpart {}", resourceLocation.toString());
                    continue;
                }
                armorPartModels.put((String) entry.getKey(), new ArmorPartModel(((Pair<IArmorPartRenderer, ResourceLocation>) entry.getValue()).getLeft(), model));
            }
        return new MultipartArmorModel(armorPartModels,transforms);
    }

    public static String getArmorName(ResourceLocation modelLocation) {
        int start = modelLocation.getResourcePath().lastIndexOf('/');
        String armorName = modelLocation.getResourcePath().substring(start < 0 ? 0 : start + 1,
                modelLocation.getResourcePath().length() - EXTENSION
                        .length());
        return armorName.toLowerCase(Locale.US);
    }


    public IModel loadArmorModel(ResourceLocation modelLocation,boolean log) {
        return ArmorModelLoader.INSTANCE.loadModel(modelLocation);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
