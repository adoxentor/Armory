package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class HeatedItemTab extends CreativeTabs {

    public HeatedItemTab() {
        super(I18n.format(TranslationKeys.CreativeTabs.HeatedItems));
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getIconItemStack() {
        ItemStack cooledStack = HeatableItemRegistry.getInstance().getBaseStack(MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN), References.InternalNames.HeatedItemTypes.INGOT);
        ItemStack heatedStack = HeatedItemFactory.getInstance().convertToHeatedIngot(cooledStack);

        return heatedStack;
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.heatedItem;
    }
}
