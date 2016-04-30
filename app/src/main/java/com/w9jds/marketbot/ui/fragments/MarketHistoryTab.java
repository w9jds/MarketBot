package com.w9jds.marketbot.ui.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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

            SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.getDefault());

            int size = historyEntries.getValue().size();
            List<MarketHistory> histories = new ArrayList<>(size);
            List<String> xAxis = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                MarketHistory history = (MarketHistory) historyEntries.getValue().get(i);
                Date recordDate = new Date(history.getRecordDate());

                xAxis.add(format.format(recordDate));
                histories.add(history);
            }

            CombinedData data = new CombinedData(xAxis);

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

        chart.setMaxVisibleValueCount(40);
        chart.set

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
        xAxis.setLabelsToSkip(15);
//        xAxis.setDrawLabels(false);

        return view;
    }

    private BarData generateOrderBarData(List<MarketHistory> historyEntries) {

        SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.getDefault());

        int size = historyEntries.size();
        List<BarEntry> entries = new ArrayList<>(size);
        List<String> xAxis = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            xAxis.add(format.format(recordDate));
            entries.add(new BarEntry((float) history.getOrderCount(), i));
        }

        BarDataSet set = new BarDataSet(entries, "Order Count");
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);

        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        return new BarData(xAxis, set);
    }

    private LineData generateAverageLineData(List<MarketHistory> historyEntries) {

        SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.getDefault());

        int size = historyEntries.size();
        List<Entry> entries = new ArrayList<>(size);
        List<String> xAxis = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            xAxis.add(format.format(recordDate));
            entries.add(new Entry((float) history.getAveragePrice(), i));
        }

        LineDataSet set = new LineDataSet(entries, "Price Averages");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return new LineData(xAxis, set);
    }

    private CandleData generateMarginCandleData(List<MarketHistory> historyEntries) {

        SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.getDefault());

        int size = historyEntries.size();
        List<CandleEntry> entries = new ArrayList<>(size);
        List<String> xAxis = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketHistory history = historyEntries.get(i);
            Date recordDate = new Date(history.getRecordDate());

            xAxis.add(format.format(recordDate));
            entries.add(new CandleEntry(i,
                (float) history.getHighPrice(),
                (float) history.getLowPrice(),
                (float) history.getAveragePrice(),
                (float) history.getAveragePrice()
            ));
        }

        CandleDataSet set = new CandleDataSet(entries, "Price Margins");
        set.setShadowColor(Color.DKGRAY);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.RED);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.rgb(122, 242, 84));
        set.setIncreasingPaintStyle(Paint.Style.STROKE);
        set.setNeutralColor(Color.BLUE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return new CandleData(xAxis, set);

    }


}
