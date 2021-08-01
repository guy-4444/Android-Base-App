package com.guy.baseapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Preconditions;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Activity_TaskRunner extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_runner);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);

        double[][] matrix = new double[200][100];

        int numOfTouches = 0;
        while (numOfTouches < 30) {
            int i = new Random().nextInt(matrix.length);
            int j = new Random().nextInt(matrix[0].length);
//            matrix[i][j] = 1.0;
            matrix[i][j] = new Random().nextDouble();
            numOfTouches++;
        }

        LongRunningTask longRunningTask = new LongRunningTask(matrix, new LongRunningTask.CallBack_Progress() {
            @Override
            public void progress(int progress) {
                progressBar2.setProgress(progress);
            }
        });
        new TaskRunner().executeAsync(longRunningTask, new TaskRunner.Callback<Bitmap>() {
            @Override
            public void onComplete(Bitmap result) {
                imageView.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            }
        });






    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static double[][] normalizeMatrix(double[][] matrix, LongRunningTask.CallBack_Progress callBack_progress) {
        double[][] newM = new double[matrix.length][matrix[0].length];
        int R = 22;

        double prg = 80.0 / matrix.length;
        double maxDist = Math.sqrt(2*R*R);
        for (int i = 0; i < matrix.length; i++) {
            callBack_progress.progress((int) (prg * i));
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = -R; k <= R; k++) {
                    for (int l = -R; l <= R; l++) {
                        try {

                            if (k==0  &&  l==0) {
                                newM[i][j] += matrix[i + k][j + l];
                            } else {
                                double distanceFactor = 1.0 - (Math.sqrt(k * k + l * l) / maxDist);
                                newM[i][j] += distanceFactor * matrix[i + k][j + l];
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) { }
                    }
                }
            }
        }

        double max = 0.0;
        for (int i = 0; i < newM.length; i++) {
            for (int j = 0; j < newM[0].length; j++) {
                max = Math.max(max, newM[i][j]);
            }
        }

        callBack_progress.progress(80);

        List<Double> list = new ArrayList<>();
        for(int i = 0; i < newM.length; i++) {
            list.addAll(DoubleStream.of(newM[i]).boxed().collect(Collectors.toList()));
        }
        callBack_progress.progress(90);

        double percentile = 90.0;
        Collections.sort(list);
        int index = (int) Math.ceil(percentile / 100.0 * list.size());
        double max90 = list.get(index-1);

        callBack_progress.progress(95);



        Log.d("pttt" ,"max = " + max);
        Log.d("pttt" ,"90% Max = " + max90);

        for (int i = 0; i < newM.length; i++) {
            for (int j = 0; j < newM[0].length; j++) {
//                newM[i][j] /= max;
                newM[i][j] = Math.min(newM[i][j], 1.0);
            }
        }
        callBack_progress.progress(100);
        return newM;
    }

    private static Bitmap generateBitmap(double[][] matrix, LongRunningTask.CallBack_Progress callBack_progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            matrix = normalizeMatrix(matrix, callBack_progress);
        }

        int HEIGHT = matrix.length;
        int WIDTH = matrix[0].length;
        Bitmap image = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        int column = 0;

        for (int row = 0; row < HEIGHT; column = 0) {
            while (column < WIDTH) {
                double val = matrix[row][column];
                image.setPixel(column, row, Color.HSVToColor(new float[]{(float) (220.0D - 220.0D * val), 1.0F, 1.0F}));
                ++column;
            }

            ++row;
        }

        return image;
    }

    static class LongRunningTask implements Callable<Bitmap> {

        public interface CallBack_Progress {
            void progress(int progress);
        }

        private double[][] matrix;
        private CallBack_Progress callBack_progress;

        public LongRunningTask(double[][] matrix, CallBack_Progress callBack_progress) {
            this.matrix = matrix;
            this.callBack_progress = callBack_progress;
        }

        public Bitmap call() {
            Bitmap bitmap = generateBitmap(this.matrix, callBack_progress);
            return bitmap;
        }
    }

    public static class TaskRunner {
        private final Executor executor = Executors.newSingleThreadExecutor();
        private final Handler handler = new Handler(Looper.getMainLooper());

        public TaskRunner() {
        }

        public <R> void executeAsync(Callable<R> callable, TaskRunner.Callback<R> callback) {
            this.executor.execute(() -> {
                try {
                    R result = callable.call();
                    this.handler.post(() -> {
                        callback.onComplete(result);
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
        }

        public interface Callback<R> {
            void onComplete(R bmp);
        }
    }
}