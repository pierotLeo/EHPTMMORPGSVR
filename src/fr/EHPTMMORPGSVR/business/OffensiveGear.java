package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public interface OffensiveGear extends Gear{
	public abstract Stat getMastery();
	public abstract Stat getImpact();
}
