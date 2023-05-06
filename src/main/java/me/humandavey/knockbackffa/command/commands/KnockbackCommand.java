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
							player.sendMessage(Util.colorize("&aYou have successfully created the map &2" + name + "&ausing &2your location &aas the spawn!"));
						} else {
							player.sendMessage(Util.colorize("&cPick a different name, &4'" + name + "' &cis already taken!"));
						}
					}
				}
				case "edit" -> {

				}
				case "delete" -> {

				}
				case "tp" -> {

				}
				case "setmap" -> {

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
