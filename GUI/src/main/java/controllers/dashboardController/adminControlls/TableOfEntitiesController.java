package controllers.dashboardController.adminControlls;

import interfaces.Controlls;
import interfaces.Repository;
import interfaces.ViewsObjectGetter;
import views.dashboard.adminView.TableOfEntities;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;


public class TableOfEntitiesController
        <T extends TableOfEntities, G extends Repository, M> implements Controlls<T>,
        ViewsObjectGetter<T> {


    protected final T tableOfEntities;
    protected Repository<G> repository;
    protected List<List> results;
    protected List<M> mappedResults;



    protected TableOfEntitiesController(T tableOfEntities, G repository) throws SQLException {
        this.tableOfEntities = tableOfEntities;
        this.repository = repository;

    }

    public static <T extends TableOfEntities, G extends Repository, M> TableOfEntitiesController
            <T, G, M>initTableOfEntitiesController
            (T tableOfEntities, G repository) throws SQLException {
        return new TableOfEntitiesController<T, G, M>(tableOfEntities, repository);
    }


    @Override
    public void config() {
        tableOfEntities.setLayout(new BorderLayout());
    }

    @Override
    public void build() {
        results = repository.selectAll();

        tableOfEntities.add(tableOfEntities.getTitle(), BorderLayout.NORTH);

        tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
        tableOfEntities.add(tableOfEntities.getTitle());
        tableOfEntities.add(tableOfEntities.getScrollPane(),BorderLayout.SOUTH);
    }


    @Override
    public void setSizes() {
        if(results.size() != 0){
            tableOfEntities.getScrollPane().getVerticalScrollBar()
                    .setPreferredSize(new Dimension(20,0));
            tableOfEntities.getScrollPane().getHorizontalScrollBar()
                    .setPreferredSize(new Dimension(0,20));
            tableOfEntities.getTable()
                    .setPreferredScrollableViewportSize(new Dimension(900,500));
        }

    }

    @Override
    public T getViewObject() {
        return tableOfEntities;
    }

    protected List<M> mapResultsToObjects(List<List> data) {
        return null;
    }
}
