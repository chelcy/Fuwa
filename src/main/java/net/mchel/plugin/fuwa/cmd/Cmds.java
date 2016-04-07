package net.mchel.plugin.fuwa.cmd;

import net.mchel.plugin.fuwa.Fuwa;
import net.mchel.plugin.fuwa.Manager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class Cmds implements CommandExecutor {

	private String prefix;
	private Manager ma;
	public Cmds(Fuwa fuwa) {
		Fuwa plugin = fuwa;
		prefix = plugin.getPrefix();
		ma = plugin.getManager();
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

		ma.switchPlayer((Player)sender);

		return true;
	}
}
