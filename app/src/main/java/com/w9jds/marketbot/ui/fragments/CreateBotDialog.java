package com.w9jds.marketbot.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w9jds.marketbot.R;

import butterknife.ButterKnife;


public class CreateBotDialog extends DialogFragment {

    private static final String ITEM_ID = "itemId";
    private long itemId;

    private OnFragmentInteractionListener listener;

    public CreateBotDialog() {
        // Required empty public constructor
    }

    public static CreateBotDialog newInstance(long itemId) {
        CreateBotDialog fragment = new CreateBotDialog();
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getLong(ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_bot_dialog, container);
        ButterKnife.bind(this, view);

        return view;
    }

//    public void onButtonPressed(Uri uri) {
//        if (listener != null) {
//            listener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
