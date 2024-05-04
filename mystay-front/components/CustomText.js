import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { useFonts, Inter_900Black  } from '@expo-google-fonts/inter';

export const TitleText = ({ text, style, testID }) => {
    let [fontsLoaded] = useFonts({
        Inter_900Black,
      });
    
      if (!fontsLoaded) {
        return null;
      }
    return (
      <Text style={[styles.title, style]} testID={testID}>{ text }</Text>
    );
}

export const SubTitleText = ({ text, testID }) => {
  let [fontsLoaded] = useFonts({
    Inter_900Black,
  });

  if (!fontsLoaded) {
    return null;
  }

  return (
    <Text style={styles.subtitle} testID={testID}>{ text }</Text>
  );
}

const styles = StyleSheet.create({
    title: {
        fontSize: 40,
        marginBottom: 20,
        fontWeight: "bold",
        color: "#1d2b42",
        fontFamily: 'Inter_900Black',
    },
    subtitle: {
      fontSize: 17,
      marginBottom: 20,
      fontWeight: "bold",
      color: "#1d2b42",
      fontFamily: 'Inter_900Black',
  },    
})
