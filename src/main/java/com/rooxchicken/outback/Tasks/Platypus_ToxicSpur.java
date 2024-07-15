package com.rooxchicken.outback.Tasks;

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

public class Platypus_ToxicSpur extends Task implements Listener
{
    private Player player;
    private int t = 0;

    public Platypus_ToxicSpur(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;
    }

    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void toxicSpur(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() == player && event.getEntity() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        entity.setVelocity(player.getLocation().getDirection().multiply(2));
        damager.getWorld().spawnParticle(Particle.DUST, damager.getLocation().clone().add(0,1,0), 140, 0.5, 0.8, 0.5, new Particle.DustOptions(Color.GREEN, 1.0f));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30*20, 2));

        if(++t > 2)
            cancel = true;
    }
}
