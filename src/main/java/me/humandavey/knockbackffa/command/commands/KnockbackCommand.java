package me.humandavey.knockbackffa.command.commands;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.command.Command;
import me.humandavey.knockbackffa.manager.MapManager;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.util.Util;
import org.bukkit.entity.Player;

public class KnockbackCommand extends Command {

	public KnockbackCommand() {
		super("knockback", new String[]{"knockbackffa", "kbffa"}, "Create, view, and edit features of the maps and toggle settings.");
	}

	@Override
	public void execute(Player player, String[] args) {
		if (player.hasPermission("knockbackffa.admin")) {
			if (args.length == 0) {
				player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
				return;
			}

			switch (args[0].toLowerCase()) {
				case "create" -> {
					if (args.length > 1) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i] + " ";
						}
						name = name.substring(0, name.length() - 1);

						if (!KnockbackFFA.getInstance().getMapManager().isMapNameTaken(name)) {
							KnockbackFFA.getInstance().getMapManager().getUnavailableMaps().add(new KnockbackMap(name, player.getLocation()));
							player.sendMessage(Util.colorize("&aYou have successfully created the map &2'" + name + "' &ausing &2your location &aas the spawn!"));
						} else {
							player.sendMessage(Util.colorize("&cPick a different name, &4'" + name + "' &cis already taken!"));
						}
					} else {
						player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
					}
				}
				case "edit" -> {
					if (args.length > 1) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i] + " ";
						}
						name = name.substring(0, name.length() - 1);

						if (KnockbackFFA.getInstance().getMapManager().getMap(name) != null) {
							// TODO: Open menu to edit map data
						} else {
							player.sendMessage(Util.colorize("&cThere is no map found with the name &4'" + name + "'&c!"));
						}
					} else {
						player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
					}
				}
				case "delete" -> {
					if (args.length > 1) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i] + " ";
						}
						name = name.substring(0, name.length() - 1);

						if (KnockbackFFA.getInstance().getMapManager().getMap(name) != null) {
							KnockbackFFA.getInstance().getMapManager().deleteMap(name);
							player.sendMessage(Util.colorize("&aThe map &2'" + name + "' &ahas been deleted!"));
						} else {
							player.sendMessage(Util.colorize("&cThere is no map found with the name &4'" + name + "'&c!"));
						}
					} else {
						player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
					}
				}
				case "tp" -> {
					if (args.length > 1) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i] + " ";
						}
						name = name.substring(0, name.length() - 1);

						KnockbackMap map = KnockbackFFA.getInstance().getMapManager().getMap(name);
						if (map != null) {
							player.teleport(map.getSpawn());
							player.sendMessage("&aYou have been teleported to the map &2'" + name +"'&a!");
							if (!map.isMapReady()) {
								player.sendMessage(Util.colorize("&e&lCAUTION: &r&eThis map is not fully setup!"));
							}
						} else {
							player.sendMessage(Util.colorize("&cThere is no map found with the name &4'" + name + "'&c!"));
						}
					} else {
						player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
					}
				}
				case "setmap" -> {
					if (args.length > 1) {
						String name = "";
						for (int i = 1; i < args.length; i++) {
							name += args[i] + " ";
						}
						name = name.substring(0, name.length() - 1);

						KnockbackMap map = KnockbackFFA.getInstance().getMapManager().getMap(name);
						if (map != null) {
							KnockbackFFA.getInstance().getMapManager().setCurrentMap(map);
							player.sendMessage(Util.colorize("&aSet &2'" + name + "' &ato the current map!"));
						} else {
							player.sendMessage(Util.colorize("&cThere is no map found with the name &4'" + name + "'&c!"));
						}
					} else {
						player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
					}
				}
				default -> {
					player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
				}
			}
		} else {
			player.sendMessage(Util.colorize("&cYou don't have permission to use this command!"));
		}
	}
}
