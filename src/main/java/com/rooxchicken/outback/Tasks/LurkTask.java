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
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

import net.minecraft.world.entity.EntityAreaEffectCloud;

public class LurkTask extends Task
{
    private Player player;
    private int t = 0;

    private AreaEffectCloud cloud;

    private Location entrance;

    public LurkTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0));

        spawnEffect(player.getLocation());
        playSound();

        tickThreshold = 10;
    }

    private void spawnEffect(Location loc)
    {
        double blockY = Library.getBlock(player, 40, 90).getLocation().getY() + 1.1;
        entrance = loc.clone();
        entrance.setY(blockY);

        cloud = (AreaEffectCloud) player.getWorld().spawnEntity(entrance, EntityType.AREA_EFFECT_CLOUD);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 200, 0), true);

        player.getWorld().spawnParticle(Particle.REDSTONE, entrance, 400, 1, 0.2, 1, new Particle.DustOptions(Color.fromRGB(0x882222), 1f));
    }

    private void playSound()
    {
        player.getWorld().playSound(entrance, Sound.BLOCK_ROOTED_DIRT_BREAK, 1, 1);
    }

    @Override
    public void run()
    {
        playSound();

        player.getWorld().spawnParticle(Particle.REDSTONE, entrance, 400, 1, 0.2, 1, new Particle.DustOptions(Color.fromRGB(0x882222), 1f));
        
        if(t == 19)
        {
            cloud.remove();
            spawnEffect(player.getLocation());
            playSound();
        }

        if(++t > 39)
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        cloud.remove();
    }
}
