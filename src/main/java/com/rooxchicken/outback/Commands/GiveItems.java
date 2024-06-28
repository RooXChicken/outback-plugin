package com.rooxchicken.outback.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Dragonfly;
import com.rooxchicken.outback.Stones.GreatWhiteShark;
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
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§x§7§6§6§A§7§4§lEssence: 0");

        ItemStack sugarGlider = new ItemStack(Material.RED_DYE);
        ItemMeta sugarMeta = sugarGlider.getItemMeta();
        sugarMeta.setDisplayName(SugarGlider.itemName);
        sugarMeta.setLore(lore);
        sugarGlider.setItemMeta(sugarMeta);

        player.getInventory().addItem(sugarGlider);

        ItemStack possum = new ItemStack(Material.YELLOW_DYE);
        ItemMeta possumMeta = possum.getItemMeta();
        possumMeta.setDisplayName(Possum.itemName);
        possumMeta.setLore(lore);
        possum.setItemMeta(possumMeta);

        player.getInventory().addItem(possum);

        ItemStack koala = new ItemStack(Material.LIME_DYE);
        ItemMeta koalaMeta = koala.getItemMeta();
        koalaMeta.setDisplayName(Koala.itemName);
        koalaMeta.setLore(lore);
        koala.setItemMeta(koalaMeta);

        player.getInventory().addItem(koala);

        ItemStack tasmanianDevil = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta devilMeta = tasmanianDevil.getItemMeta();
        devilMeta.setDisplayName(TasmanianDevil.itemName);
        devilMeta.setLore(lore);
        tasmanianDevil.setItemMeta(devilMeta);

        player.getInventory().addItem(tasmanianDevil);

        ItemStack quokka = new ItemStack(Material.PINK_DYE);
        ItemMeta quokkaMeta = quokka.getItemMeta();
        quokkaMeta.setDisplayName(Quokka.itemName);
        quokkaMeta.setLore(lore);
        quokka.setItemMeta(quokkaMeta);

        player.getInventory().addItem(quokka);

        ItemStack shark = new ItemStack(Material.GRAY_DYE);
        ItemMeta sharkMeta = shark.getItemMeta();
        sharkMeta.setDisplayName(GreatWhiteShark.itemName);
        sharkMeta.setLore(lore);
        shark.setItemMeta(sharkMeta);

        player.getInventory().addItem(shark);

        ItemStack dragonfly = new ItemStack(Material.ORANGE_DYE);
        ItemMeta dragonflyMeta = dragonfly.getItemMeta();
        dragonflyMeta.setDisplayName(Dragonfly.itemName);
        dragonflyMeta.setLore(lore);
        dragonfly.setItemMeta(dragonflyMeta);

        player.getInventory().addItem(dragonfly);

        return true;
    }

}
