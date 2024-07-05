package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

public class Echidna_StickyTongue extends Task
{
    private Player player;
    private Player target;

    private int t = 0;

    public Echidna_StickyTongue(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;

        Object t = Library.getTarget(_player, 10);
        if(t instanceof Player)
            target = (Player)t;
        else
            cancel = true;

        for(Object o : Library.getNearbyEntities(player.getLocation(), 20))
        {
            if(o instanceof LivingEntity && o != player)
            {
                LivingEntity entity = (LivingEntity)o;
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
            }
        }

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        target.setVelocity(player.getLocation().clone().subtract(target.getLocation()).toVector().multiply(0.1));

        for(int i = 0; i < 10; i++)
        {
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0,1,0).add(target.getLocation().clone().add(0,1,0).subtract(player.getLocation()).multiply(i/10.0)), 2, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(0x882222), 0.6f));
        }

        if(++t > 80)
            cancel = true;
    }
}
