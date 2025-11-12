import java.util.*;
class Movie {
    String title;
    double imdbRating;
    int releaseYear;
    int watchTimePopularity; // e.g., number of watches or hours streamed
    Movie(String title, double imdbRating, int releaseYear, int watchTimePopularity) {
        this.title = title;
        this.imdbRating = imdbRating;
        this.releaseYear = releaseYear;
        this.watchTimePopularity = watchTimePopularity;
    }
}
public class StreamFlixSorter {
    public static int partition(Movie[] movies, int low, int high, int criteria) {
        Movie pivot = movies[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(movies[j], pivot, criteria)) {
                i++;
                Movie temp = movies[i];
                movies[i] = movies[j];
                movies[j] = temp;
            }
        }
        Movie temp = movies[i + 1];
        movies[i + 1] = movies[high];
        movies[high] = temp;
        return i + 1;
    }
    public static boolean compare(Movie a, Movie b, int criteria) {
        switch (criteria) {
            case 1: // IMDB Rating — higher rating = better
                return a.imdbRating > b.imdbRating;
            case 2: // Release Year — newer = better
                return a.releaseYear > b.releaseYear;
            case 3: // Watch Time Popularity — more = better
                return a.watchTimePopularity > b.watchTimePopularity;
            default:
                return false;
        }
    }
    public static void quickSort(Movie[] movies, int low, int high, int criteria) {
        if (low < high) {
            int pi = partition(movies, low, high, criteria);
            quickSort(movies, low, pi - 1, criteria);
            quickSort(movies, pi + 1, high, criteria);
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of movies in the system: ");
        int n = sc.nextInt();
        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Movie " + (i + 1) + ":");
            System.out.println("1. Title\n2. IMDB Rating\n3. Release Year\n4. Watch Time Popularity");
            String title = sc.next();
            double rating = sc.nextDouble();
            int year = sc.nextInt();
            int popularity = sc.nextInt();
            movies[i] = new Movie(title, rating, year, popularity);
        }
        System.out.println("\n-------------------- STREAMFLIX MOVIE RECOMMENDER --------------------");
        while (true) {
            System.out.println("\nSort movies based on:\n1. IMDB Rating\n2. Release Year\n3. Watch Time Popularity");
            int criteria = sc.nextInt();
            quickSort(movies, 0, movies.length - 1, criteria);
            System.out.println("\n----- Sorted Movies -----");
            for (Movie m : movies) {
                System.out.println(m.title + " | Rating: " + m.imdbRating + " | Year: " + m.releaseYear + " | Popularity: " + m.watchTimePopularity);
            }
            System.out.println("--------------------------------------------------------------");
        }
    }
}
