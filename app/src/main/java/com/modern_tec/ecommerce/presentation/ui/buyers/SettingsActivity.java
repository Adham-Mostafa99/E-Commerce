package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivitySettingsBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    CircleImageView userPhoto;
    TextView changePhotoBtn;
    EditText fullNameEditText;
    EditText addressEditText;
    ProgressDialog progressDialog;

    private UserViewModel userViewModel;

    private Uri imageUri;
    private boolean isPhotoUpdated = false;

    private User currentUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViews();
        initViewModels();

        userViewModel.getUserInfo();
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUser = user;
                Log.v("TAG", "user:" + user.toString());
                Glide
                        .with(getApplicationContext())
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.profile)
                        .into(userPhoto);

                fullNameEditText.setText(user.getName());
                if (user.getAddress() != null)
                    addressEditText.setText(user.getAddress());

            }
        });

        userViewModel.getUpdatedProfile().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                progressDialog.dismiss();
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                finish();
            }
        });
        
        userViewModel.getIsPasswordUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(SettingsActivity.this, "password updated", Toast.LENGTH_SHORT).show();
            }
        });

        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress("Update Profile");
                currentUser.setName(fullNameEditText.getText().toString().trim());
                currentUser.setAddress(addressEditText.getText().toString().trim());

                userViewModel.updateUserProfile(currentUser, isPhotoUpdated);

            }
        });

        changePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(imageUri)
                        .start(SettingsActivity.this);

            }
        });

        binding.settingsChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog("Change Password");
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            userPhoto.setImageURI(result.getUri());

            imageUri = result.getUri();
            isPhotoUpdated = true;
            currentUser.setPhotoUrl(imageUri.toString());
        }
    }

    private void initBinding() {
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViews() {
        userPhoto = binding.settingsImageProfile;
        changePhotoBtn = binding.changeProfileImageBtn;
        fullNameEditText = binding.settingsEditName;
        addressEditText = binding.settingsEditAddress;
        progressDialog = new ProgressDialog(this);
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void showProgress(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait, while update your profile.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void createAlertDialog(String title) {
        EditText passwordEditText = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(15, 5, 15, 5);
        passwordEditText.setLayoutParams(params);
        passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Enter your new password.")
                .setView(passwordEditText)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String pass = passwordEditText.getText().toString().trim();
                        if (!pass.isEmpty()) {
                            userViewModel.updatePassword(pass);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }
}