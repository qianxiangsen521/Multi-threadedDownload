/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example.com.sunshine.download.Permission;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import example.com.sunshine.download.Home.SplashActivity;

public class SamplePermissionListener implements PermissionListener {

  private final SplashActivity activity;

  public SamplePermissionListener(SplashActivity activity) {
    this.activity = activity;
  }

  @Override public void onPermissionGranted(PermissionGrantedResponse response) {
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
  }

  @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
      PermissionToken token) {
    activity.showPermissionRationale(token);
  }
}
