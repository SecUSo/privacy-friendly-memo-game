package org.secuso.privacyfriendlymemory.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

import org.secuso.privacyfriendlymemory.ui.R;

/**
 * Created by Hannes on 05.05.2016.
 */
public class WelcomeDialog extends DialogFragment {


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(i.inflate(R.layout.dialog_welcome, null));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(getActivity().getString(R.string.app_name_long));
        builder.setPositiveButton(getActivity().getString(R.string.button_continue), null);

        return builder.create();
    }

}
