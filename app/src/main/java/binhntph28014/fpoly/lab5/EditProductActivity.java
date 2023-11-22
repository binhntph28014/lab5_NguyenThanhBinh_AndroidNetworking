package binhntph28014.fpoly.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import binhntph28014.fpoly.lab5.bai4.DeleteProductTask;
import binhntph28014.fpoly.lab5.bai4.GetProductDetailsTask;
import binhntph28014.fpoly.lab5.bai4.SaveProductDetailsTask;

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPrice, edtDes;
    private Button btnSave, btnDelete;
    String pid, strName, strPrice, strDes;
    GetProductDetailsTask productDetailsTask;
    SaveProductDetailsTask saveProductDetailsTask;
    DeleteProductTask deleteProductTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_product);
        edtName = (EditText) findViewById(R.id.edtProductName);
        edtPrice = (EditText) findViewById(R.id.edtProductPrice);
        edtDes = (EditText) findViewById(R.id.edtProductDes);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        pid = getIntent().getStringExtra(Constant.TAG_PID);
        productDetailsTask = new GetProductDetailsTask(this, edtName, edtPrice, edtDes);
        productDetailsTask.execute(pid);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                strName = edtName.getText().toString();
                strPrice = edtPrice.getText().toString();
                strDes = edtDes.getText().toString();
                saveProductDetailsTask = new SaveProductDetailsTask(this);
                saveProductDetailsTask.execute(pid, strName, strPrice, strDes);
                break;
            case R.id.btnDelete:
                deleteProductTask = new DeleteProductTask(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Deleting product...");
                builder.setMessage("Are you sure you want delete this product?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteProductTask.execute(pid);
                        dialogInterface.dismiss();

                        Toast.makeText(EditProductActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
        }
    }


}
