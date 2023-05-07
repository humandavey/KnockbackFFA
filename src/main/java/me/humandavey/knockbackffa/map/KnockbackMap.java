package me.humandavey.knockbackffa.map;

import org.bukkit.Location;

public class KnockbackMap {

	private String name;
	private Location spawn, pos1, pos2;

	public KnockbackMap(String name, Location spawn) {
		this.name = name;
		this.spawn = spawn;
	}

	public KnockbackMap(String name, Location spawn, Location pos1, Location pos2) {
		this.name = name;
		this.spawn = spawn;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public boolean isMapReady() {
		return pos1 != null && pos2 != null;
	}

	public boolean isLocationInBounds(Location location) {
		int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
		int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
		int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

		int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
		int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
		int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

		return (location.getX() >= minX && location.getX() <= maxX) && (location.getY() >= minY && location.getY() <= maxY) && (location.getZ() >= minZ && location.getZ() <= maxZ);
	}

	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}

	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpawn() {
		return spawn;
	}

	public String getName() {
		return name;
	}
}
