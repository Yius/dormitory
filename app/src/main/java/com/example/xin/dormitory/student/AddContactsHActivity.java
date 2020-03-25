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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xin.dormitory.R;

public class AddContactsHActivity extends AppCompatActivity {
    private TextView contactHName;
    private TextView contactHID;
    private TextView contactHPhone;
    private TextView contactHGovern;
    private TextView tv_add_contacts_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts_h);
        Toolbar toolbar_add_contacts_h = findViewById(R.id.toolbar_add_contacts_h);
        toolbar_add_contacts_h.setTitle("");
        setSupportActionBar(toolbar_add_contacts_h);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contactHID = findViewById(R.id.contact_h_id);
        contactHName = findViewById(R.id.contact_h_name);
        contactHPhone = findViewById(R.id.contact_h_phone);
        contactHGovern = findViewById(R.id.contact_h_govern);
        tv_add_contacts_h = findViewById(R.id.tv_add_contacts_h);
        tv_add_contacts_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddContactsHActivity.this, Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddContactsHActivity.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    write();
                }
            }
        });
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        contactHName.setText(getIntent().getStringExtra("contactHName"));
        contactHID.setText("ID："+getIntent().getStringExtra("contactHID"));
        contactHPhone.setText("联系方式："+getIntent().getStringExtra("contactHPhone"));
        contactHGovern.setText("管理宿舍楼："+getIntent().getStringExtra("contactHGovern"));
    }
    private void write(){
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        if(contactHName.getText()!=""&&contactHGovern.getText()!=""){
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,contactHName.getText()+"("+getIntent().getStringExtra("contactHGovern")+"座宿管)");
            contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        }
        if(contactHPhone.getText()!=""){
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,getIntent().getStringExtra("contactHPhone"));
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI,values);
        }
        values.clear();
        Toast.makeText(AddContactsHActivity.this,"添加成功",Toast.LENGTH_SHORT).show();

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
