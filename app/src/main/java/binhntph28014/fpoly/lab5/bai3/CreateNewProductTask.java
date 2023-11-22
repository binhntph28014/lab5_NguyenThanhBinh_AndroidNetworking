package binhntph28014.fpoly.lab5.bai3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import binhntph28014.fpoly.lab5.AllProductsActivity;
import binhntph28014.fpoly.lab5.Constant;
import binhntph28014.fpoly.lab5.JSONParser;

public class CreateNewProductTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;

    public CreateNewProductTask(Context context) {
        this.context = context;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Đang xử lí thêm...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put("name", strings[0]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put("price", strings[1]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put("description", strings[2]);
        params.add(hsDes);
        // getting JSON Object
        // Note that create product url accepts POST Method
        JSONObject jsonObject = jsonParser.makeHttpRequest(Constant.url_create_products, "POST", params);
        Log.d("Create response", jsonObject.toString());
        try {
            int success = jsonObject.getInt(Constant.TAG_SUCCESS);
            if (success == 1) {
                // Successfully created product
                Intent intent = new Intent(context, AllProductsActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                // closing Create product screen
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
