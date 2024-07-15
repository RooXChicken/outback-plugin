package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
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

public class Crocodile_DeathRoll extends Task implements Listener
{
    private Player player;
    private int t = 0;
    private int hitCount = 0;

    private ArrayList<Player> affected;

    public Crocodile_DeathRoll(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;
        affected = new ArrayList<Player>();
    }

    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void deathRoll(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() == player && event.getEntity() instanceof Player))
            return;
            
        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        if(affected.contains(entity))
            return;

        if(++hitCount < 3)
            return;

        damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 1);
        damager.getWorld().spawnParticle(Particle.DUST, damager.getLocation().clone().add(0,1,0), 140, 0.5, 0.8, 0.5, new Particle.DustOptions(Color.GREEN, 1.0f));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 100, 2));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0));

        affected.add(entity);

        if(++t > 2)
            cancel = true;
    }
}
