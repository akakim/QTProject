package tripath.com.qsalesprototypeapp.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.fragment.ItemListFragment.OnListFragmentInteractionListener;
import tripath.com.qsalesprototypeapp.fragment.dummy.DummyContent.DummyItem;
import tripath.com.qsalesprototypeapp.restclient.entity.AdvisorInfoItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();
    private final List<AdvisorInfoItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    private AdapterInterface adapterInterface;

    public ItemListRecyclerViewAdapter(List<AdvisorInfoItem> items, OnListFragmentInteractionListener listener, AdapterInterface adapterInterface) {
        this.mValues = items;
        this.mListener = listener;
        this.adapterInterface = adapterInterface;
    }

    public void addAllItem(List<AdvisorInfoItem> items ){
        mValues.clear();
        mValues.addAll(items);
    }

    public boolean isInItem () {
        return (mValues == null) ? true : false;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(holder.mItem.getRoomSeq()+"번" );

        SpannableStringBuilder ssb = new SpannableStringBuilder();

        if("10".equals(holder.mItem.getRoomStatus())){
            ssb.append("대기중");
            holder.mStatusView.setTextColor( 0xcc000000);
        }else {
            ssb.append("상담 진행중");
            holder.mStatusView.setTextColor( 0xccee4e4e);
        }
        holder.mStatusView.setText(ssb);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getAuthCode());
                }
            }
        } );
        holder.btnIntroView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem.getAuthCode());
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mStatusView;
        public final Button btnIntroView;
        public AdvisorInfoItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mStatusView = (TextView)view.findViewById( R.id.status );
            btnIntroView = (Button) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + btnIntroView.getText() + "'";
        }
    }




    public interface AdapterInterface{
       public void notifyRequestLayout();

    }
}
