package com.rooxchicken.outback.Tasks;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class ViciousTask extends Task
{
    private Player player;
    private int t = 0;

    public ViciousTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;

        for(Task task : Outback.tasks)
        {
            if(task instanceof ViciousTask)
            if(((ViciousTask)task).player == player)
                cancel = true;
        }

        tickThreshold = 40;
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
        player.getWorld().spawnParticle(Particle.BLOCK, player.getLocation().clone().add(0,1,0 ), 20, Material.REDSTONE_BLOCK.createBlockData());
    }
}
