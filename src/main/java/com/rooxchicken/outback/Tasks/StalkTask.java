package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Possum;

public class StalkTask extends Task
{
    private Player player;
    private ArrayList<Player> players;
    private int t = 0;

    private ProtocolManager protocolManager;
    private PacketContainer packet;

    private PacketAdapter packetAdapter;

    public StalkTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        players = new ArrayList<Player>();

        for(Object o : Library.getNearbyEntities(player.getLocation(), 20))
        {
            if(o instanceof Player)
            {
                Player p = (Player)o;
                ItemStack item = p.getInventory().getItemInMainHand();
                if((item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(Possum.itemName)) || p == player)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0));
                    players.add(p);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));

                    p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                    player.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 200, 1, 0.2, 1, new Particle.DustOptions(Color.GREEN, 1f));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0));

                    p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);

                    player.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 200, 1, 0.2, 1, new Particle.DustOptions(Color.RED, 1f));
                }
            }
        }

        packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());
        
        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, new ItemStack(Material.AIR)));
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, new ItemStack(Material.AIR)));
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, new ItemStack(Material.AIR)));
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, new ItemStack(Material.AIR)));
        list.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, new ItemStack(Material.AIR)));
        list.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, new ItemStack(Material.AIR)));
        packet.getSlotStackPairLists().write(0, list);

        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(!players.contains(p))
                protocolManager.sendServerPacket(p, packet);
        }

        packetAdapter = new PacketAdapter(_plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EQUIPMENT)
        {
            @Override
            public void onPacketSending(PacketEvent event)
            {
                if(event.getPacket().getIntegers().read(0) == player.getEntityId())
                    event.setCancelled(true);
            }
        };

        protocolManager.addPacketListener(packetAdapter);

        tickThreshold = 20;
    }

    @Override
    public void run()
    {
        if(++t > 14)
            cancel = true;
    }

    @Override
    public void onCancel()
    {
        protocolManager.removePacketListener(packetAdapter);

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, player.getInventory().getHelmet()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, player.getInventory().getChestplate()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, player.getInventory().getLeggings()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, player.getInventory().getBoots()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, player.getInventory().getItemInMainHand()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, player.getInventory().getItemInOffHand()));
        packet.getSlotStackPairLists().write(0, list);

        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p != player)
                protocolManager.sendServerPacket(p, packet);
        }
    }
}
