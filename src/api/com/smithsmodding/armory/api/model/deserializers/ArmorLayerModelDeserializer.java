package com.smithsmodding.armory.api.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.model.deserializers.definition.ArmorLayerModelDefinition;
import com.smithsmodding.armory.api.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.common.model.TRSRTransformation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by Iddo on 8/15/2016.
 */
public class ArmorLayerModelDeserializer implements JsonDeserializer<ArmorLayerModelDefinition> {
    static final Type TRSTType = new TypeToken<TRSRTransformation>() {}.getType();
    static public final ArmorLayerModelDeserializer INSTANCE=new ArmorLayerModelDeserializer();


    private static final Type definitionType = new TypeToken<MultiLayeredArmorModelDefinition>(){}.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(definitionType, INSTANCE).registerTypeAdapter(TRSTType,ForgeBlockStateV1.TRSRDeserializer.INSTANCE).create();


    /**
     * Method deserializes the given ModelLocation  into a ArmorLayerModel.
     * The returned definition will hold all the plates in a List.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    public ArmorLayerModelDefinition deserialize(ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
    }


    @Override
    public ArmorLayerModelDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        JsonArray array=jsonObject.getAsJsonArray("plates");
        ArmorLayerModelDefinition definition=new ArmorLayerModelDefinition();
        for (JsonElement element:array) {
            ResourceLocation texture;
            TRSRTransformation transformation;

            texture=new ResourceLocation(element.getAsJsonObject().get("texture").getAsString());
            transformation=ForgeBlockStateV1.TRSRDeserializer.INSTANCE.deserialize(element.getAsJsonObject().get("transformation"),TRSTType,context);
            definition.AddPlate(texture,transformation);
        }
        return definition;
    }

//    public static class TRSRDeserializerWarper implements JsonDeserializer<TRSRTransformation> {
//
//        public static final TRSRDeserializerWarper INSTANCE = new TRSRDeserializerWarper();
//        @Override
//        public TRSRTransformation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//                throws JsonParseException {
//
//            JsonObject obj = json.getAsJsonObject();
//            JsonElement texElem = obj.get("TRSR");
//
//            if (texElem == null) {
//                return TRSRTransformation.identity();
//            }
//            return ForgeBlockStateV1.TRSRDeserializer.INSTANCE.deserialize(texElem, typeOfT, context);
//        }
//    }

}
