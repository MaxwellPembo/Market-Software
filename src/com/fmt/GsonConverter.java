package com.fmt;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 


public class GsonConverter {
	
	
	
	
	
	public static void outputPersonData(List<Person> per, String outputFile) {
		try {
			PrintWriter pw = new PrintWriter(outputFile);
			for(Person k : per) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				pw.write(gson.toJson(k));
				pw.write("\n");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void outputStoreData(List<Store> str, String outputFile) {
		try {
			PrintWriter pw = new PrintWriter(outputFile);
			for(Store k : str) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				pw.write(gson.toJson(k));
				pw.write("\n");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void outputItemData(List<Item> items, String outputFile) {
		try {
			PrintWriter pw = new PrintWriter(outputFile);
			for(Item k : items) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				pw.write(gson.toJson(k));
				pw.write("\n");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
