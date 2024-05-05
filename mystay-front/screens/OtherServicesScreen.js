import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText'

import { get, validJWT } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';


const OtherServicesScreen = ({ navigation }) => {
  const { signOut } = React.useContext(AuthContext);

  const handleFactura = async () => {
    try {
      const factura = await get('cliente/factura');
      alert(`Pagado: ${factura}`)
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
    <TitleText text={"OTROS SERVICIOS"} />
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Cambio de habitacion"} style={styles.text} func={() => navigation.navigate('(changeroom)')} /> 
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

export default OtherServicesScreen;