package com.example.xin.dormitory.student;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xin.dormitory.R;


public class AddContactsActivity extends AppCompatActivity {
    private TextView contactName;
    private TextView contactNickName;
    private TextView contactID;
    private TextView contactPhone;
    private TextView contactBelong;
    private TextView tv_add_contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        Toolbar toolbar_add_contacts = findViewById(R.id.toolbar_add_contacts);
        toolbar_add_contacts.setTitle("");
        setSupportActionBar(toolbar_add_contacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contactID = findViewById(R.id.contact_id);
        contactName = findViewById(R.id.contact_name);
        contactNickName = findViewById(R.id.contact_nickname);
        contactPhone = findViewById(R.id.contact_phone);
        contactBelong = findViewById(R.id.contact_belong);
        tv_add_contacts = findViewById(R.id.tv_add_contacts);
        tv_add_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddContactsActivity.this, Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddContactsActivity.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    write();
                }
            }
        });
        init();
    }
    private void init(){
        contactName.setText(getIntent().getStringExtra("contactName"));
        contactID.setText("学号："+getIntent().getStringExtra("contactID"));
        contactPhone.setText("联系方式："+getIntent().getStringExtra("contactPhone"));
        contactNickName.setText("昵称："+ getIntent().getStringExtra("contactNickName"));
        contactBelong.setText("所属宿舍楼："+getIntent().getStringExtra("contactBelong"));
    }
    private void write(){
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        if(contactName.getText()!=""){
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,(String)contactName.getText());
            contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        }
        if(contactPhone.getText()!=""){
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,getIntent().getStringExtra("contactPhone"));
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        }
        values.clear();
        Toast.makeText(AddContactsActivity.this,"添加成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    write();
                }else{
                    Toast.makeText(this, "您不允许使用该权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
