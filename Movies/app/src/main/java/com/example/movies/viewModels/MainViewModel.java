package com.example.movies.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.pojo.Movie;
import com.example.movies.pojo.MovieList;
import com.example.movies.retrofit.ApiFactory;
import com.example.movies.retrofit.ApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private ApiService apiService;
    private int page = 1;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Movie>> moviesMLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiFactory.getApiService();
        loadMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesMLD;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadMovies() {
        Boolean loading = isLoading.getValue();
        if (loading != null && loading) {
            return;
        }
        Disposable disposable = apiService.loadMovies(page).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieList>() {
                    @Override
                    public void accept(MovieList movieList) throws Throwable {
                        List<Movie> loadedMovies = moviesMLD.getValue();
                        if (loadedMovies != null) {
                            loadedMovies.addAll(movieList.getMovies());
                            moviesMLD.setValue(loadedMovies);
                        } else {
                            moviesMLD.setValue(movieList.getMovies());
                        }
                        page++;
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
