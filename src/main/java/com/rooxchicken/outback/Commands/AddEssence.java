package com.rooxchicken.outback.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.rooxchicken.outback.Outback;

import net.md_5.bungee.api.ChatColor;

public class AddEssence implements CommandExecutor
{
    private Outback plugin;

    public AddEssence(Outback _plugin)
    {
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.isOp())
        {
            Player player = Bukkit.getPlayer(sender.getName());
            ItemStack item = player.getInventory().getItemInMainHand();


            if(item == null || !item.hasItemMeta() || plugin.getStoneFromName(item.getItemMeta().getDisplayName()) == null)
            {
                player.sendMessage(ChatColor.DARK_RED + "This is not a stone, silly!");
                return true;
            }

            PersistentDataContainer data = player.getPersistentDataContainer();

            int essence = Integer.parseInt(args[0]);
            int playerEssence = data.get(Outback.essenceKey, PersistentDataType.INTEGER);

            if(playerEssence - essence < 0)
            {
                player.sendMessage(ChatColor.DARK_RED + "You do not have enough essence!");
                return true;
            }
            
            data.set(Outback.essenceKey, PersistentDataType.INTEGER, playerEssence - essence);

            plugin.getStoneFromName(item.getItemMeta().getDisplayName()).addEssence(item, essence);
        }

        return true;
    }

}
