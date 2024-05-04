import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, Button } from 'react-native';
import { SubTitleText } from '../components/CustomText';
import CustomButton from '../components/CustomButton';

const QuestionnaireScreen = ({ navigation }) => {
  const [incidentText, setIncidentText] = useState('');

  const handleSubmit = () => {
    alert(`Incidencia enviada con éxito`);
    setIncidentText('');
  };

  return (
    <View style={styles.container}>
      <SubTitleText text={"Escribe tu incidencia:"} />
      <TextInput
        style={styles.input}
        multiline
        numberOfLines={4}
        value={incidentText}
        onChangeText={(text) => setIncidentText(text)}
      />
      <CustomButton text={"Enviar Incidencia"} func={handleSubmit} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    justifyContent: 'center',
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    marginTop: 10,
    marginBottom: 20,
    height: 100,
  },
});

export default QuestionnaireScreen;