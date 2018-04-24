//package com.w9jds.marketbot.ui.fragments;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.w9jds.marketbot.R;
//import com.w9jds.marketbot.classes.models.Region;
//import com.w9jds.marketbot.classes.models.Type;
//import com.w9jds.marketbot.classes.models.TypeInfo;
//import com.w9jds.marketbot.data.loader.TypeLoader;
//import com.w9jds.marketbot.utils.NumberUtils;
//
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//public final class TypeInfoTab extends Fragment implements DataLoadingSubject.DataLoadingCallbacks {
//
//    static final String ARG_TYPE = "ARG_TYPE";
//
//    @Bind(R.id.dataloading_progress) ProgressBar loading;
//    @Bind(R.id.type_name) TextView name;
//    @Bind(R.id.type_description) TextView description;
//    @Bind(R.id.mass_value) TextView mass;
//    @Bind(R.id.capacity_value) TextView capacity;
//    @Bind(R.id.volume_value) TextView volume;
//    @Bind(R.id.portion_value) TextView portion;
//    @Bind(R.id.item_icon) ImageView icon;
//
//    private Type type;
//    private TypeLoader loader;
//
//    public static TypeInfoTab create(Type type) {
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_TYPE, type);
//        TypeInfoTab fragment = new TypeInfoTab();
//        fragment.setArguments(args);
//        fragment.setRetainInstance(true);
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Bundle args = getArguments();
//        type = args.getParcelable(ARG_TYPE);
//
//        loader = new TypeLoader(getContext()) {
//            @Override
//            public void onTypeInfoLoaded(TypeInfo info) {
//                NumberFormat formatter = new DecimalFormat("#,###");
//                String capacityValue = formatter.format(info.getCapacity()) + " m3";
//
//                description.setText(Html.fromHtml(info.getDescription()));
//
//                String shortened = info.getMass() < 1000 ? String.valueOf(info.getMass()) :
//                        NumberUtils.shortened(info.getMass(), 0);
//                String massShortened = shortened + " kg";
//                mass.setText(massShortened);
//
//                shortened = info.getVolume() < 1000 ? String.valueOf(info.getVolume()) :
//                        NumberUtils.shortened(info.getVolume(), 0);
//                String vol = shortened + " m3";
//                volume.setText(vol);
//
//                capacity.setText(capacityValue);
//                portion.setText(String.valueOf(info.getPortionSize()));
//            }
//
//            @Override
//            public void onRegionsLoaded(List<Region> regions) {
//
//            }
//
//        };
//
//        loader.registerLoadingCallback(this);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_type_info, container, false);
//        ButterKnife.bind(this, view);
//
//        name.setText(type.getName());
//        Glide.with(getActivity())
//            .load(type.getIcon())
//            .into(icon);
//
//        loader.loadTypeInfo(type.getId());
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @Override
//    public void dataStartedLoading() {
//        mass.setVisibility(View.GONE);
//        capacity.setVisibility(View.GONE);
//
//        loading.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void dataFinishedLoading() {
//        loading.setVisibility(View.GONE);
//
//        mass.setVisibility(View.VISIBLE);
//        capacity.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void dataFailedLoading(String errorMessage) {
//
//    }
//
//}
