package binhntph28014.fpoly.lab5.bai4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import binhntph28014.fpoly.lab5.Constant;
import binhntph28014.fpoly.lab5.JSONParser;

public class SaveProductDetailsTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    boolean boolSuccess = false;

    public SaveProductDetailsTask(Context context) {
        this.context = context;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Saving product...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsPid = new HashMap<>();
        hsPid.put(Constant.TAG_PID, strings[0]);
        params.add(hsPid);
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put(Constant.TAG_NAME, strings[1]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put(Constant.TAG_PRICE, strings[2]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put(Constant.TAG_DESCRIPTION, strings[3]);
        params.add(hsDes);
        // sending modified data through http request
        // Notice that update product url accepts POST method
        JSONObject object = jsonParser.makeHttpRequest(Constant.url_update_product, "POST", params);
        try {
            int success = object.getInt(Constant.TAG_SUCCESS);
            if (success == 1) {
                // successfully updated
                boolSuccess = true;
            } else {
                // failed to update product
                boolSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (boolSuccess) {
            Intent intent = ((Activity) context).getIntent();
            // send result code 100 to notify about product update
            ((Activity) context).setResult(100, intent);
            ((Activity) context).finish();
        }
    }
}
