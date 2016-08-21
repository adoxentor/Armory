package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.client.model.item.unbaked.BakedSimple;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.mantle.client.model.BakedSimple;
import slimeknights.mantle.client.model.BakedWrapper;
import slimeknights.tconstruct.library.client.model.BakedMaterialModel;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;

import javax.annotation.Nonnull;
import java.util.Map;

public class BakedArmorModel extends BakedWrapper.Perspective implements IPerspectiveAwareModel {

  protected BakedMaterialModel[][] parts;
  protected BakedMaterialModel[][] brokenParts;
  protected Map<String, IBakedModel> modifierParts;
  protected final ImmutableMap<TransformType, TRSRTransformation> transforms;


  /**
   * The length of brokenParts has to match the length of armors. If a part does not have a broken texture, the entry in
   * the array simply is null.
   */
  public BakedArmorModel(IBakedModel parent, BakedMaterialModel[][] parts,
                         BakedMaterialModel[][] brokenParts,
                         Map<String, IBakedModel> modifierParts, ImmutableMap<TransformType, TRSRTransformation> transforms) {
    super(parent,transforms);
    if(parts.length != brokenParts.length) {
      throw new RuntimeException("TISA: Length of Parts and BrokenParts Array has to match");
    }
    for(int i = 0; i < parts.length; i++) {
      if(parts[i].length != brokenParts[i].length) {
        throw new RuntimeException("TISA: Length of Parts and BrokenParts Array has to match, on material: "+i);
      }
    }
    this.parts = parts;
    this.brokenParts = brokenParts;
    this.modifierParts = modifierParts;
    this.transforms=transforms;
  }

  @Nonnull
  @Override
  public ItemOverrideList getOverrides() {
    return ArmorItemOverrideList.INSTANCE;
  }

  public IBakedModel handleState(ItemStack stack){
    BakedMaterialModel parts[][] = this.parts;
    BakedMaterialModel brokenParts[][] = this.brokenParts;
    Map<String, IBakedModel> modifierParts = this.modifierParts;

    NBTTagCompound toolTag = TagUtil.getToolTag(stack);
    NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
    NBTTagList modifiers = TagUtil.getBaseModifiersTagList(stack);

    // get the texture for each part
    ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

    boolean broken = toolTag.getBoolean(Tags.BROKEN);

    // the model for the part of the given material. Broken or not-broken
    for(int i = 0; i < parts.length; i++) {
      String id = materials.getStringTagAt(i);
      for(int j=0; j<parts[i].length; j++) {
        IBakedModel partModel=null;
        if (broken && brokenParts[i][j] != null) {
          partModel = brokenParts[i][j].getModelByIdentifier(id);
        } else if(parts[i][j] !=null) {
          partModel = parts[i][j].getModelByIdentifier(id);
        }
        if(partModel!=null)
          quads.addAll(partModel.getQuads(null, null, 0));
      }

    }
    // modifiers
    for(int i = 0; i < modifiers.tagCount(); i++) {
      String modId = modifiers.getStringTagAt(i);
      IBakedModel modModel = modifierParts.get(modId);
      if(modModel != null) {
        quads.addAll(modModel.getQuads(null, null, 0));
      }
    }
    return new BakedSimple(quads.build(), transforms, this);

  }
/*

  @Override
  public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
//    return Pair.of(EmptyBakedModel.INSTANCE,new Matrix4f(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
    return super.handlePerspective(cameraTransformType);
  }
*/

  private static class ArmorItemOverrideList extends ItemOverrideList {

    static ArmorItemOverrideList INSTANCE = new ArmorItemOverrideList();

    private ArmorItemOverrideList() {
      super(ImmutableList.<ItemOverride>of());
    }

    @Nonnull
    @Override
    public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
      NBTTagCompound baseTag = TagUtil.getBaseTag(stack);
      if (!baseTag.hasNoTags()) {
        BakedArmorModel original = (BakedArmorModel) originalModel;
        BakedMaterialModel parts[][] = original.parts;
        BakedMaterialModel brokenParts[][] = original.brokenParts;
        Map<String, IBakedModel> modifierParts = original.modifierParts;

        NBTTagCompound toolTag = TagUtil.getToolTag(stack);
        NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
        NBTTagList modifiers = TagUtil.getBaseModifiersTagList(stack);

        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        boolean broken = toolTag.getBoolean(Tags.BROKEN);

        // the model for the part of the given material. Broken or not-broken
        for (int i = 0; i < parts.length; i++) {
          String id = materials.getStringTagAt(i);
          for (int j = 0; j < parts[i].length; j++) {
            IBakedModel partModel = null;
            if (broken && brokenParts[i][j] != null) {
              partModel = brokenParts[i][j].getModelByIdentifier(id);
            } else if (parts[i][j] != null) {
              partModel = parts[i][j].getModelByIdentifier(id);
            }
            if (partModel != null)
              quads.addAll(partModel.getQuads(null, null, 0));
          }

        }
        // modifiers
        for (int i = 0; i < modifiers.tagCount(); i++) {
          String modId = modifiers.getStringTagAt(i);
          IBakedModel modModel = modifierParts.get(modId);
          if (modModel != null) {
            quads.addAll(modModel.getQuads(null, null, 0));
          }
        }
        ImmutableMap<TransformType, TRSRTransformation> transform = original.transforms;
        return new BakedSimple(quads.build(), transform, original);/*{
//            @Override
//            public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
//                if (cameraTransformType == TransformType.HEAD)
//                    return Pair.of(EmptyBakedModel.INSTANCE, new Matrix4f(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//                return super.handlePerspective(cameraTransformType);
//            }
        };*/
      }
      return originalModel;
    }
  }

  @Override
  public boolean isAmbientOcclusion() {
    return false;
  }
}