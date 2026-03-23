package com.example.tuprak1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView profileUsername;
    private TextView profileBio;
    private ImageView profileImage;
    private TextView postUsername1;
    private TextView postUsername2;
    private String currentPhotoUri;

    private final ActivityResultLauncher<Intent> editProfileLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Intent data = result.getData();

                            String name = data.getStringExtra("name");
                            String username = data.getStringExtra("username");
                            String bio = data.getStringExtra("bio");
                            String photo = data.getStringExtra("photo");

                            if (name != null) profileName.setText(name);
                            if (username != null) {
                                profileUsername.setText("@" + username);
                                postUsername1.setText(username);
                                postUsername2.setText(username);
                            }
                            if (bio != null) profileBio.setText(bio);
                            if (photo != null) {
                                currentPhotoUri = photo;
                                profileImage.setImageURI(Uri.parse(photo));
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileName = findViewById(R.id.profileName);
        profileUsername = findViewById(R.id.profileUsername);
        profileBio = findViewById(R.id.profileBio);
        profileImage = findViewById(R.id.profileImage);
        postUsername1 = findViewById(R.id.postUsername1);
        postUsername2 = findViewById(R.id.postUsername2);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("name", profileName.getText().toString());
            intent.putExtra("username", profileUsername.getText().toString().replace("@", ""));
            intent.putExtra("bio", profileBio.getText().toString());
            if (currentPhotoUri != null) {
                intent.putExtra("photo", currentPhotoUri);
            }
            editProfileLauncher.launch(intent);
        });
    }
}