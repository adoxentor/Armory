package com.smithsmodding.armory.api.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.Client.BodyArmorPartRenderer;
import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.api.Client.ModelType;
import com.smithsmodding.armory.api.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDeserializer implements JsonDeserializer<MultiLayeredArmorModelDefinition> {
    public static final MultiLayeredArmorModelDeserializer instance = new MultiLayeredArmorModelDeserializer();

    private static final Type definitionType = new TypeToken<MultiLayeredArmorModelDefinition>() {
    }.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(definitionType, instance).create();

    private MultiLayeredArmorModelDeserializer() {
    }

    /**
     * Method deserializes the given ModelLocation  into a MultiComponentModel.
     * The returned definition will hold all the SubModels in a Map.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    public MultiLayeredArmorModelDefinition deserialize(ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
    }

    @Override
    public MultiLayeredArmorModelDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object=json.getAsJsonObject();
        JsonArray array=object.getAsJsonArray("layers");
        Map<String,Pair<IArmorPartRenderer,ResourceLocation>> parts= new HashMap<>();
        Map<String,Pair<IArmorPartRenderer,ResourceLocation>> brokenParts= new HashMap<>();

        for (JsonElement element:array) {
            JsonObject object1=element.getAsJsonObject();
            ModelType type=parseModelType(object1.get("type").getAsString());
            BodyArmorPartRenderer renderer=new BodyArmorPartRenderer(type);
            ResourceLocation resourceLocation=new ResourceLocation(object1.get("model").getAsString());

            JsonElement element1 = object1.get("id");
            String id=resourceLocation.toString();
            if (element1!=null)
                id = element1.getAsString();
            parts.put(id,new Pair<>(renderer,resourceLocation));
        }

        return new MultiLayeredArmorModelDefinition(parts,brokenParts,ModelHelper.TransformDeserializer.INSTANCE.deserialize(json,typeOfT,context));
    }

    public ModelType parseModelType(String s){
        if(s.equals("head")){
            return ModelType.HEAD;
        }
        if(s.equals("body"))
            return ModelType.BODY;


        return ModelType.BODY;
    }
}
