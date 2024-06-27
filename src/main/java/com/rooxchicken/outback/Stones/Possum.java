package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.DisplayInformation;

public class Possum extends Stone implements Listener
{
    private Outback plugin;

    public Possum(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);

        itemName = "§x§F§F§D§D§0§0§lPossum";

        cooldown2Key = new NamespacedKey(plugin, "possumA2");
        cooldown2Max = 45*20;
    }

    @Override
    public void tick()
    {
        
    }

    @EventHandler
    public void snatch(EntityDamageByEntityEvent event)
    {
        if(!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        Player entity = (Player)event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item)/* && getEssence(item) >= 2 */)
            {
                if(Math.random() < 0.025)
                {
                    Inventory inv = entity.getInventory();
                    if(inv.isEmpty())
                        return;

                    ArrayList<Integer> slots = new ArrayList<Integer>();
                    int index = 0;
                    for(int i = 0; i < inv.getSize(); i++)
                    {
                        if(inv.getItem(i) != null)
                        {
                            slots.add(i);
                            index++;
                        }
                    }
                    
                    int randomItemIndex = (int)(Math.random() * slots.size());
                    ItemStack randomItem = inv.getItem(slots.get(randomItemIndex));
                    inv.clear(randomItemIndex);
                    entity.getWorld().dropItem(entity.getLocation(), randomItem);
                }
            }
        }
    }
}
