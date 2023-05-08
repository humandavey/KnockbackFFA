package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.manager.InventoryManager;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.getTo() != event.getFrom()) {
			Player player = event.getPlayer();
			if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(player)) {
				KnockbackMap map = KnockbackFFA.getInstance().getMapManager().getCurrentMap();
				if (map.isLocationInBounds(player.getLocation())) {
					if (event.getTo().getY() < map.getSpawn().getY() - 5 && !player.getInventory().contains(Material.STICK)) {
						Util.resetPlayer(player, true);
						InventoryManager.giveSelection(player);
					} else if (event.getTo().getY() > map.getSpawn().getY() - 5 && player.getInventory().contains(Material.STICK)) {
						player.getInventory().clear();
					}
					if (event.getTo().getY() < Math.min(map.getPos1().getY(), map.getPos2().getY())) {
						Util.resetPlayer(player, true);
						player.teleport(KnockbackFFA.getInstance().getMapManager().getCurrentMap().getSpawn());
					}
				}
			}
		}
	}
}
