package me.humandavey.knockbackffa.manager;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.util.Util;
import me.humandavey.knockbackffa.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryManager {

	public static void saveSelection(Player player) {
		ItemStack[] allItems = player.getOpenInventory().getTopInventory().getContents();
		ItemStack[] items = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			items[i] = allItems[i];
		}

		String out = "";
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) continue;
			switch (items[i].getType()) {
				case STICK -> {
					out += "stick:" + i + ";";
				}
				case BOW -> {
					out += "bow:" + i + ";";
				}
				case ENDER_PEARL -> {
					out += "pearl:" + i + ";";
				}
				case ARROW -> {
					out += "arrows:" + i + ";";
				}
				default -> {
					out += items[i].getType().name() + ":" + i + ";";
				}
			}
		}
		out = out.substring(0, out.length() - 1);

		KnockbackFFA.getInstance().getConfig().set("inventories." + player.getUniqueId(), out);
		KnockbackFFA.getInstance().saveConfig();
	}

	public static void giveSelection(Player player) {
		if (hasCustomSelection(player)) {
			String[] items = getCustomSelection(player).split(";");
			for (String s : items) {
				String[] item = s.split(":");
				switch (item[0]) {
					case "stick" -> {
						player.getInventory().setItem(Integer.parseInt(item[1]), new ItemBuilder(Material.STICK)
								.addEnchantment(Enchantment.KNOCKBACK, 2)
								.setItemName(Util.colorize("&eKnockback Stick"))
								.build());
					}

					case "pearl" -> {
						player.getInventory().setItem(Integer.parseInt(item[1]), new ItemBuilder(Material.ENDER_PEARL)
								.build());
					}
					case "bow" -> {
						player.getInventory().setItem(Integer.parseInt(item[1]), new ItemBuilder(Material.BOW)
								.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2)
								.setItemName(Util.colorize("&6Bow"))
								.build());
					}
					case "arrows" -> {
						player.getInventory().setItem(Integer.parseInt(item[1]), new ItemStack(Material.ARROW, 10));
					}
					default -> {
						if (isABuildingBlock(item[0])) {
							player.getInventory().setItem(Integer.parseInt(item[1]), new ItemStack(Material.getMaterial(item[0]), 64));
						}
					}
				}
			}
		} else {
			player.getInventory().addItem(new ItemBuilder(Material.STICK)
					.addEnchantment(Enchantment.KNOCKBACK, 1)
					.setItemName(Util.colorize("&eKnockback Stick"))
					.build());

			player.getInventory().addItem(new ItemBuilder(Material.BOW)
					.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
					.setItemName(Util.colorize("&6Bow"))
					.build());

			player.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL)
					.build());

			player.getInventory().addItem(new ItemStack(Material.SANDSTONE, 64));

			player.getInventory().addItem(new ItemStack(Material.ARROW, 10));

			KnockbackFFA.getInstance().getConfig().set("inventories." + player.getUniqueId(), "stick:0;bow:1;pearl:2;SANDSTONE:3;arrows:3");
			KnockbackFFA.getInstance().saveConfig();
		}
	}

	private static boolean hasCustomSelection(Player player) {
		return KnockbackFFA.getInstance().getConfig().contains("inventories." + player.getUniqueId());
	}

	private static String getCustomSelection(Player player) {
		return KnockbackFFA.getInstance().getConfig().getString("inventories." + player.getUniqueId());
	}

	public static boolean isABuildingBlock(String material) {
		if (Material.getMaterial(material) == null) return false;
		return isABuildingBlock(Material.getMaterial(material));
	}

	private static boolean isABuildingBlock(Material material) {
		ArrayList<String> mats = new ArrayList<>(KnockbackFFA.getInstance().getConfig().getStringList("config.building-blocks"));
		ArrayList<Material> valid = new ArrayList<>();

		for (String s : mats) {
			if (Material.getMaterial(s) != null) {
				valid.add(Material.getMaterial(s));
			}
		}
		return valid.contains(material) && material.isBlock();
	}
}