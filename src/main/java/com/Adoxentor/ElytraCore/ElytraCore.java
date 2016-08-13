package com.Adoxentor.ElytraCore;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Created by Iddo on 6/17/2016.
 */

@Mod(modid = ElytraCore.MODID, version = ElytraCore.VERSION)
public class ElytraCore{
    public static final String MODID = "ElytraCore";
    public static final String VERSION = "0.0.1";

    public static final Logger logger = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        logger.info("init");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        logger.info("onPlayerTick");
//        EntityLivingBase player = event.player;
//        if(player instanceof EntityPlayerMP &&!player.isElytraFlying()&&ElytraEvents.check(player)&& player.isElytraFlying()){
//                ((EntityPlayerMP) player).setElytraFlying();
//        }
    }




}

