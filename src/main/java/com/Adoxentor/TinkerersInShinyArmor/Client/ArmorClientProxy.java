package com.Adoxentor.TinkerersInShinyArmor.Client;

import com.Adoxentor.TinkerersInShinyArmor.Armors.ArmorCore;
import com.Adoxentor.TinkerersInShinyArmor.Client.Models.*;
import com.Adoxentor.TinkerersInShinyArmor.TinkerersInShinyArmor;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import slimeknights.tconstruct.library.client.model.MaterialModelLoader;
import slimeknights.tconstruct.library.tools.ToolPart;

import javax.annotation.Nonnull;

/**
 * Created by Iddo on 6/19/2016.
 */
public class ArmorClientProxy extends slimeknights.tconstruct.common.ClientProxy {

    @Override
    public void preInit() {
        super.preInit();
    }
    protected static final ArmorModelLoader armorLoader = new ArmorModelLoader();
    protected static final MultipartArmorModelLoader loader = new MultipartArmorModelLoader();

    protected static final ArmorPlateModelLoader armorPlateModelLoader = new ArmorPlateModelLoader();

//    protected static final ModifierModelLoader modifierLoader = new ModifierModelLoader();

    public ArmorClientProxy() {
        ModelLoaderRegistry.registerLoader(loader);
        ModelLoaderRegistry.registerLoader(armorLoader);
        ModelLoaderRegistry.registerLoader(armorPlateModelLoader);
//        ModelLoaderRegistry.registerLoader(modifierLoader);
    }

    @Override
    protected void registerModels() {
        super.registerModels();
        for(ArmorCore armor: TinkerersInShinyArmor.armors)
            registerMultiArmorModel(armor);
        for (ToolPart part:TinkerersInShinyArmor.parts)
            registerPartModel(part);

        loader.registerPart("helmet","rotor",new RotatingArmorPartRenderer(ModelType.HEAD),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/parts/helmet/modifier/rotor"));
        loader.registerPart("helmet","base",new BodyArmorPartRenderer(ModelType.HEAD),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/armor/helmet"+ArmorModelLoader.EXTENSION));


        loader.registerPart("chest","base",new BodyArmorPartRenderer(ModelType.BODY),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/armor/chest"+ArmorModelLoader.EXTENSION));
        loader.registerPart("chest","left",new BodyArmorPartRenderer(ModelType.HAND_LEFT),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/armor/chestLeft"+ArmorModelLoader.EXTENSION));
        loader.registerPart("chest","right",new BodyArmorPartRenderer(ModelType.HAND_RIGHT),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/armor/chestRight"+ArmorModelLoader.EXTENSION));


    }

    public ResourceLocation registerPartModel(Item item) {
        ResourceLocation itemLocation = getItemLocation(item);
        if(itemLocation == null) {
            return null;
        }

        String path = "armors/" + itemLocation.getResourcePath() + MaterialModelLoader.EXTENSION;
        return registerMaterialModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }


    protected ResourceLocation registerMultiArmorModel(Item item) {
        ResourceLocation itemLocation = getItemLocation(item);
        if(itemLocation == null) {
            return null;
        }

//        String path = "armor/" + itemLocation.getResourcePath() + ArmorModelLoader.EXTENSION;
        String path = "armor/" + itemLocation.getResourcePath() + MultipartArmorModelLoader.EXTENSION;

        ResourceLocation location = new ResourceLocation(itemLocation.getResourceDomain(), path);

//        loader.registerPart("helmet","diamond_mod",new BodyArmorPartRenderer(ModelType.HEAD),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/parts/helmet/modifier/diamond_mod"));
//        loader.registerPart("helmet","oak_leaves",new BodyArmorPartRenderer(ModelType.HEAD),new ResourceLocation("minecraft","block/oak_leaves"));
//        loader.registerPart("helmet","rotor",new RotatingArmorPartRenderer(ModelType.HEAD),new ResourceLocation(TinkerersInShinyArmor.MODID.toLowerCase(),"item/parts/helmet/modifier/rotor"));



        return registerMultiArmorModel(item, location);

    }


    protected ResourceLocation registerMultiArmorModel(Item item, final ResourceLocation location) {
        if(!location.getResourcePath().endsWith(ArmorModelLoader.EXTENSION)) {
            TinkerersInShinyArmor.logger.error("The material-model " + location.toString() + " does not end with '"
                    + ArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerIt(item, location);
    }

    private static ResourceLocation registerIt(Item item, final ResourceLocation location) {
        // plop it in.
        // This here is needed for the model to be found ingame when the game looks for a model to render an Itemstack
        // we use an ItemMeshDefinition because it allows us to do it no matter what metadata we use
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to readd the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelLoader.registerItemVariants(item, location);

        return location;
    }
}
