package com.github.tadukoo.middle_earth.model.Constructs;

import com.github.tadukoo.util.StringUtil;

public enum ItemType{
	HELM,
	BRACES,
	CHEST,
	LEGS,
	BOOTS,
	L_HAND,
	R_HAND,
	QUEST,
	MISC;
	
	public static ItemType fromString(String text){
		for(ItemType type: values()){
			if(StringUtil.equals(type.toString(), text)){
				return type;
			}
		}
		return null;
	}
}
