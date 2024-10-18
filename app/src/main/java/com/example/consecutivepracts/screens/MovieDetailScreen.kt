package com.example.consecutivepracts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.consecutivepracts.components.MovieViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.unit.dp


@Composable fun MovieDetailScreen(movieId: Int, navController: NavController, viewModel: MovieViewModel = viewModel()) {
    // Получаем список фильмов из ViewModel
    val movie = viewModel.movies.find { it.id == movieId }

// Проверяем, найден ли фильм
    if (movie != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = rememberImagePainter(movie.posterUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text ="Описание: " + movie.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Премьера: " + movie.premiere, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Страна: " + movie.country, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Жанр: " + movie.genre, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Режиссер: " + movie.director, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "В ролях: " + movie.starring, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Продолжительность: " + movie.duration, style = MaterialTheme.typography.bodyLarge)

        }
    } else {
        Text(text = "Фильм не найден", style = MaterialTheme.typography.bodyMedium)
    }

    Button(
        onClick = { navController.popBackStack() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray
        )
    ) {
        Text(
            text = "Назад",
            color = Color.Black
        )
    }


}

