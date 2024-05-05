import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, StyleSheet } from 'react-native';
import { useIsFocused } from "@react-navigation/native";

import CustomButton from '../components/CustomButton';
import AuthContext from '../utils/AuthProvider';
import { TitleText, SubTitleText } from '../components/CustomText'

import { get, put, validJWT } from '../utils/Requests';

const EditProfileScreen = ({ navigation }) => {
  const [nombre, setNombre] = useState('');
  const [telefono, setTelefono] = useState('');
  const [dni, setDni] = useState('');
  const [premium, setPremium] = useState('No premium');
  const [correo, setCorreo] = useState('');

  const { signOut } = React.useContext(AuthContext);

  const isFocused = useIsFocused();

  const handleSubmit = async (navigation) => {
    try {
        await put("cliente", { nombre, telefono, dni, correo, gasto: 0.0});
        alert('Datos actualizados');
        navigation.goBack();
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

  const getUserData = async () => {
    try {
      const user = await get("cliente");
      setNombre(user.nombre);
      setTelefono(user.telefono);
      setDni(user.dni);
      setPremium(user.premium ? 'Premium' : 'No premium');
      setCorreo(user.correo);
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

  useEffect(() => {
    if (isFocused){
      getUserData();
    }
  }, [isFocused]);

  return (
    <View style={styles.container}>
      <TitleText text={"MIS DATOS"} />
      <SubTitleText text={"Escribe tus datos personales y de contacto"} />
      <View style={styles.inputContainer}>
        <TextInput
          style={[styles.textInput, { marginRight: 10}]}
          placeholder="Nombre"r
          value={nombre}
          onChangeText={setNombre}
        />
        <TextInput
          style={styles.textInput}
          placeholder="Nº de Teléfono"
          value={telefono}
          onChangeText={setTelefono}
        />
      </View>
      <View style={styles.inputContainer}>
        <TextInput
          style={[styles.textInput, { marginRight: 10}]}
          placeholder="DNI"
          value={dni}
          onChangeText={setDni}
        />

        <Text style={styles.textInput}>{premium}</Text>
      </View>
      
      <View style={styles.emailContainer}>
        <TextInput style={styles.emailInput} placeholder="Email" value={correo} onChangeText={setCorreo} />
      </View>
      <CustomButton text={"Submit"} icon={""} func={() => handleSubmit(navigation)} />
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
  inputContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between', // space out the input fields
    margin: 10
  },
  textInput: {
    padding: 10,
    backgroundColor: '#e0e0e0',
    borderRadius: 5,
    flex: 1, // equally distribute space between the two inputs in this container
  },
  fulldniInput: {
    padding: 10,
    backgroundColor: '#e0e0e0',
    borderRadius: 5,
    marginBottom: 10,
    marginRight: 40
  },
  emailInput: {
    padding: 10,
    backgroundColor: '#e0e0e0',
    borderRadius: 5,
    flex: 2, // make the email input take twice the space of the ZIP input
  },
  emailContainer: {
    flexDirection: "row",
    margin: 10,
  }
});

export default EditProfileScreen;
