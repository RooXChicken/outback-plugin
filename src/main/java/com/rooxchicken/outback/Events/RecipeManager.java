package com.rooxchicken.outback.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.rooxchicken.outback.Outback;

public class RecipeManager
{
    private Outback plugin;

    private ShapedRecipe essence;
    private ShapedRecipe stone;
    private ShapedRecipe sugarGlider;
    private ShapedRecipe possum;
    private ShapedRecipe koala;
    private ShapedRecipe tasmanianDevil;
    private ShapedRecipe quokka;
    private ShapedRecipe greatWhiteShark;
    private ShapedRecipe dragonfly;

    public RecipeManager(Outback _plugin)
    {
        plugin = _plugin;
    }

    public void loadRecipes()
    {
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
            ItemStack item = new ItemStack(Material.GREEN_DYE);
    
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
    }
}
