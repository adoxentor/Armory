package com.Adoxentor.TinkerersInShinyArmor.materials;

import com.google.common.collect.ImmutableList;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public class LinkMaterialStats extends AbstractMaterialStats {

  public final static String TYPE = "link";

  public final static String LOC_Durability = "stat.link.durability.name";
  public final static String LOC_DurabilityDesc = "stat.link.durability.desc";
  public final static String COLOR_Durability = ArmorMaterialStats.COLOR_Durability;

  public final int extraDurability; // usually between 0 and 500

  public LinkMaterialStats(int extraDurability) {
    super(TYPE);
    this.extraDurability = extraDurability;
  }

  @Override
  public List<String> getLocalizedInfo() {
    return ImmutableList.of(formatDurability(extraDurability));
  }

  @Override
  public List<String> getLocalizedDesc() {
    return ImmutableList.of(Util.translate(LOC_DurabilityDesc));
  }

  public static String formatDurability(int durability) {
    return formatNumber(LOC_Durability, COLOR_Durability, durability);
  }

}