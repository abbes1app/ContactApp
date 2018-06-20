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
public class BlankFragment2 extends Fragment {


    ArrayList<Ami> listeContacts;
    ContactArrayAdapter adapter;


    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Contacts Viber");

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

        Cursor contactCursor = cr.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.viber.voip"},
                null);


        if (contactCursor != null) {
            if (contactCursor.getCount() > 0) {
                if (contactCursor.moveToFirst()) {
                    do {
                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                            Cursor whatsAppContactCursor = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{whatsappContactId}, null);

                            if (whatsAppContactCursor != null) {
                                whatsAppContactCursor.moveToFirst();
                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                                String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                whatsAppContactCursor.close();

                                //Add Number to ArrayList
                                Ami ami = new Ami();

                                ami.setIdAmi(id);
                                if (name.contains(" ")) {
                                    ami.setNom(name.substring(name.split(" ")[0].length() + 1));
                                    ami.setPrenom(name.split(" ")[0]);
                                } else {
                                    ami.setPrenom(name);
                                }
                                ami.setTelephone(number);

                                contacts.add(ami);


                            }
                        }
                    } while (contactCursor.moveToNext());
                    contactCursor.close();
                }
            }
        }


        listeContacts.addAll(contacts);

    }

}
