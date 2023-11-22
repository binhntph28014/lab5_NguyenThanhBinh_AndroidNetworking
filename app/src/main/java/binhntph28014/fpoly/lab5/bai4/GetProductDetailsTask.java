package binhntph28014.fpoly.lab5.bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import binhntph28014.fpoly.lab5.Constant;
import binhntph28014.fpoly.lab5.JSONParser;
import binhntph28014.fpoly.lab5.Product;

public class GetProductDetailsTask extends AsyncTask<String, String, String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    EditText edtName, edtPrice, edtDes;
    Product product;

    public GetProductDetailsTask(Context context, EditText edtName, EditText edtPrice, EditText edtDes) {
        this.context = context;
        this.edtName = edtName;
        this.edtPrice = edtPrice;
        this.edtDes = edtDes;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Product Details. Please wait...");
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
        try {
            // getting product details by Making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(Constant.url_product_detail, "GET", params);
            // json success tag
            int success = json.getInt(Constant.TAG_SUCCESS);
            if (success == 1) {
                // successfelly received product details
                JSONArray productObj = json.getJSONArray(Constant.TAG_PRODUCT);
                // get first product object from JSON Array
                JSONObject obj = productObj.getJSONObject(0);
                // product with this pid found
                product = new Product();
                product.setName(obj.getString(Constant.TAG_NAME));
                product.setPrice(obj.getString(Constant.TAG_PRICE));

                product.setDescription(obj.getString(Constant.TAG_DESCRIPTION));
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
        edtName.setText(product.getName());
        edtPrice.setText(product.getPrice());
        edtDes.setText(product.getDescription());
    }
}
