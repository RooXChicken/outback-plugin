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

public class BigChompTask extends Task implements Listener
{
    private Player player;
    private int t = 0;

    private ItemStack boots;

    public BigChompTask(Outback _plugin, Player _player)
    {
        super(_plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);

        player = _player;

        if(player.getInventory().getBoots() == null)
        {
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            boots = new ItemStack(Material.AIR);
        }
        else
            boots = player.getInventory().getBoots().clone();
        
        player.getInventory().getBoots().addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 5);

        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 4));

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t > 2)
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
        player.getInventory().setBoots(boots);
        HandlerList.unregisterAll(this);
    }
}
