package com.github.tadukoo.aome;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.pojo.DatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.util.map.HashManyToManyMap;
import com.github.tadukoo.util.map.ManyToManyMap;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public abstract class ManyToManyDatabasePojo<Type1 extends DatabasePojo, Type2 extends DatabasePojo>
		extends AbstractDatabasePojo{
	
	public ManyToManyDatabasePojo(){
	
	}
	
	@Override
	public String getIDColumnName(){
		return null;
	}
	
	public abstract Class<Type1> getType1Class();
	
	public abstract String getType1ForeignTableName();
	
	public abstract String getType1IDColumnName();
	
	public abstract String getType1ForeignIDColumnName();
	
	public abstract Class<Type2> getType2Class();
	
	public abstract String getType2ForeignTableName();
	
	public abstract String getType2IDColumnName();
	
	public abstract String getType2ForeignIDColumnName();
	
	@Override
	public void setDefaultColumnDefs(){
		// Type 1 ID Column
		ColumnDefinition type1IDCol = ColumnDefinition.builder()
				.columnName(getType1IDColumnName())
				.integer()
				.defaultSize()
				.build();
		addColumnDef(type1IDCol);
		
		// Type 2 ID Column
		ColumnDefinition type2IDCol = ColumnDefinition.builder()
				.columnName(getType2IDColumnName())
				.integer()
				.defaultSize()
				.build();
		addColumnDef(type2IDCol);
		
		// Foreign Keys
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(type1IDCol)
				.references(getType1ForeignTableName())
				.referenceColumnNames(getType1ForeignIDColumnName())
				.build());
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(type2IDCol)
				.references(getType2ForeignTableName())
				.referenceColumnNames(getType2ForeignIDColumnName())
				.build());
	}
	
	public ManyToManyMap<Type1, Type2> convertToMap(
			Database database, List<ManyToManyDatabasePojo<Type1, Type2>> pojos) throws SQLException{
		ManyToManyMap<Type1, Type2> theMap = new HashManyToManyMap<>();
		for(ManyToManyDatabasePojo<Type1, Type2> pojo: pojos){
			try{
				// Grab item1
				Object id1 = pojo.getItem(getType1IDColumnName());
				Type1 type1 = getType1Class().getConstructor().newInstance();
				type1.setItem(getType1ForeignIDColumnName(), id1);
				List<Type1> results1 = type1.doSearch(database, getType1Class(), false);
				Type1 item1 = results1.get(0);
				
				// Grab item2
				Object id2 = pojo.getItem(getType2IDColumnName());
				Type2 type2 = getType2Class().getConstructor().newInstance();
				type2.setItem(getType2ForeignIDColumnName(), id2);
				List<Type2> results2 = type2.doSearch(database, getType2Class(), false);
				Type2 item2 = results2.get(0);
				
				// Add the mapping to the map
				theMap.put(item1, item2);
			}catch(NoSuchMethodException | InstantiationException |
			       IllegalAccessException | InvocationTargetException e){
				throw new IllegalStateException("Failed to instantiate a DatabasePojo", e);
			}
		}
		return theMap;
	}
}
