package com.example.tuprak1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etUsername;
    private EditText etBio;
    private ImageView editProfileImage;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            selectedImageUri = result.getData().getData();
                            if (selectedImageUri != null) {
                                editProfileImage.setImageURI(selectedImageUri);
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etBio = findViewById(R.id.etBio);
        editProfileImage = findViewById(R.id.editProfileImage);
        Button btnSaveProfile = findViewById(R.id.btnSaveProfile);

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            String name = receivedIntent.getStringExtra("name");
            String username = receivedIntent.getStringExtra("username");
            String bio = receivedIntent.getStringExtra("bio");
            String photo = receivedIntent.getStringExtra("photo");

            if (name != null) etName.setText(name);
            if (username != null) etUsername.setText(username);
            if (bio != null) etBio.setText(bio);
            if (photo != null) {
                selectedImageUri = Uri.parse(photo);
                editProfileImage.setImageURI(selectedImageUri);
            }
        }

        editProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSaveProfile.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String bio = etBio.getText().toString().trim();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("username", username);
            resultIntent.putExtra("bio", bio);

            if (selectedImageUri != null) {
                resultIntent.putExtra("photo", selectedImageUri.toString());
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
