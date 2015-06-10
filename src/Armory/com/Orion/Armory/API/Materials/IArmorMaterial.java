package com.Orion.Armory.API.Materials;

import com.Orion.Armory.Util.Client.Color;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 11:12
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IArmorMaterial
{
     String getInternalMaterialName();

     void registerNewActivePart(String pUpgradeInternalName, boolean pPartState);

     void modifyPartState(String pUpgradeInternalName, boolean pPartState);

     boolean getPartState(String pUpgradeInternalName);

     HashMap<String, Boolean> getAllPartStates();

     void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

     Float getBaseDamageAbsorption(String pTargetArmorInternalName);

     HashMap<String, Float> getAllBaseDamageAbsorbtionValues();

     void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

     int getBaseDurability(String pTargetArmorInternalName);

     HashMap<String, Integer> getAllBaseDurabilityValues();

     void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

     int getMaxModifiersOnPart(String pTargetArmorInternalName);

     HashMap<String, Integer> getAllMaxModifiersAmounts();

     String getType();

     void setColor(Color pColor);

     Color getColor();

     String getOreDicName();

     String getVisibleName();

     EnumChatFormatting getVisibleNameColor();

     boolean getIsBaseArmorMaterial();

     float getMeltingPoint();

     float getHeatCoefficient();

     void setIsBaseArmorMaterial(boolean pNewState);

     void setMeltingPoint(float pNewMeltingPoint);

     void setHeatCoefficient(float pNewCoefficient);
}