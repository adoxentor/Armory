package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

//copyed from tconstract

import com.Adoxentor.TinkerersInShinyArmor.TinkerersInShinyArmor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.io.FilenameUtils;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.model.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ArmorModelLoader implements ICustomModelLoader {

  public static String EXTENSION = ".tisa";
  public static String[] sides = {"front","back","top","bottom","left","right"};
  public static String[] partTypes = {"in", "out", "bind"};

  public static final ArmorModelLoader INSTANCE=new ArmorModelLoader();
  @Override
  public boolean accepts(ResourceLocation modelLocation) {
    return modelLocation.getResourcePath().endsWith(EXTENSION); // armormodel extension. Foo.tisa.json
  }

  @Override
  public IModel loadModel(ResourceLocation modelLocation) {
    if(!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
      return DummyModel.INSTANCE;
    }

    try {
      // Modelblock is used since our format is compatible to the vanilla format
      // and we don't have to write our own json deserializer
      // it also provides us with the textures

      int start = modelLocation.getResourcePath().lastIndexOf('/');
      String armorName = modelLocation.getResourcePath().substring(start < 0 ? 0 : start + 1,
              modelLocation.getResourcePath().length() - EXTENSION
                      .length());
      armorName = armorName.toLowerCase(Locale.US);



      Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
      ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);

      ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
//      List<MaterialModel> armors = Lists.newArrayList();
//      List<MaterialModel> brokenParts = Lists.newArrayList();

      List<List<MaterialModel>> parts=Lists.newArrayList();
      List<List<MaterialModel>> brokenParts=Lists.newArrayList();
      for (int i=0 ;i<partTypes.length;i++) {
        List<MaterialModel> partsForType=Lists.newArrayList();
        parts.add(partsForType);
        for(String side:sides){
          ResourceLocation location=new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/parts/"+armorName+"/"+partTypes[i]+"/"+side+ArmorPlateModelLoader.EXTENSION);
          IModel partModel = ModelLoaderRegistry.getModelOrMissing(location);
          if(partModel==ModelLoaderRegistry.getMissingModel())
            partModel=null;
//          else
//            builder.add(location);
          partsForType.add((MaterialModel) partModel);
        }
        List<MaterialModel> brokenPartsForType=Lists.newArrayList();
        brokenParts.add(brokenPartsForType);
        for(String side:sides){
          ResourceLocation location=new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/parts/"+armorName+"/"+partTypes[i]+"/"+side+"_broken"+ArmorPlateModelLoader.EXTENSION);
          IModel partModel = ModelLoaderRegistry.getModelOrMissing(location);
          if(partModel==ModelLoaderRegistry.getMissingModel())
            partModel=null;
//          else
//            builder.add(location);
          brokenPartsForType.add((MaterialModel) partModel);
        }
      }
//        String name = entry.getKey();
//        try {
//          int i;
//          List<MaterialModel> listToAdd;
//
//          if(name.startsWith("layer")) {
//            i = Integer.valueOf(name.substring(5));
//            listToAdd = armors;
//          }
//          else if(name.startsWith("broken")) {
//            i = Integer.valueOf(name.substring(6));
//            listToAdd = brokenParts;
//          }
//          // invalid entry, ignore
//          else {
//            TinkerRegistry.log.warn("Toolmodel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
//            continue;
//          }
//
//          ResourceLocation location = new ResourceLocation(entry.getValue());
//          MaterialModel partModel = (MaterialModel) ModelLoaderRegistry.getModel(location);
//          while(listToAdd.size() <= i) {
//            listToAdd.add(null);//      for(Map.Entry<String, String> entry : textures.entrySet()) {

//          }
//          listToAdd.set(i, partModel);
//          builder.add(location);
//        } catch(NumberFormatException e) {
//          TinkerRegistry.log.error("Armormodel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      }

//      String armorPartName = FilenameUtils.getBaseName(modelLocation.getResourcePath());
//      IModel mods;
//      try {
//        mods = ModelLoaderRegistry.getModel(ModifierModelLoader.getLocationForToolModifiers(armorPartName));
//      } catch(Exception e) {
//        TinkerRegistry.log.error(e);
//        mods = null;
//      }
      ModifierModel modifiers = null;

//      if(mods == null || !(mods instanceof ModifierModel)) {
//        TinkerRegistry.log.trace(
//            "Toolmodel {} does not have any modifiers associated with it. Be sure that the Tools internal name, the Toolmodels filename and the name used inside the Modifier Model Definition match!",
//            modelLocation);
//      }
//      else {
//        modifiers = (ModifierModel) mods;
//      }

      IModel output = new ArmorModel(builder.build(), parts, brokenParts, modifiers, transforms);

      // inform the texture manager about the textures it has to process
      CustomTextureCreator.registerTextures(builder.build());

      return output;
    } catch(IOException e) {
      TinkerersInShinyArmor.logger.error("Could not load multimodel {}", modelLocation.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ModelLoaderRegistry.getMissingModel();
  }



  @Override
  public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {

  }
}