import React, { useEffect } from 'react';
import { View, StyleSheet, TextInput } from 'react-native';
import CustomButton from '../components/CustomButton';
import { TitleText, SubTitleText } from '../components/CustomText'
import AuthContext from '../utils/AuthProvider';

import { post, validJWT } from '../utils/Requests';

import { useIsFocused } from '@react-navigation/native';


const PayScreen = ({ navigation, route }) => {
    const [bank, setBank] = React.useState('');
    const [cvv, setCvv] = React.useState('');

    const { signOut } = React.useContext(AuthContext)

    const { title, endpoint } = route.params;
    const isFocused = useIsFocused();

    
    const handlePayment = async (navigation) => {
      try {
        const response = await post(endpoint, {bank,cvv});
        alert(response)
        
        navigation.navigate("Profile")
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

  useEffect(()=> {
    if(isFocused){
      setBank('');
      setCvv('');
    }
  }, [isFocused]);


    return (
        <View style={styles.container}>
            <TitleText text={title} />
            <SubTitleText text="Introduce tus datos de pago" />
            <View style={styles.buttonContainer}>
                <TextInput
                    style={styles.input}
                    placeholder="Bank Account Number"
                    value={bank}
                    onChangeText={setBank}
                    testID='bank'
                />
            </View>
            <View style={styles.buttonContainer}>
                <TextInput
                    style={styles.input}
                    placeholder="CVV"
                    value={cvv}
                    onChangeText={setCvv}
                    testID='cvv'
                />
            </View>
            <View style={styles.buttonContainer}>
                <CustomButton icon={""} text={"Confirm"} func={() => handlePayment(navigation)} testID={"btn-pay"}/>
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
  input: {
    width: 260,
    height: 40,
    backgroundColor: '#dddddd',
    borderWidth: 1,
    borderColor: 'black',
    borderRadius: 5,
    paddingHorizontal: 10,
    marginTop: 10,
  },
  buttonContainer: {
    marginTop: 20,
  },
});

export default PayScreen;