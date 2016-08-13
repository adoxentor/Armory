package com.Adoxentor.TinkerersInShinyArmor;

import com.Adoxentor.TinkerersInShinyArmor.Armors.ArmorCore;
import com.google.common.collect.ImmutableSet;
import gnu.trove.set.hash.TLinkedHashSet;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;

import java.util.Set;

import static slimeknights.tconstruct.library.TinkerRegistry.*;

/**
 * Created by Iddo on 6/18/2016.
 */
public class ArmorRegistry {
    public static CreativeTab tabAromr = new CreativeTab("TinkerersInShinyArmor", new ItemStack(Items.GOLDEN_CHESTPLATE));

    public static final Category CATEGORY_ARMOR= new Category("armor");

    //Armors
    private static final Set<ArmorCore> armors = new TLinkedHashSet<ArmorCore>();

    public static void registerArmor(ArmorCore armor) {
        armors.add(armor);

        for(PartMaterialType pmt : armor.getRequiredComponents()) {
            for(IToolPart tp : pmt.getPossibleParts()) {
                registerToolPart(tp);
            }
        }
    }

    public static Set<ArmorCore> getArmors() {
        return ImmutableSet.copyOf(armors);
    }

}
