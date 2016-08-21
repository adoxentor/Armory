package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.api.Client.BodyArmorPartRenderer;
import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.api.Client.ModelType;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Iddo on 6/29/2016.
 */
public class BakedArmorPartModel extends BakedWrappedModel {
    public boolean hidden = false;
    ModelBiped modelBiped;
    protected IArmorPartRenderer armorPartRenderer;


    public BakedArmorPartModel(IBakedModel parent) {
        this(parent, new BodyArmorPartRenderer(ModelType.BODY));
    }

    public BakedArmorPartModel(IBakedModel parent, IArmorPartRenderer armorPartRenderer) {
        super(parent);
        modelBiped = new ModelBiped(1);
        this.armorPartRenderer = armorPartRenderer;
    }

    public void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ItemStack itemStack) {
        if (!hidden) {
            armorPartRenderer.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, this.getOverrides().handleItemState(this,itemStack,entitylivingbaseIn.getEntityWorld(),entitylivingbaseIn), itemStack);
        }
    }

    public ItemOverrideList getOverrides() {
        return ArmorPartItemOverrideList.INSTANCE;
    }

    private static class ArmorPartItemOverrideList extends ItemOverrideList {

        static ArmorPartItemOverrideList INSTANCE = new ArmorPartItemOverrideList();

        private ArmorPartItemOverrideList() {
            super(ImmutableList.<ItemOverride>of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            return ((BakedArmorPartModel) originalModel).getParentModel().getOverrides().handleItemState(((BakedArmorPartModel) originalModel),stack,world,entity);
        }
    }
}
