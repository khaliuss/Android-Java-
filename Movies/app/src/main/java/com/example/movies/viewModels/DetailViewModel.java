package com.example.movies.viewModels;

import android.app.Application;
import android.health.connect.TimeRangeFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.database.FavoriteDataBase;
import com.example.movies.database.FavoriteMovieDao;
import com.example.movies.pojo.Movie;
import com.example.movies.pojo.Trailer;
import com.example.movies.pojo.TrailerResponse;
import com.example.movies.retrofit.ApiFactory;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {

    private FavoriteMovieDao dao;
    private MutableLiveData<List<Trailer>> trailers =new MutableLiveData<>();
    private MutableLiveData<Boolean> isTrailerExist = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTrailersLoading = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public DetailViewModel(@NonNull Application application) {
        super(application);
        dao = FavoriteDataBase.getDataBase(getApplication()).favoriteMovieDao();
    }

    public LiveData<List<Trailer>> trailers() {
        return trailers;
    }

    public LiveData<Boolean> getIsTrailerExist() {
        return isTrailerExist;
    }

    public LiveData<Boolean> getIsTrailersLoading() {
        return isTrailersLoading;
    }

    public void loadTrailers(int id){
        Disposable disposable = ApiFactory.getApiService().loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isTrailersLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isTrailersLoading.setValue(false);
                    }
                })
                .repeat(2)
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailersList) throws Throwable {
                        trailers.setValue(trailersList);
                        isTrailerExist.setValue(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isTrailerExist.setValue(false);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void addMovieToFavorite(Movie movie){
        Disposable disposable = dao.addToFavorite(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeMovieFavorite(int id){
        Disposable disposable = dao.removeFromFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
