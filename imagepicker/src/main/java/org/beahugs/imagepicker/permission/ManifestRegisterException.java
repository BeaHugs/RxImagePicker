package org.beahugs.imagepicker.permission;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class ManifestRegisterException extends RuntimeException {

    ManifestRegisterException(String permission) {
        super(permission == null ?
                "No permissions are registered in the manifest file" :
                (permission + ": Permissions are not registered in the manifest file"));
    }
}
