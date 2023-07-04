import { NativeModules, Platform,  DeviceEventEmitter} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-backgroundservice-location' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const LocationProvider = NativeModules.LocationProvider
? NativeModules.LocationProvider
: new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );

export function startLocationUpdates(a: number, b: string): Promise<number> {
  return LocationProvider.startLocationUpdates(a, b);
}

export function stopLocationUpdates(): Promise<number> {
  return LocationProvider.stopLocationUpdates();
}

export function getLocatino() {
  return DeviceEventEmitter;
}
