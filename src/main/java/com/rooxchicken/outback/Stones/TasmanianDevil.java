package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
import com.rooxchicken.outback.Tasks.FuryTask;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;
import com.rooxchicken.outback.Tasks.ViciousTask;

public class TasmanianDevil extends Stone
{
    private Outback plugin;

    public static String itemName = "§x§0§0§B§B§F§F§lTasmanian Devil";

    public TasmanianDevil(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§0§0§B§B§F§F§lScreech";
        

        cooldownKey = new NamespacedKey(plugin, "tasmaniandevil");
        cooldownMax = 60*20;
    }

    @Override
    public void tick()
    {
        
    }

    @EventHandler
    public void vicious(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() instanceof Player && event.getEntity() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item, itemName) && getEssence(item) >= 2)
            {
                if(Math.random() < 0.2)
                {
                    Outback.tasks.add(new ViciousTask(plugin, entity));
                }
            }
        }
    }

    @EventHandler
    private void screech(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
             for(Object o : Library.getNearbyEntities(player.getLocation(), 5))
            {
                if(o instanceof LivingEntity && o != player)
                {
                    LivingEntity entity = (LivingEntity)o;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));

                    entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().clone().add(0,1,0), 100, 0.6, 0.5, 0.6, new Particle.DustOptions(Color.BLACK, 2f));
                }
            }

            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SCULK_SHRIEKER_SHRIEK, 1, 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void carnivore(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() instanceof Player && event.getEntity() instanceof Player))
            return;

        Entity entity = event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item, itemName) && getEssence(item) >= 10)
            {
                event.setDamage(event.getDamage() + 3);
                double hp = damager.getHealth() + (event.getFinalDamage() * 0.4);
                hp = Math.min(20, hp);
                damager.setHealth(hp);
                damager.getWorld().spawnParticle(Particle.BLOCK_DUST, damager.getLocation().clone().add(0,1,0 ), 20, Material.REDSTONE_BLOCK.createBlockData());

            }
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        Outback.tasks.add(new FuryTask(plugin, player));
    }
}
