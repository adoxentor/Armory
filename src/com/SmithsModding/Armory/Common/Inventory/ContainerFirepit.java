package com.SmithsModding.Armory.Common.Inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */


import com.SmithsModding.Armory.Common.Inventory.Slots.*;
import com.SmithsModding.Armory.Common.TileEntity.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerFirepit extends ContainerSmithsCore {
    private TileEntityFirePit iTEFirePit;

    public ContainerFirepit (InventoryPlayer pPlayerInventory, TileEntityFirePit pTEFirePit) {
        super(References.InternalNames.TileEntities.FirePitContainer, pTEFirePit, pTEFirePit, pPlayerInventory);

        this.iTEFirePit = pTEFirePit;

        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 0, 23, 27));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 1, 51, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 2, 80, 9));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 3, 109, 13));
        this.addSlotToContainer(new SlotHeatable(pTEFirePit, 4, 138, 27));

        for (int tSlotIndex = 0; tSlotIndex < TileEntityFirePit.FUELSTACK_AMOUNT; tSlotIndex++) {
            this.addSlotToContainer(new SlotFuelInput(pTEFirePit, tSlotIndex + 5, 44 + tSlotIndex * 18, 59));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(pPlayerInventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pPlayerInventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 143));
        }
    }

    public TileEntityFirePit GetTileEntity () {
        return iTEFirePit;
    }

    @Override
    public boolean canInteractWith (EntityPlayer playerIn) {
        return true;
    }
}