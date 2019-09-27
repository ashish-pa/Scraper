package com.utils;

import com.constants.MongoConstants;
import com.dao.MongoConnector;
import com.daobeans.MongoBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.util.JSON;
import com.requestbeans.RequestBean;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Utilities for query Mongo DB
 * @author Ashish Patel
 */
public class MongoUtils {

    /**
     * Method to query heroku mongo db for collection desitv with given search query
     * @param searchQuery Query object eg. {"showName", "Yeh Hai Mohabbatein"} to get results for
     * @return JSONArray of matching record objects
     * @throws UnknownHostException
     */
    public static JSONArray query(BasicDBObject searchQuery) throws UnknownHostException {
        JSONArray queryResults = new JSONArray();
        MongoConnector connector = new MongoConnector();
        MongoDatabase db = connector.getDatabase();
        MongoCollection<Document> collection = connector.getCollection(db);
        MongoCursor cursor = collection.find(searchQuery).sort(new BasicDBObject(MongoConstants._id,-1)).iterator();

        try{
            while (cursor.hasNext()) {
                queryResults.put(cursor.next());
            }
        }finally {
            cursor.close();
            connector.closeClient();
        }

        //System.out.println(queryResults);
        return queryResults;
    }

    public static JSONArray distinctQuery(RequestBean requestBean) throws ParseException {
        JSONObject resultObject;
        JSONArray results = new JSONArray();

        MongoConnector connector = new MongoConnector();
        MongoDatabase db = connector.getDatabase();
        MongoCollection<Document> collection = connector.getCollection(db);
        AggregateIterable<Document> output;

        if(requestBean.getDistinctFieldName().equals(MongoConstants.showName)){
            output = collection.aggregate(Arrays.asList(
                    new BasicDBObject("$match",
                            requestBean.toSearchObject()
                    ),
                    new BasicDBObject("$project",
                            new BasicDBObject(MongoConstants.channelName, 1)
                                .append(MongoConstants.showName, 1)
                                .append(MongoConstants.showImageUrl, 1)
                    ),
                    new BasicDBObject("$group", new BasicDBObject("_id",
                            new BasicDBObject(requestBean.getDistinctFieldName(), "$"+requestBean.getDistinctFieldName())
                            .append(MongoConstants.channelName, "$"+MongoConstants.channelName)
                            .append(MongoConstants.showImageUrl, "$"+MongoConstants.showImageUrl)
                    ))/*,
                    new BasicDBObject("$sort", new BasicDBObject(MongoConstants.showDate, -1)))*/
            ));
        }
        else{
            output = collection.aggregate(Arrays.asList(
                    new BasicDBObject("$match",
                            requestBean.toSearchObject()
                    ),
                    new BasicDBObject("$group", new BasicDBObject("_id",
                            new BasicDBObject(requestBean.getDistinctFieldName(), "$"+requestBean.getDistinctFieldName())
                    )))
            );
        }

        JSONObject obj;
        String formattedDate;
        DateFormat originalFormat;
        DateFormat targetFormat;
        Date date;
        String dateString;

        // Print for demo
        for (Document dbObject : output) {
            resultObject = new JSONObject(dbObject);
            obj = new JSONObject(resultObject.get("_id").toString());

            if(obj.has((MongoConstants.showDate))){
                dateString = obj.getString(MongoConstants.showDate).replaceAll("(?<=\\d)(st|nd|rd|th)", "");
                originalFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = originalFormat.parse(dateString);
                formattedDate = targetFormat.format(date);
                //System.out.println("---->"+obj.getString(MongoConstants.showDate) + " | " + dateString + " | " + formattedDate);
                resultObject = new JSONObject();
                resultObject.put(MongoConstants.showDate,obj.getString(MongoConstants.showDate));
                resultObject.put("formattedDate", formattedDate);
                results.put(resultObject);
            }else{
                results.put(resultObject.get("_id"));
            }


        }

        return results;
    }

    public static void main(String args[]) throws ParseException {
        /*RequestBean requestBean = new RequestBean();
        requestBean.setChannelName("Star Plus");
        requestBean.setDistinctFieldName(MongoConstants.showName);*/
        /*requestBean.setShowName("sai");
        requestBean.setDistinctFieldName(MongoConstants.showDate);*/
        //requestBean.setDistinctFieldName(MongoConstants.channelName);
        //System.out.println(MongoUtils.distinctQuery(requestBean).toString());

        MongoConnector connector = new MongoConnector();
        MongoDatabase db = connector.getDatabase();
        MongoCollection<Document> collection = connector.getCollection(db);
        String month = "May";
        collection.deleteMany(new BasicDBObject("showDate","3rd "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","4th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","5th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","6th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","7th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","8th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","9th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","10th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","11th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","12th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","13th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","14th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","15th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","16th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","17th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","18th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","19th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","20th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","21st "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","22nd "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","23rd "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","24th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","25th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","26th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","27th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","28th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","29th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","30th "+month+" 2019"));
        collection.deleteMany(new BasicDBObject("showDate","31st "+month+" 2019"));

    }

    public static JSONArray getShowSourcesForDate(RequestBean requestBean) throws ParseException {
        JSONArray results = new JSONArray();
        JSONObject resultObject = new JSONObject();
        requestBean.setDistinctFieldName(MongoConstants.showLinks);
        JSONObject showSources = distinctQuery(requestBean).getJSONObject(0).getJSONObject(MongoConstants.showLinks);
        for(Object key: showSources.keySet()){
            resultObject.put(MongoConstants.sourceName, key);

            for(Object showParts: showSources.getJSONObject(key.toString()).keySet()){
                resultObject.put(showParts.toString(), showSources.getJSONObject(key.toString()).get(showParts.toString()));
            }
            results.put(resultObject);
            resultObject = new JSONObject();
        }
        return results;
    }

    public static void createIndex() throws UnknownHostException {
        MongoConnector connector = new MongoConnector();
        MongoClient client = connector.connect();
        MongoDatabase db = connector.getDatabase();
        MongoCollection<Document> collection = connector.getCollection(db);
        BasicDBObject indexObject = new BasicDBObject();
        indexObject.put(MongoConstants.channelName, 1);
        indexObject.put(MongoConstants.showName, 1);
        indexObject.put(MongoConstants.showDate, 1);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(indexObject, indexOptions);
        client.close();
    }
}
