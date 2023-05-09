package me.humandavey.knockbackffa.listeners;

import me.humandavey.knockbackffa.KnockbackFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player victim) {
			if (KnockbackFFA.getInstance().getMapManager().isInCurrentMap(victim)) {
				event.setDamage(0);
				if (victim.getLocation().getY() > KnockbackFFA.getInstance().getMapManager().getCurrentMap().getSpawn().getY() - 5) {
					event.setCancelled(true);
				}
			}
		}
	}
}
