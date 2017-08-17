package tripath.com.qsalesprototypeapp.mvpinterface;

import java.util.List;

/**
 * Created by SSLAB on 2017-08-16.
 */

public class GetListPresenterImplement implements GetLIstPresenter,GetListInteractorImplement.OnFinishListener {

    private GetLIstView listViewItems;
    private GetListItemsInteractorImplement interactor;
    private List items;


    public GetListPresenterImplement(GetLIstView getListView,List items) {
        this.listViewItems = getListView;
//        this.interactor = new GetListItemsInteractorImplement()
        this.items = items;
    }

    public GetListPresenterImplement(GetLIstView getListView, GetListItemsInteractorImplement interactor,List items) {
        this.listViewItems = getListView;
        this.interactor = interactor;
        this.items = items;
    }



    @Override
    public void onResume() {

        if( listViewItems != null ){
            listViewItems.showProgress();
        }
        interactor.findItems(
            this, items
        );


    }

    @Override
    public void onItemClicked(int position) {
        listViewItems.goChattingRoom();
    }

    @Override
    public void onDestroy() {
        listViewItems = null;
    }

    @Override
    public void onFinished(List items) {
        if( listViewItems != null){
            this.items = items ;
            listViewItems.setItem( items);
            listViewItems.hideProgress();
        }
    }

    public GetLIstView getListViewItems(){
        return listViewItems;
    }
}
