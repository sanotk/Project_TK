package com.mypjgdx.esg.game.objects;

public abstract class Item {


	    public enum ItemType{
	    	solarcell,
	    	battery,
	    	charge,
	    	inverter
	    }

	    abstract ItemType tellMeTheType();
}
