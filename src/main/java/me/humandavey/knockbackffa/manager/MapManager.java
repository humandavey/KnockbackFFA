package me.humandavey.knockbackffa.manager;

import me.humandavey.knockbackffa.map.KnockbackMap;

import java.util.ArrayList;

public class MapManager {

	private KnockbackMap currentMap;

	private final ArrayList<KnockbackMap> availableMaps = new ArrayList<>();
	private final ArrayList<KnockbackMap> unavailableMaps = new ArrayList<>();

	public MapManager() {
		// TODO: Load maps in from config

		updateMaps();

		currentMap = availableMaps.get(0);
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
