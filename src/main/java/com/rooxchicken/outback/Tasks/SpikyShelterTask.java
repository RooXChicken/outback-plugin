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

public class SpikyShelterTask extends Task
{
    private Player player;
    private int t = 0;

    public SpikyShelterTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        
    }
}
