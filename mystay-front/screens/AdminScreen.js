import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText'

import AuthContext from '../utils/AuthProvider';


const AdminScreen = ({ navigation }) => {
  const { signOut } = React.useContext(AuthContext);
  return (
  <View style={styles.container}>
    <TitleText text={"ADMINISTRACION"} />
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Ver todos los clientes"} style={styles.text} func={() => navigation.push('Clientes')} />  
    </View>
    <View style={styles.buttonContainer}>
      <CustomButton icon={""} text={"Ver todos los empleados"} style={styles.text} func={() => navigation.push('Empleados')} />
    </View>
      <View style={styles.buttonContainer}>
        <CustomButton testID={"logout-btn"}  icon={""} text={"Logout"} func={signOut} />
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

export default AdminScreen;