package com.tutorials.andorid.app.tutorials.contentproviders;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.core.BaseActivity;
import com.tutorials.andorid.app.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.Manifest.permission.READ_CONTACTS;

public class ContentProvidersActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REQ_CODE_READ_CONTACTS = 100;

    ArrayList<Contact> contacts;

    RecyclerView recyclerViewContacts;

    String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_providers);

    }

    private void initLoaderConcept() {
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        pullContacts();
    }

    private void pullContacts() {
        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQ_CODE_READ_CONTACTS);
        } else {
            queryForContacts();
        }
    }


    private void queryForContacts() {
//        initLoaderConcept();
        new ContactsTask().execute();
    }

    private void _queryForContacts() {
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Cursor cursor = getContentResolver().query(CONTENT_URI, projectionFields, null, null, null);
        fillFromCursor(cursor);
    }


    private void fillFromCursor(@NonNull Cursor cursor) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        } else {
            contacts.clear();
        }
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String hasPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String phoneNumber = getPhoneNumber(id);
                if (!TextUtils.isEmpty(phoneNumber)) {
                    contacts.add(new Contact(id, displayName, phoneNumber, hasPhoneNumber));
                }
            }
        }
        cursor.close();
        Collections.sort(this.contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact left, Contact right) {
                return left.displayName.compareTo(right.displayName);
            }
        });
    }


    private String getPhoneNumber(@NonNull String id) {
        String phoneNumber = null;
        ContentResolver cr = getContentResolver();

        Cursor phones = cr.query(Phone.CONTENT_URI, null,
                Phone.CONTACT_ID + " = " + id, null, null);
        while (phones.moveToNext()) {
            phoneNumber = phones.getString(phones.getColumnIndex(Phone.NUMBER));
            int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
        }
        phones.close();
        return phoneNumber;
    }

    private void renderRecyclerView() {
        this.recyclerViewContacts = (RecyclerView) findViewById(R.id.rv_contacts);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        this.recyclerViewContacts.setLayoutManager(layoutManager);
        this.recyclerViewContacts.setHasFixedSize(true);

        ContactsArrayAdapter contactsArrayAdapter = new ContactsArrayAdapter(this, this.contacts);
        this.recyclerViewContacts.setAdapter(contactsArrayAdapter);
    }


    private void checkForContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQ_CODE_READ_CONTACTS);
        } else {
            queryForContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_READ_CONTACTS:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    queryForContacts();
                }
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        showProgress("", "Please Wait...");
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        fillFromCursor(cursor);
        renderRecyclerView();
        dismissDialog();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private class ContactsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            showProgress("", "Please Wait...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            _queryForContacts();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            dismissDialog();
            renderRecyclerView();
        }
    }

}
