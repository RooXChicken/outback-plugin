package com.rooxchicken.outback.Tasks;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Possum;

public class StalkTask extends Task
{
    private Player player;
    private int t = 0;

    public StalkTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;

        for(Object o : Library.getNearbyEntities(player.getLocation(), 20))
        {
            if(o instanceof Player)
            {
                Player p = (Player)o;
                ItemStack item = p.getInventory().getItemInMainHand();
                if(item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(Possum.itemName))
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));

                    p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                    player.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 200, 1, 0.2, 1, new Particle.DustOptions(Color.GREEN, 1f));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0));

                    p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);

                    player.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 200, 1, 0.2, 1, new Particle.DustOptions(Color.RED, 1f));
                }
            }
        }

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        
    }

    @Override
    public void onCancel()
    {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }
}
