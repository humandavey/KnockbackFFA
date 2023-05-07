package me.humandavey.knockbackffa.command.commands;

import me.humandavey.knockbackffa.KnockbackFFA;
import me.humandavey.knockbackffa.command.Command;
import me.humandavey.knockbackffa.map.KnockbackMap;
import me.humandavey.knockbackffa.menu.Menu;
import me.humandavey.knockbackffa.util.Util;
import me.humandavey.knockbackffa.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

							KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.x", player.getLocation().getX());
							KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.y", player.getLocation().getY());
							KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.z", player.getLocation().getZ());
							KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.yaw", player.getLocation().getYaw());
							KnockbackFFA.getInstance().getConfig().set("data." + name + ".spawn.pitch", player.getLocation().getPitch());
							KnockbackFFA.getInstance().saveConfig();
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
							KnockbackMap map = KnockbackFFA.getInstance().getMapManager().getMap(name);

							Menu menu = new Menu(map.getName(), 3);
							menu.setItemAt(10, new ItemBuilder(Material.DIAMOND).setItemName(Util.colorize("&eSet corner 1")).build());
							menu.setItemAt(11, new ItemBuilder(Material.GOLD_INGOT).setItemName(Util.colorize("&eSet corner 2")).build());
							menu.setItemAt(12, new ItemBuilder(Material.NETHER_STAR).setItemName(Util.colorize("&eSet spawn point")).build());

							menu.setOnClick(event -> {
								if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName().contains("Set corner 2")) {
									map.setPos2(player.getLocation());
									player.sendMessage(Util.colorize("&aUpdated the map &2'" + map.getName() + "' &awith a new corner 2!"));
								}
								if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName().contains("Set corner 1")) {
									map.setPos1(player.getLocation());
									player.sendMessage(Util.colorize("&aUpdated the map &2'" + map.getName() + "' &awith a new corner 1!"));
								}
								if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName().contains("Set spawn point")) {
									map.setSpawn(player.getLocation());
									player.sendMessage(Util.colorize("&aUpdated the map &2'" + map.getName() + "' &awith a new spawn point!"));
								}
							});

							menu.open(player);
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
				case "list" -> {
					KnockbackFFA.getInstance().getMapManager().updateMaps();
					for (KnockbackMap map : KnockbackFFA.getInstance().getMapManager().getAvailableMaps()) {
						if (map.equals(KnockbackFFA.getInstance().getMapManager().getCurrentMap())) {
							player.sendMessage(Util.colorize("&a&l- &a" + map.getName()));
						} else {
							player.sendMessage(Util.colorize("&a&l- &e" + map.getName()));
						}
					}
					for (KnockbackMap map : KnockbackFFA.getInstance().getMapManager().getUnavailableMaps()) {
						player.sendMessage(Util.colorize("&c&l- &e" + map.getName()));
					}
				}
				default -> {
					player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <create|edit|delete|tp|setmap> <mapname>"));
				}
			}
		} else {
			if (args.length == 0) {
				player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <join|leave>"));
				return;
			}
			switch (args[0].toLowerCase()) {
				case "join" -> {
					if (!KnockbackFFA.getInstance().getMapManager().isPlaying(player)) {
						KnockbackFFA.getInstance().getMapManager().addPlayer(player);
						player.teleport(KnockbackFFA.getInstance().getMapManager().getCurrentMap().getSpawn());
						player.sendMessage(Util.colorize("&aYou have joined KnockbackFFA!"));
					} else {
						player.sendMessage(Util.colorize("&cYou are already playing KnockbackFFA!"));
					}
				}
				case "leave" -> {
					if (KnockbackFFA.getInstance().getMapManager().isPlaying(player)) {
						KnockbackFFA.getInstance().getMapManager().removePlayer(player);
						player.teleport(Util.configToLocation(KnockbackFFA.getInstance().getConfig(), "config.default-spawn"));
						player.sendMessage(Util.colorize("&aYou have left KnockbackFFA!"));
					} else {
						player.sendMessage(Util.colorize("&cYou are not playing KnockbackFFA!"));
					}
				}
				case "hotbar" -> {
					// TODO: Implement hotbar item switcher
				}
				default -> {
					player.sendMessage(Util.colorize("&cInvalid Usage: /kbffa <join|leave|hotbar>"));
				}
			}
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args) {
		ArrayList<String> completions = new ArrayList<>();
		ArrayList<String> commands = new ArrayList<>();

		if (sender.hasPermission("knockbackffa.admin") || sender.isOp() || sender.hasPermission("*")) {
			if (args.length == 1) {
				commands.add("create");
				commands.add("edit");
				commands.add("delete");
				commands.add("tp");
				commands.add("setmap");
				StringUtil.copyPartialMatches(args[0], commands, completions);
			} else if (args.length > 1) {
				for (KnockbackMap map : KnockbackFFA.getInstance().getMapManager().getAvailableMaps()) {
					commands.add(map.getName());
				}
				for (KnockbackMap map : KnockbackFFA.getInstance().getMapManager().getUnavailableMaps()) {
					commands.add(map.getName());
				}
				StringUtil.copyPartialMatches(args[1], commands, completions);
			}
		} else {
			commands.add("join");
			commands.add("leave");
			commands.add("hotbar");
			StringUtil.copyPartialMatches(args[0], commands, completions);
		}
		Collections.sort(completions);
		return completions;
	}
}
