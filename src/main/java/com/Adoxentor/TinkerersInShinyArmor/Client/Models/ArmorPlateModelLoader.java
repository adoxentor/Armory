package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.Adoxentor.TinkerersInShinyArmor.TinkerersInShinyArmor;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.model.DummyModel;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.client.model.ModelHelper;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.io.IOException;

/**
 * Created by Iddo on 7/7/2016.
 */
public class ArmorPlateModelLoader implements ICustomModelLoader {
    public static String EXTENSION = ".samt";//shiny armor material

    public static Vector3f startPoint =new Vector3f(0.5f,0.5f,0.5f);

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourcePath()
                .endsWith(EXTENSION); // tinkermaterialmodel extension. Foo.tmat.json
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if(!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }
        try {
//            modelLocation = new ResourceLocation(modelLocation.getResourceDomain(),modelLocation.getResourcePath()+".json");
            String location=modelLocation.getResourcePath();
            String domain= modelLocation.getResourceDomain();
            String[] path=location.split("/");
            String armorName= path[3];
            String armorLayer= path[4];
            String armorSide= path[5].substring(0,path[5].length()-4);
            TRSRTransformation transformation =TRSRTransformation.blockCenterToCorner( MyModelHelper.loadTRSRFromJson(modelLocation).compose(getScaleForLayer(armorLayer)));

//            if(transformation.equals(TRSRTransformation.identity()))
//                transformation=getDefaultTRSR("");

            Vector3f endPoind=new Vector3f();
            endPoind.negate(startPoint);
//            IModel model = new ArmorPlateModel(ModelHelper.loadTextureListFromJson(modelLocation)
//                    ,new TRSRTransformation(startPoint,null,null,null)
//                    .compose(transformation).compose(new TRSRTransformation(endPoind,null,null,null)));
            IModel model=new ArmorPlateModel(ModelHelper.loadTextureListFromJson(modelLocation),transformation);
            // register the base texture for texture generation
            CustomTextureCreator.registerTextures(model.getTextures());

            return model;
        } catch(IOException e) {
            TinkerersInShinyArmor.logger.error("Could not load material model {}", modelLocation.toString());
//            e.printStackTrace();
//            loadModelDefault(modelLocation);

        }
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    public IModel loadModelDefault(ResourceLocation resourceLocation){
        if(!accepts(resourceLocation))
            return  ModelLoaderRegistry.getMissingModel();

        String location=resourceLocation.getResourcePath();
        String domain= resourceLocation.getResourceDomain();
        String[] path=location.split("/");
        String armorName= path[3];
        String armorLayer= path[4];
        String armorSide= path[5].substring(0,path[5].length()-4);



        TRSRTransformation transformation=getDefaultTRSR(path[5].substring(0,path[5].length()-4));

        IModel model=new ArmorPlateModel(ImmutableList.of(new ResourceLocation(domain,location.substring(0,path[5].length()-4))), transformation);

        // register the base texture for texture generation
        CustomTextureCreator.registerTextures(model.getTextures());

        return model;
    }

    // TODO: 8/3/2016  remove
    static float scale=1;
    static boolean changeScale=false;

    public TRSRTransformation getScaleForLayer(String layer) {
        if (changeScale) {
            scale = 1;
            if (layer.equals("out")) {
                scale = 1.005f;
            }
            if (layer.equals("out")) {
                scale = 0.995f;
            }
        }
        if (scale == 1)
            return TRSRTransformation.identity();
        return new TRSRTransformation(null, null, new Vector3f(scale, scale, scale), null);

    }

    public TRSRTransformation getDefaultTRSR(String Side){
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0, 0.5f), null, null,
                TRSRTransformation.quatFromXYZDegrees(new Vector3f(0,0,90))));
    }
}
