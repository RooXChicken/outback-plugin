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
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.AbandonTask;
import com.rooxchicken.outback.Tasks.DigestTask;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;

public class Koala extends Stone
{
    private Outback plugin;

    public static String itemName = "§x§9§A§8§7§B§7§lKoala";

    public Koala(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§9§A§8§7§B§7§lSwipe";

        cooldownKey = new NamespacedKey(plugin, "koala");
        cooldownMax = 30*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        if(getEssence(item) >= 10)
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 21, 0));
    }

    @EventHandler
    public void hardass(EntityDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player)event.getEntity();
        for(ItemStack item : DisplayInformation.playerStonesMap.get(player))
            if(checkItem(item, itemName) && player.isSneaking() && getEssence(item) >= 2)
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

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            Outback.tasks.add(new SwipeTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        Outback.tasks.add(new DigestTask(plugin, player));
    }
}
