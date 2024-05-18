package com.github.tadukoo.aome.character;

/**
 * NPC represents a non-player {@link Character} in the game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class NPC extends Character{
	
	public NPC(){
		
	};
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "NPCs";
	}
	
	public void talk(){
		// TODO Implement
		throw new UnsupportedOperationException("Not implemented yet!");
	}
}
