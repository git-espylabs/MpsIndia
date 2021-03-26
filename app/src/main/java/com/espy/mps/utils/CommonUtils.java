package com.espy.mps.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.Toast;

import com.espy.mps.BuildConfig;
import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public static int length = Toast.LENGTH_SHORT;

    public static void showDeveloperToast(Context context, String message){
        showDeveloperToast(context, message, length);
    }

    public static void showAppToast(Context context, String message){
        showAppToast(context, message, length);
    }

    public static void showDeveloperToast(Context context, String message, int toastLenght){
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, message, toastLenght).show();
        }
    }

    public static void showAppToast(Context context, String message, int toastLenght){
        Toast.makeText(context, message, toastLenght).show();
    }

    public static String getClassSimpleName(Context context){
        return context.getClass().getSimpleName();
    }

    public static String formatDate_ddMMyyyy(String inputDate){
        String outputDate = "";
        if (inputDate.equals("")){
            return  new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        }
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(inputDate);
            outputDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String formatDate_yyyyMMdd(String inputDate){
        String outputDate = "";
        if (inputDate.equals("0000-00-00")){
            return  "--";
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            outputDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String formatDate_yyyyMMddHHmmss(String inputDate){
        String outputDate = "";
        if (inputDate.equals("0000-00-00 00:00:00")){
            return  "--";
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
            outputDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String capitalizeSentence(String sententence){
        String upperString = "";
        try {
            upperString = sententence.substring(0,1).toUpperCase() + sententence.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
            upperString = "";
        }
        return upperString;
    }

    public static File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copyStream(in, out);
        }
        return tempFile;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static String compressImage(String imageFilePath, Context context) {

        String filePath = imageFilePath;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename(context);
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static String getFilename(Context context) {
        File storageDir =
                context.getExternalFilesDir("Capture");
        if (!storageDir.exists()){
            storageDir.mkdir();
        }
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_MPS_" + System.currentTimeMillis();
        String uriSting = (storageDir.getAbsolutePath() + "/" + imageFileName + ".jpg");
        return uriSting;

    }
}

