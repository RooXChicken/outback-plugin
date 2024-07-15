package com.rooxchicken.outback.Stones;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.BigChompTask;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.LurkTask;
import com.rooxchicken.outback.Tasks.SwipeTask;
import com.rooxchicken.outback.Tasks.TorporTask;

import net.md_5.bungee.api.ChatColor;

public class GreatWhiteShark extends Stone
{
    private Outback plugin;

    public static final String itemName = "§x§4§8§B§2§D§A§lGreat White Shark";
    private NamespacedKey scentKey;
    private NamespacedKey tridentKey;

    public GreatWhiteShark(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§4§8§B§2§D§A§lBig Chomp";

        scentKey = new NamespacedKey(plugin, "bloodScent");
        tridentKey = new NamespacedKey(plugin, "trident");
        cooldownKey = new NamespacedKey(plugin, "shark");
        cooldownMax = 35*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(getEssence(item) >= 2)
        {
            if(!data.has(scentKey, PersistentDataType.INTEGER))
                data.set(scentKey, PersistentDataType.INTEGER, 0);

            data.set(scentKey, PersistentDataType.INTEGER, data.get(scentKey, PersistentDataType.INTEGER) - 1);
        }

        if(getEssence(item) >= 10)
        {
            if(!data.has(tridentKey, PersistentDataType.INTEGER))
                data.set(tridentKey, PersistentDataType.INTEGER, 0);

            int tridentCooldown = data.get(tridentKey, PersistentDataType.INTEGER);
            data.set(tridentKey, PersistentDataType.INTEGER, tridentCooldown - 1);

            if(tridentCooldown <= 0)
            {
                data.set(tridentKey, PersistentDataType.INTEGER, 6000);
                ItemStack trident = new ItemStack(Material.TRIDENT);
                trident.addUnsafeEnchantment(Enchantment.RIPTIDE, 5);
                trident.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
                ItemMeta tridentMeta = trident.getItemMeta();
                tridentMeta.setDisplayName("§x§D§4§0§0§0§0§lApex Predator");
                ((Damageable)tridentMeta).setDamage(246);
                trident.setItemMeta(tridentMeta);

                for(int i = 0; i < 36; i++)
                {
                    ItemStack _trident = player.getInventory().getItem(i);
                    if(_trident != null && _trident.hasItemMeta() && _trident.getItemMeta().getDisplayName().equals(tridentMeta.getDisplayName()))
                    {
                        player.getInventory().clear(i);
                    }
                }

                player.getInventory().addItem(trident);
            }
        }
    }

    @EventHandler
    public void bloodScent(EntityDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player)event.getEntity();

        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p != player)
            {
                for(ItemStack item : DisplayInformation.playerStonesMap.get(p))
                if(checkItem(item, itemName) && p.getPersistentDataContainer().get(scentKey, PersistentDataType.INTEGER) <= 0 && getEssence(item) >= 2)
                {
                    p.sendMessage(ChatColor.RED + String.format("BLOOD SCENT: (" + player.getName() + ") X %.1f | Y %.1f | Z %.1f", player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 0));
                    p.getPersistentDataContainer().set(scentKey, PersistentDataType.INTEGER, 200);
                }
            }
        }
    }

    @EventHandler
    private void bigChomp(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            Outback.tasks.add(new BigChompTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }
}
