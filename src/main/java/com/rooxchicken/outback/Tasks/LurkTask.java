package com.rooxchicken.outback.Tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.rooxchicken.outback.Library;
import com.rooxchicken.outback.Outback;

public class LurkTask extends Task
{
    private Player player;
    private int t = 0;

    private AreaEffectCloud cloud;

    private Location entrance;

    private ProtocolManager protocolManager;
    private PacketContainer packet;

    private PacketAdapter packetAdapter;

    public LurkTask(Outback _plugin, Player _player)
    {
        super(_plugin);

        player = _player;
        protocolManager = ProtocolLibrary.getProtocolManager();

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0));

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
            if(p != player)
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


        spawnEffect(player.getLocation());
        playSound();

        tickThreshold = 10;
    }

    private void spawnEffect(Location loc)
    {
        double blockY = Library.getBlock(player, 40, 90).getLocation().getY() + 1.1;
        entrance = loc.clone();
        entrance.setY(blockY);

        cloud = (AreaEffectCloud) player.getWorld().spawnEntity(entrance, EntityType.AREA_EFFECT_CLOUD);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 200, 0), true);

        player.getWorld().spawnParticle(Particle.REDSTONE, entrance, 400, 1, 0.2, 1, new Particle.DustOptions(Color.fromRGB(0x882222), 1f));
    }

    private void playSound()
    {
        player.getWorld().playSound(entrance, Sound.BLOCK_ROOTED_DIRT_BREAK, 1, 1);
    }

    @Override
    public void run()
    {
        playSound();

        player.getWorld().spawnParticle(Particle.REDSTONE, entrance, 400, 1, 0.2, 1, new Particle.DustOptions(Color.fromRGB(0x882222), 1f));
        
        if(t == 19)
        {
            cloud.remove();
            spawnEffect(player.getLocation());
            playSound();
        }

        if(++t > 39)
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
        
        cloud.remove();
    }
}
