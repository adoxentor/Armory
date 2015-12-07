package com.SmithsModding.Armory.Client.Logic;
/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Materials.IMaterialRenderInfo;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.ArmoryClientProxy;
import com.SmithsModding.Armory.Common.Logic.ArmoryInitializer;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Util.References;

public class ArmoryClientInitializer extends ArmoryInitializer {
    public static void InitializeClient() {
        MedievalInitialization.Initialize();
        MedievalClientInitialization.registerMaterialResources();
        MedievalClientInitialization.registerUpgradeResources();
        ArmoryInitializer.SystemInit.RegisterItems();
        ArmoryInitializer.SystemInit.RegisterBlocks();
        ArmoryInitializer.SystemInit.RegisterTileEntities();
        SystemInit.registerTESR();
        SystemInit.registerIIR();
        MedievalClientInitialization.registerRingResources();
        MedievalClientInitialization.registerChainResources();
        MedievalClientInitialization.registerNuggetResources();
        MedievalClientInitialization.registerPlateResources();
        MedievalInitialization.prepareGame();
        ArmoryInitializer.SystemInit.initializeOreDic();
    }

    public static class MedievalClientInitialization {
        public static void registerMaterialResources() {
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setRenderInfo(new IMaterialRenderInfo.Metal(0xcacaca, 0f, 0.3f, 0f));
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setRenderInfo(new IMaterialRenderInfo.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
        }

        public static void registerUpgradeResources() {

        }

        public static void registerRingResources() {

        }

        public static void registerChainResources() {

        }

        public static void registerNuggetResources() {

        }

        public static void registerPlateResources() {

        }

    }

    public static class SystemInit {
        public static void registerIIR() {
            ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

            for (MultiLayeredArmor armor : MaterialRegistry.getInstance().getAllRegisteredArmors().values()) {
                proxy.registerArmorItemModel(armor);
            }
        }


        public static void registerTESR() {

        }
    }


}