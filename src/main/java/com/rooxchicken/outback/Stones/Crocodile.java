package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.AbandonTask;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SpikyShelterTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;
import com.rooxchicken.outback.Tasks.ViciousTask;

public class Crocodile extends Stone
{
    private Outback plugin;

    public static final String itemName = "§x§8§2§A§4§0§0§lCrocodile";

    public Crocodile(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§8§2§A§4§0§0§lLunge";
        

        cooldownKey = new NamespacedKey(plugin, "crocodile");
        cooldownMax = 45*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        if(getEssence(item) >= 2)
        {
            if(player.isInWater())
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 4, 3));
        }
    }

    @EventHandler
    public void digest(PlayerItemConsumeEvent event)
    {
        ItemStack item = event.getItem();
        if(item.getType().isEdible())
        {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 4, 0));
        }
    }

    @EventHandler
    private void lunge(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 0.8f);
            player.setVelocity(player.getLocation().getDirection());
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0,1,0), 90, 0.5, 0.8, 0.5, new Particle.DustOptions(Color.GREEN, 1.0f));

            Object t = Library.getTarget(player, 6);
            if(t != null && t instanceof Player)
            {
                Player target = (Player)t;
                target.damage(9);
                target.getWorld().spawnParticle(Particle.REDSTONE, target.getLocation().clone().add(0,1,0), 90, 0.5, 0.8, 0.5, new Particle.DustOptions(Color.GREEN, 1.0f));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
            }

            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        //Outback.tasks.add(new AbandonTask(plugin, player));
    }
}
