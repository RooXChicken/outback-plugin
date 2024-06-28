package com.rooxchicken.outback.Stones;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.ParachuteTask;
import com.rooxchicken.outback.Tasks.TorporTask;

public class SugarGlider extends Stone
{
    private Outback plugin;
    private HashMap<Player, ItemStack> playerChestplateMap;
    private int soundMix = 0;

    public static String itemName = "§x§F§F§6§E§0§D§lSugar Glider";

    public SugarGlider(Outback _plugin)
    {
        super(_plugin);
        plugin = _plugin;

        name = "§x§F§F§6§E§0§D§lTorpor";

        playerChestplateMap = new HashMap<Player, ItemStack>();

        cooldownKey = new NamespacedKey(plugin, "sugarGlider");
        cooldownMax = 60*20;
    }

    @Override
    public void tick()
    {
        
    }

    @Override
    public void playerTickLogic(Player player, ItemStack item)
    {
        if(!(getEssence(item) >= 10))
            return;
        Location direction = player.getLocation().clone();
        direction.setPitch(0);

        if(player.getEyeLocation().add(direction.getDirection().multiply(0.35)).getBlock().getType().isSolid())
        {
            if(player.isSneaking() && !player.isOnGround())
            {
                if(!playerChestplateMap.containsKey(player))
                    playerChestplateMap.put(player, player.getInventory().getChestplate());

                if(soundMix++ % 4 == 0)
                {
                    player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().clone().add(0, 1, 0), 20, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 1, 1);
                }
                
                player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
                player.setVelocity(player.getVelocity().add(new Vector(0, 0.1, 0)));
            }
        }

        if(playerChestplateMap.containsKey(player) && player.isOnGround())
        {
            soundMix = 0;
            player.getInventory().setChestplate(playerChestplateMap.get(player));
            playerChestplateMap.remove(player);
        }
    }

    @EventHandler
    public void preventChestplateSwitching(InventoryClickEvent event)
    {
        if(playerChestplateMap.containsKey(event.getInventory().getViewers().get(0)) && event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.ELYTRA))
            event.setCancelled(true);
    }

    @EventHandler
    public void snatch(EntityDamageByEntityEvent event)
    {
        if(!(event.getDamager() instanceof Player))
            return;

        Entity entity = event.getEntity();
        Player damager = (Player)event.getDamager();

        for(ItemStack item : DisplayInformation.playerStonesMap.get(damager))
        {
            if(checkItem(item, itemName) && getEssence(item) >= 2)
            {
                if(!entity.isOnGround())
                {
                    damager.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().clone().add(0, 1, 0), 40, 0.2, 0.2, 0.2, new Particle.DustOptions(Color.fromRGB(0x888888), 1f));

                    damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_BASALT_BREAK, 1, 1);
                    event.setDamage(event.getDamage() + 2);
                    entity.setVelocity(damager.getLocation().getDirection().multiply(-0.5));
                }
            }
        }
    }

    @EventHandler
    private void torpor(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getOffHandItem();

        if(!player.isSneaking())
            return;

        if(checkItem(item, itemName) && getEssence(item) >= 5 && checkCooldown(player, cooldownKey, cooldownMax))
        {
            Outback.tasks.add(new TorporTask(plugin, player));
            event.setCancelled(true);
        }
    }

    @Override
    public String getItemName() { return itemName; }

    @Override
    public void implode(Player player)
    {
        Outback.tasks.add(new ParachuteTask(plugin, player));
    }
}
