package com.rooxchicken.outback.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.rooxchicken.outback.Outback;
import com.rooxchicken.outback.Stones.Crocodile;
import com.rooxchicken.outback.Stones.Dragonfly;
import com.rooxchicken.outback.Stones.Echidna;
import com.rooxchicken.outback.Stones.GreatWhiteShark;
import com.rooxchicken.outback.Stones.Koala;
import com.rooxchicken.outback.Stones.Platypus;
import com.rooxchicken.outback.Stones.Possum;
import com.rooxchicken.outback.Stones.Quokka;
import com.rooxchicken.outback.Stones.Stone;
import com.rooxchicken.outback.Stones.SugarGlider;
import com.rooxchicken.outback.Stones.TasmanianDevil;

public class RecipeManager implements Listener
{
    private Outback plugin;
    private NamespacedKey randomKey;

    private ShapedRecipe essence;
    private ShapedRecipe stone;
    private ShapedRecipe sugarGlider;
    private ShapedRecipe possum;
    private ShapedRecipe koala;
    private ShapedRecipe tasmanianDevil;
    private ShapedRecipe quokka;
    private ShapedRecipe greatWhiteShark;
    private ShapedRecipe dragonfly;
    private ShapedRecipe crocodile;
    private ShapedRecipe echidna;
    private ShapedRecipe platypus;

    public RecipeManager(Outback _plugin)
    {
        plugin = _plugin;

        randomKey = new NamespacedKey(_plugin, "randomID");

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void loadRecipes()
    {
        ArrayList<String> lore = Stone.baseLore();
        lore.add("§x§7§6§6§A§7§4§lEssence: 0");
        
        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
    
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7§lEssence");
            item.setItemMeta(meta);
    
            NamespacedKey key = new NamespacedKey(plugin, "essenceRecipe");
            essence = new ShapedRecipe(key, item);
            essence.shape("nTn", "uAu", "DrD");
    
            essence.setIngredient('D', Material.DIAMOND_BLOCK);
            essence.setIngredient('n', Material.NETHERITE_SCRAP);
            essence.setIngredient('u', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
            essence.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            essence.setIngredient('r', Material.RESPAWN_ANCHOR);
            essence.setIngredient('T', Material.TOTEM_OF_UNDYING);
    
            Bukkit.addRecipe(essence);
        }

        {
            ItemStack item = new ItemStack(Material.POPPED_CHORUS_FRUIT);
    
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§5§lAnimal Core");
            item.setItemMeta(meta);
    
            NamespacedKey key = new NamespacedKey(plugin, "animalCoreRecipe");
            stone = new ShapedRecipe(key, item);
            stone.shape("abc", "def", "ghi");
    
            stone.setIngredient('a', Material.SCUTE);
            stone.setIngredient('b', Material.PHANTOM_MEMBRANE);
            stone.setIngredient('c', Material.RABBIT_HIDE);
            stone.setIngredient('d', Material.SLIME_BALL);
            stone.setIngredient('e', Material.END_CRYSTAL);
            stone.setIngredient('f', Material.NAUTILUS_SHELL);
            stone.setIngredient('g', Material.PRISMARINE_CRYSTALS);
            stone.setIngredient('h', Material.PUFFERFISH);
            stone.setIngredient('i', Material.HONEYCOMB);
    
            Bukkit.addRecipe(stone);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(SugarGlider.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "sugarGliderRecipe");
            sugarGlider = new ShapedRecipe(key, item);
            sugarGlider.shape("aba", "dAd", "ghg");
    
            sugarGlider.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            sugarGlider.setIngredient('a', Material.LEATHER);
            sugarGlider.setIngredient('b', Material.SUGAR);
            sugarGlider.setIngredient('d', Material.FEATHER);
            sugarGlider.setIngredient('g', Material.PHANTOM_MEMBRANE);
            sugarGlider.setIngredient('h', Material.HONEY_BOTTLE);
    
            Bukkit.addRecipe(sugarGlider);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Possum.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "possumRecipe");
            possum = new ShapedRecipe(key, item);
            possum.shape("aba", "dAd", "ghg");
    
            possum.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            possum.setIngredient('a', Material.ENDER_EYE);
            possum.setIngredient('b', Material.SUGAR_CANE);
            possum.setIngredient('d', Material.CHAIN);
            possum.setIngredient('g', Material.SLIME_BLOCK);
            possum.setIngredient('h', Material.LEAD);
    
            Bukkit.addRecipe(possum);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Koala.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "koalaRecipe");
            koala = new ShapedRecipe(key, item);
            koala.shape("abc", "dAf", "ghg");
    
            koala.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            koala.setIngredient('a', Material.AZALEA_LEAVES);
            koala.setIngredient('b', Material.CHERRY_LEAVES);
            koala.setIngredient('c', Material.MANGROVE_LEAVES);
            koala.setIngredient('d', Material.OAK_LEAVES);
            koala.setIngredient('f', Material.DARK_OAK_LEAVES);
            koala.setIngredient('g', Material.DIAMOND_SWORD);
            koala.setIngredient('h', Material.DIAMOND_CHESTPLATE);
    
            Bukkit.addRecipe(koala);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(TasmanianDevil.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "devilRecipe");
            tasmanianDevil = new ShapedRecipe(key, item);
            tasmanianDevil.shape("aba", "dAd", "ghg");
    
            tasmanianDevil.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            tasmanianDevil.setIngredient('a', Material.OBSIDIAN);
            tasmanianDevil.setIngredient('b', Material.BEEF);
            tasmanianDevil.setIngredient('d', Material.RABBIT_HIDE);
            tasmanianDevil.setIngredient('g', Material.IRON_SWORD);
            tasmanianDevil.setIngredient('h', Material.NETHERITE_SWORD);
    
            Bukkit.addRecipe(tasmanianDevil);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Quokka.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "quokkaRecipe");
            quokka = new ShapedRecipe(key, item);
            quokka.shape("aba", "dAd", "ghg");
    
            quokka.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            quokka.setIngredient('a', Material.GOLDEN_CARROT);
            quokka.setIngredient('b', Material.DIAMOND_BLOCK);
            quokka.setIngredient('d', Material.RABBIT_HIDE);
            quokka.setIngredient('g', Material.RABBIT_FOOT);
            quokka.setIngredient('h', Material.GOLDEN_APPLE);
    
            Bukkit.addRecipe(quokka);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(GreatWhiteShark.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "sharkRecipe");
            greatWhiteShark = new ShapedRecipe(key, item);
            greatWhiteShark.shape("aba", "dAf", "ghe");
    
            greatWhiteShark.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            greatWhiteShark.setIngredient('a', Material.PRISMARINE_CRYSTALS);
            greatWhiteShark.setIngredient('b', Material.TRIDENT);
            greatWhiteShark.setIngredient('d', Material.TROPICAL_FISH);
            greatWhiteShark.setIngredient('f', Material.SALMON);
            greatWhiteShark.setIngredient('g', Material.COD);
            greatWhiteShark.setIngredient('h', Material.POINTED_DRIPSTONE);
            greatWhiteShark.setIngredient('e', Material.PUFFERFISH);
    
            Bukkit.addRecipe(greatWhiteShark);
        }

        //no dragonfly recipe ._.

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Crocodile.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "crocodileRecipe");
            crocodile = new ShapedRecipe(key, item);
            crocodile.shape("aaa", "dAd", "ghg");
    
            crocodile.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            crocodile.setIngredient('a', Material.SCUTE);
            crocodile.setIngredient('d', Material.WATER_BUCKET);
            crocodile.setIngredient('g', Material.POINTED_DRIPSTONE);
            crocodile.setIngredient('h', Material.SNIFFER_EGG);
    
            Bukkit.addRecipe(crocodile);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Echidna.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "echidnaRecipe");
            echidna = new ShapedRecipe(key, item);
            echidna.shape("aba", "bAb", "ghg");
    
            echidna.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            echidna.setIngredient('a', Material.DIAMOND_SWORD);
            echidna.setIngredient('b', Material.POINTED_DRIPSTONE);
            echidna.setIngredient('g', Material.STICK);
            echidna.setIngredient('h', Material.NETHERITE_PICKAXE);
    
            Bukkit.addRecipe(echidna);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Platypus.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "platypusRecipe");
            platypus = new ShapedRecipe(key, item);
            platypus.shape("aba", "dAd", "ghg");
    
            platypus.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            platypus.setIngredient('a', Material.DIAMOND_PICKAXE);
            platypus.setIngredient('b', Material.POISONOUS_POTATO);
            platypus.setIngredient('d', Material.LILY_PAD);
            platypus.setIngredient('g', Material.BRUSH);
            platypus.setIngredient('h', Material.TURTLE_EGG);
    
            Bukkit.addRecipe(platypus);
        }

        {
            ItemStack item = new ItemStack(Material.GRAY_DYE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Dragonfly.itemName);
            itemMeta.setLore(lore);
            itemMeta.getPersistentDataContainer().set(randomKey, PersistentDataType.STRING, String.valueOf(Math.random()));
            item.setItemMeta(itemMeta);
    
            NamespacedKey key = new NamespacedKey(plugin, "dragonflyRecipe");
            dragonfly = new ShapedRecipe(key, item);
            dragonfly.shape("AaA", "dbd", "ghg");
    
            dragonfly.setIngredient('A', Material.POPPED_CHORUS_FRUIT);
            dragonfly.setIngredient('a', Material.DRAGON_HEAD);
            dragonfly.setIngredient('b', Material.DRAGON_EGG);
            dragonfly.setIngredient('d', Material.ELYTRA);
            dragonfly.setIngredient('g', Material.NETHER_STAR);
            dragonfly.setIngredient('h', Material.NETHERITE_BLOCK);
    
            Bukkit.addRecipe(dragonfly);
        }
    }

    @EventHandler
    private void starsInCraft(PrepareItemCraftEvent event)
    {
        if(event.getRecipe() == null)
            return;

        ItemStack core = stone.getResult();

        if(!event.getRecipe().getResult().hasItemMeta())
            return;

        if(event.getRecipe().getResult().getItemMeta().getDisplayName().contains("§l"))
        {
            for(ItemStack item : event.getInventory())
            {
                if(item != null && item.getType().equals(Material.POPPED_CHORUS_FRUIT))
                {
                    if(!(item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(core.getItemMeta().getDisplayName())))
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                }
            }
        }
    }
}
