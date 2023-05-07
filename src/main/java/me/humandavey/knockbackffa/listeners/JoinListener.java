package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.setFallDistance(0);
		player.teleport(KnockbackFFA.getInstance().getMapManager().getCurrentMap().getSpawn());
	}
}
