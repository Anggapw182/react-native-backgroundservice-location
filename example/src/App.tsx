import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import { startLocationUpdates, stopLocationUpdates, getLocatino } from 'react-native-backgroundservice-location';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  // React.useEffect(() => {
  //   multiply(3, 7).then(setResult);
  // }, []);

  getLocatino();

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
