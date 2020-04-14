package com.example.rolplay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class DialogCarga extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder constructorDelDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_carga,null);

        constructorDelDialog.setView(view);

        return constructorDelDialog.create();
    }
}
