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
import com.rooxchicken.outback.Common.Sphere;

public class Echidna_SpinySphere extends Task implements Listener
{
    private Player player;
    private int t = 0;

    private Sphere sphere;

    public Echidna_SpinySphere(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;

        sphere = new Sphere(new Color[] {Color.GRAY}, 10);
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 1));

        tickThreshold = 2;
    }

    @Override
    public void run()
    {
        if(++t > 54)
            cancel = true;

        sphere.run(player.getLocation(), 2, 20, 1, 0.1);
    }
    
    @EventHandler
    public void reflect(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() instanceof Player && event.getEntity() == player))
        return;
        
        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();
        
        damager.damage(event.getDamage()/2.0);
    }
    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
    }
}
