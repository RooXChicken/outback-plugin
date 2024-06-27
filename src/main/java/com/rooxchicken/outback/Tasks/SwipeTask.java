package com.rooxchicken.outback.Tasks;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class SwipeTask extends Task
{
    private Player player;
    private int t = 0;

    public SwipeTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t > 5)
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
    }
}
