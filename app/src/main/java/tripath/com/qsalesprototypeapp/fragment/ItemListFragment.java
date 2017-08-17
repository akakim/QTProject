package tripath.com.qsalesprototypeapp.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tripath.com.qsalesprototypeapp.BaseActivity;
import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.fragment.dummy.DummyContent;
import tripath.com.qsalesprototypeapp.fragment.dummy.DummyContent.DummyItem;
import tripath.com.qsalesprototypeapp.restclient.entity.AdvisorInfoItem;
import tripath.com.qsalesprototypeapp.restclient.entity.CommonClass;
import tripath.com.qsalesprototypeapp.wiget.DividerItemDecoration;
import tripath.com.qsalesprototypeapp.wiget.customRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemListFragment extends Fragment implements ItemListRecyclerViewAdapter.AdapterInterface{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    List <AdvisorInfoItem >codes = new ArrayList<AdvisorInfoItem>();
    ItemListRecyclerViewAdapter adapter;

    public ItemListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemListFragment newInstance(int columnCount) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            codes = getArguments().getParcelableArrayList("codes");


        }else {
            Log.e(getClass().getSimpleName(),"getArguments error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.default_divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);


//        for(AdvisorInfoItem item : codes ){
//            Log.d(getClass().getSimpleName(),item.toString());
//        }
        // Set the adapter

        Context context = view.getContext();
        customRecyclerView recyclerView = (customRecyclerView) view.findViewById(R.id.list);

        if (mColumnCount <= 1) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(dividerItemDecoration);
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new ItemListRecyclerViewAdapter(codes, mListener,this);


        if(codes.size() == 0 ) {
            view.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            view.findViewById(R.id.emptyView).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(adapter);

//        adapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void refreshArgs(Bundle bundle){


        List <AdvisorInfoItem > items = bundle.getParcelableArrayList("codes");

        if(items.size() == 0 ) {
            getView().findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.list).setVisibility(View.GONE);
        }else {
            getView().findViewById(R.id.emptyView).setVisibility(View.GONE);
            getView().findViewById(R.id.list).setVisibility(View.VISIBLE);
        }

        adapter.addAllItem(items);
        adapter.notifyDataSetChanged();
        mListener.onRefreshEnd();
//        BaseActivity acv = ((BaseActivity)getActivity());
//        if(acv.isLoading()){
//            acv.removeLoading();
//        }
    }

    @Override
    public void notifyRequestLayout() {

    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
        void onRefreshEnd();
    }
}
