package com.example.todoboom;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import android.util.Log;



public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>{

    private static String TAG ="RecyclerViewAdaptor";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
        boolean onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener; 
    }

    public RecyclerViewAdaptor(Context context ,ArrayList<String> imageNames) {
        this.mImageNames = imageNames;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_of_item, parent, false);
        ViewHolder holder = new ViewHolder(view, mListener);
        return holder;
    }

    @Override
    public int getItemCount() {

        //return mImageNames == null ? 0 :mImageNames.size();
        return mImageNames.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imageName.setText(mImageNames.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView imageName;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.text_side);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "clicked");
                    if(listener != null){
                        int position = getAdapterPosition();
                        Log.d(TAG, toString().valueOf(position));
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });  // setOnClickListener

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG, "long clicked");
                    if (listener != null){
                        int position = getAdapterPosition();
                        Log.d(TAG, toString().valueOf(position));

                        if(position != RecyclerView.NO_POSITION){
                            listener.onLongClick(position);
                        }
                    }
                    return true;
                }
            });  // setOnLongClickListener
        }
    }
}
