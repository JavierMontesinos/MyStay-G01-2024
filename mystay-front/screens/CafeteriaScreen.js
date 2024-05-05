import React from 'react';
import { View } from 'react-native';
import { StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText';

import { post, validJWT } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';

const CafeteriaScreen = () => {
  const { signOut } = React.useContext(AuthContext);

  const postService = async (nombre, label) => {
    
    const serviceData = {
      nombre,
      descripcion: "Comida a la habitación",
      fecha: new Date().toISOString()
    };

    try {
      await post("cliente/servicio", serviceData);
      alert(`Se ha pedido correctamente ${label} a la habitación`)
    } catch (error) {
      if (validJWT(error.response?.data, signOut)) {
        console.log(error)
        if (error.response?.data){
          alert(error.response?.data)
        } else {
          alert(error)
        }
      }

      return false;
    }

  };

  return (
    <View style={styles.container}>
      <TitleText text={"SERVICIO DE CAFETERÍA"} />
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Desayuno"} style={styles.text} func={() => postService("Desayuno", 'el desayuno')} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Comida"} func={() => postService("Comida", 'la comida')} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={""} text={"Cena"} func={() => postService("Cena", 'la cena')}/>
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
