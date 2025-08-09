package fun.mikolaj0524.manhuntPlugin.Objects;

import org.bukkit.Location;

public class RunnerWorld {
	private Location worldNether;
	private Location netherWorld;
	private Location worldEnd;

	public RunnerWorld(){
		this.netherWorld = null;
		this.worldNether = null;
		this.worldEnd = null;
	}

	public RunnerWorld(Location netherWorld, Location worldNether, Location worldEnd){
		this.netherWorld = netherWorld;
		this.worldNether = worldNether;
		this.worldEnd = worldEnd;
	}

	public void setWorldNether(Location loc){
		this.worldNether = loc;
	}

	public void setNetherWorld(Location loc){
		this.netherWorld = loc;
	}

	public void setWorldEnd(Location loc){
		this.worldEnd = loc;
	}

	public Location getWorldNether() {
		return this.worldNether;
	}

	public Location getNetherWorld() {
		return this.netherWorld;
	}

	public Location getWorldEnd() {
		return this.worldEnd;
	}
}
