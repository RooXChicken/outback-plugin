package com.rooxchicken.outback.Tasks;

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

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t == 5)
        {
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_FANGS_ATTACK, 1, 1);
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
