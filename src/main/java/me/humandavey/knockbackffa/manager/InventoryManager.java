package me.humandavey.knockbackffa.manager;

import me.humandavey.knockbackffa.util.Util;
import me.humandavey.knockbackffa.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

	public static void giveItems(Player player) {
		player.getInventory().addItem(new ItemBuilder(Material.STICK)
				.addEnchantment(Enchantment.KNOCKBACK, 1)
				.setItemName(Util.colorize("&eKnockback Stick"))
				.build());

		player.getInventory().addItem(new ItemBuilder(Material.BOW)
				.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
				.setItemName(Util.colorize("&6Bow"))
				.build());

		player.getInventory().addItem(new ItemStack(Material.SANDSTONE, 64));

		player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
	}
}
