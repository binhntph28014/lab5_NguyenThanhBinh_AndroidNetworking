package binhntph28014.fpoly.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import binhntph28014.fpoly.lab5.bai3.CreateNewProductTask;

public class AddProductActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription;
    private Button btnAdd;
    String pid, strName,strPrice,strDes;
    CreateNewProductTask newProductTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        edtName = (EditText)findViewById(R.id.edtProductName);
        edtPrice = (EditText)findViewById(R.id.edtProductPrice);
        edtDescription = (EditText)findViewById(R.id.edtProductDes);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        newProductTask = new CreateNewProductTask(this);
        pid = getIntent().getStringExtra(Constant.TAG_PID);
        newProductTask.execute(pid);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = edtName.getText().toString();
                strPrice = edtPrice.getText().toString();
                strDes = edtDescription.getText().toString();
                newProductTask.execute(pid, strName, strPrice, strDes);
            }
        });
    }
}