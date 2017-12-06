package com.example.pablo.redditchallengepablo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pablo.redditchallengepablo.data.modelreddit.Child;
import com.example.pablo.redditchallengepablo.data.modelreddit.RedditResponse;
//import com.example.pablo.redditchallengepablo.dummy.DummyContent;
//import com.example.pablo.redditchallengepablo.dummy.DummyContent.DummyItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RedditFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_REDDIT_RESPONSE = "redditResponse";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView rvCarList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    private OnListFragmentInteractionListener mListener;
    static List<Child> childList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RedditFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RedditFragment newInstance(List<Child>childList) {
        RedditFragment fragment = new RedditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REDDIT_RESPONSE, (Serializable)childList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            childList  = (List<Child>) getArguments().getSerializable(ARG_REDDIT_RESPONSE);
           // mColumnCount = getArguments().getInt(ARG_REDDIT_RESPONSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
/*
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new RecyclerViewAdapterReddit(DummyContent.ITEMS, mListener));
        }
        */
        return view;
        //return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCarList = view.findViewById(R.id.rvCarList);
        //  toggleButton = view.findViewById(R.id.toggle_button_id);

        // famousRecyclerView = view.findViewById(R.id.recycler_view_fragment_left);
        //I need to receive by paremeter the layout manager then a switch to select the correct one




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        RecyclerViewAdapterReddit adapter = new RecyclerViewAdapterReddit(childList);
        //  adapter.setListener(AmazonFragment.this);
        rvCarList.setAdapter(adapter);




        layoutManager = new LinearLayoutManager(getContext());
        rvCarList.setLayoutManager(layoutManager);
        rvCarList.setLayoutManager(layoutManager);

        itemAnimator = new DefaultItemAnimator();
        rvCarList.setLayoutManager(layoutManager);
        rvCarList.setItemAnimator(itemAnimator);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        */
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);
    }
}
