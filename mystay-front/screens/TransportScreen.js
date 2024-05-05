import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText';

import { post, validJWT } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';

const TransportScreen = () => {
  
  const { signOut } = React.useContext(AuthContext);

  const postService = async (nombre, descripcion) => {
    const serviceData = {
      nombre,
      descripcion,
      fecha: new Date().toISOString()
    };

    try {
      await post("cliente/servicio", serviceData);
      alert(`Se ha pedido correctamente el servicio de ${nombre}`)
    } catch (error) {
      if (validJWT(error.response?.data, signOut)) {
        console.log(error)
        if (error.response?.data){
          alert(error.response?.data)
        } else {
          alert(error)
        }
      }
    }
  };
  
  return (
    <View style={styles.container}>
      <TitleText text={"TRANSPORTE"} />
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Taxi"} style={styles.text} testID={"taxi"} func={() => postService("taxi", "Recogerme en taxi de inmediato en mi localización")} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Transporte al aeropuerto"} testID={"aeropuerto"}  func={() => postService("recoger en aeropuerto", "Recogerme del aeropuerto de inmediato para ir al hotel")}/>
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