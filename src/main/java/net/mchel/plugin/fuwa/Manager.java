package net.mchel.plugin.fuwa;

import net.mchel.plugin.fuwa.Fuwa;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
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
	 * @param center ちゅうしんのばしょ
	 */
	public void playEffect(final Location center) {

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

	public void playEffect(final Location center, final int redius_first ,final int redius_expand, final double interval) {

		new BukkitRunnable() {
			int i = redius_first;
			@Override
			public void run() {
				if (i <= redius_expand) {
					show(center, i , interval);
					i++;
				} else {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 1L, 3L);
	}


	private void show(final Location center, final int radius, final double interval) {
		BigDecimal rad = new BigDecimal(radius);
		BigDecimal inte = new BigDecimal(interval);

		BigDecimal angle_one = inte.divide(rad, 4, BigDecimal.ROUND_HALF_UP);

		BigDecimal angle_mid = angle_one;

		for (int i = 1 ; angle_mid.doubleValue() < 2*Math.PI ; i++) {

			showN(center, radius, angle_mid);

			angle_mid = angle_mid.add(angle_one.multiply(new BigDecimal(i)));
		}

	}

	private void showN(final Location center, final double radius , final BigDecimal angle) {
		Location loc = center.clone();
		loc.setX(loc.getX() + radius * Math.cos(angle.doubleValue()));
		loc.setZ(loc.getZ() + radius * Math.sin(angle.doubleValue()));
		loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 100, 0,1,0,0.0001);
	}



}
