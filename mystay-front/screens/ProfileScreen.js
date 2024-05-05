import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText } from '../components/CustomText'

import AuthContext from '../utils/AuthProvider';

const ProfileScreen = ({ navigation }) => {
  const { signOut } = React.useContext(AuthContext);
  
  return (
    <View style={styles.container}>
      <TitleText text={"MI PERFIL"} testID={"perfil-titulo"} />
      <View style={styles.buttonContainer}>
        <CustomButton testID={"perfil-btn"} icon={"pencil"} text={"Edita tus datos personales"} func={() => navigation.navigate('(profile)')} /> 
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton testID={"reservas-btn"} icon={"book"} text={"Consulta tus reservas"} func={() => navigation.navigate('(reserves)')} />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton icon={"star"} text={"Hazte premium"} func={() => navigation.navigate('(pay)', { title: 'HAZTE PREMIUM!', endpoint: 'cliente/premium'})} />
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
  }
});

export default ProfileScreen;
