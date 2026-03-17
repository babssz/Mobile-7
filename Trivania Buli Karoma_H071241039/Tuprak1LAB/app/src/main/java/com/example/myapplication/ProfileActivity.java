package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvUsername, tvBio, tvTopUsername;
    ImageView ivProfilePic, ivBottomNavProfile;
    Button btnEdit;

    ActivityResultLauncher<Intent> editLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName             = findViewById(R.id.tv_name);
        tvUsername         = findViewById(R.id.tv_username);
        tvTopUsername      = findViewById(R.id.tv_top_username);
        tvBio              = findViewById(R.id.tv_bio);
        ivProfilePic       = findViewById(R.id.iv_profile_pic);
        ivBottomNavProfile = findViewById(R.id.iv_bottom_nav_profile);
        btnEdit            = findViewById(R.id.btn_edit_profile);

        editLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name     = data.getStringExtra("name");
                            String username = data.getStringExtra("username");
                            String bio      = data.getStringExtra("bio");
                            String photoUri = data.getStringExtra("photo_uri");

                            if (name != null)     tvName.setText(name);
                            if (bio != null)      tvBio.setText(bio);
                            if (username != null) {
                                tvUsername.setText(username);
                                tvTopUsername.setText(username);
                            }

                            if (photoUri != null) {
                                Uri uri = Uri.parse(photoUri);
                                ivProfilePic.setImageURI(uri);
                                ivBottomNavProfile.setImageURI(uri);
                                ivProfilePic.setTag(photoUri);
                            }
                        }
                    }
                }
        );

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("name",     tvName.getText().toString());
            intent.putExtra("username", tvUsername.getText().toString());
            intent.putExtra("bio",      tvBio.getText().toString());

            Object tag = ivProfilePic.getTag();
            if (tag != null) {
                intent.putExtra("photo_uri", tag.toString());
            }

            editLauncher.launch(intent);
        });
    }
}