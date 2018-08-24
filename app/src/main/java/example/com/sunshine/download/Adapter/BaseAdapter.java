package example.com.sunshine.download.Adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<T,H extends BaseViewHolder> extends PagedListAdapter<T, BaseViewHolder> {
    private BaseAdapter.OnItemClickListener mOnItemClickListener;
    private BaseAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private SparseIntArray layouts;
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
  
    public BaseAdapter(@Nullable List<T> data) {
        this(0,data);
    }

    public BaseAdapter(@LayoutRes int layoutResId , @Nullable List<T> data) {
        this(layoutResId,data,null);
    }
    public BaseAdapter(@LayoutRes int layoutResId , @Nullable List<T> data,@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }
    private int getLayoutId(int viewType) {

        return layouts.get(viewType);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }
    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        H baseViewHolder;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);

        baseViewHolder = onCreateDefViewHolder(parent, viewType);
        bindViewClickListener(baseViewHolder);
        return baseViewHolder;
    }

    protected H onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mLayoutResId;
        if (layouts != null) {
            layoutId = getLayoutId(viewType);
        }
        return createBaseViewHolder(parent, layoutId);
    }
    protected H createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    protected H createBaseViewHolder(View view) {
        return (H) new BaseViewHolder(view);
    }


    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        onBaseViewHolder((H)holder,getItem(position));
    }

    public abstract void onBaseViewHolder(H holder, T item);


    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position < mData.size())
            return mData.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().onItemClick(BaseAdapter.this, v, baseViewHolder.getLayoutPosition());
                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return getOnItemLongClickListener().onItemLongClick(BaseAdapter.this, v, baseViewHolder.getLayoutPosition());
                }
            });
        }
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    public interface OnItemLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param adapter  the adpater
         * @param view     The view whihin the RecyclerView that was clicked and held.
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        boolean onItemLongClick(BaseAdapter adapter, View view, int position);
    }


    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView itemView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         *
         * @param adapter  the adpater
         * @param view     The itemView within the RecyclerView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }



    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been long clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }



    /**
     * @return The callback to be invoked with an item in this RecyclerView has
     * been long clicked and held, or null id no callback as been set.
     */
    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    /**
     * @return The callback to be invoked with an item in this RecyclerView has
     * been clicked and held, or null id no callback as been set.
     */
    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

}
