package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SkyEmperorTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;

import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces.p;

public class Dragonfly extends Stone
{
    private Outback plugin;
    private PotionEffect lastEffect;

    public static String itemName = "§x§D§4§A§5§0§B§lAustralian Emperor Dragonfly";

    public Dragonfly(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§D§4§A§5§0§B§lSky Emperor";

        cooldownKey = new NamespacedKey(plugin, "dragonfly");
        cooldownMax = 30*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        /* if(getEssence(item) >= 10) */ //ESSENCECHECK
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(5.0);
    }

    @EventHandler
    public void hardass(EntityDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player)event.getEntity();
        for(ItemStack item : DisplayInformation.playerStonesMap.get(player))
            if(checkItem(item, itemName) && player.isSneaking()/* && getEssence(item) >= 2*/) //ESSENCECHECK
            {
                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0, 1, 0), 40, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 1);
                event.setDamage(event.getDamage() * 0.8);
            }
    }

    @EventHandler
    private void swipe(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && checkCooldown(player, cooldownKey, cooldownMax)/* && getEssence(item) >= 5 */) //ESSENCECHECK
        {
            Outback.tasks.add(new SkyEmperorTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void replacePoison(EntityPotionEffectEvent event)
    {
        if(event.getNewEffect() == null || !(event.getEntity() instanceof Player))
            return;

        Player player = (Player)event.getEntity();
        for(ItemStack item : DisplayInformation.playerStonesMap.get(player))
        if(checkItem(item, itemName)/* && getEssence(item) >= 10*/) //ESSENCECHECK
        {
            PotionEffect potion = event.getNewEffect();
            if(lastEffect == null)
                lastEffect = potion;
            
            if(comparePotionEffects(lastEffect, potion))
            {
                lastEffect = potion;
                return;
            }

            lastEffect = potion;

            player.addPotionEffect(new PotionEffect(potion.getType(), potion.getDuration() * 2, (potion.getAmplifier()+1)*2 - 1));
            event.setCancelled(true);
        }
    }

    private boolean comparePotionEffects(PotionEffect potion1, PotionEffect potion2)
    {
        PotionEffectType type1 = potion1.getType();
        PotionEffectType type2 = potion2.getType();

        int amplifier1 = potion1.getAmplifier();
        int amplifier2 = potion2.getAmplifier();

        int duration1 = potion1.getDuration();
        int duration2 = potion2.getDuration();

        return (type1.equals(type2) && (amplifier1+1)*2 - 1 == amplifier2 && duration1 * 2 == duration2);
    }
}
