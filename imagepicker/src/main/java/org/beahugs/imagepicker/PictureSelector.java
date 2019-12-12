package org.beahugs.imagepicker;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.beahugs.imagepicker.entry.Folder;

import java.util.ArrayList;
import java.util.List;

import static org.beahugs.imagepicker.ImagePicker.REQUEST_CODE;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class PictureSelector {

    public static List<Folder> getMultipleResult(Intent data) {
        List<Folder> result = new ArrayList<>();
        if (data != null) {
            result = (List<Folder>) data.getSerializableExtra(REQUEST_CODE);
            if (result == null) {
                result = new ArrayList<>();
            }
            return result;
        }
        return result;
    }

}
