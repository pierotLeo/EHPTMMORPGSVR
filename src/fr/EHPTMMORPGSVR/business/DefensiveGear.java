package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public interface DefensiveGear extends Gear{
	public abstract Stat getSolidity();
	public abstract Stat getBurden();
}
