package net.mchel.plugin.fuwa.listener;

import net.mchel.plugin.fuwa.Fuwa;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class Manager {

	private Fuwa plugin;

	public Manager(Fuwa fuwa) {
		plugin = fuwa;
	}

	private ArrayList<Player> onlist = new ArrayList<>();

	public void addPlayer(Player p) {
		if (!onlist.contains(p)) {
			onlist.add(p);
		}
	}

	public void removePlayer(Player p) {
		while (onlist.contains(p)) {
			onlist.remove(p);
		}
	}

	public boolean isContain(Player p) {
		return onlist.contains(p);
	}
}