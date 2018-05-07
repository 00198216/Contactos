package com.example.charl.contactos;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favoritos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favoritos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favoritos extends Fragment {
    RecyclerView rv;
    ContactosAdapter2 adapter;
    ArrayList<Contactos> series;
    ArrayList<Contactos> series2;
    GridLayoutManager lManager;
    Bundle bundle1;
    Iterator iterator;
    Contactos conta;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Favoritos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favoritos.
     */
    // TODO: Rename and change types and number of parameters
    public static Favoritos newInstance(String param1, String param2) {
        Favoritos fragment = new Favoritos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista=inflater.inflate(R.layout.fragment_favoritos, container, false);

        rv =  vista.findViewById(R.id.recycler2);
        GridLayoutManager gManager = new GridLayoutManager(getContext(),3);

        RecyclerView.LayoutManager lManager = gManager;

        rv.setLayoutManager(gManager);

        series= new ArrayList<>();
        series2= new ArrayList<>();

        rv.setLayoutManager(lManager);

        bundle1 = getArguments();


        adapter= new ContactosAdapter2(getContext(),series){

            @Override
            public void onVerClick(View v, int pos) {

            }

            @Override
            public void Contador(int cont) {

            }
        };

              if( bundle1 != null){

                int cont=0;

                  series2=  bundle1.getParcelableArrayList("Pass");
                  iterator=series2.listIterator();


                  while(iterator.hasNext()){
                      conta= (Contactos) iterator.next();
                      series.add(cont,conta);
                      int i=0;
                      for (i = 0; i < cont; ++i) {
                          if(series2.get(i)==series2.get(cont)){
                              series2.remove(i);
                              series.remove(i);
                              break;
                          }
                      }
                      adapter.notifyItemInserted(cont);
                      adapter.notifyItemRangeChanged(cont,series2.size());

                      cont++;

                  }

            }




        rv.setAdapter(adapter);




        SearchView search = getActivity().findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                series.clear();
                query = query.toLowerCase();

                for(int i=0; i < series2.size(); i++){
                    if(series2.get(i).getName().toLowerCase().contains(query) ||series2.get(i).getNumero().contains(query) ){
                        series.add(series2.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
                return true;


            }



            @Override
            public boolean onQueryTextChange(String newText) {

                series.clear();

                if(newText.length() ==0) {
                    series.addAll(series2);
                    adapter.notifyDataSetChanged();
                }

                else {

                    newText = newText.toLowerCase();

                    for(int i=0; i < series2.size(); i++){
                        if(series2.get(i).getName().toLowerCase().contains(newText) ||series2.get(i).getNumero().contains(newText)){

                            series.add(series2.get(i));
                        }
                    }

                    adapter.notifyDataSetChanged();
                    return true;
                }

                return true;
            }
        });

       return vista;
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
}
