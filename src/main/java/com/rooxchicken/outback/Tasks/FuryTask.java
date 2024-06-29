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

public class FuryTask extends Task implements Listener
{
    private Player player;
    private int t = 0;

    public FuryTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 300, 9));
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
        int strength = 1;
        for(Object o : Library.getNearbyEntities(player.getLocation(), 5))
        {
            if(o instanceof Player && o != player)
            {
                strength += 3;
            }
        }

        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(strength);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t > 14)
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
        HandlerList.unregisterAll(this);
    }
}
