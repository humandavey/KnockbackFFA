package me.humandavey.knockbackffa.map;

import me.humandavey.knockbackffa.KnockbackFFA;
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
		if (pos1 == null || pos2 == null) return false;
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

		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-1.x", pos1.getX());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-1.y", pos1.getY());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-1.z", pos1.getZ());
		KnockbackFFA.getInstance().saveConfig();

		KnockbackFFA.getInstance().getMapManager().updateMaps();
	}

	public void setPos2(Location pos2) {
		this.pos2 = pos2;

		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-2.x", pos2.getX());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-2.y", pos2.getY());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".corner-2.z", pos2.getZ());
		KnockbackFFA.getInstance().saveConfig();

		KnockbackFFA.getInstance().getMapManager().updateMaps();
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;

		KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.x", spawn.getX());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.y", spawn.getY());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.z", spawn.getZ());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.yaw", spawn.getYaw());
		KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.pitch", spawn.getPitch());
		KnockbackFFA.getInstance().saveConfig();

		KnockbackFFA.getInstance().getMapManager().updateMaps();
	}

	public Location getSpawn() {
		return spawn;
	}

	public Location getPos1() {
		return pos1;
	}

	public Location getPos2() {
		return pos2;
	}

	public String getName() {
		return name;
	}
}
