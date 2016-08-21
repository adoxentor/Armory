package com.Adoxentor.TinkerersInShinyArmor.Client.Models;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

import static com.smithsmodding.smithscore.util.client.ModelHelper.getReaderForResource;

/**
 * Created by Iddo on 7/7/2016.
 */
public class MyModelHelper extends ModelHelper {
    static final Type TRSTType = new TypeToken<TRSRTransformation>() {}.getType();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(TRSTType, TRSRDeserializerWarper.INSTANCE)
            .create();

    public static TRSRTransformation loadTRSRFromJson(ResourceLocation location) throws IOException {
        Reader reader = getReaderForResource(location);
        try {
            return GSON.fromJson(reader, TRSTType);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }



}
