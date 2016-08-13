package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import net.minecraft.entity.EntityLivingBase;

/**
 * Created by Iddo on 8/6/2016.
 */
public interface ArmorPartBipedRenderer {
    void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale);

}