package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class AbandonTask extends Task
{
    private Player player;
    private int t = 0;

    private Silverfish silverfish;

    public AbandonTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 2));
        
        silverfish = (Silverfish)player.getWorld().spawnEntity(player.getLocation(), EntityType.SILVERFISH);
        silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        silverfish.setHealth(40);
        silverfish.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 1000000, 2));

        player.getWorld().spawnParticle(Particle.REDSTONE, silverfish.getLocation(), 140, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(silverfish == null || silverfish.isDead())
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }
}
