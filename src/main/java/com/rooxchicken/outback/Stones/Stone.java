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
    public String itemName;

    public NamespacedKey cooldown1Key;
    public NamespacedKey cooldown2Key;
    public NamespacedKey cooldown3Key;

    public int cooldown1Max;
    public int cooldown2Max;
    public int cooldown3Max;

    public Stone(Outback _plugin)
    {
        plugin = _plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, _plugin);
    }

    public void tick() {}

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

    public boolean checkItem(ItemStack item)
    {
        return (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(itemName));
    }

    public boolean checkCooldown(Player player, NamespacedKey key, int cooldown)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(!data.has(key, PersistentDataType.INTEGER))
            data.set(key, PersistentDataType.INTEGER, 0);

        if(data.get(key, PersistentDataType.INTEGER) == 0)
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

    public void checkHasCooldown(Player player, NamespacedKey cd1, NamespacedKey cd2, NamespacedKey cd3)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(cd1 != null)
        if(!data.has(cd1, PersistentDataType.INTEGER))
            data.set(cd1, PersistentDataType.INTEGER, 0);

        if(cd2 != null)
        if(!data.has(cd2, PersistentDataType.INTEGER))
            data.set(cd2, PersistentDataType.INTEGER, 0);

        if(cd3 != null)
        if(!data.has(cd3, PersistentDataType.INTEGER))
            data.set(cd3, PersistentDataType.INTEGER, 0);
    }
}
