package com.rooxchicken.outback;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import com.rooxchicken.outback.Commands.ResetCooldown;
import com.rooxchicken.outback.Tasks.Task;

public class Outback extends JavaPlugin implements Listener
{
    public static NamespacedKey essenceKey;

    public static ArrayList<Task> tasks;
    private List<String> blockedCommands = new ArrayList<>();

    @Override
    public void onEnable()
    {
        Bukkit.resetRecipes();
        //ProtocolLibrary.getProtocolManager().removePacketListeners(this);

        tasks = new ArrayList<Task>();
    
        essenceKey = new NamespacedKey(this, "essence");
        
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("resetcooldown").setExecutor(new ResetCooldown(this));

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            public void run()
            {
                ArrayList<Task> _tasks = new ArrayList<Task>();
                for(Task t : tasks)
                    _tasks.add(t);
                
                ArrayList<Task> toRemove = new ArrayList<Task>();

                for(Task t : _tasks)
                {
                    t.tick();

                    if(t.cancel)
                        toRemove.add(t);
                }

                for(Task t : toRemove)
                {
                    t.onCancel();
                    tasks.remove(t);
                }
            }
        }, 0, 1);

        for(Player player : Bukkit.getOnlinePlayers())
            checkHasEssence(player);

        getLogger().info("Orbiting since 1987 (made by roo)");
    }

    @EventHandler
    public void addPlayerOrbit(PlayerJoinEvent event)
    {
        checkHasEssence(event.getPlayer());
    }

    @EventHandler
    public void stealEssence(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        Entity killer = event.getEntity().getKiller();
        if(killer != null)
        {
            //killer.getPersistentDataContainer().set(killsKey, PersistentDataType.INTEGER, killer.getPersistentDataContainer().get(killsKey, PersistentDataType.INTEGER) + 1);
        }
    }

    @EventHandler
	private void onPlayerTab(PlayerCommandSendEvent e)
    {
		e.getCommands().removeAll(blockedCommands);
	}

    private void checkHasEssence(Player player)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(!data.has(essenceKey, PersistentDataType.INTEGER))
            data.set(essenceKey, PersistentDataType.INTEGER, 0);
    }
}
