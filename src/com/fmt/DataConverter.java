package com.fmt;

import java.util.List;
import java.util.Map;

public class DataConverter {

	public static void convertData () {
		
		String filePersons = "data/Persons.csv";
		String fileStores = "data/Stores.csv";
		String fileInvItems = "data/Items.csv";
		String filePersonsOP = "data/Persons.json";
		String fileStoresOP = "data/Stores.json";
		String fileItemsOP = "data/Items.json";
		
		List<Person> people = ParseCSVData.parsePersonData(filePersons);
		Map<String, Person> mapOfPeople = Person.mapPersons(people);
		List<Store> stores = ParseCSVData.tokeStoreDat(fileStores, mapOfPeople);
		List<Item> items = ParseCSVData.tokeItemDat(fileInvItems);
		
		
		GsonConverter.outputPersonData(people, filePersonsOP);
		GsonConverter.outputStoreData(stores, fileStoresOP);
		GsonConverter.outputItemData(items, fileItemsOP);
		
		
		
	}

}
