package com.rooxchicken.outback.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Koala;
import com.rooxchicken.outback.Stones.Possum;
import com.rooxchicken.outback.Stones.Quokka;
import com.rooxchicken.outback.Stones.SugarGlider;
import com.rooxchicken.outback.Stones.TasmanianDevil;
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

        ItemStack sugarGlider = new ItemStack(Material.RED_DYE);
        ItemMeta sugarMeta = sugarGlider.getItemMeta();
        sugarMeta.setDisplayName(SugarGlider.itemName);
        sugarGlider.setItemMeta(sugarMeta);

        player.getInventory().addItem(sugarGlider);

        ItemStack possum = new ItemStack(Material.YELLOW_DYE);
        ItemMeta possumMeta = possum.getItemMeta();
        possumMeta.setDisplayName(Possum.itemName);
        possum.setItemMeta(possumMeta);

        player.getInventory().addItem(possum);

        ItemStack koala = new ItemStack(Material.LIME_DYE);
        ItemMeta koalaMeta = koala.getItemMeta();
        koalaMeta.setDisplayName(Koala.itemName);
        koala.setItemMeta(koalaMeta);

        player.getInventory().addItem(koala);

        ItemStack tasmanianDevil = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta devilMeta = tasmanianDevil.getItemMeta();
        devilMeta.setDisplayName(TasmanianDevil.itemName);
        tasmanianDevil.setItemMeta(devilMeta);

        player.getInventory().addItem(tasmanianDevil);

        ItemStack quokka = new ItemStack(Material.PINK_DYE);
        ItemMeta quokkaMeta = quokka.getItemMeta();
        quokkaMeta.setDisplayName(Quokka.itemName);
        quokka.setItemMeta(quokkaMeta);

        player.getInventory().addItem(quokka);

        return true;
    }

}
