package com.rooxchicken.outback.Tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class LoseTeethTask extends Task implements Listener
{
    private Player player;
    private int hits = 0;

    private ItemStack boots;
    private HashMap<Player, Integer> affectedPlayers;

    public LoseTeethTask(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;

        affectedPlayers = new HashMap<Player, Integer>();

        tickThreshold = 20;
    }

    @Override
    public void run()
    {

        for(Map.Entry<Player, Integer> entry : affectedPlayers.entrySet())
        {
            entry.setValue(entry.getValue() - 1);
            if(entry.getValue() <= 0)
                entry.getKey().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        }
    }

    @EventHandler
    public void givePoison(EntityDamageByEntityEvent event)
    {
        if(hits > 4)
            return;
        if(event.getDamager() != player || !(event.getEntity() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        hits++;
        entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
        entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 200, 1, 0.2, 1, new Particle.DustOptions(Color.RED, 1f));

        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2);
        if(!affectedPlayers.containsKey(entity))
            affectedPlayers.put(entity, (int)(60.0/hits));
    }

    @Override
    public void onCancel()
    {
        for(Map.Entry<Player, Integer> entry : affectedPlayers.entrySet())
        {
            entry.getKey().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        }

        HandlerList.unregisterAll(this);
    }
}
