package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

//copyed from tconstract

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorModelLoader2 implements ICustomModelLoader {

  public static String EXTENSION = ".tisa";

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
      Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
      ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);
      ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
      List<MaterialModel> parts = Lists.newArrayList();
      List<MaterialModel> brokenParts = Lists.newArrayList();


      IModel output=null;
      return output;
    } catch(IOException e) {
      TinkerRegistry.log.error("Could not load multimodel {}", modelLocation.toString());
    }
    return ModelLoaderRegistry.getMissingModel();
  }

  @Override
  public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {

  }
}