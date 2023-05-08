package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		KnockbackFFA.getInstance().getMapManager().removePlayer(event.getPlayer());
	}
}
