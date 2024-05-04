import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText'

import { get } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';


const CheckOutScreen = ({ navigation }) => {
  const { signOut } = React.useContext(AuthContext);

  const handleFactura = async () => {
    try {
      const factura = await get('cliente/factura');
      alert(`Pagado: ${factura}`)
    } catch (error) {
      if (validJWT(error.response?.data, signOut)) {
        console.log(error.response?.data)
        alert(error.response?.data)
      }
    }
  };

  return (
  <View style={styles.container}>
    <TitleText text={"CHECK-OUT"} />
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Pagar"} style={styles.text} func={() => navigation.navigate('(pay)')} /> 
    </View>
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Ver factura"} func={handleFactura} />
    </View>
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Anular llave"} />
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

export default CheckOutScreen;