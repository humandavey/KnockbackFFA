package me.humandavey.knockbackffa;

import me.humandavey.knockbackffa.command.commands.KnockbackCommand;
import me.humandavey.knockbackffa.listeners.BlockListener;
import me.humandavey.knockbackffa.listeners.JoinListener;
import me.humandavey.knockbackffa.listeners.PlayerDamageListener;
import me.humandavey.knockbackffa.listeners.PlayerMoveListener;
import me.humandavey.knockbackffa.manager.MapManager;
import me.humandavey.knockbackffa.nametag.NametagManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KnockbackFFA extends JavaPlugin {

	private static KnockbackFFA instance;
	private MapManager mapManager;

	@Override
	public void onEnable() {
		instance = this;

		setupConfigs();
		setupManagers();
		registerListeners();
		registerCommands();
	}

	@Override
	public void onDisable() {

	}

	private void setupConfigs() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	private void setupManagers() {
		mapManager = new MapManager();
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new NametagManager(), this);
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
	}

	private void registerCommands() {
		new KnockbackCommand();
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public static KnockbackFFA getInstance() {
		return instance;
	}
}
