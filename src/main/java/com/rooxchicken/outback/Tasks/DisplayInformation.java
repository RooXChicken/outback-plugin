package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Stone;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class DisplayInformation extends Task
{
    public static HashMap<Player, ArrayList<ItemStack>> playerStonesMap;
    private Outback plugin;
    
    public DisplayInformation(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;
        tickThreshold = 1;

        playerStonesMap = new HashMap<Player, ArrayList<ItemStack>>();
    }

    @Override
    public void run()
    {
        playerStonesMap.clear();
        for(Player player : Bukkit.getOnlinePlayers())
        {
            String display = "";
            playerStonesMap.put(player, new ArrayList<ItemStack>());
            for(ItemStack item : player.getInventory())
            {
                if(item != null && item.hasItemMeta())
                {
                    playerStonesMap.get(player).add(item);
                    Stone stone = plugin.getStoneFromName(item.getItemMeta().getDisplayName());
                    if(stone != null)
                    {
                        String stoneTxt = stone.tickCooldown(player, item);
                        if(!stoneTxt.equals("") && !display.equals(""))
                            display += ChatColor.WHITE + " | ";
                        display += stoneTxt;
                    }
                }
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(display));

        }
    }
}
