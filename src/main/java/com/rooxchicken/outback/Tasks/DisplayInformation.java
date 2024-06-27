package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Stone;

public class DisplayInformation extends Task
{
    public static HashMap<Player, ArrayList<ItemStack>> playerStonesMap;
    
    public DisplayInformation(Outback _plugin)
    {
        super(_plugin);
        tickThreshold = 1;

        playerStonesMap = new HashMap<Player, ArrayList<ItemStack>>();
    }

    @Override
    public void run()
    {
        playerStonesMap.clear();
        for(Player player : Bukkit.getOnlinePlayers())
        {
            playerStonesMap.put(player, new ArrayList<ItemStack>());
            for(ItemStack item : player.getInventory())
            {
                if(item != null && item.hasItemMeta())
                    playerStonesMap.get(player).add(item);
            }

        }
    }
}
