
# react-native-backgroundservice-location

Package for React-Native Android get location running background service


## Supported platforms

| Platform             | Support                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Android | ![#00b48a](https://via.placeholder.com/10/00b48a?text=+) |
| iOS | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |
| Web | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |
| Windows | ![#ff0707](https://via.placeholder.com/10/ff0707?text=+) |


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
To request access to location, you need to add the following line to your app's  =>
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
import { startLocationUpdates, stopLocationUpdates, getLocatino } from 'react-native-backgroundservice-location';

export default function App() {
 
  getLocatino().addListener('onLocationUpdate', locationMap => {
    console.log('Received :', locationMap);
  })

  return (
    <View style={styles.container}>
      <Button title="Start Location Updates" onPress={()=>startLocationUpdates(1000,null)} />
      <Button title="Stop Location Updates" onPress={()=>stopLocationUpdates()} />
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
- [getLocatino](#getLocatino)

### Details

#### startLocationUpdates
startLocationUpdates function to run the process for pulling location and running function in the background service.

(alias) startLocationUpdates(a: number, b: string): Promise<number> import startLocationUpdates

This function need two parameter :

1. Delay in milisecond (number), This delay is for how long it takes to process the location data pull.
2. url API to save data feedback location into database. Can you create api in your localhost with endpoint =>

```
Methods : POST
Body Request : 
{
    "lat": "STRING", 
    "lon": "STRING",
    "accuracy": "STRING", 
    "androiddate": "STRING", 
}
URL : http://{yoururl}/api/BackgroundLocation/LocationLog
 ```

if you don't use the default function to send data to the database then fill it with "null"

Example :

startLocationUpdates(
      1000,
      'http://localhost:9951/api/BackgroundLocation/LocationLog',
    )

or

startLocationUpdates(1000, null);

#### stopLocationUpdates
This function for stop / kill process in background service, no parameter.

#### getLocatino
This function for get data location, provided that you have run the startLocationUpdates function.

Example : 
```
getLocatino().addListener('onLocationUpdate', locationMap => {
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
## Maintainers
This module is developed and maintained by [Angga Putra](https://github.com/Anggapw182).

I owe a lot to the fantastic React & React Native community, and I contribute back with my free time 👨🏼‍💼💻 so if you like the project, please star it ⭐️!

If you need any help with this module, or anything else, feel free to reach out to me! I provide boutique consultancy services for React & React Native. Just visit my website, or send me an email at anggaputra182@gmail.com 🙏🏻



## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.
## License
The library is released under the MIT licence. For more information see [LICENSE](https://choosealicense.com/licenses/mit/).
