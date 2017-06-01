package sg.edu.rp.c347.p07smsretriever;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFirst extends Fragment {


    Button btnAddText;
    TextView tvFrag1, tvData;
    EditText numEntered;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        tvFrag1 = (TextView) view.findViewById(R.id.tvSms1);
        btnAddText = (Button) view.findViewById(R.id.btnRetrieve);
        tvData = (TextView) view.findViewById(R.id.textViewData);
        numEntered = (EditText) view.findViewById(R.id.etNum);

        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numEntered.getText().toString();


                Uri uri = Uri.parse("content://sms");


                String[] reqCols = new String[]{"date", "address", "body", "type"};


                ContentResolver cr = getContentResolver();

                Cursor cursor = cr.query(uri, reqCols, null, null, null);
                String smsBody = "";
                if (cursor.moveToFirst()) {
                    do {
                        long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat
                                .format("dd MMM yyyy h:mm:ss aa", dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);
                        if (type.equalsIgnoreCase("1")) {
                            type = "Inbox:";
                        } else {
                            type = "Sent:";
                        }
                        smsBody += type + " " + address + "\n at " + date
                                + "\n\"" + body + "\"\n\n";
                    } while (cursor.moveToNext());
                }
                tvData.setText(smsBody);






            }
        });

        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    btnAddText.performClick();

                } else {

                    Toast.makeText( MainActivity.class ,"Permission not granted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}


