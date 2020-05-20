package com.example.todoboom;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SimpleDialog extends AppCompatDialogFragment{

    private OnDeleteClickListener mListener;

    public interface OnDeleteClickListener{
        void onClickYes();
        void onClickNo();
    }

    public void setDeleteClickListener(OnDeleteClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete message").setMessage("Would you like to delete this item? ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onClickYes();
                    }
                })
        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onClickNo();
            }
        });
        return builder.create();
    }
}