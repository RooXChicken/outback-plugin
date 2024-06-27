package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class TorporTask extends Task
{
    private Player player;
    private int t = 0;

    public TorporTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 3));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 1, 1);
        player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0, 1, 0), 40, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(t < 5)
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0, 1, 0), 40, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));
        if(++t == 5)
        {
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 1);
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0, 1, 0), 100, 0.5, 0.4, 0.5, new Particle.DustOptions(Color.RED, 1f));
        }

        if(t > 15)
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
    }
}
