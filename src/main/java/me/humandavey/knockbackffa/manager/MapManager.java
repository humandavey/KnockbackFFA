package me.humandavey.knockbackffa.manager;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.util.Util;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Map;

public class MapManager {

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
			currentMap = availableMaps.get(0);
		}
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

	private void updateMaps() {
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
