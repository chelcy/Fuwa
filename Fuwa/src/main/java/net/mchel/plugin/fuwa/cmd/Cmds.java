package net.mchel.plugin.fuwa.cmd;

import net.mchel.plugin.fuwa.Fuwa;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class Cmds implements CommandExecutor {

	private Fuwa plugin;
	private String prefix;
	public Cmds(Fuwa fuwa) {
		this.plugin = fuwa;
		this.prefix = plugin.getPrefix();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("fuwa")) {
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "ぷれいやーちゃっとからじっこうしてね(ち◜◡‾ぇ)");
			return true;
		}



		return true;
	}
}
