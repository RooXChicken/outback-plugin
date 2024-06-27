package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.rooxchicken.outback.Outback;

public abstract class Stone implements Listener
{
    private Outback plugin;
    public String name;
    public static String itemName = "§x§2§E§2§E§2§E§lStone";

    public NamespacedKey cooldownKey;

    public int cooldownMax;

    public Stone(Outback _plugin)
    {
        plugin = _plugin;
        name = "Stone";

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
        return Integer.parseInt(meta.getLore().get(0).split(":")[1].trim());
    }

    public void setEssence(ItemStack item, int essence)
    {
        if(item == null || !item.hasItemMeta())
            return;

        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Essence: " + essence);
    }

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
