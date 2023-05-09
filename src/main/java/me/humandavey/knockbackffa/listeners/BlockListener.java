package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(event.getPlayer()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.getBlockPlaced().setType(Material.REDSTONE_BLOCK);
				}
			}.runTaskLater(KnockbackFFA.getInstance(), 20 * 3);
			new BukkitRunnable() {
				@Override
				public void run() {
					event.getBlockPlaced().setType(Material.AIR);
				}
			}.runTaskLater(KnockbackFFA.getInstance(), 20 * 5);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(event.getPlayer()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(event.getPlayer()) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent event) {
		if (KnockbackFFA.getInstance().getMapManager().isPlaying((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}
}
