package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PortfolioElem extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_portfolio_elem);
        TextView portfolioName = findViewById(R.id.name_text_view);
        TextView portfolioSubject = findViewById(R.id.subject_text_view);
        TextView portfolioLevel = findViewById(R.id.level_text_view);
        TextView portfolioResult = findViewById(R.id.result_text_view);
        TextView portfolioDate = findViewById(R.id.date_text_veiw);

        portfolioName.setText(Portfolio.currentPortfolio.name);
        portfolioSubject.setText(Portfolio.currentPortfolio.subject);
        portfolioLevel.setText(Portfolio.currentPortfolio.level);
        portfolioResult.setText(Portfolio.currentPortfolio.result);
        portfolioDate.setText(Portfolio.currentPortfolio.date);

    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, Portfolio.class);
        startActivity(intent);
    }
}
