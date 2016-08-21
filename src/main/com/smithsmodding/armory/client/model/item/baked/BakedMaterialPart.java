package com.smithsmodding.armory.client.model.item.baked;

import com.smithsmodding.armory.api.Client.IArmorPartRenderer;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;

/**
 * Created by Iddo on 8/13/2016.
 */
public class BakedMaterialPart extends BakedArmorPartModel {
    BakedSubComponentModel model;

    public BakedMaterialPart(BakedSubComponentModel model) {
        super(model);
        this.model = model;
    }

    public BakedMaterialPart( BakedSubComponentModel model,IArmorPartRenderer armorPartRenderer) {
        super(model, armorPartRenderer);
        this.model = model;
    }

    public BakedArmorPartModel getModelByIdentifier(String identifier){
        return new BakedArmorPartModel(model.getModelByIdentifier(identifier),this.armorPartRenderer);
    }

}
