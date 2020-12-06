package comp231.s5g2.tindeappproject.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import comp231.s5g2.tindeappproject.R;

public class CustomAlertDialog {

    //declare private members
    private Activity activity;
    private AlertDialog dialog;

    //Constructor
    CustomAlertDialog(Activity activity){
        this.activity = activity;
    }

    //method to load the alert dialog
    void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_alert_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

    }

    //method to cancel dialog
    void dismissDialog(){
        dialog.dismiss();

    }
}
