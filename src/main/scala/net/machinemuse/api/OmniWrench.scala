package net.machinemuse.api

import buildcraft.api.tools.IToolWrench
import cofh.api.item.IToolHammer
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Optional
import mods.railcraft.api.core.items.IToolCrowbar
import net.machinemuse.powersuits.powermodule.tool.{MFFSFieldTeleporterModule, OmniWrenchModule}
import net.machinemuse.utils.ElectricItemUtils
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityMinecart
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import powercrystals.minefactoryreloaded.api.IMFRHammer

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 5:06 PM, 29/04/13
 */

trait OmniWrench
  extends ModularWrench
  with ModularCrowbar
  with ModularHammer
  with ModularCrescentHammer
  /*with ForceFieldManipulator*/ {

}

// Railcraft Crowbar
@Optional.Interface(iface = "mods.railcraft.api.core.items.IToolCrowbar", modid = "Railcraft")
trait ModularCrowbar extends IToolCrowbar {
  def canWhack(player: EntityPlayer, crowbar: ItemStack, x: Int, y: Int, z: Int): Boolean = {
    if (crowbar != null && crowbar.getItem.isInstanceOf[IModularItem]) {
      return ModuleManager.itemHasActiveModule(crowbar, OmniWrenchModule.MODULE_OMNI_WRENCH)
    } else {
      return false
    }
  }

  def onWhack(player: EntityPlayer, crowbar: ItemStack, x: Int, y: Int, z: Int) {
    player.swingItem
  }

  def canLink(player: EntityPlayer, crowbar: ItemStack, cart: EntityMinecart): Boolean = {
    if (crowbar != null && crowbar.getItem.isInstanceOf[IModularItem]) {
      return ModuleManager.itemHasActiveModule(crowbar, OmniWrenchModule.MODULE_OMNI_WRENCH)
    } else {
      return false
    }
  }

  def onLink(player: EntityPlayer, crowbar: ItemStack, cart: EntityMinecart) {
    player.swingItem
  }

  def canBoost(player: EntityPlayer, crowbar: ItemStack, cart: EntityMinecart): Boolean = {
    if (crowbar != null && crowbar.getItem.isInstanceOf[IModularItem]) {
      return ModuleManager.itemHasActiveModule(crowbar, OmniWrenchModule.MODULE_OMNI_WRENCH)
    } else {
      return false
    }
  }

  def onBoost(player: EntityPlayer, crowbar: ItemStack, cart: EntityMinecart) {
    player.swingItem
  }
}

// CoFH Hammer
@Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "CoFHCore")
trait ModularCrescentHammer
  extends IToolHammer {
    def isUsable(item: ItemStack, user: EntityLivingBase, x: Int, y: Int, z: Int): Boolean = {
      return ModuleManager.itemHasActiveModule(item, OmniWrenchModule.MODULE_OMNI_WRENCH)
    }

    def toolUsed(item: ItemStack, user: EntityLivingBase, x: Int, y: Int, z: Int) = {
    }
}

// Buildcraft Wrench
@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraft|Core")
trait ModularWrench
  extends IToolWrench {

  def canWrench(player: EntityPlayer, x: Int, y: Int, z: Int): Boolean = {
    if (player.getCurrentEquippedItem != null && player.getCurrentEquippedItem.getItem.isInstanceOf[IModularItem]) {
      return ModuleManager.itemHasActiveModule(player.getCurrentEquippedItem, OmniWrenchModule.MODULE_OMNI_WRENCH)
    }
    return false
  }

  def wrenchUsed(player: EntityPlayer, x: Int, y: Int, z: Int) {
    player.swingItem
  }
}

// MFR Hammer
@Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.IMFRHammer", modid = "MineFactoryReloaded")
trait ModularHammer extends IMFRHammer {
}

// Considering implementing EnderIO ITool here, but the NBT method is thoroughly tested and works properly right now. - 2014-12-01 Korynkai

// MFFS ForceField Manipulator -- Requires calclavia reimplementation. - 2014-12-01 Korynkai
/*trait ForceFieldManipulator extends IFieldTeleporter {
  def canFieldTeleport(player: EntityPlayer, stack: ItemStack, teleportCost: Int): Boolean = {
    if (ModuleManager.itemHasModule(stack, MFFSFieldTeleporterModule.MODULE_FIELD_TELEPORTER)) {
      if (ElectricItemUtils.getPlayerEnergy(player) > ModuleManager.computeModularProperty(stack, MFFSFieldTeleporterModule.FIELD_TELEPORTER_ENERGY_CONSUMPTION)) {
        return true
      }
      else if (FMLCommonHandler.instance.getEffectiveSide.isServer) {
        player.sendChatToPlayer(ChatMessageComponent.createFromText("[Field Security] Could not teleport through forcefield. 20,000J is required to teleport."))
      }
    }
    return false
  }*/

  /*def onFieldTeleportSuccess(player: EntityPlayer, stack: ItemStack, teleportCost: Int) {
    ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(stack, MFFSFieldTeleporterModule.FIELD_TELEPORTER_ENERGY_CONSUMPTION))
  }

  def onFieldTeleportFailed(player: EntityPlayer, stack: ItemStack, teleportCost: Int) {
  }
}*/
