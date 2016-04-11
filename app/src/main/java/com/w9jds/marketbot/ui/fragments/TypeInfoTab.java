package com.w9jds.marketbot.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.TypeInfo;
import com.w9jds.marketbot.data.DataLoadingSubject;
import com.w9jds.marketbot.data.loader.TypeLoader;
import com.w9jds.marketbot.ui.ItemActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 3/3/16.
 */
public final class TypeInfoTab extends Fragment implements DataLoadingSubject.DataLoadingCallbacks {

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
    @Bind(R.id.volume_value)
    TextView volume;
    @Bind(R.id.portion_value)
    TextView portion;

    @Bind(R.id.item_icon)
    ImageView icon;

    private int position;
    private long typeId;
    private TypeLoader loader;

    /**
     * TODO: Possibly show ship 3d view using webresource
     */
    public static TypeInfoTab create(int page, long typeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putLong(ARG_TYPEID, typeId);
        TypeInfoTab fragment = new TypeInfoTab();
        fragment.setArguments(args);
        return fragment;
    }

    private String formatNumberValue(double value, String unit) {
        if (value > 1000000) {
            return String.format(Locale.ENGLISH, "%.2fm %s", value / 1000000.0, unit);
        }
        else {
            NumberFormat formatter = new DecimalFormat("#,##0.00");
            return formatter.format(value) + " " + unit;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        position = args.getInt(ARG_PAGE);
        typeId = args.getLong(ARG_TYPEID);

        loader = new TypeLoader(getContext()) {
            @Override
            public void onDataLoaded(TypeInfo info) {
                NumberFormat formatter = new DecimalFormat("#,###");
                String capacityValue = formatter.format(info.getCapacity()) + " m3";

                description.setText(Html.fromHtml(info.getDescription()));
                mass.setText(formatNumberValue(info.getMass(), "kg"));
                capacity.setText(capacityValue);
                portion.setText(String.valueOf(info.getPortionSize()));
                volume.setText(formatNumberValue(info.getVolume(), "m3"));
            }
        };

        loader.registerLoadingCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_info, container, false);
        ButterKnife.bind(this, view);

        ItemActivity host = (ItemActivity) getActivity();
        name.setText(host.getCurrentTypeName());
//        Glide.with(host)
//                .load(host)
//                .into(icon);

        loader.loadTypeInfo(typeId);
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

    @Override
    public void dataFailedLoading(String errorMessage) {

    }

}
