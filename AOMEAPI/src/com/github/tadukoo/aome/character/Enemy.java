package com.github.tadukoo.aome.character;

/**
 * Enemy represents an enemy {@link Character} in the game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class Enemy extends Character{
	// TODO: What is specific to this?
	public Enemy(){
		
	};
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "Enemies";
	}
}
