package com.tutorials.andorid.app;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.andorid.app.model.UserProfile;
import com.tutorials.andorid.app.model.user.User;
import com.tutorials.andorid.app.service.NetworkConnection;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 100;

    private ImageView imageView;

    private static final String TAG = "HomeActivity";

    private int fragmentIndex = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreate: ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setStatus("Click any button");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void setStatus(String message) {
        TextView txtViewStatus = (TextView) findViewById(R.id.txt_view_status);
        txtViewStatus.setText(message);
    }

    public void showWebViewExample(View view) {
        Intent intent = new Intent(HomeActivity.this, WebViewActivityExample.class);
        startActivity(intent);
    }

    public void showCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
        Intent finalIntent = Intent.createChooser(intent, "Take Via");
        if (finalIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(finalIntent);
        }
    }

    public void showProfilePage(View view) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Ritwik");
        userProfile.setLastName("Vallabdhas");
        userProfile.setEmail("ritwik@gmail.com");
        userProfile.setPhoneNumber("+1111111111");

        startActivity(ProfileActivity.createIntent(this, userProfile));
    }

    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            super.startActivity(intent);
        }
    }

    public void addFragment(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = SampleFragment.getInstance("FRAGMENT - " + this.fragmentIndex);
        if (this.fragmentIndex % 2 == 0) {
            fragmentTransaction.replace(R.id.fragment_container_one, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container_two, fragment);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.fragmentIndex++;
    }

    public void showProgress(View view) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Question");
        builder.setMessage("Do you want to proceed ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(HomeActivity.this, "Ok Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(HomeActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        builder.show();
    }

    public void connectToNetwork(View view) {
        try {
            showProgress("", "Please Wait...");
            NetworkConnection<ArrayList<User>> connection = new NetworkConnection("https://jsonplaceholder.typicode.com/users");
            connection.makeRequest(NetworkConnection.RequestType.GET, new NetworkConnection.Callback() {
                @Override
                public void onSuccess(Object response) {
                    dismissProgress();
                    Log.i(TAG, "onSuccess: ");
                    ArrayList<User> users = (ArrayList<User>) response;
                    TextView txtViewStatus = (TextView) findViewById(R.id.txt_view_status);
                    txtViewStatus.setText("Success response");
//                    setStatus("Number of Users : " + users.size());
                }

                @Override
                public void onError(String error) {
                    dismissProgress();
                    Log.i(TAG, "onError: ");
                    setStatus("Some error");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    private void showProgress(String title, String message) {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(HomeActivity.this);
        }

        if (!this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgress() {
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

}
