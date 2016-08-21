package com.smithsmodding.armory.api.Client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 8/6/2016.
 */
public interface IArmorPartRenderer {
    public void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, IBakedModel model, ItemStack itemStack);

}
