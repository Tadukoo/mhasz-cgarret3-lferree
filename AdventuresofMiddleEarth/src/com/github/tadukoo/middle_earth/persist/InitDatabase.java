package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
//import com.github.tadukoo.middle_earth.persist.FakeDatabase;

public class InitDatabase {
	public static void init() {
				DatabaseProvider.setInstance(new DerbyDatabase());
	}
}
