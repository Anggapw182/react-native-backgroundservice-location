import { NativeModules, Platform,  DeviceEventEmitter} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-backgroundservice-location' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// const BackgroundserviceLocation = NativeModules.BackgroundserviceLocation
//   ? NativeModules.BackgroundserviceLocation
//   : new Proxy(
//       {},
//       {
//         get() {
//           throw new Error(LINKING_ERROR);
//         },
//       }
//     );

// export function multiply(a: number, b: number): Promise<number> {
//   return BackgroundserviceLocation.multiply(a, b);
// }


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

export function getLocatino(): Promise<number> {
  return(
    DeviceEventEmitter.addListener('onLocationUpdate', locationMap => {
      console.log('Received location update 1:', locationMap);
    })
  );
}
