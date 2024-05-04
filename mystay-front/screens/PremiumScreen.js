import React from 'react';
import { View } from 'react-native';
import { StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText'

const PremiumScreen = () => {
  return (
    <View style={styles.container}>
      <TitleText text={"SERVICIO DE CAFETERÍA"} />
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Reserva en restaurantes"} style={styles.text} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Entradas de espectáculos"} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Guias turísticos"} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Compra de articulos"} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Concierge service"} />
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

export default PremiumScreen;
