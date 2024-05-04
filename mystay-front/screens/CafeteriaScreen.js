import React from 'react';
import { View } from 'react-native';
import { StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText';

const CafeteriaScreen = () => {
  return (
    <View style={styles.container}>
      <TitleText text={"SERVICIO DE CAFETERÍA"} />
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Desayuno"} style={styles.text} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Comida"} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Cena"} />
      </View>
    </View>
  );
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonContainer: {
    marginTop: 20,
  },
});

export default CafeteriaScreen;
