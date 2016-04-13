package net.mchel.plugin.fuwa;

import net.mchel.plugin.fuwa.Fuwa;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.io.BukkitObjectInputStream;

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

	/**
	 * 円形に広がっていくエフェクト
	 * @param center
	 * @param redius_first
	 * @param redius_expand
	 * @param interval
	 */
	public void playEffectA(final Location center, final int redius_first ,final int redius_expand, final double interval) {
		new BukkitRunnable() {
			int i = redius_first;
			@Override
			public void run() {
				if (i <= redius_expand) {
					showA(center, i , interval);
					i++;
				} else {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 1L, 3L);
	}
	private void showA(final Location center, final int radius, final double interval) {
		BigDecimal rad = new BigDecimal(radius);
		BigDecimal angle_one = new BigDecimal(interval).divide(rad, 4, BigDecimal.ROUND_HALF_UP);
		BigDecimal angle_mid = angle_one;
		for (int i = 1 ; angle_mid.doubleValue() < 2*Math.PI ; i++) {
			createSmokeParticle(center, radius, angle_mid, 100, 0,1,0,0.0001);
			angle_mid = angle_mid.add(angle_one.multiply(new BigDecimal(i)));
		}
	}

	/**
	 * くるくるまわりながら上昇するえふぇくと
	 * @param center 初期中心位置
	 * @param radius 半径
	 * @param height 高さの上限
	 * @param addHeight 1つのエフェクトごとの高さ変化
	 * @param interval エフェクトの間隔
	 * @param angleRatio 角度の倍数
	 */
	public void playEffectB(final Location center, final double radius, final double height, final double addHeight , final double interval, final double angleRatio) {
		new BukkitRunnable() {
			Location loc = center.clone();
			BigDecimal rad = new BigDecimal(radius);
			BigDecimal angle_one = new BigDecimal(interval).divide(rad, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(angleRatio));
			BigDecimal angle_mid = angle_one;
			double maxHeight = loc.getY() + height;
			@Override
			public void run() {
				if (loc.getY() > maxHeight) {
					cancel();
				} else {
					createSmokeParticle(loc, radius, angle_mid, 100,0,0.1,0,0.0001);
					angle_mid = angle_mid.add(angle_one);
				}
				loc.add(0,addHeight, 0);
			}
		}.runTaskTimerAsynchronously(plugin, 1L, 1L);
	}



	public void playEffectB2(final Location center, final double radius_first, final double radius_remove, final double addHeight , final double interval, final double angleRatio) {
		if (radius_first <= 0) {
			return;
		}
		new BukkitRunnable() {
			Location loc = center.clone();
			BigDecimal rad = new BigDecimal(radius_first);
			BigDecimal angle_one = new BigDecimal(interval).divide(rad, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(angleRatio));
			BigDecimal angle_mid = angle_one;
			BigDecimal angle_mid_op = angle_one.add(new BigDecimal(Math.PI));
			double radius_mid = radius_first;

			@Override
			public void run() {
				if (radius_mid < 0) {
					createFireExplodeParticle(loc);
					cancel();
				} else {
					radius_mid -= radius_remove;
					createSmokeParticle(loc, radius_mid, angle_mid, 100,0,0.1,0,0.0001);
					createSmokeParticle(loc, radius_mid, angle_mid_op, 100,0,0.1,0,0.0001);
					angle_mid = angle_mid.add(angle_one);
					angle_mid_op = angle_mid_op.add(angle_one);
				}
				loc.add(0,addHeight, 0);
			}
		}.runTaskTimerAsynchronously(plugin, 1L, 1L);
	}


	/**
	 * パーティクル発生装置
	 * @param center 中心位置
	 * @param radius 半径
	 * @param angle ラジアン角度
	 * @param count 回数
	 * @param x 広がりx軸
	 * @param y 広がりy軸
	 * @param z 広がりz軸
	 * @param speed 広がりスピード
	 */
	private void createSmokeParticle(final Location center, final double radius , final BigDecimal angle , final int count , final double x , final double y , final double z , final double speed) {
		Location loc = center.clone();
		loc.setX(loc.getX() + radius * Math.cos(angle.doubleValue()));
		loc.setZ(loc.getZ() + radius * Math.sin(angle.doubleValue()));
		loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, count, x, y, z, speed);
	}

	private void createFireExplodeParticle(final Location center) {
		Location loc = center.clone();
		loc.getWorld().spawnParticle(Particle.LAVA, loc,  100,2,2,2,0.1);
		loc.getWorld().spawnParticle(Particle.FLAME, loc,  500,2,2,2,0.8);
		//loc.getWorld().spawnParticle(Particle.SPELL, loc,  100,2,2,2,0.01);
		loc.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc,  100,2,2,2,0.01);
		//loc.getWorld().spawnParticle(Particle.SNOWBALL, loc,  100,5,5,5,0.01);

		loc.getWorld().playSound(loc,Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE,1F,1F);
	}


}
