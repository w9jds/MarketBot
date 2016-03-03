package com.w9jds.marketbot.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.TypeInfo;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 3/3/16.
 */
public class TypeInfoTab extends Fragment implements BaseDataManager.DataLoadingCallbacks {

    static final String ARG_PAGE = "ARG_PAGE";
    static final String ARG_TYPEID = "ARG_TYPEID";

    @Bind(R.id.dataloading_progress)
    ProgressBar loading;

    @Bind(R.id.type_name)
    TextView name;
    @Bind(R.id.type_description)
    TextView description;
    @Bind(R.id.mass_value)
    TextView mass;
    @Bind(R.id.capacity_value)
    TextView capacity;

    private int position;
    private int typeId;
    private DataManager dataManager;

    public static TypeInfoTab create(int page, int typeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_TYPEID, typeId);
        TypeInfoTab fragment = new TypeInfoTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        position = args.getInt(ARG_PAGE);
        typeId = args.getInt(ARG_TYPEID);

        dataManager = new DataManager(getContext()) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {

            }

            @Override
            public void onDataLoaded(Object data) {
                if (data instanceof TypeInfo) {
                    TypeInfo info = (TypeInfo) data;

                    name.setText(info.getName());
                    description.setText(Html.fromHtml(info.getDescription()));
                    mass.setText(String.valueOf(info.getMass()));
                    capacity.setText(String.valueOf(info.getCapacity()));
                }
            }
        };

        dataManager.registerCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_info, container, false);
        ButterKnife.bind(this, view);

        dataManager.loadTypeInfo(typeId);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void dataStartedLoading() {
        mass.setVisibility(View.GONE);
        capacity.setVisibility(View.GONE);

        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dataFinishedLoading() {
        loading.setVisibility(View.GONE);

        mass.setVisibility(View.VISIBLE);
        capacity.setVisibility(View.VISIBLE);
    }

}
