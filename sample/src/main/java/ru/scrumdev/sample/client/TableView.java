package ru.scrumdev.sample.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import ru.scrumdev.sample.shared.Bid;

import java.util.List;

/**
 * Created by Dmitriy on 03.06.2016.
 */
public class TableView extends Composite {
    CellTable<Bid> bidTable = new CellTable<Bid>();

    PeriodicDataLoader dataLoader = new PeriodicDataLoader();


    public void buildView() {
        // Create name column.
        TextColumn<Bid> qtyColumn = new TextColumn<Bid>() {
            @Override
            public String getValue(Bid bid) {
                return String.valueOf(bid.getQty());
            }
        };

        // Make the name column sortable.
        qtyColumn.setSortable(true);

        // Create address column.
        TextColumn<Bid> priceColumn = new TextColumn<Bid>() {
            @Override
            public String getValue(Bid bid) {
                return String.valueOf(bid.getPrice());
            }
        };

        // Add the columns.
        bidTable.addColumn(qtyColumn, "QTY");
        bidTable.addColumn(priceColumn, "Price");



        // Connect the table to the data provider.
        dataLoader.addDataDisplay(bidTable);

        dataLoader.loadData();

        initWidget(bidTable);
    }


    class PeriodicDataLoader {
        private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);


        Timer timer = new Timer() {
            @Override
            public void run() {
                loadData();
            }
        };

        public PeriodicDataLoader() {
            this.timer.scheduleRepeating(1000);
        }

        // Create a data provider.
        final ListDataProvider<Bid> dataProvider = new ListDataProvider<Bid>();

        public void addDataDisplay(CellTable<Bid> bidTable) {
            dataProvider.addDataDisplay(bidTable);
        }

        public void loadData() {
            greetingService.getSellBids(new AsyncCallback<List<Bid>>() {
                @Override
                public void onFailure(Throwable caught) {}

                @Override
                public void onSuccess(List<Bid> result) {
                    dataProvider.setList(result);
                }
            });
        }

    }

}
