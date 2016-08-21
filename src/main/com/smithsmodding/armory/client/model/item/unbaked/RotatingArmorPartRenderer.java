package com.smithsmodding.armory.client.model.item.unbaked;

import com.smithsmodding.armory.api.Client.BodyArmorPartRenderer;
import com.smithsmodding.armory.api.Client.ModelType;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 8/9/2016.
 */
public class RotatingArmorPartRenderer extends BodyArmorPartRenderer {
    ModelTransforms original;
    public RotatingArmorPartRenderer(ModelType type) {
        super(type);
        original=t;
    }

    public RotatingArmorPartRenderer(ModelType type, ModelTransforms transforms) {
        super(type, transforms);
        original=t;
    }

    @Override
    public void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, IBakedModel model, ItemStack itemStack) {
        this.t.rotateAngleY=original.rotateAngleY+(ageInTicks%360)/100f;
        super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, model, itemStack);
    }
}
