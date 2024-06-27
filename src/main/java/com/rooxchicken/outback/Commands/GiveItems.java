package com.rooxchicken.outback.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rooxchicken.outback.Outback;
public class GiveItems implements CommandExecutor
{
    private Outback plugin;

    public GiveItems(Outback _plugin)
    {
        plugin = _plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.isOp())
            return false;

        Player player = Bukkit.getPlayer(sender.getName());
        

        return true;
    }

}
