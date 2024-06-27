package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

import net.minecraft.core.particles.ParticleParamRedstone;

public class ScreechTask extends Task
{
    private Player player;
    private int t = 0;

    public ScreechTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;

        for(Object o : Library.getNearbyEntities(player.getLocation(), 5))
        {
            if(o instanceof LivingEntity && o != player)
            {
                LivingEntity entity = (LivingEntity)o;
                entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));

                entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().clone().add(0,1,0), 100, 0.6, 0.5, 0.6, new Particle.DustOptions(Color.BLACK, 2f));
            }
        }

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SCULK_SHRIEKER_SHRIEK, 1, 1);

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        if(player.getHealth() <= 10)
        {
            cancel = true;
            return;
        }

        player.damage(2);
        player.getWorld().spawnParticle(Particle.BLOCK_DUST, player.getLocation().clone().add(0,1,0 ), 20, Material.REDSTONE_BLOCK.createBlockData());
    }
}
