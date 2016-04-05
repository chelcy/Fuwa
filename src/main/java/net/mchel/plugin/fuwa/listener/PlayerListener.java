package net.mchel.plugin.fuwa.listener;

import net.mchel.plugin.fuwa.Fuwa;
import net.mchel.plugin.fuwa.Manager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BlockIterator;
import sun.security.action.GetLongAction;

/**
 * @author Chelcy
 *         2016/04/05
 */
public class PlayerListener implements Listener{

	private Fuwa plugin;
	private String prefix;
	private Manager ma;
	public PlayerListener(Fuwa fuwa) {
		this.plugin = fuwa;
		prefix = plugin.getPrefix();
		ma = plugin.getManager();
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!ma.isContain(p) || !e.getAction().toString().startsWith("RIGHT_") || !e.getHand().equals(EquipmentSlot.HAND)) {
			return;
		}
		Block b = getTargetBlock(p);
		if (b == null) {
			return;
		}
		e.setCancelled(true);
		ma.playEffect(p , b.getLocation().add(0,1,0));
	}

	private Block getTargetBlock(Player p) {
		BlockIterator bi = new BlockIterator(p , 200);
		while (bi.hasNext()) {
			Block b = bi.next();
			if (b.getType() != Material.AIR) {
				return b;
			}
		}
		return null;
	}
}
