package com.Adoxentor.TinkerersInShinyArmor;

import com.Adoxentor.TinkerersInShinyArmor.Armors.ArmorCore;
import com.Adoxentor.TinkerersInShinyArmor.Armors.ChestPlate;
import com.Adoxentor.TinkerersInShinyArmor.Armors.Helmet;
import com.Adoxentor.TinkerersInShinyArmor.Client.ArmorClientProxy;
import com.Adoxentor.TinkerersInShinyArmor.Client.LayerModArmor;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.TConstruct;

import slimeknights.tconstruct.common.CommonProxy;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static slimeknights.tconstruct.tools.TinkerTools.pattern;

@Mod(modid = TinkerersInShinyArmor.MODID, version = TinkerersInShinyArmor.VERSION,dependencies = "required-after:"+TConstruct.modID)
public class TinkerersInShinyArmor {
    public static final String MODID = "TinkerersInShinyArmor";
    public static final String VERSION = "0.0.1";
    public static Logger logger= LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.Adoxentor.TinkerersInShinyArmor.Client.ArmorClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;

    public static List<ArmorCore> armors = Lists.newLinkedList();      // contains all armors registered
    public static List<ToolPart> parts =Lists.newLinkedList();
    //armors
    ArmorCore helmet;
    ArmorCore chestPlate;

    //armors
    ToolPart rotated;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.info("preInt"+this.getClass().getName());
        registerParts();
        if(event.getSide().isClient()) {
            ArmorClientProxy.initClient();
        }
        registerArmors();
        proxy.preInit();

    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        Map<String, RenderPlayer> refSkinMap=null;
        try {
            Class rm = RenderManager.class;
            Field skinMap = rm.getDeclaredField("skinMap");
            skinMap.setAccessible(true);
            refSkinMap=((Map<String, RenderPlayer>) (skinMap.get(Minecraft.getMinecraft().getRenderManager())));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(refSkinMap!=null) {
            refSkinMap.get("default").addLayer(new LayerModArmor(refSkinMap.get("default")));
            refSkinMap.get("slim").addLayer(new LayerModArmor(refSkinMap.get("slim")));
        }
        else {
            logger.error("failed to add layer");
        }
        TinkerRegistry.registerToolCrafting(helmet);


    }
    private void registerParts(){

//        rotated= registerToolPart(new ToolPart(1),"rotated_part");
    }
    private ToolPart registerToolPart(ToolPart part, String name) {
        ToolPart ret = registerItem(part, name);

        ItemStack stencil = new ItemStack(pattern);
        Pattern.setTagForPart(stencil, part);
        TinkerRegistry.registerStencilTableCrafting(stencil);

        parts.add(ret);

        return ret;
    }
    private void registerArmors() {
        helmet = registerArmor(new Helmet(), "helmet");
        chestPlate = registerArmor(new ChestPlate(),"chest");
    }

    private static <T extends ArmorCore> T registerArmor(T item, String unlocName) {
        armors.add(item);
        return registerItem(item, unlocName);
    }
    protected static <T extends Item> T registerItem(T item, String name) {
        if(!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", name));
        }

        item.setUnlocalizedName(Util.prefix(name));
        item.setRegistryName(Util.getResource(name));
        GameRegistry.register(item);
        return item;
    }



}
