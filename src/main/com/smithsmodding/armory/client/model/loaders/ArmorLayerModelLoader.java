package com.smithsmodding.armory.client.model.loaders;

import com.smithsmodding.armory.client.model.item.unbaked.ArmorLayerModel;
import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.model.deserializers.ArmorLayerModelDeserializer;
import com.smithsmodding.armory.api.model.deserializers.definition.ArmorLayerModelDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.registry.ArmorRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

/**
 * Created by Marc on 06.12.2015.
 */
public class ArmorLayerModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".AL-Armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // AL armory extension. Foo.AL-armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Retrieve the Name of the armor.
            //The file name without the extension has to be equal to the Name used in Armories registry.
            String armorInternalName = FilenameUtils.getBaseName(modelLocation.getResourcePath());
            MultiLayeredArmor armor = ArmorRegistry.getInstance().getArmor(armorInternalName);

            //If none is registered return missing model and print out an error.
            if (armor == null) {
                ModLogger.getInstance().error("The given model: " + modelLocation.toString() + " Is not registered to any armor known to armory.");
                return ModelLoaderRegistry.getMissingModel();
            }


            //Load the default definition of the model as defined by the registrar first.
            ArmorLayerModelDefinition definition = ArmorLayerModelDeserializer.INSTANCE.deserialize(modelLocation);
            ArmorLayerModel model= new ArmorLayerModel(ImmutableList.copyOf(definition.getPlates()));
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
            for (Pair<ResourceLocation,TRSRTransformation> pair:definition.getPlates()) {
                builder.add(pair.getKey());
            }
            MaterializedTextureCreator.registerBaseTexture(builder.build());
            return model;
        } catch (IOException e) {
            ModLogger.getInstance().error(String.format("Could not load multimodel {}", modelLocation.toString()));
        }
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/red"));
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/blue"));
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/yellow"));
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/green"));
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/black"));
        MaterializedTextureCreator.registerBaseTexture(new ResourceLocation("armory","items/white"));

        return DummyModel.INSTANCE;
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }
}
