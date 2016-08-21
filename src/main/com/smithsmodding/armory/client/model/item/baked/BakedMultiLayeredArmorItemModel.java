package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.common.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class BakedMultiLayeredArmorItemModel extends BakedWrappedModel.PerspectiveAware {

    private static final List<List<BakedQuad>> empty_face_quads;
    private static final List<BakedQuad> empty_list;

    static {
        empty_list = Collections.emptyList();
        empty_face_quads = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            empty_face_quads.add(empty_list);
        }
    }

    protected final Overrides overrides;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected HashMap<String, BakedArmorPartModel> parts;
    protected HashMap<String, BakedArmorPartModel> brokenParts;
    protected final IBakedModel parent;
    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry in
     * the array simply is null.
     */
    public BakedMultiLayeredArmorItemModel(IBakedModel parent, HashMap parts, HashMap brokenParts, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(parent,transforms);
        this.parent=parent;
        this.parts = parts;
        this.brokenParts = brokenParts;
        overrides = new Overrides(this);
        this.transforms = transforms;
    }



    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }


    private static final class Overrides extends ItemOverrideList {

        private final BakedMultiLayeredArmorItemModel parent;

        public Overrides(BakedMultiLayeredArmorItemModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            NBTTagCompound baseTag = NBTHelper.getTagCompound(stack);

            if (baseTag.hasNoTags()) {
                return originalModel;
            }

            ArrayList<MLAAddon> installedAddons = new ArrayList<MLAAddon>();
            installedAddons.addAll(ArmorNBTHelper.getAddonMap(stack).keySet());

            //Sort the list based on priority.
            Collections.sort(installedAddons, new Comparator<MLAAddon>() {
                @Override
                public int compare(MLAAddon o1, MLAAddon o2) {
                    return Integer.compare(o1.getLayerPriority(), o2.getLayerPriority());
                }
            });

            // get the texture for each part
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            boolean broken = ArmorNBTHelper.checkIfStackIsBroken(stack);

            for (MLAAddon addon : installedAddons) {
                String addonID = addon.getUniqueID();
                String modelID = addonID;
                if (addon.isMaterialDependent()) {
                    addonID = ((MaterialDependentMLAAddon) addon).getMaterialIndependentID();
                    modelID = ((MaterialDependentMLAAddon) addon).getUniqueMaterialID();
                }

                BakedArmorPartModel partModel;
                if (broken && parent.brokenParts.containsKey(addonID) && parent.brokenParts.get(addonID) != null) {
                    partModel = parent.brokenParts.get(addonID);
                } else if (parent.parts.containsKey(addonID) && parent.parts.get(addonID) != null) {
                    partModel = parent.parts.get(addonID);
                } else {
                    continue;
                }
                if (partModel instanceof BakedMaterialPart)
                    partModel= ((BakedMaterialPart) partModel).getModelByIdentifier(modelID);


                quads.addAll(partModel.getQuads(null, null, 0));
            }

//            IBakedModel model = new ItemLayerModel.BakedItemModel(quads.build(), parent.getParticleTexture(), parent.transforms, parent.getOverrides(), null);
            IBakedModel model=new BipedBakedArmorModel(parent,quads.build(),parent.transforms,stack);
            return model;
        }
    }
}
