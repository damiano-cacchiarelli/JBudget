/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * La classe {@code InterfaceAdapterGson} permettere la serializzare e
 * deserializzare di interfacce definendo una struttura che permette di
 * distinguere i vari oggetti. "CLASSNAME" è la chiave per ottenere il nome
 * della classe dell'oggetto, mentre "DATA" è la chiave che mappa il reale
 * oggetto JSON.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 * @param <T>
 */
public class InterfaceAdapterGson<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	/**
	 * 
	 */
	private static final String CLASSNAME = "CLASSNAME";
	
	/**
	 * 
	 */
	private static final String DATA = "DATA";

	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		String className = prim.getAsString();
		Class<?> klass = getObjectClass(className);
		return context.deserialize(jsonObject.get(DATA), klass);
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(CLASSNAME, src.getClass().getName());
		jsonObject.add(DATA, context.serialize(src));
		return jsonObject;
	}

	/**
	 * Metodo di supporto per ottenere la deserializzazione del className dell'oggetto.
	 */
	private Class<?> getObjectClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e.getMessage());
		}
	}

}
