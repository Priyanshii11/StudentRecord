package com.priyu.studentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4;
    Button bt1,bt2,bt3,bt4,bt5;
    DatabaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.sid);
        editText2 = findViewById(R.id.name);
        editText3 = findViewById(R.id.email);
        editText4 = findViewById(R.id.cc);

        bt1 = findViewById(R.id.add);
        bt2 = findViewById(R.id.delete);
        bt3 = findViewById(R.id.view);
        bt4 = findViewById(R.id.update);
        bt5 = findViewById(R.id.viewAll);

        mydb = new DatabaseHelper(this);

        AddData();
        DeleteData();
        updateData();
        view();
        viewAll();
    }
    public void AddData(){
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(editText1.getText().
                        toString(), editText3.getText().toString(),
                        editText4.getText(),toString());
                if (isInserted==true){
                    Toast.makeText(MainActivity.this,
                            "Data Inserted",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,
                            "Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void DeleteData(){
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deletedRow = mydb.deleteData(editText1.getText().toString());
                if (deletedRow > 0){
                    Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                } else if( deletedRow==0) {
                    editText1.setError("Insert Id value");
                }else{
                    Toast.makeText(MainActivity.this, "OOPSSS!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void view(){
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editText1.getText().toString();

                if (id.equals(String.valueOf(""))){
                    editText1.setError("Enter ID");
                    return;
                }
                Cursor cursor =mydb.getData(id);
                String data = null;
                if (cursor.moveToNext()){
                    data = "ID:"+cursor.getString(0)+"\n"+
                            "NAME:"+cursor.getString(1)+"\n"+
                            "EMAIL:"+cursor.getString(3 )+"\n"+
                            "COURSE COUNT"+cursor.getString(4)+"\n";
                }
                showMessage("DATA",data);
            }
        });
    }
    public void updateData(){
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = mydb.updateData(editText1.getText().toString(),
                        editText2.getText().toString(),
                        editText3.getText().toString(),
                        editText4.getText().toString());

                if (isUpdate == true){
                    Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void viewAll(){
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = mydb.getAllData();

                if (cursor.getCount() == 0){
                    showMessage("Error", "Nothing found in DB");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("CC: "+cursor.getString(3)+"\n");
                }
                showMessage("All data", buffer.toString());

            }
        });


    }


    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}