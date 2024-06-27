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
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;

import net.md_5.bungee.api.ChatColor;

public class GreatWhiteShark extends Stone
{
    private Outback plugin;

    public static String itemName = "§x§7§5§7§5§7§5§lGreat White Shark";

    public GreatWhiteShark(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§7§5§7§5§7§5§lBig Chomp";

        cooldownKey = new NamespacedKey(plugin, "shark");
        cooldownMax = 35*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        /* if(getEssence(item) >= 10) */ //ESSENCECHECK
            //player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 21, 0));
    }

    @EventHandler
    public void bloodScent(EntityDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player)event.getEntity();

        for(Player p : Bukkit.getOnlinePlayers())
        {
            for(ItemStack item : DisplayInformation.playerStonesMap.get(p))
            if(checkItem(item, itemName)/* && getEssence(item) >= 2*/) //ESSENCECHECK
            {
                p.sendMessage(ChatColor.RED + String.format("BLOOD SCENT: (" + player.getName() + ") X %.1f | Y %.1f | Z %.1f", player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 0));
            }
        }
    }

    @EventHandler
    private void bigChomp(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && checkCooldown(player, cooldownKey, cooldownMax)/* && getEssence(item) >= 5 */) //ESSENCECHECK
        {
            //Outback.tasks.add(new SwipeTask(plugin, player));
            event.setCancelled(true);
        }
    }
}
