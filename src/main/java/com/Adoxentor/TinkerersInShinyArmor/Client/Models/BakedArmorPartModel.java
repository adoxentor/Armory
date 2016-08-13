package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.mantle.client.model.BakedWrapper;

/**
 * Created by Iddo on 6/29/2016.
 */
public class BakedArmorPartModel extends BakedWrapper {
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
            return ((BakedArmorPartModel) originalModel).parent.getOverrides().handleItemState(((BakedArmorPartModel) originalModel).parent,stack,world,entity);
        }
    }
}
