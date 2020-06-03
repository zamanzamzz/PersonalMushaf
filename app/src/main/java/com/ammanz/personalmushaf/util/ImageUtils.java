package com.ammanz.personalmushaf.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageUtils {
    public static void loadBitmap(String path, ImageView imageView) {
        AtomicReference<Bitmap> bitmap = new AtomicReference<>();
        Observable.fromCallable(() -> {
            bitmap.set(BitmapFactory.decodeFile(path));
            return true;
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((result) -> {
                imageView.setImageBitmap(bitmap.get());
        });
    }
}
