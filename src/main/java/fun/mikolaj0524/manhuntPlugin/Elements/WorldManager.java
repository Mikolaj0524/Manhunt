package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.*;
import org.bukkit.event.Listener;

public class WorldManager implements Listener {

	public static World world;
	public static Location spawn;
	public static boolean spawnExist = false;

	public static void buildSpawn() {
		int centerX = 0;
		int centerZ = 0;
		int baseY = world.getHighestBlockYAt(centerX, centerZ);

		for (int x = -3; x <= 3; x++) {
			for (int y = baseY; y <= baseY + 6; y++) {
				for (int z = -3; z <= 3; z++) {
					int worldX = centerX + x;
					int worldY = y;
					int worldZ = centerZ + z;

					if (y == baseY) {
						world.getBlockAt(worldX, worldY, worldZ).setType(Material.BEDROCK);
					} else {
						world.getBlockAt(worldX, worldY, worldZ).setType(Material.AIR);
					}
				}
			}
		}
		spawnExist = true;
	}

}
