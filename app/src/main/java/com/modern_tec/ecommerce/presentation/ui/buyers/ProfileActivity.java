package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.databinding.ActivityProfileBinding;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    private UserViewModel userViewModel;

    private Uri imageUri;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModels();

        binding.profileBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userViewModel.getUserInfo();
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateProfileData(user);
                currentUser = user;
            }
        });

        binding.profileAddAddressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditInformationActivity.class));
            }
        });

        binding.editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditInformationActivity.class));
            }
        });

        binding.settingsChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog("Change Password");
            }
        });

        binding.profileChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(imageUri)
                        .start(ProfileActivity.this);

            }
        });

    }

    private void updateProfileData(User user) {
        Glide
                .with(getApplicationContext())
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.profile)
                .into(binding.imageProfile);

        binding.profileName.setText(user.getName());
        if (user.getAddress().size() > 0) {
            binding.profileAddAddressTxt.setVisibility(View.GONE);
            binding.profileAddressDetails.setVisibility(View.VISIBLE);
            binding.profileAddressCity.setVisibility(View.VISIBLE);
            binding.profileAddressPostalCode.setVisibility(View.VISIBLE);
            binding.profileAddressPhone.setVisibility(View.VISIBLE);

            binding.profileAddressDetails.setText(user.getAddress().get(0).getAddressName());
            binding.profileAddressCity.setText(user.getAddress().get(0).getCity());
            binding.profileAddressPostalCode.setText(user.getAddress().get(0).getPostalCode());
            binding.profileAddressPhone.setText(user.getAddress().get(0).getPhone());
        } else {
            binding.profileAddAddressTxt.setVisibility(View.VISIBLE);
            binding.profileAddressDetails.setVisibility(View.GONE);
            binding.profileAddressCity.setVisibility(View.GONE);
            binding.profileAddressPostalCode.setVisibility(View.GONE);
            binding.profileAddressPhone.setVisibility(View.GONE);
        }
    }

    private void initBinding() {
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initViewModels() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            binding.imageProfile.setImageURI(result.getUri());

            imageUri = result.getUri();
            currentUser.setPhotoUrl(imageUri.toString());
            userViewModel.updateUserProfile(currentUser, true);
        }
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