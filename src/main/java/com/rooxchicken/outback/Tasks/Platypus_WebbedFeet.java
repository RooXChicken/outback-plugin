package com.rooxchicken.outback.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Outback;

public class Platypus_WebbedFeet extends Task implements Listener
{
    private Player player;
    private int t = 0;

    private ItemStack boots;

    public Platypus_WebbedFeet(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;

        if(player.getInventory().getBoots() == null)
        {
            cancel = true;
            return;
        }
        else
            boots = player.getInventory().getBoots().clone();
        
        player.getInventory().getBoots().addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 4);

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t > 1)
            cancel = true;
    }

    @EventHandler
    public void preventChestplateSwitching(InventoryClickEvent event)
    {
        if(event.getInventory().getViewers().get(0) == player && event.getCurrentItem() != null && event.getCurrentItem().equals(player.getInventory().getBoots()))
            event.setCancelled(true);
    }

    @Override
    public void onCancel()
    {
        HandlerList.unregisterAll(this);
        if(boots == null)
            return;
        player.getInventory().setBoots(boots);
    }
}
