package com.w9jds.marketbot.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class MarketHistoryTab extends Fragment {

    static final String ARG_PAGE = "ARG_PAGE";

    @Bind(R.id.combine_chart) CombinedChart chart;

    private YAxis rightAxis;
    private YAxis leftAxis;

    private CompositeSubscription subscriptions;
    private int position;

    public static MarketHistoryTab create(int page, BehaviorSubject<Map.Entry<Integer, List<?>>> behaviorSubject) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MarketHistoryTab fragment = new MarketHistoryTab();
        fragment.setArguments(args);

        fragment.getSubscriptions().add(behaviorSubject
                .doOnError(Throwable::printStackTrace)
                .doOnNext(fragment.updateTab)
                .subscribe());

        return fragment;
    }

    public MarketHistoryTab() {
        subscriptions = new CompositeSubscription();
    }

    public Action1<Map.Entry<Integer, List<?>>> updateTab = historyEntries -> {
        if (historyEntries.getKey() == position) {

            int size = historyEntries.getValue().size();
            List<MarketHistory> histories = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                histories.add((MarketHistory) historyEntries.getValue().get(i));
            }

            CombinedData data = new CombinedData();

            data.setData(generateOrderBarData(histories));
            data.setData(generateAverageLineData(histories));
            data.setData(generateMarginCandleData(histories));

            chart.setData(data);
            chart.invalidate();
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public CompositeSubscription getSubscriptions() {
        return subscriptions;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        position = args.getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_history, container, false);
        ButterKnife.bind(this, view);

        chart.setDescription("");
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);

        chart.setDrawOrder(new CombinedChart.DrawOrder[] {
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE
        });

        rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f);

        leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        return view;
    }

    private BarData generateOrderBarData(List<MarketHistory> historyEntries) {

        BarData data = new BarData();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

        int size = historyEntries.size();
        List<BarEntry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            entries.add(new BarEntry((float) history.getOrderCount(), i,
                    format.format(recordDate)));
        }

        BarDataSet set = new BarDataSet(entries, "Order Count");
//        set.setColor();
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        data.addDataSet(set);
        return data;
    }

    private LineData generateAverageLineData(List<MarketHistory> historyEntries) {

        LineData data = new LineData();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

        int size = historyEntries.size();
        List<Entry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            entries.add(new Entry((float) history.getAveragePrice(), i, format.format(recordDate)));
        }

        LineDataSet set = new LineDataSet(entries, "Price Averages");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4f);
        set.setDrawCubic(true);
        set.setDrawValues(true);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        data.addDataSet(set);
        return data;
    }

    private CandleData generateMarginCandleData(List<MarketHistory> historyEntries) {

        CandleData data = new CandleData();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

        int size = historyEntries.size();
        List<CandleEntry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            entries.add(new CandleEntry(i,
                (float) history.getAveragePrice(),
                (float) history.getAveragePrice(),
                (float) history.getHighPrice(),
                (float) history.getLowPrice(),
                format.format(recordDate)
            ));
        }

        CandleDataSet set = new CandleDataSet(entries, "Price Margins");
        set.setBarSpace(0.3f);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        data.addDataSet(set);
        return data;

    }


}
