package me.humandavey.knockbackffa;

import me.humandavey.knockbackffa.command.commands.ExampleCommand;
import me.humandavey.knockbackffa.nametag.NametagManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KnockbackFFA extends JavaPlugin {

	private static KnockbackFFA instance;

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

	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new NametagManager(), this);
	}

	private void registerCommands() {
		new ExampleCommand();
	}

	public static KnockbackFFA getInstance() {
		return instance;
	}
}
