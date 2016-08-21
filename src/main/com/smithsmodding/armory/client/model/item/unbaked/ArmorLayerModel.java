package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.client.model.item.baked.BakedSimple;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.*;

public class ArmorLayerModel implements IModel {



  private final ImmutableList<Pair<ResourceLocation,TRSRTransformation>> plates;

    public ArmorLayerModel(ImmutableList<Pair<ResourceLocation, TRSRTransformation>> plates) {
        this.plates = plates;
    }



  @Override
  public Collection<ResourceLocation> getDependencies() {
	return ImmutableList.of();
  }

  @Override
  public Collection<ResourceLocation> getTextures() {
	ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
      for (Pair<ResourceLocation,TRSRTransformation> plate:plates) {
          builder.add(plate.getKey());
      }
      builder.add(new ResourceLocation("armory","items/red"));
      builder.add(new ResourceLocation("armory","items/blue"));
      builder.add(new ResourceLocation("armory","items/yellow"));
      builder.add(new ResourceLocation("armory","items/green"));
      builder.add(new ResourceLocation("armory","items/black"));
      builder.add(new ResourceLocation("armory","items/white"));

      return builder.build();
  }

  @Override
  public IBakedModel bake(IModelState iModelState, VertexFormat vertexFormat, Function<ResourceLocation, TextureAtlasSprite> function) {
    return generateBakedComponentModel(iModelState,vertexFormat,function);
  }

  public BakedSubComponentModel generateBakedComponentModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
	List<BakedSubComponentModel> bakedSubComponentModels = new LinkedList<>();
	for (Pair<ResourceLocation,TRSRTransformation> plate:plates)
	  bakedSubComponentModels.add(new ArmorSubComponentModel(ImmutableList.of(plate.getKey())).generateBakedComponentModel(TRSRTransformation.blockCenterToCorner(plate.getValue()), format, bakedTextureGetter));


	ImmutableList.Builder<BakedQuad> builder = new ImmutableList.Builder<>();
	for (BakedSubComponentModel model : bakedSubComponentModels) {
	  builder.addAll(model.getParentModel().getQuads(null, null, 1));
	}
	IBakedModel base = new BakedSimple(builder.build(), null, bakedSubComponentModels.get(0).getParticleTexture(), false, true, bakedSubComponentModels.get(0).getOverrides());


	HashMap<String, IArmorMaterial> armorMaterials = MaterialRegistry.getInstance().getArmorMaterials();
	BakedSubComponentModel bakedSubComponentModel = new BakedSubComponentModel(base);
	for (Map.Entry<String, IArmorMaterial> entry : armorMaterials.entrySet()) {
	  builder = new ImmutableList.Builder<>();
	  for (BakedSubComponentModel model : bakedSubComponentModels) {
		builder.addAll(model.getModelByIdentifier(entry.getValue().getUniqueID()).getQuads(null, null, 1));
	  }
	  IBakedModel model = new BakedSimple(builder.build(), null, bakedSubComponentModels.get(0).getParticleTexture(), false, true, bakedSubComponentModels.get(0).getOverrides());

	  bakedSubComponentModel.addMaterialModel(entry.getValue(), model);
	}

    return bakedSubComponentModel;
  }

  @Override
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }




}