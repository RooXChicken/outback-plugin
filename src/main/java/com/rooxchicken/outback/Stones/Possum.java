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
import com.rooxchicken.outback.Tasks.TorporTask;

public class Possum extends Stone
{
    private Outback plugin;

    public Possum(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "Lurk";
        itemName = "§x§F§F§D§D§0§0§lPossum";

        cooldownKey = new NamespacedKey(plugin, "possum");
        cooldownMax = 45*20;
    }

    @Override
    public void tick()
    {
        
    }

    @EventHandler
    public void snatch(EntityDamageByEntityEvent event)
    {
        if(!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item)/* && getEssence(item) >= 2 */) //ESSENCECHECK
            {
                if(Math.random() < 0.05)
                {
                    Inventory inv = entity.getInventory();
                    if(inv.isEmpty())
                        return;

                    ArrayList<Integer> slots = new ArrayList<Integer>();
                    int index = 0;
                    for(int i = 0; i < inv.getSize(); i++)
                    {
                        if(inv.getItem(i) != null)
                        {
                            slots.add(i);
                            index++;
                        }
                    }
                    
                    int randomItemIndex = (int)(Math.random() * slots.size());
                    ItemStack randomItem = inv.getItem(slots.get(randomItemIndex));
                    inv.clear(randomItemIndex);
                    entity.getWorld().dropItem(entity.getLocation(), randomItem);
                }
            }
        }
    }

    @EventHandler
    private void lurk(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item) && checkCooldown(player, cooldownKey, cooldownMax)/* && getEssence(item) >= 5 */) //ESSENCECHECK
        {
            Outback.tasks.add(new LurkTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void thirdArm(PlayerInteractEvent event)
    {
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!checkItem(item)/* && getEssence(item) >= 10*/) //ESSENCECHECK
            return;

        Location target = Library.getTargetLocation(player, 8);
        if(target == null)
            return;

        for(int i = 0; i < 50; i++)
        {
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(target.clone().subtract(player.getLocation()).multiply(i/50.0)), 2, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(0x882222), 1f));
        }

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BIG_DRIPLEAF_FALL, 1, 1);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 60, 2));

        target.add(0, 4, 0);
        player.setVelocity(player.getLocation().getDirection().multiply(target.distance(player.getLocation())).multiply(0.2));
    }
}
