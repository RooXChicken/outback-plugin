package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
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
import com.rooxchicken.outback.Tasks.Platypus_ToxicSpur;
import com.rooxchicken.outback.Tasks.Platypus_WebbedFeet;
import com.rooxchicken.outback.Tasks.SpikyShelterTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;
import com.rooxchicken.outback.Tasks.ViciousTask;

public class Platypus extends Stone
{
    private Outback plugin;
    private int t = 0;

    public static final String itemName = "§x§8§4§4§F§D§9§lPlatypus";

    public Platypus(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§8§4§4§F§D§9§lWater Burrow";
        
        cooldownKey = new NamespacedKey(plugin, "platypus");
        cooldownMax = 60*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        t++;
        if(getEssence(item) >= 2)
        {
            if(t % 200 == 0)
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 0));
                //Outback.tasks.add(new Platypus_WebbedFeet(plugin, player));
            }
        }
        if(getEssence(item) >= 10)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 21, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 21, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 21, 0));
        }
    }

    @EventHandler
    private void waterBurrow(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 1);
            for(int x = 0; x < 3; x++)
            {
                for(int z = 0; z < 3; z++)
                {
                    for(int y = 0; y < 10; y++)
                    {
                        Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX() + (1-x), player.getLocation().getBlockY() - y, player.getLocation().getBlockZ() + (1-z));
                        if(block != null && !(block.getState() instanceof Container) && !block.getType().equals(Material.BEDROCK))
                            block.setType(Material.WATER);

                        block.getWorld().spawnParticle(Particle.REDSTONE, block.getLocation(), 4, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.BLUE, 1.0f));
                    }
                }
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void efficient(PlayerItemConsumeEvent event)
    {
        ItemStack item = event.getItem();
        if(item.getType().isEdible())
        {
            event.getPlayer().setExhaustion(0);
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        Outback.tasks.add(new Platypus_ToxicSpur(plugin, player));
    }
}
