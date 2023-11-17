import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:stack_appodeal_flutter/stack_appodeal_flutter.dart';

class AppodealNativeAd extends StatefulWidget {
  final NativeAdOptions options;
  final String placement;

  const AppodealNativeAd({
    Key? key,
    required this.options,
    this.placement = "default",
  }) : super(key: key);

  @override
  _AppodealNativeAdState createState() => _AppodealNativeAdState();
}

class _AppodealNativeAdState extends State<AppodealNativeAd> {
  final UniqueKey _key = UniqueKey();
  final String _viewType = 'appodeal_flutter/native_view';

  @override
  Widget build(BuildContext context) {

    print("NativeAdOptions: ${widget.options.toMap}");



    return Platform.isIOS
        ? UiKitView(
            key: _key,
            viewType: _viewType,
            creationParams: _nativeCreationParams,
            creationParamsCodec: const StandardMessageCodec(),
          )
        : PlatformViewLink(
            key: _key,
            viewType: _viewType,
            surfaceFactory:
                (BuildContext context, PlatformViewController controller) {
              return AndroidViewSurface(
                controller: controller as AndroidViewController,
                gestureRecognizers: const <Factory<
                    OneSequenceGestureRecognizer>>{},
                hitTestBehavior: PlatformViewHitTestBehavior.opaque,
              );
            },
            onCreatePlatformView: (PlatformViewCreationParams params) {
              return PlatformViewsService.initSurfaceAndroidView(
                id: params.id,
                viewType: _viewType,
                layoutDirection: TextDirection.ltr,
                creationParams: _nativeCreationParams,
                creationParamsCodec: const StandardMessageCodec(),
                onFocus: () {
                  params.onFocusChanged(true);
                },
              )
                ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
                ..create();
            },
          );
  }

  Map<String, dynamic> get _nativeCreationParams => {
        'options': widget.options.toMap,
        'placement': widget.placement,
      };
}
