package com.example.kaeuc.smartufpa.utils;

import android.util.Log;

import com.example.kaeuc.smartufpa.R;
import com.example.kaeuc.smartufpa.models.overpass.Element;
import com.example.kaeuc.smartufpa.models.overpass.OverpassJsonModel;
import com.example.kaeuc.smartufpa.models.Place;
import com.example.kaeuc.smartufpa.models.PlaceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeuch on 09/01/2017.
 * Classe responsável por processar a resposta JSON do servidor Overpass e retornar os locais
 * á atividade principal em forma de ArrayList<Place>
 */

public class JsonParser {
    // TAG para logs
    private static final String TAG = JsonParser.class.getSimpleName();

    public static List<Place> parseLocalResponse(String jsonResponse){
        return null;
    }

    public static ArrayList<Place> parseOverpassResponse(String jsonInput) throws EmptyResponseException{
        Gson gson = new Gson();
        OverpassJsonModel overpassJsonModel;
        try{
            overpassJsonModel = gson.fromJson(jsonInput, OverpassJsonModel.class);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            throw new IrregularQueryException("Your query presents an syntax error.");
        }

        if(overpassJsonModel.getElements().isEmpty()){
            throw new EmptyResponseException("The response for your query is empty");
        }

        PlaceFactory factory = PlaceFactory.getInstance();
        ArrayList<Place> places = new ArrayList<>();
        try {
            for (Element element :
                overpassJsonModel.getElements()) {
                if (element.isCenterEmpty())
                    places.add(factory.createPlace(element.getId(),
                            element.getLat(), element.getLon(), element.getTags()));
                else
                    places.add(factory.createPlace(element.getId(),
                            element.getCenter().getLat(), element.getCenter().getLon(), element.getTags()));

            }
        }catch (NullPointerException e){
            e.printStackTrace();

        }
        return places;
    }

    public static class EmptyResponseException extends RuntimeException{
        private EmptyResponseException(String message) { super(message); }
    }

    public static class IrregularQueryException extends RuntimeException{
        private IrregularQueryException(String message) { super(message); }
    }


}
