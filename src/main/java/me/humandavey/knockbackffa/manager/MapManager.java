package me.humandavey.knockbackffa.manager;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class MapManager {

	private ArrayList<Player> players = new ArrayList<>();
	private KnockbackMap currentMap;

	private final ArrayList<KnockbackMap> availableMaps = new ArrayList<>();
	private final ArrayList<KnockbackMap> unavailableMaps = new ArrayList<>();

	public MapManager() {
		FileConfiguration config = KnockbackFFA.getInstance().getConfig();
		for (String section : config.getConfigurationSection("data").getKeys(false)) {
			if (config.getConfigurationSection("data." + section + ".spawn") != null
					&& config.getConfigurationSection("data." + section + ".corner-1") != null
					&& config.getConfigurationSection("data." + section + ".corner-2") != null) {
				KnockbackMap map = new KnockbackMap(section,
						Util.configToLocation(config, "data." + section + ".spawn"),
						Util.configToLocation(config, "data." + section + ".corner-1"),
						Util.configToLocation(config, "data." + section + ".corner-2")
				);

				unavailableMaps.add(map);
			} else if (config.getConfigurationSection("data." + section + ".spawn") != null) {
				KnockbackMap map = new KnockbackMap(section,
						Util.configToLocation(config, "data." + section + ".spawn")
				);

				unavailableMaps.add(map);
			}
		}

		updateMaps();

		if (availableMaps.size() > 0) {
			setCurrentMap(availableMaps.get(0));
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				if (availableMaps.indexOf(currentMap) + 1 > availableMaps.size() - 1) {
					setCurrentMap(availableMaps.get(0));
					return;
				}
				setCurrentMap(availableMaps.get(availableMaps.indexOf(currentMap) + 1));
			}
		}.runTaskTimer(KnockbackFFA.getInstance(), (20 * 60) * KnockbackFFA.getInstance().getConfig().getInt("config.map-rotation-minutes"),(20 * 60) * KnockbackFFA.getInstance().getConfig().getInt("config.map-rotation-minutes"));
	}

	public boolean isInCurrentMap(Player player) {
		return currentMap.isLocationInBounds(player.getLocation());
	}

	public boolean isMapNameTaken(String name) {
		for (KnockbackMap map : availableMaps) {
			if (map.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		for (KnockbackMap map : unavailableMaps) {
			if (map.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void deleteMap(String name) {
		KnockbackMap map = getMap(name);
		if (map == null) return;

		KnockbackFFA.getInstance().getConfig().set("data." + map.getName(), null);
		KnockbackFFA.getInstance().saveConfig();
	}

	public void updateMaps() {
		for (int i = unavailableMaps.size() - 1; i >= 0; i--) {
			if (unavailableMaps.get(i).isMapReady()) {
				availableMaps.add(unavailableMaps.get(i));
				unavailableMaps.remove(i);
			}
		}
		for (int i = availableMaps.size() - 1; i >= 0; i--) {
			if (!availableMaps.get(i).isMapReady()) {
				unavailableMaps.add(availableMaps.get(i));
				availableMaps.remove(i);
			}
		}
	}

	public void setCurrentMap(KnockbackMap currentMap) {
		this.currentMap = currentMap;

		for (Player player : players) {
			player.setHealth(20);
			player.setFoodLevel(0);
			player.setFallDistance(0);
			player.teleport(currentMap.getSpawn());
		}
	}

	public KnockbackMap getMap(String name) {
		for (KnockbackMap map : availableMaps) {
			if (map.getName().equalsIgnoreCase(name)) {
				return map;
			}
		}
		for (KnockbackMap map : unavailableMaps) {
			if (map.getName().equalsIgnoreCase(name)) {
				return map;
			}
		}
		return null;
	}

	public void addPlayer(Player player) {
		players.remove(player);
		players.add(player);

		player.teleport(currentMap.getSpawn());
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public boolean isPlaying(Player player) {
		return players.contains(player);
	}

	public KnockbackMap getCurrentMap() {
		return currentMap;
	}

	public ArrayList<KnockbackMap> getAvailableMaps() {
		return availableMaps;
	}

	public ArrayList<KnockbackMap> getUnavailableMaps() {
		return unavailableMaps;
	}
}
