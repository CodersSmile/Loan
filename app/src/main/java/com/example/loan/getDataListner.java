package com.example.loan;

import org.json.JSONObject;

public interface getDataListner {
    void onGetExtradata(JSONObject jSONObject);

    void onRedirect(String str);

    void onReload();

    void onSuccess();

    void onUpdate(String str);
}
