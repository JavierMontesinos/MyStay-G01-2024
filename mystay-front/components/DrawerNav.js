import React from "react";
import { DrawerContentScrollView, DrawerItem, createDrawerNavigator } from '@react-navigation/drawer';
import TabNavigator from "./TabNav";

const Drawer = createDrawerNavigator();

const CustomDrawerNav = (props) => {
  return(
    <DrawerContentScrollView {...props}>
      <DrawerItem label="Check-out" onPress={() => props.navigation.navigate('(checkout)')} />
      <DrawerItem label="Nueva reserva" onPress={() => props.navigation.navigate('(reserve)')} />
      <DrawerItem label="Servicios confort" onPress={() => props.navigation.navigate('(comfort)')} />
      <DrawerItem label="Incidencias" onPress={() => props.navigation.navigate('(incidents)')} />
      <DrawerItem label="Otros servicios" onPress={() => props.navigation.navigate('(services)')} />
    </DrawerContentScrollView>
  );
}

const DrawerNav = () => {
    return (
      <Drawer.Navigator
        drawerContent={(props) => <CustomDrawerNav {...props} />}
        screenOptions={({ route }) => ({
            headerTitle: ""
          }
        )}
        
      >
        <Drawer.Screen name="(tabnav)" options={{ }} component={TabNavigator} />
      </Drawer.Navigator>
    );
  }

export default DrawerNav;