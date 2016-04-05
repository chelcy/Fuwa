package net.mchel.plugin.fuwa;

import net.mchel.plugin.fuwa.cmd.Cmds;
import net.mchel.plugin.fuwa.listener.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class Fuwa extends JavaPlugin{

	private PluginManager pm = this.getServer().getPluginManager();
	private String prefix;

	@Override
	public void onEnable() {
		super.onEnable();
		prefix = ChatColor.GOLD + " ふわふわ♪" + ChatColor.DARK_GRAY + " ≫ " + ChatColor.RESET;

		pm.registerEvents(new PlayerListener(this) , this);

		Cmds cmds = new Cmds(this);
		Map<String, Map<String, Object>> pluginCommands
				= (Map<String, Map<String, Object>>) this.getDescription().getCommands();
		for (String command : pluginCommands.keySet()) {
			this.getServer().getPluginCommand(command).setExecutor(cmds);
		}

	}


	public String getPrefix() {
		return prefix;
	}


}
