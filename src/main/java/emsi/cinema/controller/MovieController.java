package emsi.cinema.controller;

import emsi.cinema.common.FileUploadUtil;
import emsi.cinema.model.Movie;
import emsi.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/movies")
@RestController
public class MovieController {
    private final MovieService movieService;
    private final FileUploadUtil fileUploadUtil;

    @Autowired
    public MovieController(MovieService movieService, FileUploadUtil fileUploadUtil) {
        this.movieService = movieService;
        this.fileUploadUtil = fileUploadUtil;
    }

    @PostMapping("/addnew")
    public ResponseEntity<Movie> addMovie(@RequestParam("title") String title,
                                          @RequestParam("images") List<MultipartFile> images,
                                          @RequestParam("trailer") MultipartFile trailer) {
        try {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile image : images) {
                String imageUrl = fileUploadUtil.uploadImage(image);
                imageUrls.add(imageUrl);
            }

            String trailerUrl = fileUploadUtil.uploadTrailer(trailer);

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setImageUrls(imageUrls);
            movie.setTrailerUrl(trailerUrl);
            movie.setTimeCreate(new Timestamp(System.currentTimeMillis()));

            Movie createdMovie = movieService.createMovie(movie);
            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/allmovie")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie>  movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Endpoint to serve images
    @GetMapping("/movies/images/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
        Resource resource = fileUploadUtil.loadFileAsResource(fileName, "static/upload/images/");
        return ResponseEntity.ok().body(resource);
    }

    // Endpoint to serve trailers
    @GetMapping("/movies/trailers/{fileName:.+}")
    public ResponseEntity<Resource> serveTrailer(@PathVariable String fileName) throws IOException {
        Resource resource = fileUploadUtil.loadFileAsResource(fileName, "static/upload/trailers/");
        return ResponseEntity.ok().body(resource);
    }
}