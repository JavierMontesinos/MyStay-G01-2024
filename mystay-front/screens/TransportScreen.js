import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText';
import axios from 'axios';


const TransportScreen = () => {
  const postService = async (tipo, descripcion) => {
    try {
      // Create a new service object with the required data
      const serviceData = {
        tipo,
        descripcion,
        fecha: new Date().toISOString() // Get current date and time
      };
  
      // Send POST request to the server
      const response = await axios.post('http://192.168.1.139:8443/services/1', serviceData);
      alert(`Se ha pedido correctamente el servicio de ${tipo}`)
      console.log('Service created successfully:', response.data);
    } catch (error) {
      console.error('Error creating service:', error);
    }
  };
  


  return (
    <View style={styles.container}>
      <TitleText text={"TRANSPORTE"} />
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Taxi"} style={styles.text} func={() => postService("taxi", "Recogerme en taxi de inmediato en mi localización")} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Transporte al aeropuerto"}  func={() => postService("recoger en aeropuerto", "Recogerme del aeropuerto de inmediato para ir al hotel")}/>
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Billetes"} />
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
  text: {
    textAlign: 'center',
  }
});

export default TransportScreen;