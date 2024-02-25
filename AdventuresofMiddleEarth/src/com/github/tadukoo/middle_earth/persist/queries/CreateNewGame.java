package com.github.tadukoo.middle_earth.persist.queries;

import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.IDatabase;
import com.github.tadukoo.middle_earth.persist.InitDatabase;

public class CreateNewGame {
	public static void main(String[] args) throws Exception {
		
		InitDatabase.init();
		// get the DB instance and execute transaction

		IDatabase db = DatabaseProvider.getInstance();
		db.createNewGame("pete");
	}
}
