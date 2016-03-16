package rs.fon.parlament.rest.parsers.json;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ParlamentJsonParser {
	
	public static Gson gson = new GsonBuilder().serializeNulls().create();
	
	public static <T> JsonObject serialize(List<T> list, int limit, int page, long count){
		JsonObject json = new JsonObject();
		
		JsonElement element = gson.toJsonTree(list, new TypeToken<List<T>>() {}.getType());
		
		json.addProperty("limit", limit);
		json.addProperty("page", page);
		json.addProperty("count", count);
		json.add("data", element.getAsJsonArray());
		
		return json;
	}
	
	public static <T> JsonObject serialize(List<T> list){
		JsonObject json = new JsonObject();
		
		JsonElement element = gson.toJsonTree(list, new TypeToken<List<T>>() {}.getType());
		
		json.add("data", element.getAsJsonArray());
		
		return json;
	}

}
