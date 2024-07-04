package com.rooxchicken.outback.Stones;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.rooxchicken.outback.Outback;

import net.md_5.bungee.api.ChatColor;

public abstract class Stone implements Listener
{
    private Outback plugin;
    public String name;
    public static String itemName = "§x§2§E§2§E§2§E§lStone";

    public NamespacedKey cooldownKey;
    public HashMap<Player, Integer> implodeTries;

    public int cooldownMax;

    public Stone(Outback _plugin)
    {
        plugin = _plugin;
        name = "Stone";

        implodeTries = new HashMap<Player, Integer>();

        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);
    }

    public void tick() {}

    public String tickCooldown(Player player, ItemStack item, String name)
    {
        playerTickLogic(player, item);
        checkHasCooldown(player, cooldownKey);
        PersistentDataContainer data = player.getPersistentDataContainer();

        int cooldown = data.get(cooldownKey, PersistentDataType.INTEGER) - 1;
        data.set(cooldownKey, PersistentDataType.INTEGER, cooldown);

        if(cooldown > 0)
            return name + ": " + cooldown/20 + "s";
        
        return "";
    }

    public static ArrayList<String> baseLore()
    {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Shift+Offhand this item to activate the second ability!");
        lore.add("§7Shift+LeftClick this item 3 times to implode it!");
        return lore;
    }

    public void resetCooldown(Player player)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        data.set(cooldownKey, PersistentDataType.INTEGER, 0);
    }

    public void playerTickLogic(Player player, ItemStack item) {}

    public int getEssence(ItemStack item)
    {
        if(item == null || !item.hasItemMeta())
            return -1;

        ItemMeta meta = item.getItemMeta();
        return Integer.parseInt(meta.getLore().get(2).split(":")[1].trim());
    }

    public void setEssence(ItemStack item, int essence)
    {
        if(item == null || !item.hasItemMeta())
            return;

        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = baseLore();
        lore.add("§x§7§6§6§A§7§4§lEssence: " + essence);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void addEssence(ItemStack item, int essence)
    {
        if(item == null || !item.hasItemMeta())
            return;

        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = baseLore();
        int newEssence = (Integer.parseInt(meta.getLore().get(2).split(":")[1].trim()) + essence);
        lore.add("§x§7§6§6§A§7§4§lEssence: " + newEssence);
        meta.setLore(lore);
        item.setItemMeta(meta);

        if(newEssence == 0)
            return;

        if(newEssence >= 10)
            item.setType(Material.RED_DYE);
        else if(newEssence > 4)
            item.setType(Material.YELLOW_DYE);
        else if(newEssence > 1)
            item.setType(Material.GREEN_DYE);
    }

    @EventHandler
    public void activateImplode(PlayerInteractEvent event)
    {
        if(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_AIR)
            return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(!player.isSneaking() || !checkItem(item, getItemName()) || !(plugin.getEssence(player) >= 1))
            return;

        if(!implodeTries.containsKey(player))
            implodeTries.put(player, 0);

        int tries = implodeTries.get(player) + 1;

        implodeTries.remove(player);

        if(tries < 4)
        {
            player.sendMessage(ChatColor.RED + "You need to try " + (4-tries) + " more " + ((4-tries == 1) ? "time" : "times") + " to implode this stone!");
            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        }
        else
        {
            player.playSound(player.getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 1);
            item.setType(Material.GRAY_DYE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(itemName);
            item.setItemMeta(meta);
            setEssence(item, 0);
            implode(event.getPlayer());
            implodeTries.put(player, 0);

            plugin.setEssence(player, plugin.getEssence(player)-1);

            return;
        }

        implodeTries.put(player, tries);
    }

    public String getItemName() { return itemName; };

    public void implode(Player player) {}

    public boolean checkItem(ItemStack item, String name)
    {
        return (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(name));
    }

    public boolean checkCooldown(Player player, NamespacedKey key, int cooldown)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(!data.has(key, PersistentDataType.INTEGER))
            data.set(key, PersistentDataType.INTEGER, 0);

        if(data.get(key, PersistentDataType.INTEGER) <= 0)
        {
            data.set(key, PersistentDataType.INTEGER, cooldown);
            return true;
        }

        return false;
    }

    public boolean checkCooldownNoset(Player player, NamespacedKey key, int cooldown)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(!data.has(key, PersistentDataType.INTEGER))
            data.set(key, PersistentDataType.INTEGER, 0);

        return (data.get(key, PersistentDataType.INTEGER) == 0);
    }

    public void checkHasCooldown(Player player, NamespacedKey cd)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(cd != null)
        if(!data.has(cd, PersistentDataType.INTEGER))
            data.set(cd, PersistentDataType.INTEGER, 0);
    }
}
