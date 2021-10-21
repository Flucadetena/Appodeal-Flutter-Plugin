import 'dart:async';
import 'dart:io';

import 'package:stack_appodeal_flutter/stack_appodeal_flutter.dart';
import 'package:appodeal_flutter_example/Interstitial.dart';
import 'package:appodeal_flutter_example/banner.dart';
import 'package:appodeal_flutter_example/banner_view.dart';
import 'package:appodeal_flutter_example/mrec_view.dart';
import 'package:appodeal_flutter_example/rewarded_video.dart';
import 'package:flutter/material.dart';

import 'consent_manager.dart';

void main() {
  runApp(MaterialApp(
    home: AppodealDemoApp(),
  ));
}

class AppodealDemoApp extends StatefulWidget {
  static String appKey = Platform.isAndroid
      ? "fee50c333ff3825fd6ad6d38cff78154de3025546d47a84f"
      : "466de0d625e01e8811c588588a42a55970bc7c132649eede";

  @override
  _AppodealDemoAppState createState() => _AppodealDemoAppState();
}

class _AppodealDemoAppState extends State<AppodealDemoApp> {

  @override
  void initState() {
    super.initState();
  }

  Future<void> initialization() async {
    Appodeal.setLogLevel(Appodeal.LogLevelVerbose);
    Appodeal.setTesting(true);
    Appodeal.setAutoCache(Appodeal.INTERSTITIAL, false);
    Appodeal.setAutoCache(Appodeal.REWARDED_VIDEO, false);
    Appodeal.setTriggerOnLoadedOnPrecache(Appodeal.INTERSTITIAL, true);
    Appodeal.setSharedAdsInstanceAcrossActivities(true);
    Appodeal.setSmartBanners(false);
    Appodeal.setTabletBanners(false);
    Appodeal.setBannerAnimation(false);
    Appodeal.disableNetwork("admob");
    Appodeal.disableNetworkForSpecificAdType("vungle", Appodeal.INTERSTITIAL);
    Appodeal.disableLocationPermissionCheck();
    Appodeal.disableWriteExternalStoragePermissionCheck();
    Appodeal.setUserId("1");
    Appodeal.setUserAge(22);
    Appodeal.setUserGender(Appodeal.GENDER_FEMALE);
    Appodeal.setCustomFilterString("key", "value");
    Appodeal.setCustomFilterBool("key", true);
    Appodeal.setCustomFilterInt("setCustomFilterInt", 123);
    Appodeal.setCustomFilterDouble("setCustomFilterDouble", 2.1);
    Appodeal.muteVideosIfCallsMuted(true);
    Appodeal.setChildDirectedTreatment(true);
    Appodeal.setExtraDataBool("setExtraDataBool", true);
    Appodeal.setExtraDataInt("setExtraDataInt", 123);
    Appodeal.setExtraDataDouble("setExtraDataDouble", 1.2);
    Appodeal.setExtraDataString("setExtraDataString", "value");
    Appodeal.setUseSafeArea(true);
    Appodeal.initialize(
        AppodealDemoApp.appKey,
        [
          Appodeal.REWARDED_VIDEO,
          Appodeal.INTERSTITIAL,
          Appodeal.BANNER,
          Appodeal.MREC
        ],
        false);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text('Appodeal Flutter Demo'),
        ),
        body: Column(children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.only(
                    left: 4.0, right: 4.0, top: 8.0, bottom: 4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () {
                    initialization();
                  },
                  child: const Text('INITIALIZATION'),
                ),
              ),
            ],
          ),
          //Header
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.only(
                    left: 4.0, right: 4.0, top: 8.0, bottom: 4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => InterstitialPage()),
                    );
                  },
                  child: const Text('INTERSTITIAL'),
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => RewardedVideoPage()),
                    );
                  },
                  child: const Text('REWARDED VIDEO'),
                ),
              ),
            ],
          ),
          //Interstitial
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => BannerPage()),
                    );
                  },
                  child: const Text('BANNER'),
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => BannerViewPage()),
                    );
                  },
                  child: const Text('BANNER VIEW'),
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => MrecViewPage()),
                    );
                  },
                  child: const Text('MREC VIEW'),
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(4.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      textStyle: const TextStyle(fontSize: 20),
                      fixedSize: Size(300, 20)),
                  onPressed: () async {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => ConsentManagerPage()),
                    );
                  },
                  child: const Text('CONSENT MANAGER'),
                ),
              ),
            ],
          ),
        ]),
      ),
    );
  }

  static void showToast(String message) {
    print(message);
  }
}
