package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public class NpcBrain extends Thread implements Serializable{
	public NpcBrain(Runnable target){
		super(target);
	}
}
