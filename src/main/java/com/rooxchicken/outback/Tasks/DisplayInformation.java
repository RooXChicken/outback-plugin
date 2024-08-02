package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Library;
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
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0);
            player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(1.0);
            player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE).setBaseValue(3.0);
            player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(4.5);
            String display = "";
            playerStonesMap.put(player, new ArrayList<ItemStack>());
            for(ItemStack item : player.getInventory())
            {
                if(item != null && item.hasItemMeta())
                {
                    if(plugin.getStoneFromName(item.getItemMeta().getDisplayName()) != null && item.equals(player.getInventory().getItemInMainHand()))
                    {
                        if(!display.equals(""))
                            display = "§x§7§6§6§A§7§4§lEssence: " + plugin.getEssence(player) + " | " + display;
                        else
                            display = "§x§7§6§6§A§7§4§lEssence: " + plugin.getEssence(player);
                    }
                    playerStonesMap.get(player).add(item);
                    Stone stone = plugin.getStoneFromName(item.getItemMeta().getDisplayName());
                    if(stone != null)
                    {
                        String stoneTxt = stone.tickCooldown(player, item, plugin.getAbilityFromName(item.getItemMeta().getDisplayName()));
                        if(!stoneTxt.equals("") && !display.equals(""))
                            display += ChatColor.WHITE + " | ";
                        display += stoneTxt;
                    }
                }
            }

            if(!display.equals(""))
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(display));

        }
    }
}
