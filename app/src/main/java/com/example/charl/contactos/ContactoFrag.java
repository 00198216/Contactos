package com.example.charl.contactos;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import icepick.Icepick;
import icepick.State;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactoFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactoFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private RecyclerView rv;
    private ContactosAdapter adapter;
    private Uri ur;
    Contactos conta;
    String pos;
    Bundle bundle1 = new Bundle();


    ArrayList<Contactos> list = new ArrayList<>();
   @State ArrayList<Contactos> list2 = new ArrayList<>();
     ArrayList<Contactos> list3 = new ArrayList<>();




    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(list2 != null) {
            Icepick.saveInstanceState(this, outState);
        }

    }



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactoFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactoFrag newInstance(String param1, String param2) {
        ContactoFrag fragment = new ContactoFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 2){

            if(data.hasExtra("Name")==true){

                Contactos conta = (Contactos)data.getExtras().getParcelable("Name");

                Contactos conta2 =new Contactos(conta.getName(),conta.getLname(),conta.getNumero(),conta.getID(),conta.getImgconv(),conta.getCumple(),conta.getMail(),conta.getAdress());


                list.add(conta2);
                list2.add(conta2);






            }
        }
        adapter.notifyDataSetChanged();
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Preparing the layout for ContactoFragment

        view = inflater.inflate(R.layout.fragment_contacto, container, false);





        rv = view.findViewById(R.id.recycler);
        GridLayoutManager gManager = new GridLayoutManager(getContext(), 3);

        RecyclerView.LayoutManager lManager = gManager;

        rv.setLayoutManager(lManager);


        adapter = new ContactosAdapter(getContext(),  getContacts()) {

            @Override
            public void onVerClick(View v, int pos) {

                if (list2 != null) {

                    if (list2.get(pos).getCheck()) {

                        Favoritos frag = new Favoritos();

                        Bundle bundle = new Bundle();
                        list3.add(list2.get(pos));
                        bundle.putParcelableArrayList("Pass", list3);

                        frag.setArguments(bundle);
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.Favor, frag);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            public void Contador(int cont) {

            }


        };


        rv.setAdapter(adapter);

        Icepick.restoreInstanceState(this,savedInstanceState);




        SearchView look = getActivity().findViewById(R.id.search);
        look.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                list.clear();
                query = query.toLowerCase();

                for(int i=0; i < list2.size(); i++){
                    if(list2.get(i).getName().toLowerCase().contains(query) ||list2.get(i).getNumero().contains(query) ){
                        list.add(list2.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
                return true;


            }



            @Override
            public boolean onQueryTextChange(String newText) {

                list.clear();

                if(newText.length() ==0) {
                    list.addAll(list2);
                    adapter.notifyDataSetChanged();
                }

                else {

                    newText = newText.toLowerCase();

                    for(int i=0; i < list2.size(); i++){
                        if(list2.get(i).getName().toLowerCase().contains(newText) ||list2.get(i).getNumero().contains(newText)){

                            list.add(list2.get(i));
                        }
                    }

                    adapter.notifyDataSetChanged();
                    return true;
                }

                return true;
            }
        });


        return view;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Contactos> getContacts(){

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.perfil)
                + '/' + getResources().getResourceTypeName(R.drawable.perfil) + '/' + getResources().getResourceEntryName(R.drawable.perfil) );

        Cursor cursor= getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null
                , null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        while(cursor.moveToNext()) {
            Contactos x = new Contactos("", "", "", "",imageUri, "","","" );
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String apellido = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String ad = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_SOURCE));
            x.setName(name);
            x.setLname(apellido);
            x.setID(id);
            x.setAdress(ad);


            Cursor phone = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    ,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+id,null,null);
            String phon;
            while (phone.moveToNext()){
                phon=phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))==null){
                    x.setImgconv(imageUri);
                }else{
                    x.setImgconv(Uri.parse(phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))));
                }
                x.setNumero(phon);
            }
            phone.close();


            list.add(x);
            list2.add(x);
        }



        return list;
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume(){
        super.onResume();
        pos = null;
        conta = null;
        Intent getdata = getActivity().getIntent();
        if(getdata.getStringExtra(Intent.EXTRA_TEXT) !=null){
            modify();
        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void modify(){
        Intent getinfo = getActivity().getIntent();
        Bundle bundle = getinfo.getExtras();
        conta = (Contactos) bundle.getParcelable("passw");
        pos= getinfo.getStringExtra(Intent.EXTRA_TEXT);



        if (list2.get(parseInt(pos)) != conta) {
            list.set(parseInt(pos), conta);
            list2.set(parseInt(pos), conta);
            adapter.notifyDataSetChanged();

        }


    }

}


