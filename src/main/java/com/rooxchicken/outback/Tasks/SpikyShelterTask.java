package com.rooxchicken.outback.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.rooxchicken.outback.Outback;

public class SpikyShelterTask extends Task
{
    private Player player;
    private Vector oldLoc;
    private int t = 0;

    public SpikyShelterTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 200, 0));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHERRY_LEAVES_BREAK, 1, 1);

        for(int i = 0; i < 2; i++)
        {
            player.getWorld().setBlockData(player.getLocation().clone().add(1,i,0), Material.JUNGLE_LEAVES.createBlockData());
            player.getWorld().setBlockData(player.getLocation().clone().add(1,i,1), Material.JUNGLE_LEAVES.createBlockData());
            player.getWorld().setBlockData(player.getLocation().clone().add(0,i,1), Material.JUNGLE_LEAVES.createBlockData());
            player.getWorld().setBlockData(player.getLocation().clone().add(0,i,0), Material.JUNGLE_LEAVES.createBlockData());
        }

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        if(t % 15 == 0)
        {
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHERRY_LEAVES_BREAK, 1, 1);
        }
        
        if(++t > 200)
            cancel = true;
    }
}
