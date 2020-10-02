package com.develop.eventmanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

public class Utilities {
    static OnImagePickerDialogListener onImagePickerDialogListener;
    static Dialog diaImage;

   /* public static Dialog imagePicker(OnImagePickerDialogListener onImagePicker, Context ctx){

        onImagePickerDialogListener = onImagePicker;
        if (diaImage == null) {
            diaImage = new Dialog(ctx, R.style.hidetitle);
            // String Gender=preferenceSingleTon.getGender();
            diaImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            diaImage.setContentView(R.layout.dialog_image_chooser);
            ImageView cameraImg = (ImageView) diaImage.findViewById(R.id.dialog_camera_button);
            ImageView galleryImg = (ImageView)diaImage.findViewById(R.id.dialog_gallery_button);

            cameraImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diaImage.dismiss();
                    onImagePickerDialogListener.onCameraButtonClick();
                }
            });
            galleryImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diaImage.dismiss();
                    onImagePickerDialogListener.onGalleryButtonClick();
                }
            });
        }

        diaImage.show();

        return diaImage;

    }*/
}
