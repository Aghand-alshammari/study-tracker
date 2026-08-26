package com.taqaddum.app;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (host == null) return;

        NavController controller = host.getNavController();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigation, controller);
        controller.addOnDestinationChangedListener((navController, destination, arguments) ->
                bottomNavigation.setVisibility(destination.getId() == R.id.editSubjectFragment
                        ? View.GONE : View.VISIBLE));
    }
}
