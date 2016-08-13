package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.tconstruct.library.client.model.BakedMaterialModel;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.client.model.ModelHelper;
import slimeknights.tconstruct.library.client.model.ModifierModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ArmorModel2 implements IModel {

  List<IModel> subModels;
  private final ImmutableList<ResourceLocation> textures;

//  public ArmorModel(ImmutableList<ResourceLocation> defaultTextures,
//                   List<MaterialModel> armors,
//                   List<MaterialModel> brokenPartBlocks,
//                   Float[] layerRotations,

  public ArmorModel2(List<IModel> subModels, ImmutableList<ResourceLocation> textures) {
    this.subModels = subModels;
    this.textures = textures;
  }
//                   ModifierModel modifiers,
//                   ImmutableMap<TransformType, TRSRTransformation> transforms,
//                   ImmutableMap<TransformType, TRSRTransformation> transformsBlocking) {
//
//    this.partBlocks = armors;
//    this.brokenPartBlocks = brokenPartBlocks;
//    this.layerRotations = layerRotations;
//    this.modifiers = modifiers;
//    this.transforms = transforms;
//    this.transformsBlocking = transformsBlocking;
//    this.textures = defaultTextures;
//  }

  public ArmorModel2(ImmutableList<ResourceLocation> defaultTextures,
                     List<List<MaterialModel>> parts,
                     List<List<MaterialModel>> brokenPartBlocks,
                     ModifierModel modifiers, ImmutableMap<TransformType, TRSRTransformation> transforms) {
    this.partBlocks = parts;
    this.brokenPartBlocks = brokenPartBlocks;
    this.modifiers = modifiers;
    this.textures = defaultTextures;
    this.transforms=transforms;
  }

  @Override
  public Collection<ResourceLocation> getDependencies() {
    return ImmutableList.of();
  }

  @Override
  public Collection<ResourceLocation> getTextures() {
    ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

    builder.addAll(textures);

    // modifier textures
    if(modifiers != null) {
      builder.addAll(modifiers.getTextures());
    }

    return builder.build();
  }

  @Override
  public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    ImmutableList.Builder<BakedQuad> builder=ImmutableList.Builder<BakedQuad>();
    for (IModel model:subModels) {
      model.bake(state,format,bakedTextureGetter).getQuads(null,null,0);


    }
//    IBakedModel base = new ItemLayerModel(textures).bake(state, format, bakedTextureGetter);
////    state= new ModelStateComposition(state, new TRSRTransformation(new Vector3f(1,0,0),null,null,null));
//    BakedMaterialModel[][] partModels = new BakedMaterialModel[partBlocks.size()][];
//    BakedMaterialModel[][] brokenPartModels = new BakedMaterialModel[partBlocks.size()][]; // has to be same size
//
//    // we build simple models for the armors, so we can extract the UV information AND have depth
//    for(int i = 0; i < partBlocks.size(); i++) {
//      List<MaterialModel> partBlock = partBlocks.get(i);
//      if (partBlock.size() > 0) {
//        partModels[i] = new BakedMaterialModel[partBlock.size()];
//        for (int j = 0; j < partBlock.size(); j++) {
//          MaterialModel m = partBlock.get(j);
//          if (m != null)
//            partModels[i][j] = m.bakeIt(getStateForPart(i, state), format, bakedTextureGetter);
//        }
//      } else
//        brokenPartModels[i] = null;
//    }
//    //again for broken armors
//    for(int i = 0; i < brokenPartBlocks.size(); i++) {
//      List<MaterialModel> brokenPartBlock = brokenPartBlocks.get(i);
//      if (brokenPartBlock.size() > 0) {
//        brokenPartModels[i] = new BakedMaterialModel[brokenPartBlock.size()];
//        for (int j = 0; j < brokenPartBlock.size(); j++) {
//          MaterialModel m = brokenPartBlock.get(j);
//          if (m != null)
//            brokenPartModels[i][j] = m.bakeIt(getStateForPart(i, state), format, bakedTextureGetter);
//        }
//      }
//      else
//        brokenPartModels[i]=null;
//    }
//
//    Map<String, IBakedModel> modifierModels;
//    if(modifiers != null) {
//      modifierModels = modifiers.bakeModels(state, format, bakedTextureGetter);
//    }
//    else{
//      modifierModels = new THashMap<String, IBakedModel>();
//    }
//    return new BakedArmorModel(base, partModels, brokenPartModels, modifierModels,transforms);
//  }
//
  private IModelState getStateForPart(int i, IModelState originalState) {
    switch (i){
      case 0:
        return new ModelStateComposition(originalState, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0.5f,0.5f,0.5f), TRSRTransformation.quatFromXYZ(0, 0, (float) (i * Math.PI / 2)), null, null)));

    }

    return originalState;
  }

  @Override
  public IModelState getDefaultState() {
    return ModelHelper.DEFAULT_TOOL_STATE;
  }
}