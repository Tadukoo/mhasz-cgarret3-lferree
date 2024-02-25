package com.github.tadukoo.middle_earth.persist.queries;

import com.github.tadukoo.middle_earth.model.Constructs.Item;
import com.github.tadukoo.middle_earth.model.Constructs.Object;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.IDatabase;
import com.github.tadukoo.middle_earth.persist.InitDatabase;

public class QueryForObjectByID {

	public static void main(String[] args) throws Exception {
		
		
		InitDatabase.init();
		// get the DB instance and execute transaction

		IDatabase db = DatabaseProvider.getInstance();
		
		Object object = db.getObjectByID(1);
		
		// check if anything was returned and output the list
		if (object == null) {
			System.out.println("No items found with that ID");
		}
		else {
			System.out.println("Object ID = " + object.getID() 
				+ "\nObject Name = " + object.getName() 
				+ "\nLong Desc = " + object.getLongDescription() 
				+ "\nShort Desc = " + object.getShortDescription() 
				+ "\nCommand Responses: " + object.getCommandResponses());
			if(object.getItems().isEmpty()) {
				System.out.println("No items");
			} else {
				for(Item item : object.getItems()) {
					System.out.println("\tItem ID = " + item.getID() 
						+ "\n\tItem Name = " + item.getName() 
						+ "\n\tLong Desc = " + item.getLongDescription() 
						+ "\n\tShort Desc = " + item.getShortDescription() 
			
						+ "\n\tDesc Update = " + item.getdescription_update()
						+ "\n\tAttack Bonus = " + item.getattack_bonus()
						+ "\n\tDefense Bonus = " + item.getdefense_bonus()
						+ "\n\tHP Bonus = " + item.gethp_bonus() 
			
						+ "\n\tItem Weight = " + item.getItemWeight() 
						+ "\n\tItem Type = " + item.getItemType()
						+ "\n\tLevel Requirement = " + item.getlvl_requirement()
			
						+ "\n\tIs a quest item? " + item.getIsQuestItem() + "\n\n");
				}
			}
		}
	}
}
