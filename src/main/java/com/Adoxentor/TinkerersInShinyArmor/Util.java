package com.Adoxentor.TinkerersInShinyArmor;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.Locale;

/**
 * Created by Iddo on 6/18/2016.
 */
public class Util {
    public static final String RESOURCE =TinkerersInShinyArmor.MODID.toLowerCase();
    public static String prefix(String name) {
        return String.format("%s.%s", RESOURCE, name.toLowerCase(Locale.US));
    }

    public static ResourceLocation getResource(String res) {
        return new ResourceLocation(RESOURCE, res);
    }

    public static ResourceLocation getItemLocation(Item item) {
        // get the registered name for the object
        Object o = GameData.getItemRegistry().getNameForObject(item);

        // are you trying to add an unregistered item...?
        if(o == null) {
            TinkerRegistry.log.error("Item %s is not registered!" + item.getUnlocalizedName());
            // bad boi
            return null;
        }

        return (ResourceLocation) o;
    }

}
