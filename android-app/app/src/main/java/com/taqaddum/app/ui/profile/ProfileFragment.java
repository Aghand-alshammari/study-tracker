package com.taqaddum.app.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.taqaddum.app.R;

public class ProfileFragment extends Fragment {
    private static final String PREFS = "profile";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE = "image_uri";
    private ImageView profileImage;
    private final ActivityResultLauncher<String[]> imagePicker = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(), this::onImageSelected);

    public ProfileFragment() { super(R.layout.fragment_profile); }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        super.onViewCreated(view, state);
        profileImage = view.findViewById(R.id.profile_image);
        TextInputEditText nameInput = view.findViewById(R.id.name_input);
        TextInputEditText emailInput = view.findViewById(R.id.email_input);
        TextInputLayout emailLayout = view.findViewById(R.id.email_layout);
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        nameInput.setText(preferences.getString(KEY_NAME, ""));
        emailInput.setText(preferences.getString(KEY_EMAIL, ""));
        restoreImage(preferences.getString(KEY_IMAGE, null));
        view.findViewById(R.id.add_photo_button).setOnClickListener(v -> imagePicker.launch(new String[]{"image/*"}));
        view.findViewById(R.id.save_profile_button).setOnClickListener(v -> {
            String email = text(emailInput).trim();
            if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.setError(getString(R.string.email_invalid));
                return;
            }
            emailLayout.setError(null);
            preferences.edit().putString(KEY_NAME, text(nameInput).trim()).putString(KEY_EMAIL, email).apply();
            Toast.makeText(requireContext(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
        });
    }

    private void onImageSelected(Uri uri) {
        if (uri == null || profileImage == null) return;
        try {
            requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(KEY_IMAGE, uri.toString()).apply();
            profileImage.setImageURI(uri);
        } catch (SecurityException ignored) {
            Toast.makeText(requireContext(), R.string.photo_access_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void restoreImage(String value) {
        if (value == null || value.isEmpty()) return;
        try { profileImage.setImageURI(Uri.parse(value)); }
        catch (SecurityException ignored) { requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().remove(KEY_IMAGE).apply(); }
    }

    private String text(TextInputEditText input) { return input.getText() == null ? "" : input.getText().toString(); }
}
