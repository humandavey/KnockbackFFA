package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.getTo() != event.getFrom()) {
			Player player = event.getPlayer();
			if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(player)) {
				KnockbackMap map = KnockbackFFA.getInstance().getMapManager().getCurrentMap();
				if (map.isLocationInBounds(player.getLocation())) {
					if (event.getTo().getY() < map.getSpawn().getY() - 5 && !player.getInventory().contains(Material.STICK)) {
						player.getInventory().addItem(new ItemBuilder(Material.STICK).addEnchantment(Enchantment.KNOCKBACK, 2).build());
						player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 2));
						player.getInventory().addItem(new ItemStack(Material.SANDSTONE, 64));
					} else if (event.getTo().getY() > map.getSpawn().getY() - 5 && player.getInventory().contains(Material.STICK)) {
						player.getInventory().clear();
					}
					if (event.getTo().getY() < Math.min(map.getPos1().getY(), map.getPos2().getY())) {
						player.setHealth(20);
						player.setFoodLevel(20);
						player.getInventory().clear();
						player.setFallDistance(0);
						player.teleport(KnockbackFFA.getInstance().getMapManager().getCurrentMap().getSpawn());
					}
				}
			}
		}
	}
}
