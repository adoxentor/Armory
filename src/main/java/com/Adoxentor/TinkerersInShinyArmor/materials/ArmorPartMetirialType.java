package com.Adoxentor.TinkerersInShinyArmor.materials;

import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;

/**
 * Created by Iddo on 6/18/2016.
 */
public class ArmorPartMetirialType extends PartMaterialType {
    public ArmorPartMetirialType(IToolPart part, String... statIDs) {
        super(part, statIDs);
    }
    public static PartMaterialType link(IToolPart part) {
        return new PartMaterialType(part, LinkMaterialStats.TYPE);
    }
    public static PartMaterialType armor(IToolPart part) {
        return new PartMaterialType(part, ArmorMaterialStats.TYPE);
    }
    public static PartMaterialType Padding(IToolPart part) {
        return new PartMaterialType(part, PaddingMaterialStats.TYPE);
    }



}
