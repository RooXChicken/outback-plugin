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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

public class SkyEmperorTask extends Task implements Listener
{
    private Player player;
    private Entity grabbedPlayer;
    private int t = 0;

    public SkyEmperorTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        player.setAllowFlight(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        tickThreshold = 1;
    }

    @Override
    public void run()
    {
        t++;
        if(t > 200)
        {
            player.setAllowFlight(false);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 11, 0));

            if(player.isOnGround())
                cancel = true;
        }
        else
        {
            if(grabbedPlayer != null)
            {
                Location grab = player.getLocation().clone().subtract(0,1,0);
                grab.setPitch(-10);

                grabbedPlayer.teleport(grab);
            }
        }
    }

    @EventHandler
    private void grabPlayer(PlayerInteractEvent event)
    {
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        Entity target = Library.getTarget(player, 3);
        if(target == null)
            return;

        grabbedPlayer = target;

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BIG_DRIPLEAF_FALL, 1, 1);
    }

    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
    }
}
