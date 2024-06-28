package com.rooxchicken.outback.Tasks;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

public class ParachuteTask extends Task implements Listener
{
    private Player player;
    private HashMap<Player, ItemStack> playerChestplateMap;
    private int t = 0;

    public ParachuteTask(Outback _plugin, Player _player)
    {
        super(_plugin);
        playerChestplateMap = new HashMap<Player, ItemStack>();

        player = _player;
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        for(Object o : Library.getNearbyEntities(player.getLocation(), 10))
        {
            if(o instanceof Player)
            {
                player.getWorld().playSound(((Player)o).getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
                playerChestplateMap.put((Player)o, ((Player)o).getInventory().getChestplate());
                ((Player)o).getInventory().setChestplate(new ItemStack(Material.ELYTRA));
                ((Player)o).setVelocity(new Vector(0, 1.2, 0));
            }
        }

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        if(++t < 2)
            return;
        
        if(player.isOnGround())
        {
            player.getInventory().setChestplate(playerChestplateMap.get(player));
            playerChestplateMap.remove(player);

            for(Object o : Library.getNearbyEntities(player.getLocation(), 10))
            {
                if(o instanceof LivingEntity)
                {
                    ((LivingEntity)o).damage(16);
                    player.getWorld().playSound(((LivingEntity)o).getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 1);
                }
            }

            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 1);
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 4000, 5, 0, 5, new Particle.DustOptions(Color.GRAY, 2f));

            for(Entry<Player, ItemStack> p : playerChestplateMap.entrySet())
            {
                p.getKey().getInventory().setChestplate(p.getValue());
            }

            playerChestplateMap.clear();
            cancel = true;
        }
    }

    @EventHandler
    public void preventChestplateSwitching(InventoryClickEvent event)
    {
        if(playerChestplateMap.containsKey(event.getInventory().getViewers().get(0)) && event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.ELYTRA))
            event.setCancelled(true);
    }

    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
    }
}
