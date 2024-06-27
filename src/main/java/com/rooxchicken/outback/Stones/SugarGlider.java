package com.rooxchicken.outback.Stones;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.DisplayInformation;

public class SugarGlider extends Stone implements Listener
{
    private Outback plugin;

    public SugarGlider(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);

        itemName = "§x§F§F§6§E§0§D§lSugar Glider";

        cooldown2Key = new NamespacedKey(plugin, "sugarGliderA2");
        cooldown2Max = 60*20;
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

        Entity entity = event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item)/* && getEssence(item) >= 2 */)
            {
                if(!entity.isOnGround())
                {
                    event.setDamage(event.getDamage() + 2);
                    entity.setVelocity(damager.getLocation().getDirection().multiply(-0.5));
                }
            }
        }
    }
}
