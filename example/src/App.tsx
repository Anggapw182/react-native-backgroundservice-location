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
