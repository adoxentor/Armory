package com.smithsmodding.armory.api.Client;

import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.api.Client.ModelType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 8/6/2016.
 */
public class BodyArmorPartRenderer implements IArmorPartRenderer {
    ModelBiped modelBiped;
    private ModelType type = ModelType.BODY;
    protected ModelTransforms t;

    public BodyArmorPartRenderer(ModelType type){
        this(type,getTransformForPart(type));
    }
    public BodyArmorPartRenderer(ModelType type, ModelTransforms transforms){
        modelBiped=new ModelBiped(1);
        this.type=type;
        t=transforms;
    }


    public void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, IBakedModel model,ItemStack itemStack) {
        modelBiped.setRotationAngles(limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale,entitylivingbaseIn);
        GlStateManager.pushMatrix();
        if (entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
        getBiped(type).postRender(0.0625F);


        //fitting block to the body
        GlStateManager.translate(t.rotationPointX, t.rotationPointY, t.rotationPointZ);
        if (t.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(t.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (t.rotateAngleY != 0.0F) {
            GlStateManager.rotate(t.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (t.rotateAngleX != 0.0F) {
            GlStateManager.rotate(t.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.translate(t.offsetX, t.offsetY, t.offsetZ);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(t.baseScale, -t.baseScale, -t.baseScale);



        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getRenderItem().renderItem(new ItemStack(Items.APPLE), ItemCameraTransforms.TransformType.NONE);
//        minecraft.getRenderItem().renderItem(itemStack,model);
        minecraft.getRenderItem().renderItem(new ItemStack(Items.APPLE),model);


        GlStateManager.popMatrix();

    }



    public static ModelTransforms getTransformForPart(ModelType type){
        ModelTransforms t=new ModelTransforms();
        switch (type){

            case HAND_RIGHT:
                t.offsetX=-0.0625F;
                t.baseScale=0.3751F;
                break;
            case HAND_LEFT:
                t.offsetX=0.0625F;
                t.baseScale=0.3751F;
                break;
            case HEAD:
                t.offsetY=-0.25F;
                t.baseScale = 0.625F;
                break;
            case BODY:
                t.offsetY=-0.25F;
                break;
            case LEG_RIGHT:
                break;
            case LEG_LEFT:
                break;
            case BOOT_RIGHT:
                break;
            case BOOT_LEFT:
                break;
        }
        return t;
    }



    public ModelRenderer getBiped(ModelType type) {
        switch (type) {

            case HAND_RIGHT:
                return modelBiped.bipedRightArm;
            case HAND_LEFT:
                return modelBiped.bipedLeftArm;
            case HEAD:
                return modelBiped.bipedHead;
            case BODY:
                break;
            case LEG_RIGHT:
                break;
            case LEG_LEFT:
                break;
            case BOOT_RIGHT:
                break;
            case BOOT_LEFT:
                break;
            default:
                return modelBiped.bipedHead;
        }
        return modelBiped.bipedBody;
    }

    public static class ModelTransforms {
        public float baseScale=1;
        public float rotateAngleX=0;
        public float rotateAngleY=0;
        public float rotateAngleZ=0;
        public float offsetX=0;
        public float offsetY=0;
        public float offsetZ=0;
        public float rotationPointX=0;
        public float rotationPointY=0;
        public float rotationPointZ=0;
    }

    public static void pre(){
    }

}
