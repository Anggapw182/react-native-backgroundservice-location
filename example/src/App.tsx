import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import {
  startLocationUpdates,
  stopLocationUpdates,
  getLocatino,
  getUniqueId,
} from 'react-native-backgroundservice-location';

export default function App() {
  getLocatino().addListener('onLocationUpdate', (locationMap) => {
    console.log('Received :', locationMap);
  });

  const getImei = () => {
    console.log('get uniq id');

    getUniqueId()
      .then((uniqueID) => {
        console.log(uniqueID);
      })
      .catch((error) => {
        console.error(error);
      });

    // locationUpdateSubscription.remove();
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
