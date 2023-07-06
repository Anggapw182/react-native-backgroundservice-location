# react-native-backgroundservice-location

Package for React-Native Android get location running background service

## Supported platforms

| Platform | Support                                                  |
| -------- | -------------------------------------------------------- |
| Android  | ![#00b48a](https://via.placeholder.com/10/00b48a?text=+) |
| iOS      | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |
| Web      | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |
| Windows  | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |

## Getting started

Install my-project with npm

```bash
  npm install react-native-backgroundservice-location --save
```

or

```bash
  yarn add install react-native-backgroundservice-location
```

## Configuration and Permissions

### Android

This package build using

- "react-native": "0.72.1"
- BackgroundserviceLocation_kotlinVersion=1.7.0
- BackgroundserviceLocation_minSdkVersion=21
- BackgroundserviceLocation_targetSdkVersion=31
- BackgroundserviceLocation_compileSdkVersion=31
- BackgroundserviceLocation_ndkversion=21.4.7075529
- JavaVersion.VERSION_1_8
- gradle:7.2.1

To request access to location, you need to add the following line to your app's =>
android/src/main/AndroidManifest.xml

```bash
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Usage/Examples

```javascript
import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import {
  startLocationUpdates,
  stopLocationUpdates,
  getLocation,
  getUniqueId,
} from 'react-native-backgroundservice-location';

export default function App() {
  getLocation().addListener('onLocationUpdate', (locationMap) => {
    console.log('Received :', locationMap);
  });

  const getImei = () => {
    getUniqueId()
      .then((uniqueID) => {
        console.log(uniqueID);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <View style={styles.container}>
      <Button
        title="Start Location Updates"
        onPress={() =>
          startLocationUpdates(
            15000,
            'http://localhost:9951/api/BackgroundLocation/LocationLog',
            'suep'
          )
        }
      />
      <Button
        title="Stop Location Updates"
        onPress={() => stopLocationUpdates()}
      />
      <Button title="Get getUniqueId" onPress={() => getImei()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
```

Check out the [example project](https://github.com/Anggapw182/react-native-backgroundservice-location/tree/main/example) for more examples.

## Methods

- [startLocationUpdates](#startLocationUpdates)
- [stopLocationUpdates](#stopLocationUpdates)
- [getLocation](#getLocation)
- [getUniqueId](#getUniqueId)

### Details

#### startLocationUpdates

startLocationUpdates function to run the process for pulling location and running function in the background service.

(alias) startLocationUpdates(a: number, b: string): Promise<number> import startLocationUpdates

This function need two parameter :

1. Delay in milisecond (number), This delay is for how long it takes to process the location data pull.
2. url API to save data feedback location into database. Can you create api in your localhost with endpoint =>

```
MethogetLocation
Body Request :
{
    "lat": "DOUBLE",
    "lon": "DOUBLE",
    "accuracy": "DOUBLE",
    "androiddate": "STRING",
    "uniqueID": "STRING"
}
URL : http://{yoururl}/api/BackgroundLocation/LocationLog
```

Example Code from [APIS](https://github.com/Anggapw182/react-native-backgroundservice-location/tree/main/example%20api)

if you don't use the default function to send data to the database then fill it with "null"

Example :

startLocationUpdates(
1000,
'http://localhost:9951/api/BackgroundLocation/LocationLog','userID'
)

or

startLocationUpdates(1000, null, null);

if the third parameter is null then it will default to string "system"

#### stopLocationUpdates

This function for stop / kill process in background service, no parameter.

#### getLocation

This function for get data location, provided that you have run the startLocationUpdates function.

Example :

```
getLocation().addListener('onLocationUpdate', locationMap => {
    console.log('Received :', locationMap);
    // your code for process data locationMap
  })
```

Object data :

```
{
 "accuracy": 1,
 "androiddate": "04-07-2023 14:21:03",
 "latitude": -7.257472,
 "longitude": 112.752088,
 "urlPost": null
}
```

### getUniqueId

Gets the device unique ID

Example

```
const getImei = () => {
   getUniqueId()
     .then((uniqueID) => {
       console.log(uniqueID);
     })
     .catch((error) => {
       console.error(error);
     });
 };
```

## Maintainers

This module is developed and maintained by [Angga Putra](https://github.com/Anggapw182).

I owe a lot to the fantastic React & React Native community, and I contribute back with my free time 👨🏼‍💼💻 so if you like the project, please star it ⭐️!

If you need any help with this module, or anything else, feel free to reach out to me! I provide boutique consultancy services for React & React Native. Just visit my website, or send me an email at anggaputra182@gmail.com 🙏🏻

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

The library is released under the MIT licence. For more information see [LICENSE](https://choosealicense.com/licenses/mit/).
