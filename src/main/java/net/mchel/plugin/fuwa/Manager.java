package net.mchel.plugin.fuwa;

import net.mchel.plugin.fuwa.Fuwa;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class Manager {

	private Fuwa plugin;
	private String prefix;

	public Manager(Fuwa fuwa) {
		plugin = fuwa;
		prefix = plugin.getPrefix();
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

	public void switchPlayer(Player p) {
		if (isContain(p)) {
			removePlayer(p);
			p.sendMessage(prefix + "解除しました。");
		} else {
			addPlayer(p);
			p.sendMessage(prefix + "設定しました。");
		}
	}


	/**
	 * えふぇくとぷれいしよー
	 * @param player ぷれいやー
	 * @param center ちゅうしんのばしょ
	 */
	public void playEffect(final Player player , final Location center) {

		new BukkitRunnable() {
			int i = 0;
			final Location loc = center.clone();
			final World world = loc.getWorld();
			@Override
			public void run() {

				world.spawnParticle(Particle.EXPLOSION_NORMAL, loc, 100, 1,1,1,0.0001);

				loc.add(0,1,0);

				i++;
				if (i == 50) {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin,2,2);


	}



}
