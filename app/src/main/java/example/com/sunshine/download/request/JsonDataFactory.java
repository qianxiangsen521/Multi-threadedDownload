package example.com.sunshine.download.request;

import android.text.TextUtils;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.com.sunshine.download.Http.entity.PlayUrlItem;
import example.com.sunshine.download.Http.entity.RadioInfo;

public class JsonDataFactory {


    public static ArrayList<RadioInfo> getRadioInfoList(String paramString) {
        ArrayList<RadioInfo> radioInfoList = new ArrayList<RadioInfo>();
        if (paramString == null || (paramString.trim().equals(""))) {
            return null;
        }
        try {
            JSONArray josnArray = new JSONArray(paramString);

            for (int i = 0; i < josnArray.length(); i++) {
                JSONObject jsonObject = josnArray.getJSONObject(i);
                RadioInfo radioInfo = new RadioInfo();
                if (jsonObject.has("id")) {
                    radioInfo
                            .setId(Integer.parseInt(jsonObject.getString("id")));
                }
                if (jsonObject.has("name")) {
                    radioInfo.setName(jsonObject.getString("name"));
                }
                if (jsonObject.has("current_play")) {
                    radioInfo.setCurrentPlay(jsonObject
                            .getString("current_play"));
                }
                if (jsonObject.has("recommend_language")) {
                    radioInfo.setRecommed(jsonObject
                            .getString("recommend_language"));
                }
                if (jsonObject.has("Introduction")) {
                    radioInfo.setIntroduction(jsonObject
                            .getString("Introduction"));
                }
                if (jsonObject.has("current_play_time")) {
                    radioInfo.setCurrentPlayTime(jsonObject
                            .getString("current_play_time"));
                }
                if (jsonObject.has("img_url")) {
                    radioInfo.setImgUrl(jsonObject.getString("img_url"));
                }
                if (jsonObject.has("broadcaster")) {
                    radioInfo.setBroadCaster(jsonObject
                            .getString("broadcaster"));
                }
                if (jsonObject.has("length")) {
                    radioInfo.setPlayLength(jsonObject.getString("length"));
                }
                if (jsonObject.has("type")) {
                    radioInfo.setType(jsonObject.getString("type"));
                }
                if (jsonObject.has("playbill_url")) {
                    radioInfo.setRadioBillUrl(jsonObject
                            .getString("playbill_url"));
                }
                if (jsonObject.has("praise")) {
                    radioInfo.setPraiseUrl(jsonObject.getString("praise"));
                }
                if (jsonObject.has("enjoy_url")) {
                    radioInfo.setEnjoyUrl(jsonObject.getString("enjoy_url"));
                }
                if (jsonObject.has("more_enjoy_url")) {
                    radioInfo.setMoreEnjoyUrl(jsonObject
                            .getString("more_enjoy_url"));
                }
                if (jsonObject.has("album_list_url")) {
                    radioInfo.setAlbumListUrl(jsonObject
                            .getString("album_list_url"));
                }
                if (jsonObject.has("album_Introduction_url")) {
                    radioInfo.setAlbumIntroductionUrl(jsonObject
                            .getString("album_Introduction_url"));
                }
                if (jsonObject.has("album_img")) {
                    radioInfo.setAlbumDetailImgUrl(jsonObject
                            .getString("album_img"));
                }
                if (jsonObject.has("play_url")) {
                    JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
                    parsePlayUrl(tvjsonArray, radioInfo);
                }
                if (jsonObject.has("play_count")) {
                    radioInfo.setPlayCount(jsonObject.getString("play_count"));
                }
                if (jsonObject.has("is_collect")) {
                    radioInfo.setCollect(jsonObject.getBoolean("is_collect"));
                }
                if (jsonObject.has("aid")) {
                    radioInfo.setAid(jsonObject.getString("aid"));
                }
                if (jsonObject.has("download_url")) {
                    radioInfo.setDownloadUrl(jsonObject
                            .getString("download_url"));
                }
                // 专辑或者节目
                if (jsonObject.has("play_url")
                        && !radioInfo.getType().equals("4")
                        && !radioInfo.getType().equals("5")) {
                    JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
                    parsePlayUrl(tvjsonArray, radioInfo);
                }

                if (jsonObject.has("albums_play")
                        && (radioInfo.getType().equals("4") || radioInfo
                        .getType().equals("5"))) {
                    // JSONArray tvjsonArray = jsonObject
                    // .getJSONArray("albums_play");
                    // parseAlbumUrl(tvjsonArray, radioInfo);
                }

                if (jsonObject.has("albums_url")) {
                    radioInfo.setAlbums_url(jsonObject.getString("albums_url"));
                }
                if (jsonObject.has("album_id")) {
                    radioInfo.setAlbumId(Integer.parseInt(jsonObject
                            .getString("album_id")));
                }
                if (jsonObject.has("is_fee")) {
                    radioInfo.setIs_fee(jsonObject.getInt("is_fee"));
                }
                if (jsonObject.has("fee")) {
                    radioInfo.setFee(jsonObject.getInt("fee"));
                }
                if (jsonObject.has("recommend_pos_id")) {
                    radioInfo.setRecommend_pos_id(Integer.parseInt(jsonObject
                            .getString("recommend_pos_id")));
                }
                if (jsonObject.has("auto_model")) {
                    radioInfo.setAuto_model(jsonObject.getInt("auto_model"));
                }
                if (jsonObject.has("albumname")) {
                    radioInfo.setAlbum_name(jsonObject.getString("albumname"));
                }
                if (radioInfo.getType().equals(8)) {
                    if (!TextUtils.isEmpty(radioInfo.getDownloadUrl())) {
                        radioInfoList.add(radioInfo);
                    }
                } else {
                    radioInfoList.add(radioInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return radioInfoList;
    }

    private static void parsePlayUrl(JSONArray jsnArray, RadioInfo radioInfo) {
        ArrayList<PlayUrlItem> playUrlList = new ArrayList<PlayUrlItem>();
        if (jsnArray == null || jsnArray.length() == 0) {
            return;
        }
        try {
            for (int i = 0; i < jsnArray.length(); i++) {
                JSONObject jsonObject = jsnArray.getJSONObject(i);
                PlayUrlItem playUrlItem = new PlayUrlItem();
                if (jsonObject.has("id"))
                    if (!jsonObject.getString("id").equals("")) {
                        playUrlItem.setId(Integer.parseInt(jsonObject
                                .getString("id")));
                    }
                if (jsonObject.has("play_url"))
                    playUrlItem.setUrl(jsonObject.getString("play_url"));
                playUrlList.add(playUrlItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        radioInfo.setPlayUrlList(playUrlList);
    }

    public static ArrayList<PlayUrlItem> parseAlbumUrl(String paramString) {

        ArrayList<PlayUrlItem> playUrlList = new ArrayList<PlayUrlItem>();
        if (paramString == null || (paramString.trim().equals(""))) {
            return null;
        }
        try {
            JSONArray josnArray = new JSONArray(paramString);
            for (int i = 0; i < josnArray.length(); i++) {
                JSONObject jsonObject = josnArray.getJSONObject(i);
                PlayUrlItem playUrlItem = new PlayUrlItem();
                if (jsonObject.has("id")) {
                    if (!jsonObject.getString("id").equals("")) {
                        playUrlItem.setId(Integer.parseInt(jsonObject
                                .getString("id")));
                    }
                }
                if (jsonObject.has("play_url")) {
                    playUrlItem.setUrl(jsonObject.getString("play_url"));
                }
                if (jsonObject.has("name")) {
                    playUrlItem.setName(jsonObject.getString("name"));
                }
                playUrlList.add(playUrlItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playUrlList;
    }

//    private static ArrayList<CategorySubCityModelInfo> parseSubCityList(
//            JSONArray jsnArray) {
//        ArrayList<CategorySubCityModelInfo> subCityList = null;
//        try {
//            subCityList = new ArrayList<CategorySubCityModelInfo>();
//            for (int i = 0; i < jsnArray.length(); i++) {
//                JSONObject jsonObject = jsnArray.getJSONObject(i);
//                CategorySubCityModelInfo radioInfo = new CategorySubCityModelInfo();
//                if (jsonObject.has("name")) {
//                    if (!jsonObject.getString("name").equals("null")) {
//                        radioInfo.setChannelName(jsonObject.getString("name"));
//                    }
//                }
//
//                if (jsonObject.has("url")) {
//                    radioInfo.setChannelUrl(jsonObject.getString("url"));
//                }
//                subCityList.add(radioInfo);
//            }
//        } catch (JSONException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//
//        return subCityList;
//    }

    private static ArrayList<RadioInfo> parseRadioInfoList(JSONArray jsnArray) {
        ArrayList<RadioInfo> radioInfoList = null;
        try {
            radioInfoList = new ArrayList<RadioInfo>();
            for (int i = 0; i < jsnArray.length(); i++) {
                JSONObject jsonObject = jsnArray.getJSONObject(i);
                RadioInfo radioInfo = new RadioInfo();
                if (jsonObject.has("id")) {
                    if (!jsonObject.getString("id").equals("null")) {
                        radioInfo.setId(Integer.parseInt(jsonObject
                                .getString("id")));
                    }
                }
                if (jsonObject.has("name")) {
                    radioInfo.setName(jsonObject.getString("name"));
                }
                if (jsonObject.has("current_play")) {
                    radioInfo.setCurrentPlay(jsonObject
                            .getString("current_play"));
                }
                if (jsonObject.has("recommend_language")) {
                    radioInfo.setRecommed(jsonObject
                            .getString("recommend_language"));
                }
                if (jsonObject.has("Introduction")) {
                    radioInfo.setIntroduction(jsonObject
                            .getString("Introduction"));
                }
                if (jsonObject.has("current_play_time")) {
                    radioInfo.setCurrentPlayTime(jsonObject
                            .getString("current_play_time"));
                }
                if (jsonObject.has("img_url")) {
                    radioInfo.setImgUrl(jsonObject.getString("img_url"));
                }
                if (jsonObject.has("broadcaster")) {
                    radioInfo.setBroadCaster(jsonObject
                            .getString("broadcaster"));
                }
                if (jsonObject.has("length")) {
                    radioInfo.setPlayLength(jsonObject.getString("length"));
                }
                if (jsonObject.has("type")) {
                    radioInfo.setType(jsonObject.getString("type"));
                }
                if (jsonObject.has("playbill_url")) {
                    radioInfo.setRadioBillUrl(jsonObject
                            .getString("playbill_url"));
                }
                if (jsonObject.has("praise")) {
                    radioInfo.setPraiseUrl(jsonObject.getString("praise"));
                }
                if (jsonObject.has("enjoy_url")) {
                    radioInfo.setEnjoyUrl(jsonObject.getString("enjoy_url"));
                }
                if (jsonObject.has("more_enjoy_url")) {
                    radioInfo.setMoreEnjoyUrl(jsonObject
                            .getString("more_enjoy_url"));
                }
                if (jsonObject.has("album_list_url")) {
                    radioInfo.setAlbumListUrl(jsonObject
                            .getString("album_list_url"));
                }
                if (jsonObject.has("album_Introduction_url")) {
                    radioInfo.setAlbumIntroductionUrl(jsonObject
                            .getString("album_Introduction_url"));
                }
                if (jsonObject.has("album_img")) {
                    radioInfo.setAlbumDetailImgUrl(jsonObject
                            .getString("album_img"));
                }
                // 专辑或者节目
                if (jsonObject.has("play_url")
                        && !radioInfo.getType().equals("4")
                        && !radioInfo.getType().equals("5")) {
                    JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
                    parsePlayUrl(tvjsonArray, radioInfo);
                }

                if (jsonObject.has("albums_play")
                        && (radioInfo.getType().equals("4") || radioInfo
                        .getType().equals("5"))) {
                    // JSONArray tvjsonArray = jsonObject
                    // .getJSONArray("albums_play");
                    // parseAlbumUrl(tvjsonArray, radioInfo);
                }
                if (jsonObject.has("play_count")) {
                    radioInfo.setPlayCount(jsonObject.getString("play_count"));
                }
                if (jsonObject.has("is_collect")) {
                    radioInfo.setCollect(jsonObject.getBoolean("is_collect"));
                }
                if (jsonObject.has("download_url")) {
                    radioInfo.setDownloadUrl(jsonObject
                            .getString("download_url"));
                }
                if (jsonObject.has("album_id")) {
                    String album_id = jsonObject
                            .getString("album_id");
                    if (!album_id.equals("null")){
                        radioInfo.setAlbumId(Integer.parseInt(album_id));
                    }else{
                        radioInfo.setAlbumId(0);
                    }
                }
                if (jsonObject.has("albums_url")) {
                    radioInfo.setAlbums_url(jsonObject.getString("albums_url"));
                }
                if (jsonObject.has("is_fee")) {
                    radioInfo.setIs_fee(jsonObject.getInt("is_fee"));
                }
                if (jsonObject.has("fee")) {
                    radioInfo.setFee(jsonObject.getInt("fee"));
                }
                if (jsonObject.has("recommend_pos_id")) {
                    radioInfo.setRecommend_pos_id(Integer.parseInt(jsonObject
                            .getString("recommend_pos_id")));
                }
                if (jsonObject.has("auto_model")) {
                    radioInfo.setAuto_model(jsonObject.getInt("auto_model"));
                }
                if (jsonObject.has("album_name")) {
                    radioInfo.setAlbum_name(jsonObject.getString("album_name"));
                }
                if (jsonObject.has("album_id")) {
                    radioInfo.setAlbum_id(jsonObject.getString("album_id"));
                }
                radioInfoList.add(radioInfo);
            }
        } catch (JSONException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return radioInfoList;
    }

//    private static ArrayList<FavStatusInfo> parseFavStatusList(
//            JSONArray jsnArray) {
//        ArrayList<FavStatusInfo> favStatusList = new ArrayList<FavStatusInfo>();
//        try {
//            for (int i = 0; i < jsnArray.length(); i++) {
//                JSONObject jsonObject = jsnArray.getJSONObject(i);
//                FavStatusInfo favStatusInfo = new FavStatusInfo();
//                if (jsonObject.has("id")) {
//                    if (!jsonObject.getString("id").equals("null")) {
//                        favStatusInfo.setId(Integer.parseInt(jsonObject
//                                .getString("id")));
//                    }
//                }
//                if (jsonObject.has("type")) {
//                    favStatusInfo.setType(jsonObject.getString("type"));
//                }
//
//                if (jsonObject.has("status")) {
//                    if (!jsonObject.getString("status").equals("null")) {
//                        favStatusInfo.setIsFav(Integer.parseInt(jsonObject
//                                .getString("status")));
//                    }
//
//                }
//                favStatusList.add(favStatusInfo);
//            }
//        } catch (JSONException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//
//        return favStatusList;
//    }

    public static ArrayList<CategoryInfoList> parseCategoryIndex(String str) {
        if ((str == null) || str.length() <= 0) {
            return null;
        }
        ArrayList<CategoryInfoList> categoryList = new ArrayList<CategoryInfoList>();

        try {
            JSONArray jsonArray = new JSONArray(str);
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;

            }
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                CategoryInfoList categoryInfoList = new CategoryInfoList();
                if (obj.has("type_id")) {
                    categoryInfoList.setCategoryType(Integer.parseInt(obj
                            .getString("type_id")));
                }
                if (obj.has("title")) {
                    categoryInfoList.setCategoryName(obj.getString("title"));
                }
                if (obj.has("contents")) {
                    JSONArray contentJsonArray = obj.getJSONArray("contents");
                    parseCategoryIndexInner(contentJsonArray, categoryInfoList);
                }

                categoryList.add(categoryInfoList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryList;

    }

    private static void parseCategoryIndexInner(JSONArray jsonArray,
                                                CategoryInfoList categoryInfoList) {
        if (jsonArray == null || jsonArray.length() <= 0) {
            return;
        }
        ArrayList<CategoryInfo> listInfo = new ArrayList<CategoryInfo>();
        parseCategory(listInfo, jsonArray);
        categoryInfoList.setCategoryList(listInfo);
    }

    // public static ArrayList<CategoryInfo> parseHomeCenterCategory(String str)
    // {
    // if ((str == null) || str.length() <= 0) {
    // return null;
    // }
    // ArrayList<CategoryInfo> listInfo = new ArrayList<CategoryInfo>();
    // try {
    // JSONArray jsonArray = new JSONArray(str);
    // if (jsonArray == null || jsonArray.length() <= 0) {
    // return null;
    //
    // }
    // parseCategory(listInfo, jsonArray);
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // return listInfo;
    // }

    private static void parseCategory(ArrayList<CategoryInfo> listInfo,
                                      JSONArray jsonArray) {
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                CategoryInfo categoryInfo = new CategoryInfo();
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.has("id")) {
                    categoryInfo.setId(obj.getString("id"));
                }
                if (obj.has("name")) {
                    categoryInfo.setName(obj.getString("name"));
                }
                if (obj.has("detail_url")) {
                    categoryInfo.setDetailUrl(obj.getString("detail_url"));
                }
                if (obj.has("img_url")) {
                    categoryInfo.setImgUrl(obj.getString("img_url"));
                }
                if (obj.has("type_id")) {
                    categoryInfo.setType(obj.getString("type_id"));
                }
                if (obj.has("area_id")) {
                    categoryInfo.setAreaId(obj.getString("area_id"));
                }
                if (obj.has("cat_id")) {
                    categoryInfo.setCatID(obj.getString("cat_id"));
                }
                listInfo.add(categoryInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private static void parseCategoryChoose(JSONArray jsonArray,
//                                            CategoryChooseList cateChooseList) {
//        if (jsonArray == null || jsonArray.length() <= 0) {
//            return;
//        }
//        ArrayList<CategoryChooseInfo> listInfo = new ArrayList<CategoryChooseInfo>();
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//
//                listInfo.add(parseCategoryChoose(obj));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        cateChooseList.setCategoryList(listInfo);
//    }
//
//    private static CategoryChooseInfo parseCategoryChoose(JSONObject jsonObject) {
//        CategoryChooseInfo chooseInfo = new CategoryChooseInfo();
//        try {
//            if (jsonObject.has("id")) {
//                chooseInfo.setId(jsonObject.getString("id"));
//            }
//            if (jsonObject.has("name")) {
//                chooseInfo.setName(jsonObject.getString("name"));
//            }
//            if (jsonObject.has("img_url")) {
//                chooseInfo.setImgUrl(jsonObject.getString("img_url"));
//            }
//            if (jsonObject.has("detail_url")) {
//                chooseInfo.setDetailUrl(jsonObject.getString("detail_url"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return chooseInfo;
//    }
//
//    public static ArrayList<CategoryChooseList> parseCategoryChoose(String str) {
//        if ((str == null) || str.length() <= 0) {
//            return null;
//        }
//        ArrayList<CategoryChooseList> categoryChooseLists = new ArrayList<CategoryChooseList>();
//        try {
//            JSONArray jsonArray = new JSONArray(str);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject obj = jsonArray.getJSONObject(i);
//                CategoryChooseList categoryInfoList = new CategoryChooseList();
//                if (obj.has("title")) {
//                    categoryInfoList.setChooseName(obj.getString("title"));
//                }
//                if (obj.has("contents")) {
//                    JSONArray contentJsonArray = obj.getJSONArray("contents");
//                    parseCategoryChoose(contentJsonArray, categoryInfoList);
//                }
//
//                categoryChooseLists.add(categoryInfoList);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return categoryChooseLists;
//    }
//
//    public static ArrayList<CategoryCommList> parseCategoryCommLive(String str) {
//        if ((str == null) || str.length() <= 0) {
//            return null;
//        }
//        ArrayList<CategoryCommList> categoryCommList = new ArrayList<CategoryCommList>();
//        try {
//            JSONArray jsonArray = new JSONArray(str);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject obj = jsonArray.getJSONObject(i);
//                CategoryCommList categoryInfoList = new CategoryCommList();
//                if (obj.has("title")) {
//                    categoryInfoList.setCategoryName(obj.getString("title"));
//                }
//                if (obj.has("contents")) {
//                    JSONArray contentJsonArray = obj.getJSONArray("contents");
//                    parseRadioInfo(contentJsonArray, categoryInfoList);
//                }
//
//                categoryCommList.add(categoryInfoList);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return categoryCommList;
//    }
//
//    private static void parseRadioInfo(JSONArray jsonArray,
//                                       CategoryCommList categoryInfoList) {
//        if (jsonArray == null || jsonArray.length() <= 0) {
//            return;
//        }
//        ArrayList<RadioInfo> listInfo = new ArrayList<RadioInfo>();
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//
//                listInfo.add(parseRaidoInfo(obj));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        categoryInfoList.setCategoryList(listInfo);
//    }

    private static RadioInfo parseRaidoInfo(JSONObject jsonObject) {
        RadioInfo radioInfo = new RadioInfo();
        try {
            if (jsonObject.has("id")) {
                radioInfo.setId(Integer.parseInt(jsonObject.getString("id")));
            }
            if (jsonObject.has("name")) {
                radioInfo.setName(jsonObject.getString("name"));
            }
            if (jsonObject.has("current_play")) {
                radioInfo.setCurrentPlay(jsonObject.getString("current_play"));
            }
            if (jsonObject.has("recommend_language")) {
                radioInfo.setRecommed(jsonObject
                        .getString("recommend_language"));
            }
            if (jsonObject.has("Introduction")) {
                radioInfo.setIntroduction(jsonObject.getString("Introduction"));
            }
            if (jsonObject.has("current_play_time")) {
                radioInfo.setCurrentPlayTime(jsonObject
                        .getString("current_play_time"));
            }
            if (jsonObject.has("img_url")) {
                radioInfo.setImgUrl(jsonObject.getString("img_url"));
            }
            if (jsonObject.has("broadcaster")) {
                radioInfo.setBroadCaster(jsonObject.getString("broadcaster"));
            }
            if (jsonObject.has("length")) {
                radioInfo.setPlayLength(jsonObject.getString("length"));
            }
            if (jsonObject.has("type")) {
                radioInfo.setType(jsonObject.getString("type"));
            }
            if (jsonObject.has("playbill_url")) {
                radioInfo.setRadioBillUrl(jsonObject.getString("playbill_url"));
            }
            if (jsonObject.has("praise")) {
                radioInfo.setPraiseUrl(jsonObject.getString("praise"));
            }
            if (jsonObject.has("enjoy_url")) {
                radioInfo.setEnjoyUrl(jsonObject.getString("enjoy_url"));
            }
            if (jsonObject.has("more_enjoy_url")) {
                radioInfo.setMoreEnjoyUrl(jsonObject
                        .getString("more_enjoy_url"));
            }
            if (jsonObject.has("download_url")) {
                radioInfo.setDownloadUrl(jsonObject.getString("download_url"));
            }
            if (jsonObject.has("play_url")) {
                JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
                parsePlayUrl(tvjsonArray, radioInfo);
            }
            if (jsonObject.has("play_count")) {
                radioInfo.setPlayCount(jsonObject.getString("play_count"));
            }
            if (jsonObject.has("is_fee")) {
                radioInfo.setIs_fee(jsonObject.getInt("is_fee"));
            }
            if (jsonObject.has("fee")) {
                radioInfo.setFee(jsonObject.getInt("fee"));
            }
            if (jsonObject.has("auto_model")) {
                radioInfo.setAuto_model(jsonObject.getInt("auto_model"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return radioInfo;
    }

//    public static ArrayList<AlbumList> parseAlbumList(String str) {
//        if ((str == null) || str.length() <= 0) {
//            return null;
//        }
//        ArrayList<AlbumList> categoryCommList = new ArrayList<AlbumList>();
//        try {
//            JSONArray jsonArray = new JSONArray(str);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject obj = jsonArray.getJSONObject(i);
//                AlbumList categoryInfoList = new AlbumList();
//                if (obj.has("title")) {
//                    categoryInfoList.setCategoryName(obj.getString("title"));
//                }
//                if (i == 0) {
//                    if (obj.has("contents")) {
//                        if (!obj.getString("contents").equals("null")) {
//                            JSONArray contentJsonArray = obj
//                                    .getJSONArray("contents");
//                            parseAlbumInfo(contentJsonArray, categoryInfoList);
//                        }
//
//                    }
//                } else {
//                    if (obj.has("contents")) {
//                        JSONObject jsonObject = obj.getJSONObject("contents");
//                        ArrayList<HomeMoreInfo> info = getAlumbContents(jsonObject);
//                        if (info != null && info.size() >= 1) {
//                            categoryInfoList.setCategoryList(info.get(0)
//                                    .getRadioList());
//                        }
//
//                    }
//                }
//
//                categoryCommList.add(categoryInfoList);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return categoryCommList;
//    }
//
//    private static void parseAlbumInfo(JSONArray jsonArray,
//                                       AlbumList categoryInfoList) {
//        if (jsonArray == null || jsonArray.length() <= 0) {
//            return;
//        }
//        ArrayList<RadioInfo> listInfo = new ArrayList<RadioInfo>();
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//
//                listInfo.add(parseRaidoInfo(obj));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        categoryInfoList.setCategoryList(listInfo);
//    }

    public static ArrayList<CategoryRadioInfo> getHomeCategoryList(
            String paramString) {
        ArrayList<CategoryRadioInfo> localArrayList = new ArrayList<CategoryRadioInfo>();
        if ((paramString == null) || (paramString.trim().equals("")))
            localArrayList = null;
        try {
            JSONArray jsonArray = new JSONArray(paramString.toString());
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                CategoryRadioInfo info = new CategoryRadioInfo();
                if (item.has("title")) {
                    info.setTitle(item.get("title").toString());
                }
                if (item.has("more_contents_url")) {
                    info.setMoreContent(item.get("more_contents_url")
                            .toString());
                }
                if (item.has("index_type")) {
                    info.setIndexType(item.getInt("index_type"));
                }
                if (item.has("contents")) {
                    if (!item.isNull("contents")) {
                        JSONArray tvjsonArray = item.getJSONArray("contents");
                        info.setRadioInfos(parseRadioInfoList(tvjsonArray));
                        if (tvjsonArray.length() > 0)
                            localArrayList.add(info);
                    } else {
                        localArrayList.add(info);
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return localArrayList;
    }

//    public static ArrayList<HotSearchInfo> gethotInfoList(String paramString) {
//        ArrayList<HotSearchInfo> infos = new ArrayList<HotSearchInfo>();
//        if ((paramString == null) || (paramString.trim().equals("")))
//            infos = null;
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                HotSearchInfo info = new HotSearchInfo();
//                if (item.has("id")) {
//                    info.setId(item.get("id").toString());
//                }
//                if (item.has("name")) {
//                    info.setName(item.get("name").toString());
//                }
//                if (item.has("detail_url")) {
//                    info.setSearchUrl(item.getString("detail_url").toString());
//                }
//                infos.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return infos;
//    }

    public static ArrayList<RadioInfo> parseAlbumDetailList(String str) {
        if ((str == null) || str.length() <= 0) {
            return null;
        }
        ArrayList<RadioInfo> categoryCommList = new ArrayList<RadioInfo>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;

            }
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                categoryCommList.add(parseRaidoInfo(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoryCommList;
    }

//    public static ArrayList<RankingList> getRankingList(String paramString) {
//
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        ArrayList<RankingList> localArrayList = new ArrayList<RankingList>();
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//            // JSONArray jsonArray = new JSONArray(paramString);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                RankingList info = new RankingList();
//                if (item.has("title")) {
//                    info.setName(item.get("title").toString());
//                }
//                if (item.has("more_url")) {
//                    info.setMoreUrl(item.get("more_url").toString());
//                }
//
//                if (item.has("id")) {
//                    info.setId(item.get("id").toString());
//                }
//                if (item.has("contents")) {
//                    JSONArray tvjsonArray = item.getJSONArray("contents");
//                    info.setRadioList(parseRadioInfoList(tvjsonArray));
//                    if (tvjsonArray.length() > 0)
//                        localArrayList.add(info);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }
//
//    public static ArrayList<CategoryLiveListInfo> getCategoryLiveCityList(
//            String paramString) {
//
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        ArrayList<CategoryLiveListInfo> localArrayList = new ArrayList<CategoryLiveListInfo>();
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                CategoryLiveListInfo info = new CategoryLiveListInfo();
//                if (item.has("name")) {
//                    info.setName(item.get("name").toString());
//                }
//
//                if (item.has("sub_area")) {
//                    JSONArray tvjsonArray = item.getJSONArray("sub_area");
//                    info.setSubCityList(parseRadioInfoList(tvjsonArray));
//                    if (tvjsonArray.length() > 0)
//                        localArrayList.add(info);
//                }
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }
//
//    public static RankingFavList getRankFavList(String paramString) {
//
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        RankingFavList localArrayList = new RankingFavList();
//        try {
//            JSONObject item = new JSONObject(paramString);
//            if (item.has("cate")) {
//                localArrayList.setCate(item.get("cate").toString());
//            }
//            if (item.has("contents")) {
//                JSONArray tvjsonArray = item.getJSONArray("contents");
//                localArrayList
//                        .setFavStatusList(parseFavStatusList(tvjsonArray));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }

    public static String getAddFavStatus(String paramString) {

        if ((paramString == null) || (paramString.trim().equals("")))
            return null;

        String addFavStatus = null;
        try {
            JSONObject item = new JSONObject(paramString);
            if (item.has("status")) {
                addFavStatus = String.valueOf(item.getInt("status"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addFavStatus;
    }

//    public static ArrayList<HomeMoreInfo> getHomeMoreList(String paramString) {
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        ArrayList<HomeMoreInfo> localArrayList = new ArrayList<HomeMoreInfo>();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//            HomeMoreInfo info = new HomeMoreInfo();
//            if (jObject.has("count")) {
//                info.setCount(jObject.get("count").toString());
//            }
//            if (jObject.has("contents")) {
//                JSONArray tvjsonArray = jObject.getJSONArray("contents");
//                info.setRadioList(parseRadioInfoList(tvjsonArray));
//                if (tvjsonArray.length() > 0) {
//                    localArrayList.add(info);
//                } else {
//                    return null;
//                }
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }
//
//    public static HomeMoreInfo getCatChannelList(String paramString) {
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        HomeMoreInfo info = new HomeMoreInfo();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//            // HomeMoreInfo info = new HomeMoreInfo();
//            if (jObject.has("count")) {
//                info.setCount(jObject.get("count").toString());
//            }
//            if (jObject.has("contents")) {
//                JSONArray tvjsonArray = jObject.getJSONArray("contents");
//                info.setRadioList(parseRadioInfoList(tvjsonArray));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return info;
//    }
//
//    public static ArrayList<HomeMoreInfo> getAlumbContents(JSONObject obj) {
//
//        ArrayList<HomeMoreInfo> localArrayList = new ArrayList<HomeMoreInfo>();
//        try {
//            if (obj == null || obj.length() <= 0) {
//                return null;
//            }
//            HomeMoreInfo info = new HomeMoreInfo();
//            if (obj.has("count")) {
//                info.setCount(obj.get("count").toString());
//            }
//            if (obj.has("contents")) {
//                JSONArray tvjsonArray = obj.getJSONArray("contents");
//                info.setRadioList(parseRadioInfoList(tvjsonArray));
//                if (tvjsonArray.length() > 0)
//                    localArrayList.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }

    public static ArrayList<RadioInfo> parseAllRanking(String str) {
        if ((str == null) || str.length() <= 0) {
            return null;
        }
        ArrayList<RadioInfo> categoryCommList = new ArrayList<RadioInfo>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;

            }
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                categoryCommList.add(parseRaidoInfo(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoryCommList;
    }

//    public static ArrayList<FavList> parseAllFav(String paramString) {
//
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//
//        ArrayList<FavList> localArrayList = new ArrayList<FavList>();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//            // JSONArray jsonArray = new JSONArray(paramString);
//            FavList info = new FavList();
//            if (jObject.has("totalcount")) {
//                info.setCount(jObject.get("totalcount").toString());
//            }
//            if (jObject.has("totalPage")) {
//                info.setTotalPage(jObject.get("totalPage").toString());
//            }
//
//            if (jObject.has("status")) {
//                info.setStatus(jObject.get("status").toString());
//            }
//            if (jObject.has("content")) {
//                JSONArray tvjsonArray = jObject.getJSONArray("content");
//                info.setRadioList(parseRadioInfoList(tvjsonArray));
//                if (tvjsonArray.length() > 0)
//                    localArrayList.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return localArrayList;
//    }
//
//    public static ArrayList<Province> getProvinceList(String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        ArrayList<Province> provinces = new ArrayList<Province>();
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                Province info = new Province();
//                if (item.has("id")) {
//                    info.setProId(item.get("id").toString());
//                }
//                if (item.has("name")) {
//                    info.setName(item.get("name").toString());
//                }
//                if (item.has("detail_url")) {
//                    info.setProUrl(item.getString("detail_url").toString());
//                }
//                provinces.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return provinces;
//    }
//
//    public static ArrayList<PlayCardInfo> getProgramList(String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        ArrayList<PlayCardInfo> infos = new ArrayList<PlayCardInfo>();
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                PlayCardInfo info = new PlayCardInfo();
//                if (item.has("id")) {
//                    info.setId(Integer.valueOf((String) item.get("id")));
//                }
//                if (item.has("channel_id")) {
//                    info.setChannleId(item.getString("channel_id"));
//                }
//                if (item.has("ht_date")) {
//                    info.setHtData(item.getString("ht_date"));
//                }
//                if (item.has("name")) {
//                    info.setName(item.get("name").toString());
//                }
//                if (item.has("play_time_begin")) {
//                    info.setPlayTimeStart(item.get("play_time_begin")
//                            .toString());
//                }
//                if (item.has("play_time_end")) {
//                    info.setPlayTimeEnd(item.getString("play_time_end")
//                            .toString());
//                }
//                if (item.has("play_state")) {
//                    info.setState(item.getString("play_state").toString());
//                }
//                if (item.has("play_url")) {
//                    JSONArray tvjsonArray = item.getJSONArray("play_url");
//                    indexCallListener(tvjsonArray, info);
//                }
//                infos.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return infos;
//    }
//
//    private static void indexCallListener(JSONArray jsnArray,
//                                          PlayCardInfo paCardInfo) {
//        ArrayList<PlayUrlItem> playUrlList = new ArrayList<PlayUrlItem>();
//        try {
//            for (int i = 0; i < jsnArray.length(); i++) {
//                JSONObject jsonObject = jsnArray.getJSONObject(i);
//                PlayUrlItem playUrlItem = new PlayUrlItem();
//                if (jsonObject.has("id"))
//                    playUrlItem.setId(Integer.parseInt(jsonObject
//                            .getString("id")));
//                if (jsonObject.has("play_url"))
//                    playUrlItem.setUrl(jsonObject.getString("play_url"));
//                playUrlList.add(playUrlItem);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        paCardInfo.setPlayUrlList(playUrlList);
//    }
//
//    public static VersionInfo getVersionInfo(String paramString) {
//        VersionInfo info = new VersionInfo();
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//        if (paramString.toString().length() == 1
//                || paramString.toString().length() == 4) {
//            return null;
//        }
//        try {
//            JSONObject item = new JSONObject(paramString);
//            if (item.has("package"))
//                info.setPackageName(item.getString("package"));
//            if (item.has("download_url"))
//                info.setDownloadUrl(item.getString("download_url"));
//            if (item.has("version_name"))
//                info.setVersionName(item.getString("version_name"));
//            if (item.has("version")) {
//                String version = item.getString("version");
//                if (null == version || "".equals(version)
//                        || "null".equals(version)) {
//                    info.setVersion(0);
//                } else {
//                    info.setVersion(Long.parseLong(version));
//                }
//            }
//            if (item.has("content")) {
//                info.setOptimization(item.getString("content"));
//            }
//            // String version = item.getString("version");
//            // String optimization = item.getString("optimization_content");
//            // if (item.has("is_forceupgrade")) {//强制升级字段，现在没有使用
//            // String is_forceupgrade = item.getString("is_forceupgrade");
//            // info.setIs_forceupgrade(is_forceupgrade);
//            // }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return info;
//    }
//
//    public static StatusInfo getStatusInfo(String paramString) {
//        StatusInfo statusInfo = new StatusInfo();
//        if ((paramString == null) || (paramString.trim().equals("")))
//            return null;
//        try {
//            JSONObject jsonObject = new JSONObject(paramString);
//            String status = jsonObject.getString("status");
//            statusInfo.setStatus(status);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return statusInfo;
//    }

    public static String getFeedbackInfo(String paramString) {
        String status = null;
        if ((paramString == null) || (paramString.trim().equals("")))
            return null;
        try {
            JSONObject item = new JSONObject(paramString);
            status = item.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String getFavDelInfo(String paramString) {
        String status = null;
        if ((paramString == null) || (paramString.trim().equals("")))
            return null;

        try {
            JSONObject item = new JSONObject(paramString);
            status = item.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static ArrayList<RadioInfo> getHitShowInfoList(String paramString) {
        if ((paramString == null) || paramString.length() <= 0) {
            return null;
        }
        ArrayList<RadioInfo> infos = new ArrayList<RadioInfo>();
        try {
            JSONArray jsonArray = new JSONArray(paramString);
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                RadioInfo info = new RadioInfo();
                if (item.has("id")) {
                    info.setId(Integer.valueOf((String) item.get("id")));
                }
                if (item.has("name")) {
                    info.setName(item.get("name").toString());
                }
                if (item.has("img_url")) {
                    info.setImgUrl(item.get("img_url").toString());
                }
                if (item.has("detail_url")) {
                    info.setAlbumListUrl(item.getString("detail_url")
                            .toString());
                }
                infos.add(info);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }

//    public static ArrayList<CategoryProvinceInfo> getCategoryProvinceList(
//            String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        ArrayList<CategoryProvinceInfo> infos = new ArrayList<CategoryProvinceInfo>();
//        try {
//            JSONArray jsonArray = new JSONArray(paramString);
//            if (jsonArray == null || jsonArray.length() <= 0) {
//                return null;
//            }
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject item = jsonArray.getJSONObject(i);
//                CategoryProvinceInfo info = new CategoryProvinceInfo();
//                if (item.has("id")) {
//                    info.setId(item.get("id").toString());
//                }
//                if (item.has("name")) {
//                    info.setName(item.get("name").toString());
//                }
//                if (item.has("type_id")) {
//                    info.setType_id(item.get("type_id").toString());
//                }
//                if (item.has("sub_area")) {
//                    info.setSub_area(item.get("sub_area").toString());
//                }
//                infos.add(info);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return infos;
//    }

    public static RadioInfo getCategoryReqMoreDetailIntro(String paramString) {
        if ((paramString == null) || paramString.length() <= 0) {
            return null;
        }
        RadioInfo info = null;
        try {
            JSONObject item = new JSONObject(paramString);
            info = new RadioInfo();
            if (item.has("id")) {
                if (!item.get("id").equals("")) {
                    info.setId(Integer.valueOf((String) item.get("id")));
                }
            }
            if (item.has("name")) {
                info.setName(item.get("name").toString());
            }
            if (item.has("introduction")) {
                info.setIntroduction(item.get("introduction").toString());
            }
            if (item.has("album_img")) {
                info.setAlbumDetailImgUrl(item.get("album_img").toString());
            }
            if (item.has("is_collect")) {
                info.setCollect(item.getBoolean("is_collect"));
            }
            if (item.has("pay_status")) {
                info.setPay_status(item.getInt("pay_status"));
            }
            if (item.has("cms_album_id")) {
                info.setCms_album_id(item.getInt("cms_album_id"));
            }
            if (item.has("auto_model")) {
                info.setAuto_model(item.getInt("auto_model"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static RadioInfo getPlayerCurrentPlayInfo(String paramString) {
        if ((paramString == null) || paramString.length() <= 0) {
            return null;
        }
        RadioInfo info = null;
        try {
            JSONObject item = new JSONObject(paramString);
            info = new RadioInfo();
            if (item.has("id")) {
                if (!item.get("id").equals("")) {
                    // 这里在id为空时报类型转化异常
                    info.setId(Integer.valueOf((String) item.get("id")));
                }
            }
            if (item.has("name")) {
                info.setName(item.get("name").toString());
            }
            if (item.has("download_url")) {
                info.setDownloadUrl(item.get("download_url").toString());
            }
            if (item.has("programintro")) {
                info.setProgram_intro(item.get("programintro").toString());
            }
            if (item.has("album_id")) {
                info.setAlbum_id(item.get("album_id").toString());
            }
            if (item.has("album_name")) {
                info.setAlbum_name(item.get("album_name").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }
//
//    public static HitShowDetailList getHitDetailShowInfoList(String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        HitShowDetailList detailList = new HitShowDetailList();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//
//            // if (jObject.has("h_type")) {
//            // detailList.setType(jObject.get("h_type").toString());
//            // }
//            // if (jObject.has("contents")) {
//            // JSONObject jObject1 = new JSONObject(jObject.get("contents")
//            // .toString());
//            if (jObject.has("count")) {
//                detailList.setCount(Integer.valueOf(jObject.get("count")
//                        .toString()));
//            }
//
//            if (jObject.has("contents")) {
//                JSONArray tvjsonArray = jObject.getJSONArray("contents");
//                if (tvjsonArray.length() > 0)
//                    detailList.setRadioList(parseRadioInfoList(tvjsonArray));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return detailList;
//    }
//
//    public static CategoryRequestMoreList getCategoryReqMoreList(
//            String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        CategoryRequestMoreList detailList = new CategoryRequestMoreList();
//        ArrayList<RadioInfo> infos = new ArrayList<RadioInfo>();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//
//            if (jObject.has("is_type")) {
//                detailList.setType(jObject.get("is_type").toString());
//            }
//            if (jObject.has("contents")) {
//                JSONObject jObject1 = new JSONObject(jObject.get("contents")
//                        .toString());
//                if (jObject1.has("count")) {
//                    detailList.setCount(Integer.valueOf(jObject1.get("count")
//                            .toString()));
//
//                }
//                if (jObject1.has("contents")) {
//                    JSONArray tvjsonArray = jObject1.getJSONArray("contents");
//                    if (tvjsonArray.length() > 0)
//                        detailList
//                                .setRadioList(parseRadioInfoList(tvjsonArray));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return detailList;
//    }
//
//    public static CategoryRequestAlbumList getCategoryReqAlbumList(
//            String paramString) {
//        if ((paramString == null) || paramString.length() <= 0) {
//            return null;
//        }
//        CategoryRequestAlbumList detailList = new CategoryRequestAlbumList();
//        ArrayList<RadioInfo> infos = new ArrayList<RadioInfo>();
//        try {
//            JSONObject jObject = new JSONObject(paramString);
//            if (jObject == null || jObject.length() <= 0) {
//                return null;
//            }
//
//            if (jObject.has("order")) {
//                detailList.setOrder(Integer.valueOf(jObject.get("order")
//                        .toString()));
//            }
//            if (jObject.has("count")) {
//                detailList.setCount(Integer.valueOf(jObject.get("count")
//                        .toString()));
//            }
//            if (jObject.has("contents")) {
//
//                JSONArray tvjsonArray = jObject.getJSONArray("contents");
//                if (tvjsonArray.length() > 0)
//                    detailList.setRadioList(parseRadioInfoList(tvjsonArray));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return detailList;
//    }

    public static boolean getFavFlag(String paramString) {
        boolean isfav = false;
        if ((paramString == null) || (paramString.trim().equals("")))
            return (Boolean) null;
        try {
            JSONObject item = new JSONObject(paramString);
            isfav = item.getBoolean("connect");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isfav;
    }

    public static ArrayList<CommonHomeMoreAndCategoryInfo> getHomeCommonList(
            String paramString) {
        ArrayList<CommonHomeMoreAndCategoryInfo> localArrayList = new ArrayList<CommonHomeMoreAndCategoryInfo>();
        if ((paramString == null) || (paramString.trim().equals("")))
            localArrayList = null;
        try {
            JSONArray jsonArray = new JSONArray(paramString.toString());
            if (jsonArray == null || jsonArray.length() <= 0) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                CommonHomeMoreAndCategoryInfo info = new CommonHomeMoreAndCategoryInfo();
                if (item.has("title")) {
                    info.setTitleName(item.getString("title").toString());
                }
                if (item.has("cate_name")) {// 顶部显示标题
                    info.setCateType(item.getString("cate_name").toString());
                }
                if (item.has("more_url")) {
                    info.setMoreUrl(item.getString("more_url").toString());
                }
                if (item.has("contents")) {
                    if (!item.isNull("contents")) {
                        try {
                            JSONArray tvjsonArray = item
                                    .getJSONArray("contents");
                            info.setRadioInfos(parseRadioInfoList(tvjsonArray));
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        // if (tvjsonArray.length() > 0)
                    }
                }
                localArrayList.add(info);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return localArrayList;
    }

    public static PlayUrlItem getBackListener(String paramString) {
        if ((paramString == null) || paramString.length() <= 0) {
            return null;
        }
        PlayUrlItem playUrlItem = null;
        try {
            JSONObject jsonObject = new JSONObject(paramString);
            playUrlItem = new PlayUrlItem();
            if (jsonObject.has("id"))
                playUrlItem.setId(Integer.parseInt(jsonObject.getString("id")));
            if (jsonObject.has("play_url"))
                playUrlItem.setUrl(jsonObject.getString("play_url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playUrlItem;
    }

    public static String getSplasListener(String splaString) {
        String splaInfo = null;
        if ((splaString == null) || splaString.length() <= 0) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(splaString);
            splaInfo = jsonObject.getString("startup_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return splaInfo;
    }
//
//    public static ShareInfo getCeremonyListener(String ceremonyString) {
//        if ((ceremonyString == null) || ceremonyString.length() <= 0) {
//            return null;
//        }
//        ShareInfo shareInfo = null;
//        try {
//            JSONObject jsonObject = new JSONObject(ceremonyString);
//            shareInfo = new ShareInfo();
//            if (jsonObject.has("act_share_word")) {
//                shareInfo.setAct_share_word(jsonObject
//                        .getString("act_share_word"));
//            }
//            if (jsonObject.has("act_share_title")) {
//                shareInfo.setAct_share_title(jsonObject
//                        .getString("act_share_title"));
//            }
//            if (jsonObject.has("act_share_img")) {
//                shareInfo.setAct_share_img(jsonObject
//                        .getString("act_share_img"));
//            }
//            if (jsonObject.has("act_share_url")) {
//                shareInfo.setIdact_share_url(jsonObject
//                        .getString("act_share_url"));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return shareInfo;
//    }
//
//    public static PayStatus getPayStatus(String jsonString) {
//        if (TextUtils.isEmpty(jsonString)) {
//            return null;
//        }
//        PayStatus payStatus = null;
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            payStatus = new PayStatus();
//            if (jsonObject.has("status")) {
//                payStatus.setStatus(jsonObject.getInt("status"));
//            }
//            if (jsonObject.has("contents")) {
//                payStatus.setContents(jsonObject.getString("contents"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return payStatus;
//
//    }
//
//    /**
//     * 获取订购列表信息
//     *
//     * @param json
//     * @return
//     */
//    public static OrderItemInfo getOrderItemInfo(String json) {
//        if (TextUtils.isEmpty(json)) {
//            return null;
//        }
//        OrderItemInfo orderItemInfo = new OrderItemInfo();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray jsonArray = null;
//            if (jsonObject.has("valid")) {
//                jsonArray = jsonObject.getJSONArray("valid");
//                ArrayList<OrderItem> orderData = parseOrderData(jsonArray);
//                orderItemInfo.setOrderItems(orderData);
//            }
//            if (jsonObject.has("invalid")) {
//                jsonArray = jsonObject.getJSONArray("invalid");
//                ArrayList<OrderItem> unOrderItems = parseOrderData(jsonArray);
//                orderItemInfo.setUnOrderItems(unOrderItems);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return orderItemInfo;
//    }
//
//    /**
//     * 解析订购数据
//     *
//     * @param jsonArray
//     * @return
//     */
//    private static ArrayList<OrderItem> parseOrderData(JSONArray jsonArray) {
//
//        orderItems = new ArrayList<OrderItem>();
//        OrderItem orderItem = null;
//
//        if ((null == jsonArray) || jsonArray.length() <= 0) {
//            return null;
//        }
//        int length = jsonArray.length();
//        for (int i = 0; i < length; i++) {
//            JSONObject item;
//            try {
//                item = jsonArray.getJSONObject(i);
//                orderItem = new OrderItem();
//                if (item.has("id")) {
//                    orderItem.setId(item.getString("id"));
//                }
//                if (item.has("project_id")) {
//                    orderItem.setProject_id(item.getString("project_id"));
//                }
//                if (item.has("cms_album_id")) {
//                    orderItem.setCms_album_id(item.getString("cms_album_id"));
//                }
//                if (item.has("sn")) {
//                    orderItem.setSn(item.getString("sn"));
//                }
//                if (item.has("depay")) {
//                    orderItem.setDepay(item.getString("depay"));
//                }
//                if (item.has("day")) {
//                    orderItem.setDay(item.getString("day"));
//                }
//                if (item.has("cdate")) {
//                    orderItem.setCdate(item.getString("cdate"));
//                }
//                if (item.has("mdate")) {
//                    orderItem.setMdate(item.getString("mdate"));
//                }
//                if (item.has("validate")) {
//                    orderItem.setValidate(item.getString("validate"));
//                }
//                if (item.has("valid_time")) {
//                    orderItem.setValid_time(item.getString("valid_time"));
//                }
//                if (item.has("name")) {
//                    orderItem.setName(item.getString("name"));
//                }
//                if (item.has("is_fee")) {
//                    orderItem.setIs_fee(item.getString("is_fee"));
//                }
//                if (item.has("fee")) {
//                    orderItem.setFee(item.getString("fee"));
//                }
//                if (item.has("album_id")) {
//                    orderItem.setAlbum_id(item.getString("album_id"));
//                }
//                orderItems.add(orderItem);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//        return orderItems;
//    }
//
//    public static FeeMoreInfo getFeeMoreInfo(String jsonString) {
//        if (TextUtils.isEmpty(jsonString)) {
//            return null;
//        }
//        FeeMoreInfo feeMoreInfo = null;
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            feeMoreInfo = new FeeMoreInfo();
//            if (jsonObject.has("pic")) {
//                feeMoreInfo.setPic(jsonObject.getString("pic"));
//            }
//            if (jsonObject.has("intro")) {
//                feeMoreInfo.setIntro(jsonObject.getString("intro"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return feeMoreInfo;
//
//    }
//
//    /**
//     * 解析音频介绍信息
//     *
//     * @param jsonString
//     * @return
//     */
//    public static RadioIntroInfo getRadioIntroInfo(String jsonString) {
//        if (TextUtils.isEmpty(jsonString)) {
//            return null;
//        }
//        RadioIntroInfo info = null;
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            info = new RadioIntroInfo();
//            if (jsonObject.has("id")) {
//                info.setId(jsonObject.getString("id"));
//            }
//            if (jsonObject.has("intro")) {
//                String intro = jsonObject.getString("intro");
//                if (TextUtils.isEmpty(intro) || "null".equals(intro)) {
//                    info.setIntro("");
//                } else {
//                    info.setIntro(intro);
//                }
//            }
//            if (jsonObject.has("imgs")) {
//                JSONArray jsonArray = jsonObject.getJSONArray("imgs");
//                if (jsonArray.length() > 0) {
//                    JSONObject object;
//                    ArrayList<String> imgUrls = new ArrayList<String>() {
//                    };
//                    String imgUrl;
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        object = jsonArray.getJSONObject(i);
//                        if (object.has("imgUrl")) {
//                            imgUrl = object.getString("imgUrl");
//                            imgUrls.add(imgUrl);
//                        }
//                    }
//                    info.setImgs(imgUrls);
//                }
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return info;
//    }
//
//
//    public static SearchHotAllResult getHotAllResult(JSONObject jObject) {
//        if (jObject == null) {
//            return new SearchHotAllResult();
//        }
//        SearchHotAllResult hotAllResult = new SearchHotAllResult();
//        try {
//            ArrayList<RadioInfo> radioInfoList = new ArrayList<RadioInfo>();
//            if (jObject.has("type")) {
//                hotAllResult.setType(jObject.getInt("type"));
//            }
//            if (jObject.has("count")) {
//                hotAllResult.setCount(jObject.getString("count"));
//            }
//            if (jObject.has("list")) {
//                JSONArray jsonArray = jObject.getJSONArray("list");
//                if (jsonArray != null && jsonArray.length() > 0) {
//                    JSONObject jsonObject;
//                    for (int j = 0; j < jsonArray.length(); j++) {
//                        jsonObject = jsonArray.getJSONObject(j);
//                        RadioInfo radioInfo = new RadioInfo();
//                        if (hotAllResult.getType() == 4) {
//                            if (jsonObject.has("id")) {
//                                radioInfo
//                                        .setAlbumId(Integer.parseInt(jsonObject.getString("id")));
//                            }
//                        } else {
//                            if (jsonObject.has("id")) {
//                                radioInfo
//                                        .setId(Integer.parseInt(jsonObject.getString("id")));
//                            }
//                        }
//                        if (jsonObject.has("name")) {
//                            radioInfo.setName(jsonObject.getString("name"));
//                        }
//                        if (jsonObject.has("current_play")) {
//                            radioInfo.setCurrentPlay(jsonObject
//                                    .getString("current_play"));
//                        }
//                        if (jsonObject.has("recommend_language")) {
//                            radioInfo.setRecommed(jsonObject
//                                    .getString("recommend_language"));
//                        }
//                        if (jsonObject.has("Introduction")) {
//                            radioInfo.setIntroduction(jsonObject
//                                    .getString("Introduction"));
//                        }
//                        if (jsonObject.has("current_play_time")) {
//                            radioInfo.setCurrentPlayTime(jsonObject
//                                    .getString("current_play_time"));
//                        }
//                        if (jsonObject.has("img_url")) {
//                            radioInfo.setImgUrl(jsonObject.getString("img_url"));
//                        }
//                        if (jsonObject.has("broadcaster")) {
//                            radioInfo.setBroadCaster(jsonObject
//                                    .getString("broadcaster"));
//                        }
//                        if (jsonObject.has("length")) {
//                            radioInfo.setPlayLength(jsonObject.getString("length"));
//                        }
//                        if (jsonObject.has("type")) {
//                            radioInfo.setType(jsonObject.getString("type"));
//                        }
//                        if (jsonObject.has("playbill_url")) {
//                            radioInfo.setRadioBillUrl(jsonObject
//                                    .getString("playbill_url"));
//                        }
//                        if (jsonObject.has("praise")) {
//                            radioInfo.setPraiseUrl(jsonObject.getString("praise"));
//                        }
//                        if (jsonObject.has("enjoy_url")) {
//                            radioInfo.setEnjoyUrl(jsonObject.getString("enjoy_url"));
//                        }
//                        if (jsonObject.has("more_enjoy_url")) {
//                            radioInfo.setMoreEnjoyUrl(jsonObject
//                                    .getString("more_enjoy_url"));
//                        }
//                        if (jsonObject.has("album_list_url")) {
//                            radioInfo.setAlbumListUrl(jsonObject
//                                    .getString("album_list_url"));
//                        }
//                        if (jsonObject.has("album_Introduction_url")) {
//                            radioInfo.setAlbumIntroductionUrl(jsonObject
//                                    .getString("album_Introduction_url"));
//                        }
//                        if (jsonObject.has("album_img")) {
//                            radioInfo.setAlbumDetailImgUrl(jsonObject
//                                    .getString("album_img"));
//                        }
//                        if (jsonObject.has("play_url")) {
//                            JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
//                            parsePlayUrl(tvjsonArray, radioInfo);
//                        }
//                        if (jsonObject.has("play_count")) {
//                            radioInfo.setPlayCount(jsonObject.getString("play_count"));
//                        }
//                        if (jsonObject.has("is_collect")) {
//                            radioInfo.setCollect(jsonObject.getBoolean("is_collect"));
//                        }
//                        if (jsonObject.has("aid")) {
//                            radioInfo.setAid(jsonObject.getString("aid"));
//                        }
//                        if (jsonObject.has("download_url")) {
//                            radioInfo.setDownloadUrl(jsonObject
//                                    .getString("download_url"));
//                        }
//                        // 专辑或者节目
//                        if (jsonObject.has("play_url")
//                                && !radioInfo.getType().equals("4")
//                                && !radioInfo.getType().equals("5")) {
//                            JSONArray tvjsonArray = jsonObject.getJSONArray("play_url");
//                            parsePlayUrl(tvjsonArray, radioInfo);
//                        }
//
//                        if (jsonObject.has("albums_play")
//                                && (radioInfo.getType().equals("4") || radioInfo
//                                .getType().equals("5"))) {
//                            // JSONArray tvjsonArray = jsonObject
//                            // .getJSONArray("albums_play");
//                            // parseAlbumUrl(tvjsonArray, radioInfo);
//                        }
//
//                        if (jsonObject.has("albums_url")) {
//                            radioInfo.setAlbums_url(jsonObject.getString("albums_url"));
//                        }
//                        if (jsonObject.has("album_id")) {
//                            radioInfo.setAlbum_id(jsonObject
//                                    .getString("album_id"));
//                        }
//                        if (jsonObject.has("recommend_pos_id")) {
//                            radioInfo.setRecommend_pos_id(Integer.parseInt(jsonObject
//                                    .getString("recommend_pos_id")));
//                        }
//                        if (jsonObject.has("auto_model")) {
//                            radioInfo.setAuto_model(jsonObject.getInt("auto_model"));
//                        }
//                        if (jsonObject.has("albumname")) {
//                            radioInfo.setAlbum_name(jsonObject.getString("albumname"));
//                        }
//                        radioInfoList.add(radioInfo);
//                        hotAllResult.setList(radioInfoList);
//                    }
//                    return hotAllResult;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return hotAllResult;
//    }


    /**
     * 最新接口获取搜索详情
     *
     * @param paramString
     * @return
     */
//    public static ArrayList<SearchHotAllResult> getHotAllInfoList(String paramString) {
//        ArrayList<SearchHotAllResult> searchHotAllResults = new ArrayList<SearchHotAllResult>();
//        if (paramString == null || (paramString.trim().equals(""))) {
//            return null;
//        }
//        try {
//            ArrayList<RadioInfo> radioInfoList = new ArrayList<RadioInfo>();
//            JSONArray josnArray = new JSONArray(paramString);
////            SearchHotAllResult hotAllResult;
//            if (josnArray == null || josnArray.length() == 0) {
//                return null;
//            }
//            for (int i = 0; i < josnArray.length(); i++) {
//                JSONObject jObject = josnArray.getJSONObject(i);
//                SearchHotAllResult hotAllResult = getHotAllResult(jObject);
//                if (hotAllResult == null) {
//                    hotAllResult = new SearchHotAllResult();
//                }
//                searchHotAllResults.add(hotAllResult);
//                if (BuildConfig.DEBUG) Log.d("JsonDataFactory", searchHotAllResults.toString());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return searchHotAllResults;
//    }

    public static ArrayList<String> getSearchKeywordList(String paramStr) {
        ArrayList<String> searchKeywordList = new ArrayList<>();
        if (!TextUtils.isEmpty(paramStr)) {
            try {
                JSONArray jsonArray = new JSONArray(paramStr);
                if (null != jsonArray && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String str = (String) jsonArray.get(i);
                        if (!TextUtils.isEmpty(str)) {
                            searchKeywordList.add(str);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return searchKeywordList;
    }
}
