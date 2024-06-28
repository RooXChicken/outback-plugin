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
import com.rooxchicken.outback.Tasks.SpikyShelterTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;
import com.rooxchicken.outback.Tasks.ViciousTask;

public class Quokka extends Stone
{
    private Outback plugin;

    public static String itemName = "§x§F§F§8§6§8§2§lQuokka";

    public Quokka(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§F§F§8§6§8§2§lSpiky Shelter";
        

        cooldownKey = new NamespacedKey(plugin, "quokka");
        cooldownMax = 45*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        if(getEssence(item) >= 10)
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                Object o = Library.getTarget(p, 5);
                if(o == player)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
                }
            }

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 21, 2));
        }
    }

    @EventHandler
    public void bounce(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() instanceof Player && event.getEntity() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item, itemName) && getEssence(item) >= 2)
            {
                if(!damager.isOnGround())
                {
                    event.setDamage(event.getDamage() * 1.5);
                    damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_SLIME_JUMP_SMALL, 1, 1);
                }
            }
        }
    }

    @EventHandler
    private void spikyShelter(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            Outback.tasks.add(new SpikyShelterTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }
}
