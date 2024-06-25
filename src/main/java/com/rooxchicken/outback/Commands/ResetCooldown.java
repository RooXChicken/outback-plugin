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

public class ResetCooldown implements CommandExecutor
{
    private Outback plugin;

    public ResetCooldown(Outback _plugin)
    {
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.isOp())
        {
            Player player = Bukkit.getPlayer(sender.getName());
            // BaseOrbit orbit = plugin.getOrbit(player);
            // PersistentDataContainer data = player.getPersistentDataContainer();
            
            // data.set(orbit.cooldown1Key, PersistentDataType.INTEGER, 0);
            // if(orbit.cooldown2Max != -1)
            //     data.set(orbit.cooldown2Key, PersistentDataType.INTEGER, 0);
        }

        return true;
    }

}
