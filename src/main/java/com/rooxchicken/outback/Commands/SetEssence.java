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

public class SetEssence implements CommandExecutor
{
    private Outback plugin;

    public SetEssence(Outback _plugin)
    {
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.isOp())
        {
            Player player = Bukkit.getPlayer(sender.getName());
            PersistentDataContainer data = player.getPersistentDataContainer();
            
            data.set(Outback.essenceKey, PersistentDataType.INTEGER, Integer.parseInt(args[0]));
        }

        return true;
    }

}
