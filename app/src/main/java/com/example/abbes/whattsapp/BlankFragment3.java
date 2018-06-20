package com.example.abbes.whattsapp;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.Fragment;



import java.util.ArrayList;
import com.example.abbes.whattsapp.Ami;
import com.example.abbes.whattsapp.ContactArrayAdapter;
import com.example.abbes.whattsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment {


    ArrayList<Ami> listeContacts;
    ContactArrayAdapter adapter;


    public BlankFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Importer Contacts");

        this.listeContacts = new ArrayList<Ami>();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.listcontact, container, false);
        ListView lv = (ListView)rootView.findViewById(R.id.listk);

        chargerContacts();
        adapter = new ContactArrayAdapter(getContext(), listeContacts);
        lv.setAdapter(adapter);


        return  rootView ;
    }


    private void chargerContacts() {

        ArrayList<Ami> contacts = new ArrayList<>();
        listeContacts.clear();

        ContentResolver cr = getActivity().getContentResolver();

        Cursor contactCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        try {
            while (contactCursor.moveToNext()) {
                String idContact = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String nomPrenom = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String telephone = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));

                Ami ami = new Ami();

                ami.setIdAmi(idContact);
                if (nomPrenom.contains(" ")) {
                    ami.setNom(nomPrenom.substring(nomPrenom.split(" ")[0].length() + 1));
                    ami.setPrenom(nomPrenom.split(" ")[0]);
                } else {
                    ami.setPrenom(nomPrenom);
                }
                ami.setTelephone(telephone);

                contacts.add(ami);
            }
            contactCursor.close();
        } catch (NullPointerException e) {
            Log.d("ListeContacts contacts", e.toString());
        }

        listeContacts.addAll(contacts);

    }

}
