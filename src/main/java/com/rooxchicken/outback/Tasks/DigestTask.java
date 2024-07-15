package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

import net.minecraft.world.entity.EntityAreaEffectCloud;

public class DigestTask extends Task implements Listener
{
    private Player player;
    private int t = 0;

    private PotionEffect oldStrength;
    private int strength = 0;

    public DigestTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        oldStrength = player.getPotionEffect(PotionEffectType.STRENGTH);
        player.removePotionEffect(PotionEffectType.STRENGTH);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        if(strength != 0)
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 2, strength-1));

        if(++t > 600)
            cancel = true;
    }

    @EventHandler
    public void givePoison(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() != player || !(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity)event.getEntity();
        Player damager = (Player)event.getDamager();

        if(strength != 0)
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, strength-1));
    }

    @EventHandler
    public void addToStrength(BlockBreakEvent event)
    {
        if(event.getPlayer() != player || !event.getBlock().getType().equals(Material.MANGROVE_LEAVES))
            return;

        strength++;
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        player.getWorld().spawnParticle(Particle.DUST, player.getLocation().clone().add(0,1,0), 100, 0.2, 0.5, 0.2, new Particle.DustOptions(Color.RED, 1.5f));
    }

    @Override
    public void onCancel()
    {
        player.removePotionEffect(PotionEffectType.STRENGTH);
        if(oldStrength != null)
            player.addPotionEffect(oldStrength);

        HandlerList.unregisterAll(this);
    }
}
