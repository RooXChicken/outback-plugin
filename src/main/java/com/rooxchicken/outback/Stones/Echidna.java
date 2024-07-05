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
import com.rooxchicken.outback.Tasks.Echidna_SpinySphere;
import com.rooxchicken.outback.Tasks.Echidna_StickyTongue;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SpikyShelterTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;
import com.rooxchicken.outback.Tasks.ViciousTask;

public class Echidna extends Stone
{
    private Outback plugin;
    private boolean toggled = false;

    public static final String itemName = "§x§7§6§5§5§3§9§lEchidna";

    public Echidna(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§7§6§5§5§3§9§lSpiny Sphere";
        

        cooldownKey = new NamespacedKey(plugin, "echidna");
        cooldownMax = 20*20;
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
    private void astuteSnout(PlayerInteractEvent event)
    {
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(!checkItem(item, itemName) || !(getEssence(item) >= 2))
            return;

        for(Object o : Library.getNearbyEntities(player.getLocation(), 100))
        {
            if(o instanceof Player && o != player)
            {
                if(!toggled)
                    ((Player)o).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000, 0));
                else
                    ((Player)o).removePotionEffect(PotionEffectType.GLOWING);

                    
            }
        }

        toggled = !toggled;
    }

    @EventHandler
    private void spinySphere(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            Outback.tasks.add(new Echidna_SpinySphere(plugin, player));

            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        Outback.tasks.add(new Echidna_StickyTongue(plugin, player));
    }
}
