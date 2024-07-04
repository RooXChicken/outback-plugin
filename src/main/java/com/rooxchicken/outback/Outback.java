package com.rooxchicken.outback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.rooxchicken.outback.Commands.AddEssence;
import com.rooxchicken.outback.Commands.GiveItems;
import com.rooxchicken.outback.Commands.PrintHeldItemCommand;
import com.rooxchicken.outback.Commands.ResetCooldown;
import com.rooxchicken.outback.Commands.SetEssence;
import com.rooxchicken.outback.Events.RecipeManager;
import com.rooxchicken.outback.Stones.Dragonfly;
import com.rooxchicken.outback.Stones.GreatWhiteShark;
import com.rooxchicken.outback.Stones.Koala;
import com.rooxchicken.outback.Stones.Possum;
import com.rooxchicken.outback.Stones.Quokka;
import com.rooxchicken.outback.Stones.Stone;
import com.rooxchicken.outback.Stones.SugarGlider;
import com.rooxchicken.outback.Stones.TasmanianDevil;
import com.rooxchicken.outback.Tasks.DisplayInformation;
import com.rooxchicken.outback.Tasks.Task;

public class Outback extends JavaPlugin implements Listener
{
    public static NamespacedKey essenceKey;

    public static ArrayList<Task> tasks;

    private SugarGlider sugarGlider;
    private Possum possum;
    private Koala koala;
    private TasmanianDevil tasmanianDevil;
    private Quokka quokka;
    private GreatWhiteShark greatWhiteShark;
    private Dragonfly dragonfly;

    private RecipeManager recipeManager;

    @Override
    public void onEnable()
    {
        ArrayList<String> result = new ArrayList<String>();
        try
        {
            URL pasteURL = new URL("https://pastebin.com/raw/Yj09afnY");
				URLConnection http = pasteURL.openConnection();
				BufferedReader in = new BufferedReader(
										new InputStreamReader(
											http.getInputStream()));

            String line = "";
            while ((line = in.readLine()) != null) 
                result.add(line);

            in.close();
        }
        catch(Exception e) { /* no error handing haha */ }

        
        if(!result.get(0).equals("ROO-APPROVED :3"))
        {
            HandlerList.unregisterAll(Bukkit.getPluginManager().getPlugin(getName()));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getLogger().info(result.get(0));

        Bukkit.resetRecipes();
        recipeManager = new RecipeManager(this);

        tasks = new ArrayList<Task>();
        tasks.add(new DisplayInformation(this));

        sugarGlider = new SugarGlider(this);
        possum = new Possum(this);
        koala = new Koala(this);
        tasmanianDevil = new TasmanianDevil(this);
        quokka = new Quokka(this);
        greatWhiteShark = new GreatWhiteShark(this);
        dragonfly = new Dragonfly(this);
    
        essenceKey = new NamespacedKey(this, "essence");
        
        getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("resetcooldown").setExecutor(new ResetCooldown(this));
        this.getCommand("printname").setExecutor(new PrintHeldItemCommand(this));
        this.getCommand("giveitems").setExecutor(new GiveItems(this));

        this.getCommand("addessence").setExecutor(new AddEssence(this));
        this.getCommand("setessence").setExecutor(new SetEssence(this));

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
            checkEssenceExists(player);

        recipeManager.loadRecipes();

        getLogger().info("Up to 1987 Australian animals! (made by roo)");
    }

    public Stone getStoneFromName(String name)
    {
        switch(name)
        {
            case SugarGlider.itemName: return sugarGlider;
            case Possum.itemName: return possum;
            case Koala.itemName: return koala;
            case TasmanianDevil.itemName: return tasmanianDevil;
            case Quokka.itemName: return quokka;
            case GreatWhiteShark.itemName: return greatWhiteShark;
            case Dragonfly.itemName: return dragonfly;
        }

        return null;
    }

    public String getAbilityFromName(String name)
    {
        switch(name)
        {
            case SugarGlider.itemName: return sugarGlider.name;
            case Possum.itemName: return possum.name;
            case Koala.itemName: return koala.name;
            case TasmanianDevil.itemName: return tasmanianDevil.name;
            case Quokka.itemName: return quokka.name;
            case GreatWhiteShark.itemName: return greatWhiteShark.name;
            case Dragonfly.itemName: return dragonfly.name;
        }

        return null;
    }

    @EventHandler
    public void checkHasEssence(PlayerJoinEvent event)
    {
        checkEssenceExists(event.getPlayer());
    }

    @EventHandler
    public void stealEssence(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        Entity killer = event.getEntity().getKiller();
        if(killer != null)
        {
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            PersistentDataContainer killerData = killer.getPersistentDataContainer();
            int killerEssence = killerData.get(essenceKey, PersistentDataType.INTEGER);
            int playerEssence = playerData.get(essenceKey, PersistentDataType.INTEGER);

            if(playerEssence == 0)
                return;

            if(Math.random() <= 0.45)
                playerEssence -= 1;
            else if(Math.random() <= 0.80)
                playerEssence -= 2;
            else if(Math.random() <= 1)
                playerEssence -= 3;

            if(Math.random() <= 0.40)
                killerEssence += 1;
            else if(Math.random() <= 0.75)
                killerEssence += 2;
            else if(Math.random() <= 1)
                killerEssence += 3;

            playerEssence = Math.max(playerEssence, 0);
            killerEssence = Math.min(killerEssence, 20);

            playerData.set(essenceKey, PersistentDataType.INTEGER, playerEssence);
            killerData.set(essenceKey, PersistentDataType.INTEGER, killerEssence);
        }
    }

    public int getEssence(Player player)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        return data.get(essenceKey, PersistentDataType.INTEGER);
    }

    public void setEssence(Player player, int essence)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        data.set(essenceKey, PersistentDataType.INTEGER, essence);
    }

    private void checkEssenceExists(Player player)
    {
        PersistentDataContainer data = player.getPersistentDataContainer();

        if(!data.has(essenceKey, PersistentDataType.INTEGER))
            data.set(essenceKey, PersistentDataType.INTEGER, 0);
    }
}
